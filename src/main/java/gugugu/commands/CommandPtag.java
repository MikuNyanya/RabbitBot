package gugugu.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;
import gugugu.bots.BotRabbit;
import gugugu.constant.ConstantImage;
import gugugu.service.PixivService;
import utils.StringUtil;

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
        String timeCheck = timeCheck(sender);
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
        } catch (Exception ex) {
            BotRabbit.bot.getLogger().error(ConstantImage.PIXIV_TAG_GET_ERROR_GROUP_MESSAGE + ex.toString(), ex);
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

    /**
     * 操作间隔控制
     *
     * @return
     */
    private String timeCheck(User sender) {
        Long qq = sender.getInfo().getUserId();
        String name = sender.getInfo().getNickname();
        if(BotRabbit.MASTER_QQ.contains(qq)){
            return null;
        }
        if (!PIXIV_TAG_SPLIT_MAP.containsKey(qq)) {
            return null;
        }
        Long lastTime = PIXIV_TAG_SPLIT_MAP.get(qq);
        if (null == lastTime) {
            return null;
        }
        Long nowTime = System.currentTimeMillis();
        Long splitTime = nowTime - lastTime;
        //判断是否允许操作
        if (splitTime <= PIXIV_TAG_SPLIT_TIME) {
            return String.format(PIXIV_TAG_SPLIT_ERROR, name, (PIXIV_TAG_SPLIT_TIME - splitTime) / 1000);
        }
        //其他情况允许操作
        return null;
    }
}
