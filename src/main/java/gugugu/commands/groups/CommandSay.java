package gugugu.commands.groups;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import gugugu.constant.ConstantFreeTime;
import gugugu.filemanage.FileManager;
import utils.RandomUtil;

import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * <p>
 * 说些日常句子
 */
public class CommandSay implements GroupCommand {
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
        if (ConstantFreeTime.MSG_TYPE_FREE_TIME.size() <= 0) {
            return ConstantFreeTime.MSG_TYPE_FREE_TIME_EMPTY;
        }
        //从列表中删除获取的消息，实现伪随机，不然重复率太高了，体验比较差
        String msg = RandomUtil.rollAndDelStrFromList(ConstantFreeTime.MSG_TYPE_FREE_TIME);
        //删到五分之一时重新加载集合
        if (ConstantFreeTime.MSG_TYPE_FREE_TIME.size() < ConstantFreeTime.MSG_TYPE_FREE_TIME_MAX_SIZE / 5) {
            FileManager.loadFreeTime();
        }
        return msg;
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("Say", "say", "说话");
    }
}
