package gugugu.commands.groups;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import gugugu.bots.BotRabbit;
import gugugu.constant.ConstantConfig;
import gugugu.entity.GroupUserInfo;
import gugugu.service.NCoV_2019ReportService;

import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * <p>
 * nCoV疫情实时总况
 */
public class CommandnCov implements GroupCommand {
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
        //非管理员禁用该指令
        GroupUserInfo user = event.getGroupUserInfo();
        //判断权限
        if (!user.isAdmin()) {
            return ConstantConfig.COMMAND_ROLE_ADMIN;
        }

        String groupMsg = "";
        try {
            groupMsg = NCoV_2019ReportService.reportInfoNowWorld();
        } catch (Exception ex) {
            BotRabbit.bot.getLogger().error("nCoV疫情实时消息推送执行异常:" + ex.toString(), ex);
            groupMsg = "nCoV疫情信息系统，它挂了";
        }
        return groupMsg;
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("nCov", "ncov");
    }

}
