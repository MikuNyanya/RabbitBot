package gugugu.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;
import gugugu.bots.LoggerRabbit;
import gugugu.constant.ConstantCommon;
import gugugu.constant.ConstantImage;
import gugugu.service.PixivBugService;
import gugugu.service.PixivService;
import utils.NumberUtil;
import utils.StringUtil;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * <p>
 * 根据pixiv图片id搜索图片
 */
public class CommandPid implements EverywhereCommand {
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
        if (null == args || args.size() == 0) {
            return ConstantImage.PIXIV_IMAGE_ID_IS_EMPTY;
        }
        //基本输入校验
        String pid = args.get(0);
        if (StringUtil.isEmpty(pid)) {
            return ConstantImage.PIXIV_IMAGE_ID_IS_EMPTY;
        }
        if (!NumberUtil.isNumberOnly(pid)) {
            return ConstantImage.PIXIV_IMAGE_ID_IS_NUMBER_ONLY;
        }

        String result = "";
        try {
            //是否走爬虫
            String pixiv_config_use_api = ConstantCommon.common_config.get(ConstantImage.PIXIV_CONFIG_USE_API);
            if (ConstantImage.OFF.equalsIgnoreCase(pixiv_config_use_api)) {
                result = PixivBugService.searchPixivImgById(NumberUtil.toLong(pid));
            } else {
                result = PixivService.searchPixivImgById(NumberUtil.toLong(pid));
            }
        } catch (FileNotFoundException fileNotFoundEx) {
            LoggerRabbit.logger().warning(ConstantImage.PIXIV_IMAGE_DELETE + fileNotFoundEx.toString());
            return ConstantImage.PIXIV_IMAGE_DELETE;
        } catch (Exception ex) {
            LoggerRabbit.logger().error(ConstantImage.PIXIV_ID_GET_ERROR_GROUP_MESSAGE + ex.toString(), ex);
            result = ConstantImage.PIXIV_ID_GET_ERROR_GROUP_MESSAGE;
        }
        return result;
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("PixivImageId", "pid");
    }
}
