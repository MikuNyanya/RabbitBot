package gugugu.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import gugugu.bots.BotRabbit;

import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * <p>
 * 帮助
 */
public class CommandHelp implements GroupCommand {
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
        StringBuilder msg = new StringBuilder();
        msg.append("这里是[" + BotRabbit.BOT_NAME + "(RabbitBot)]，一个居无定所的群机器人\n");
        msg.append("===指令列表===\n");
        msg.append("[.r] 生成一个1-100的随机数\n");
        msg.append("[.roll] 在提供的几个参数里随机选择一个，输入该指令以查看详解\n");
        msg.append("[.rp] 查看今天的人品,每天的人品是固定的\n");
        msg.append("[.sj] 查看当前时间\n");
        msg.append("[.say] 主动触发一句日常语句\n");
        msg.append("[.addnj] 添加一个日常语句\n");
        msg.append("[.cls] 群清屏\n");
        msg.append("[.ab] 数字游戏，输入该指令以查看详解\n");
        msg.append("[.rb] 数字游戏，输入该指令以查看详解\n");
        msg.append("[.addkwl] 添加一个关键词回复（模糊），输入该指令以查看详解\n");
        msg.append("[.addkwn] 添加一个关键词回复（全匹配），输入该指令以查看详解\n");
        msg.append("[.扭蛋] 就是扭蛋\n");
        msg.append("[.扭蛋 add] 新添加一个扭蛋\n");
        msg.append("[.wbn (on,off,lasttag,token,exec)] 微博消息推送指令，需要群管理权限\n");
        msg.append("=============\n");
        return msg.toString();
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("Help", "help");
    }
}
