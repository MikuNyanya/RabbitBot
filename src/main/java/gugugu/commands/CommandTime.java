package gugugu.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;
import gugugu.bots.BotRabbit;
import utils.DateUtil;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * <p>
 * 当前时间
 */
public class CommandTime implements EverywhereCommand {
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
        return String.format("%s报时：%s", BotRabbit.BOT_NAME, DateUtil.toString(new Date()));
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("Time Now", "time", "sj", "时间");
    }
}
