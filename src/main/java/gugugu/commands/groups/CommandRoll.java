package gugugu.commands.groups;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import gugugu.entity.InfoGroupUser;
import utils.RandomUtil;

import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * <p>
 * 随机数指令
 */
public class CommandRoll implements GroupCommand {
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
        //随机数 0 - 100 包含0
        int rollNum = RandomUtil.roll();
        //实际不需要0，排除掉
        if (0 == rollNum) {
            rollNum = 1;
        }

        //群员信息
        InfoGroupUser groupUserInfo = event.getGroupUserInfo();
        //附加指令
        String commandParam = "";
        if (null != args && args.size() > 0) {
            commandParam = String.format("为[%s]", args.get(0));
        }

        //【群员名称】 装饰性语句 "roll="随机数
        return String.format("[%s]%s roll=%s", groupUserInfo.getGroupUserName(), commandParam, rollNum);
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("roll", "r");
    }
}
