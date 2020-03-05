package gugugu.commands.groups;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import gugugu.entity.GroupUserInfo;
import utils.RandomUtil;

import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * <p>
 * 随机选择一个事物
 */
public class CommandRollArgs implements GroupCommand {
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
        if (null == args || args.size() <= 0) {
            return "请输入要roll的参数，格式：[.roll 事物1 事物2 ...]";
        }
        if (args.size() == 1) {
            return "哈？就一个东西你让我roll啥";
        }

        //随机出一个结果
        int rollNum = RandomUtil.roll(args.size() - 1);

        //群员信息
        GroupUserInfo groupUserInfo = event.getGroupUserInfo();

        return String.format("为[%s]做出选择：roll=%s", groupUserInfo.getGroupUserName(), args.get(rollNum));
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("rollArgs", "roll");
    }
}
