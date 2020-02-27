package gugugu.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;

import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * <p>
 * 清屏指令
 */
public class CommandCls implements EverywhereCommand {
    private static String clsMessage = null;

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
        if (clsMessage == null) {
            clsMessage = "";
            for (int i = 0; i < 20; i++) clsMessage += "\n";
            clsMessage += "已清屏!";
        }

        return clsMessage;
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("clear", "cls", "CLS", "清屏");
    }
}
