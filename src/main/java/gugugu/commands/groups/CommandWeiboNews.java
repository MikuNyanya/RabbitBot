package gugugu.commands.groups;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import gugugu.bots.BotRabbit;
import gugugu.constant.ConstantCommon;
import gugugu.constant.ConstantFile;
import gugugu.constant.ConstantWeiboNews;
import gugugu.entity.GroupUserInfo;
import gugugu.filemanage.FileManagerConfig;
import gugugu.service.WeiboNewsService;
import utils.NumberUtil;
import utils.StringUtil;

import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * <p>
 * 微博消息
 */
public class CommandWeiboNews implements GroupCommand {
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
        if (!user.isAdmin()) {
            return ConstantWeiboNews.COMMAND_ROLE_ADMIN;
        }

        if (null == args || args.size() == 0) {
            return "";
        }

        //二级指令
        String arg = args.get(0);
        switch (arg) {
            case ConstantWeiboNews.ON:
                //开启微博消息推送
                //检查有无授权码
                if (!ConstantCommon.common_config.containsKey("weiboToken")) {
                    return ConstantWeiboNews.NO_ACCESSTOKEN;
                }
                //修改配置
                ConstantCommon.common_config.put("weiboNewStatus", "1");
                //更新配置文件
                FileManagerConfig.doCommand(ConstantFile.FILE_COMMAND_WRITE);
                return ConstantWeiboNews.OPEN_SUCCESS;
            case ConstantWeiboNews.OFF:
                //关闭微博消息推送
                //修改配置
                ConstantCommon.common_config.put("weiboNewStatus", "0");
                //更新配置文件
                FileManagerConfig.doCommand(ConstantFile.FILE_COMMAND_WRITE);
                return ConstantWeiboNews.OFF_SUCCESS;
            case ConstantWeiboNews.ACCESS_TOKEN:
                //从外部接受接口授权码
                if (args.size() < 2 || StringUtil.isEmpty(args.get(1))) {
                    return ConstantWeiboNews.ACCESS_TOKEN_OVERRIDE_FAIL;
                }
                //加入配置文件
                ConstantCommon.common_config.put("weiboToken", args.get(1));
                //更新配置文件
                FileManagerConfig.doCommand(ConstantFile.FILE_COMMAND_WRITE);
                return ConstantWeiboNews.ACCESS_TOKEN_OVERRIDE_SUCCESS;
            case ConstantWeiboNews.SINCEID:
                //从外部接受sinceId
                if (args.size() < 2 || StringUtil.isEmpty(args.get(1))) {
                    return ConstantWeiboNews.SINCEID_OVERRIDE_FAIL;
                }
                String sinceIdStr = args.get(1);
                if (!NumberUtil.isNumberOnly(sinceIdStr)) {
                    return ConstantWeiboNews.SINCEID_OVERRIDE_FAIL_NOW_NUMBER;
                }
                //覆写SINCEID配置
                ConstantCommon.common_config.put("sinceId", sinceIdStr);
                //更新配置文件
                FileManagerConfig.doCommand(ConstantFile.FILE_COMMAND_WRITE);
                return ConstantWeiboNews.SINCEID_OVERRIDE_SUCCESS;
            case ConstantWeiboNews.EXEC:
                //立刻执行一次推送
                return doWeiboPush();
        }
        return "";
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("WeiboNews", "wbn");
    }

    //执行一次微博消息推送
    private String doWeiboPush() {
        //检查有无授权码
        if (!ConstantCommon.common_config.containsKey("weiboToken")) {
            return ConstantWeiboNews.NO_ACCESSTOKEN;
        }
        try {
            WeiboNewsService.doPushWeiboNews();
        } catch (Exception ex) {
            BotRabbit.bot.getLogger().error("主动微博消息推送执行异常:" + ex.toString(), ex);
            return ConstantWeiboNews.EXEC_ERROR;
        }
        return "";
    }
}
