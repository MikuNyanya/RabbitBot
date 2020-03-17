package gugugu.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;
import gugugu.bots.BotRabbit;
import gugugu.constant.ConstantHitokoto;
import gugugu.service.HitokotoService;
import gugugu.service.RabbitBotService;
import utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MikuLink
 * @date 2020/03/16 15:33
 * for the Reisen
 * <p>
 * 触发一次一言
 * 这玩意太菜了，不用了，把数据慢慢爬下来筛选了加到日常兔叽里
 */
@Deprecated
public class CommandHitokoto implements EverywhereCommand {
    //操作间隔 账号，操作时间戳
    private static Map<Long, Long> HITOKOTO_SPLIT_MAP = new HashMap<>();
    //操作间隔
    private static final Long HITOKOTO_SPLIT_TIME = 1000L * 60;
    private static final String HITOKOTO_SPLIT_ERROR = "[%s]%s秒后可以使用一言";

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
        String timeCheck = RabbitBotService.commandTimeSplitCheck(HITOKOTO_SPLIT_MAP, sender.getInfo().getUserId(), sender.getInfo().getNickname(),
                HITOKOTO_SPLIT_TIME, HITOKOTO_SPLIT_ERROR);
        if (StringUtil.isNotEmpty(timeCheck)) {
            return timeCheck;
        }
        try {
            return HitokotoService.getHitokoto();
        } catch (Exception ex) {
            BotRabbit.bot.getLogger().error(ConstantHitokoto.HITOKOTO_ERROR + ex.toString(), ex);
            return ConstantHitokoto.HITOKOTO_ERROR;
        }
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("Hitokoto", "一言");
    }
}
