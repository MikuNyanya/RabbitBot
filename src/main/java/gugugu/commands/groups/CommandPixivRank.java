package gugugu.commands.groups;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import gugugu.bots.BotRabbit;
import gugugu.constant.ConstantCommon;
import gugugu.constant.ConstantImage;
import gugugu.constant.ConstantWeiboNews;
import gugugu.entity.GroupUserInfo;
import gugugu.entity.pixiv.PixivRankImageInfo;
import gugugu.service.PixivBugService;
import gugugu.service.PixivService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MikuLink
 * @date 2020/02/19 16:10
 * for the Reisen
 * <p>
 * P站日榜
 */
public class CommandPixivRank implements GroupCommand {
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
        //非群主禁用该指令
        GroupUserInfo user = event.getGroupUserInfo();
        if (!user.isOwner() && !user.isMaster()) {
            return ConstantWeiboNews.COMMAND_ROLE_ADMIN;
        }

        try {
            //获取日榜
            List<PixivRankImageInfo> imageList = null;
            //是否走爬虫
            String pixiv_config_use_api = ConstantCommon.common_config.get(ConstantImage.PIXIV_CONFIG_USE_API);
            if (ConstantImage.OFF.equalsIgnoreCase(pixiv_config_use_api)) {
                imageList = PixivBugService.getPixivIllustRank(ConstantImage.PIXIV_IMAGE_PAGESIZE);
            } else {
                imageList = PixivService.getPixivIllustRank(1, ConstantImage.PIXIV_IMAGE_PAGESIZE);
            }
            for (PixivRankImageInfo imageInfo : imageList) {
                //拼接一个发送一个，中间间隔5秒
                String resultStr = PixivService.parsePixivImgInfoToGroupMsg(imageInfo);
                event.getHttpApi().sendGroupMsg(event.getGroupId(), resultStr);
                Thread.sleep(1000L * 5);
            }
        } catch (Exception ex) {
            BotRabbit.bot.getLogger().error(ConstantImage.PIXIV_IMAGE_RANK_ERROR + ex.toString(), ex);
            return ConstantImage.PIXIV_IMAGE_RANK_ERROR;
        }
        return "";
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("PixivRank", "pixivrank", "prank");
    }
}
