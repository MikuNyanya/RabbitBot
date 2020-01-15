package gugugu.listeners;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import gugugu.bots.BotRabbit;
import gugugu.constant.*;
import gugugu.filemanage.FileManagerKeyWordLike;
import gugugu.filemanage.FileManagerKeyWordNormal;
import gugugu.service.ImageService;
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
    //保存群最后一条消息，用于复读
    private static Map<Long, String[]> LAST_MSG_MAP = new HashMap<>();

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

            //每次只会触发一个回复
            //ABABA 句式检索
            boolean groupRep = groupABABA(event);
            if (groupRep) {
                return;
            }

            //关键词全匹配
            groupRep = groupKeyWord(event);
            if (groupRep) {
                return;
            }

            //关键词匹配(模糊)
            groupRep = groupKeyWordLike(event);
            if (groupRep) {
                return;
            }

            //群复读
            groupRep = groupRepeater(event);
            if (groupRep) {
                return;
            }
        } catch (Exception ex) {
            BotRabbit.bot.getLogger().error(String.format("群消息业务处理异常，groupId:%s,msg:%s", event.getGroupId(), event.getMessage()), ex);
        }
    }

    /**
     * 群复读
     *
     * @param event 群消息监控
     * @return bol值 表示有没有进行群复读
     */
    private boolean groupRepeater(EventGroupMessage event) {
        //接收到的群消息
        String groupMsg = event.getMessage();
        Long groupId = event.getGroupId();

        //第一次消息初始化
        if (!LAST_MSG_MAP.containsKey(groupId)) {
            LAST_MSG_MAP.put(groupId, new String[2]);
        }

        String[] msgs = LAST_MSG_MAP.get(groupId);
        //群复读，三个相同的消息，复读一次，并重置计数
        if ((StringUtil.isEmpty(msgs[0]) || StringUtil.isEmpty(msgs[1]))
                || !(msgs[0].equals(msgs[1]) && msgs[0].equals(groupMsg))) {
            //刷新消息列表
            msgs[1] = msgs[0];
            msgs[0] = groupMsg;
            LAST_MSG_MAP.put(groupId, msgs);
            return false;
        }

        //概率打断复读，100%对复读打断复读的语句做出反应
        if (ConstantRepeater.REPEATER_KILLER_LIST.contains(groupMsg) || ConstantRepeater.REPEATER_STOP_LIST.contains(groupMsg)) {
            //打断复读的复读
            groupMsg = RandomUtil.rollStrFromList(ConstantRepeater.REPEATER_STOP_LIST);
        } else if (RandomUtil.rollBoolean(-80)) {
            //打断复读
            groupMsg = RandomUtil.rollStrFromList(ConstantRepeater.REPEATER_KILLER_LIST);
        }

        //正常复读
        event.getHttpApi().sendGroupMsg(event.groupId, groupMsg);
        //复读一次后，重置复读计数
        LAST_MSG_MAP.put(groupId, new String[2]);
        return true;
    }

    /**
     * 跟随回复ABABA句式
     *
     * @param event 群消息监控
     * @return bol值 表示有没有进行群消息回复
     */
    private boolean groupABABA(EventGroupMessage event) {
        //接收到的群消息
        String groupMsg = event.getMessage();

        //检查句式
        if (!StringUtil.isABABA(groupMsg)) {
            return false;
        }
        String msg = RandomUtil.rollStrFromList(ConstantFreeTime.MSG_TYPE_ABABA);
        //回复群消息
        event.getHttpApi().sendGroupMsg(event.groupId, msg);
        return true;
    }

    /**
     * 群消息关键词检测
     *
     * @param event 群消息监控
     * @return bol值 表示有没有进行群消息回复
     */
    private boolean groupKeyWord(EventGroupMessage event) {
        String groupMsg = event.getMessage();

        //全匹配关键词
        String mapKey = FileManagerKeyWordNormal.keyWordNormalRegex(groupMsg);
        if (StringUtil.isEmpty(mapKey)) {
            return false;
        }

        //随机选择回复
        String msg = RandomUtil.rollStrFromList(ConstantKeyWord.key_wrod_normal.get(mapKey));
        //回复群消息
        event.getHttpApi().sendGroupMsg(event.groupId, msg);
        return true;

    }

    /**
     * 群消息关键词检测(模糊)
     *
     * @param event 群消息监控
     * @return bol值 表示有没有进行群消息回复
     */
    private boolean groupKeyWordLike(EventGroupMessage event) throws IOException {
        String groupMsg = event.getMessage();

        //检测图片关键词
        if (groupMsg.contains("咕") || groupMsg.contains("鸽")) {
            String gugugImageCQ = ImageService.getGuguguRandom();
            //回复群消息
            event.getHttpApi().sendGroupMsg(event.groupId, gugugImageCQ);
            return true;
        }


        //检测模糊关键词
        String mapKey = FileManagerKeyWordLike.keyWordLikeRegex(groupMsg);
        if (StringUtil.isEmpty(mapKey)) {
            return false;
        }

        //随机选择回复
        String msg = RandomUtil.rollStrFromList(ConstantKeyWord.key_wrod_like.get(mapKey));
        //回复群消息
        event.getHttpApi().sendGroupMsg(event.groupId, msg);
        return true;
    }
}
