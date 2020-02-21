package gugugu.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RVersionInfo;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import cc.moecraft.icq.user.User;
import gugugu.bots.BotRabbit;

import java.util.ArrayList;

import static cc.moecraft.utils.StringUtils.capitalizeFirstLetterOfEachWord;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * <p>
 * 系统简述
 */
public class CommandSystem implements EverywhereCommand {
    /**
     * 执行指令
     *
     * @param event   事件
     * @param sender  发送者的用户
     * @param command 指令名 ( 不包含指令参数 )
     * @param args    指令参数 ( 不包含指令名 )
     * @return 发送回去的消息 ( 当然也可以手动发送然后返回空 )
     */
    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args) {
        RVersionInfo versionInfo = event.getHttpApi().getVersionInfo().getData();

        StringBuilder msg = new StringBuilder();
        msg.append("==========\n");
        msg.append("[Name] " + BotRabbit.BOT_NAME + "(RabbitBot)\n");
        msg.append("[Birthday] 2019-12-3\n");
        msg.append("[Version] V2.0\n");
        msg.append("[酷Q] " + capitalizeFirstLetterOfEachWord(versionInfo.getCoolqEdition()) + "\n");
        msg.append("[HTTP API] " + versionInfo.getPluginVersion() + "\n");
        msg.append("System Online\n");
        msg.append("\\兔叽万岁/\n");
        msg.append("==========");

        return msg.toString();
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("System", "system");
    }
}
