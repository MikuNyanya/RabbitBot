package gugugu.listeners;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import gugugu.bots.LoggerRabbit;
import gugugu.constant.ConstantBlackList;
import gugugu.constant.ConstantCommon;
import gugugu.service.KeyWordService;
import utils.StringUtil;

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
            LoggerRabbit.logger().error(String.format("群消息业务处理异常，groupId:%s,msg:%s，%s", event.getGroupId(), event.getMessage(), ex.toString()), ex);
        }
    }
}
