package gugugu.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import gugugu.constant.ConstantCommon;
import utils.RandomUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * <p>
 * 随机数指令
 */
public class CommandRP implements GroupCommand {
    public static Map<Long, Integer> MAP_RP = new HashMap<>();

    /**
     * 执行指令
     *
     * @param event   事件
     * @param sender  发送者的用户
     * @param group   群
     * @param command 指令名 ( 不包含指令参数 )
     * @param args    指令参数 ( 不包含指令名 )
     * @return 发送回去的消息 ( 当然也可以手动发送然后返回空 )
     */
    @Override
    public String groupMessage(EventGroupMessage event, GroupUser sender, Group group, String command, ArrayList<String> args) {
        //获取群员Q号
        Long groupUserId = event.getGroupUserInfo().getUserId();

        //rp
        int rollNum = 0;
        if (MAP_RP.containsKey(groupUserId)) {
            rollNum = MAP_RP.get(groupUserId);
        } else {
            rollNum = RandomUtil.roll();
            MAP_RP.put(groupUserId, rollNum);
        }

        //可以随机点装饰性语句
        String msgEx = getMsgEx(rollNum);
        return String.format("【%s】今天的人品值：%s%s", event.getGroupUserInfo().getGroupUserName(), rollNum, msgEx);
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("rp", "rp", "RP", "人品", "今日人品", "今日rp");
    }

    //获取附加短语，可以放一些彩蛋性质的东西，会附带在报时消息尾部
    private String getMsgEx(int rollNum) {
        if (rollNum <= 0) {
            return ConstantCommon.NEXT_LINE + "这人可真是惨到家了...";
        } else if (rollNum == 9) {
            return ConstantCommon.NEXT_LINE + "baka~";
        } else if (rollNum == 100) {
            return ConstantCommon.NEXT_LINE + "恭喜恭喜";
        }
        return "";
    }
}
