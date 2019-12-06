package gugugu.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import gugugu.constant.ConstantFreeTime;
import gugugu.filemanage.FileManager;
import utils.StringUtil;

import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * <p>
 * 添加日常句子
 */
public class CommandAddFreeTime implements GroupCommand {
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
            return ConstantFreeTime.EXPLAIN;
        }

        //拼接语句，并对空格进行处理(保留空格，但是当前系统会吧空格作为分隔参数的依据)
        StringBuilder nichijouStr = new StringBuilder();
        for (String str : args) {
            if (StringUtil.isEmpty(str)) continue;
            nichijouStr.append(" ");
            nichijouStr.append(str);
            //长度校验
            if (nichijouStr.length() > 100) {
                return ConstantFreeTime.MSG_ADD_OVER_LENGTH;
            }
        }
        //写入文件并添加到list
        FileManager.addFreeTimes(nichijouStr.substring(1));

        return ConstantFreeTime.MSG_ADD_SUCCESS;
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("AddNichijou", "addnj", "添加日常话语");
    }
}
