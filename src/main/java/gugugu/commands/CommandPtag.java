package gugugu.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;
import gugugu.bots.LoggerRabbit;
import gugugu.constant.ConstantImage;
import gugugu.service.PixivService;
import gugugu.service.RabbitBotService;
import utils.StringUtil;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * <p>
 * 根据pixiv图片tag随机搜索图片
 */
public class CommandPtag implements EverywhereCommand {
    //操作间隔 账号，操作时间戳
    public static Map<Long, Long> PIXIV_TAG_SPLIT_MAP = new HashMap<>();
    //操作间隔
    public static final Long PIXIV_TAG_SPLIT_TIME = 1000L * 60;
    public static final String PIXIV_TAG_SPLIT_ERROR = "[%s]%s秒后可以使用tag搜索";

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
        //操作间隔判断
        String timeCheck = RabbitBotService.commandTimeSplitCheck(PIXIV_TAG_SPLIT_MAP, sender.getInfo().getUserId(), sender.getInfo().getNickname(), PIXIV_TAG_SPLIT_TIME, PIXIV_TAG_SPLIT_ERROR);
        if (StringUtil.isNotEmpty(timeCheck)) {
            return timeCheck;
        }
        //刷新操作间隔
        PIXIV_TAG_SPLIT_MAP.put(sender.getId(), System.currentTimeMillis());

        if (null == args || args.size() == 0) {
            return ConstantImage.PIXIV_IMAGE_TAG_IS_EMPTY;
        }
        //基本输入校验
        StringBuilder tagSB = new StringBuilder();
        for (String param : args) {
            tagSB.append(" ");
            tagSB.append(param);
        }
        String tag = StringUtil.trim(tagSB.toString());
        if (StringUtil.isEmpty(tag)) {
            return ConstantImage.PIXIV_IMAGE_TAG_IS_EMPTY;
        }

        String result = "";
        try {
            result = PixivService.getPixivIllustByTag(tag);
        } catch (SocketTimeoutException stockTimeoutEx) {
            LoggerRabbit.logger().warning(ConstantImage.PIXIV_IMAGE_TIMEOUT + stockTimeoutEx.toString());
            return ConstantImage.PIXIV_IMAGE_TIMEOUT;
        } catch (Exception ex) {
            LoggerRabbit.logger().error(ConstantImage.PIXIV_TAG_GET_ERROR_GROUP_MESSAGE + ex.toString(), ex);
            result = ConstantImage.PIXIV_TAG_GET_ERROR_GROUP_MESSAGE;
            //异常后清除间隔允许再次操作
            PIXIV_TAG_SPLIT_MAP.remove(sender.getId());
        }
        return result;
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("PixivImageTag", "ptag");
    }
}
