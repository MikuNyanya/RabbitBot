package gugugu.listeners;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import gugugu.bots.BotRabbit;
import gugugu.constant.*;
import gugugu.filemanage.FileManagerKeyWordLike;
import gugugu.filemanage.FileManagerKeyWordNormal;
import gugugu.service.ImageService;
import gugugu.service.KeyWordService;
import utils.RandomUtil;
import utils.StringUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * 所有群消息监听
 */
public class GroupListener extends IcqListener {

    @EventHandler
    public void onPMEvent(EventGroupMessage event) {
        try {
            //黑名单
            Long senderId = event.getSenderId();
            if (ConstantBlackList.BLACK_LIST.contains(senderId)) {
                return;
            }

            //过滤掉消息为空的
            if (StringUtil.isEmpty(event.getMessage())) {
                return;
            }

            //屏蔽正常指令
            if (ConstantCommon.COMMAND_INDEX.equalsIgnoreCase(event.getMessage().substring(0, 1))) {
                return;
            }

            //所有关键词匹配业务
            KeyWordService.getService().keyWordMatchGroup(event);
        } catch (Exception ex) {
            BotRabbit.bot.getLogger().error(String.format("群消息业务处理异常，groupId:%s,msg:%s", event.getGroupId(), event.getMessage()), ex);
        }
    }
}
