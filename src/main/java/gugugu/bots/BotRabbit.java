package gugugu.bots;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.PicqConfig;
import cc.moecraft.icq.command.interfaces.IcqCommand;
import cc.moecraft.icq.event.IcqListener;
import gugugu.commands.*;
import gugugu.commands.groups.*;
import gugugu.listeners.GroupListener;
import gugugu.listeners.GroupMemberDecreaseListener;
import gugugu.listeners.GroupMemberIncreaseListener;
import gugugu.listeners.SimpleTextLoggingListener;

import java.util.Arrays;
import java.util.List;

/**
 * create by MikuLink on 2019/12/3 13:14
 * for the Reisen
 * <p>
 * 兔叽/机
 */
public class BotRabbit {
    //机器人名称
    public static final String BOT_NAME = "兔叽";
    //机器人名称
    public static final String BOT_NAME_EN = "RabbitBot";
    //qq号
    public static final Long BOT_QQ = 1020992834L;
    //主(人)账号
    public static final List<Long> MASTER_QQ = Arrays.asList(455806936L);
    //picq端口
    public static final int SOCKET_PORT = 31033;
    //酷Q端口
    private static final int POST_PORT = 31032;
    //酷Q地址
    private static final String POST_URL = "127.0.0.1";
    //代理地址
    public static final String PROXY_ADDRESS = "127.0.0.1";
    //代理端口
    public static final int PROXY_PROT = 31051;

    //机器人对象，方便别的地方引用
    public static PicqBotX bot;


    /**
     * 启动兔机
     */
    public static void start() {
        // 创建机器人对象 ( 传入配置 )
        bot = new PicqBotX(new PicqConfig(SOCKET_PORT)
                .setDebug(true)
//                .setSecret("This is secret")
//                .setAccessToken("Brq4KSm+3UdaUJnLZ+AJfj**v-vePWL$")
        );

        // 添加一个机器人账户 ( 名字, 发送URL, 发送端口 )
        bot.addAccount(BOT_NAME, POST_URL, POST_PORT);
//        bot.addAccount("Bot01", "127.0.0.1", 31091);

        // 启用HyExp ( 非必要 )
//        bot.setUniversalHyExpSupport(true);

        // 设置异步
        bot.getConfig().setUseAsyncCommands(true);

        // 注册事件监听器, 可以注册多个监听器
        bot.getEventManager().registerListeners(listeners);

        // 在没有Debug的时候加上这个消息日志监听器
        if (!bot.getConfig().isDebug())
            bot.getEventManager().registerListener(new SimpleTextLoggingListener());

        // 注册指令
        // 从 v3.0.1.730 之后不会自动注册指令了, 因为效率太低 (≈4000ms), 而且在其他框架上有Bug
        bot.getCommandManager().registerCommands(commands);

        // Debug输出所有已注册的指令
        LoggerRabbit.logger().debug("已注册指令：" + bot.getCommandManager().getCommands().toString());

        // 启动机器人, 不会占用主线程
        bot.startBot();
    }

    /**
     * 要注册的指令
     */
    private static IcqCommand[] commands = new IcqCommand[]{
            new CommandHelp(),
            new CommandSystem(),
            new CommandCls(),
            new CommandRoll(),
            new CommandRollArgs(),
            new CommandTime(),
            new CommandRP(),
            new CommandRollBomb(),
            new CommandABomb(),
            new CommandSay(),
            new CommandAddFreeTime(),
            new CommandAddKeyWordNormal(),
            new CommandAddKeyWordLike(),
            new CommandCapsuleToy(),
            new CommandWeiboNews(),
            new CommandWeather(),
            new CommandConfig(),
            new CommandnCov(),
            new CommandImageSearch(),
            new CommandPid(),
            new CommandPixivRank(),
            new CommandPtag(),
            new CommandAnimeSearch(),
            new CommandMorseCode(),
            new CommandPtags(),
            new CommandQRCode(),
            new CommandPUserIllust(),
            new CommandPUsers(),
            new CommandPwd()
    };

    /**
     * 要注册的监听器
     */
    private static IcqListener[] listeners = new IcqListener[]{
            new GroupListener(),
            new GroupMemberIncreaseListener(),
            new GroupMemberDecreaseListener()
    };
}
