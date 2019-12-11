package gugugu.listeners;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import gugugu.constant.*;
import utils.RandomUtil;
import utils.RegexUtil;
import utils.StringUtil;

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
    private static Map<Long, String> LAST_MSG_MAP = new HashMap<>();

    @EventHandler
    public void onPMEvent(EventGroupMessage event) {
        //过滤掉消息为空的
        if (StringUtil.isEmpty(event.getMessage())) {
            return;
        }

        //黑名单
        Long senderId = event.getSenderId();
        if (ConstantBlackList.BLACK_LIST.contains(senderId)) {
            return;
        }

        //每次只会触发一个回复
        //群复读
        boolean groupRep = groupRepeater(event);
        if (groupRep) {
            return;
        }

        //ABABA 句式检索
        groupRep = groupABABA(event);
        if (groupRep) {
            return;
        }

        //关键词全匹配
        groupRep = groupKeyWord(event);
        if (groupRep) {
            return;
        }

        //关键词匹配(模糊)
        groupKeyWordLike(event);
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
            LAST_MSG_MAP.put(groupId, "");
        }

        //屏蔽正常指令
        if (ConstantCommon.COMMAND_INDEX.equalsIgnoreCase(groupMsg.substring(0, 1))) {
            return false;
        }

        //群复读，两个相同的消息，复读一次，并重置计数
        if (LAST_MSG_MAP.get(groupId).equals(groupMsg)) {
            if (ConstantRepeater.REPEATER_KILLER_LIST.contains(groupMsg) || ConstantRepeater.REPEATER_STOP_LIST.contains(groupMsg)) {
                //打断
                groupMsg = RandomUtil.rollStrFromList(ConstantRepeater.REPEATER_STOP_LIST);
            } else if (RandomUtil.rollBoolean(-80)) {
                //打断复读
                groupMsg = RandomUtil.rollStrFromList(ConstantRepeater.REPEATER_KILLER_LIST);
            }
            event.getHttpApi().sendGroupMsg(event.groupId, groupMsg);
            LAST_MSG_MAP.put(groupId, "");
            return true;
        }
        //覆盖最后消息
        LAST_MSG_MAP.put(groupId, groupMsg);
        return false;
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

        //循环mapkey，找到包含关键词的key，然后拆分key确认是否全匹配，如果不是继续循环到下一个key
        for (String keyRegex : ConstantKeyWord.key_wrod_normal.keySet()) {
            //正则匹配
            boolean regex = false;
            for (String oneKey : keyRegex.split("\\|")) {
                if (RegexUtil.regex(groupMsg, "^" + oneKey + "$")) {
                    regex = true;
                    break;
                }
            }

            if (!regex) {
                continue;
            }
            //随机选择回复
            String msg = RandomUtil.rollStrFromList(ConstantKeyWord.key_wrod_normal.get(keyRegex));
            //回复群消息
            event.getHttpApi().sendGroupMsg(event.groupId, msg);
            return true;
        }
        return false;
    }

    /**
     * 群消息关键词检测(模糊)
     *
     * @param event 群消息监控
     * @return bol值 表示有没有进行群消息回复
     */
    private boolean groupKeyWordLike(EventGroupMessage event) {
        String groupMsg = event.getMessage();

        //循环mapkey，找到包含关键词的key，然后拆分key确认是否全匹配，如果不是继续循环到下一个key
        for (String keyRegex : ConstantKeyWord.key_wrod_like.keySet()) {
            //正则匹配
            boolean isRegex = false;
            for (String keyWords : keyRegex.split("\\|")) {
                //拼接正则
                String regex = getKeyWordLikeRegex(keyWords);

                //进行正则匹配
                if (RegexUtil.regex(groupMsg, regex)) {
                    isRegex = true;
                    break;
                }
            }

            if (!isRegex) {
                continue;
            }
            //随机选择回复
            String msg = RandomUtil.rollStrFromList(ConstantKeyWord.key_wrod_like.get(keyRegex));
            //回复群消息
            event.getHttpApi().sendGroupMsg(event.groupId, msg);
            return true;
        }
        return false;
    }

    /**
     * 解析模糊关键词，组成正则
     *
     * @param keyWords 模糊匹配关键词原始字符串
     * @return 模糊匹配正则表达式
     */
    private String getKeyWordLikeRegex(String keyWords) {
        StringBuilder regex = new StringBuilder();
        boolean isFirst = true;
        for (String key : keyWords.split("&")) {
            if (isFirst) {
                regex.append(key);
                isFirst = false;
                continue;
            }
            regex.append("\\S*");
            regex.append(key);
        }
        return regex.toString();
    }
}
