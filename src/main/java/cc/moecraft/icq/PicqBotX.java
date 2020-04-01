package cc.moecraft.icq;

import cc.moecraft.icq.accounts.AccountManager;
import cc.moecraft.icq.accounts.AccountManagerListener;
import cc.moecraft.icq.accounts.BotAccount;
import cc.moecraft.icq.command.CommandListener;
import cc.moecraft.icq.command.CommandManager;
import cc.moecraft.icq.event.EventManager;
import cc.moecraft.icq.exceptions.VerifyFailedException;
import cc.moecraft.icq.listeners.HyExpressionListener;
import cc.moecraft.icq.receiver.PicqHttpServer;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RVersionInfo;
import cc.moecraft.icq.user.GroupManager;
import cc.moecraft.icq.user.GroupUserManager;
import cc.moecraft.icq.user.UserManager;
import utils.MiscUtils;
import cc.moecraft.logger.format.AnsiColor;
import cc.moecraft.utils.HyExpressionResolver;
import cn.hutool.http.HttpException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gugugu.bots.LoggerRabbit;
import gugugu.constant.ConstantFile;
import gugugu.filemanage.*;
import gugugu.quartzs.RabbitBotJob;
import lombok.Getter;

import java.util.function.Consumer;

import static cc.moecraft.icq.PicqConstants.HTTP_API_VERSION_DETECTION;
import static cc.moecraft.icq.PicqConstants.VERSION;
import static utils.MiscUtils.logInitDone;
import static utils.MiscUtils.logResource;
import static cc.moecraft.logger.format.AnsiColor.GREEN;

/**
 * The class {@code PicqBotX} is the main controller class of all the
 * components of the CoolQ bot program.
 * <p>
 * Class created by the HyDEV Team on 2019-03-23!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-23 12:46
 */
@Getter
public class PicqBotX {
    /**
     * Picq配置 | Picq configuration
     */
    private final PicqConfig config;

    /**
     * HTTP监听服务器
     */
    private PicqHttpServer httpServer;

    /**
     * 事件管理器
     */
    private EventManager eventManager;

    /**
     * 机器人账号管理器
     */
    private AccountManager accountManager;

    /**
     * 用户对象缓存管理器
     */
    private UserManager userManager;

    /**
     * 群对象缓存管理器
     */
    private GroupManager groupManager;

    /**
     * 群用户对象缓存管理器
     */
    private GroupUserManager groupUserManager;

    /**
     * 指令管理器
     */
    private CommandManager commandManager;

    /**
     * 全局替换HyExp表达式 (如果是null就不替换)
     */
    private HyExpressionResolver hyExpressionResolver = null;

    /**
     * 构造器
     *
     * @param config Picq配置
     */
    public PicqBotX(PicqConfig config) {
        this(config, true);
    }

    /**
     * 构造器
     *
     * @param config Picq配置
     * @param init   是否启动服务器
     */
    public PicqBotX(PicqConfig config, boolean init) {
        this.config = config;
        if (init) {
            init();
        }
    }

    /**
     * 构造器
     *
     * @param socketPort 接收端口
     */
    public PicqBotX(int socketPort) {
        this(new PicqConfig(socketPort));
    }

    /**
     * 初始化
     */
    private void init() {
        // 设置是否输出 Init 消息
        MiscUtils.disabled = !config.isLogInit();

        //日志初始化
        LoggerRabbit.init(config);
        int fullProgress = 9;
        int prgressIndex = 0;
        LoggerRabbit.logger().timing.init();
        logResource(LoggerRabbit.logger(), config.getColorSupportLevel() == null ? "splash" : "splash-precolored", "version", VERSION);
        logInitDone(LoggerRabbit.logger(), "日志系统      ", prgressIndex, fullProgress - prgressIndex);

        // 用户和群缓存管理器
        userManager = new UserManager(this);
        groupUserManager = new GroupUserManager(this);
        groupManager = new GroupManager(this);
        prgressIndex++;
        logInitDone(LoggerRabbit.logger(), "缓存管理器      ", prgressIndex, fullProgress - prgressIndex);

        // Debug设置没啦w
        prgressIndex++;
        logInitDone(LoggerRabbit.logger(), "DEBUG设置        ", prgressIndex, fullProgress - prgressIndex);

        // 事件管理器
        eventManager = new EventManager(this);
        eventManager.registerListener(new HyExpressionListener());
        prgressIndex++;
        logInitDone(LoggerRabbit.logger(), "事件管理器      ", prgressIndex, fullProgress - prgressIndex);

        // 账号管理器
        accountManager = new AccountManager();
        eventManager.registerListener(new AccountManagerListener(accountManager));
        prgressIndex++;
        logInitDone(LoggerRabbit.logger(), "账号管理器      ", prgressIndex, fullProgress - prgressIndex);

        // HTTP监听服务器
        httpServer = new PicqHttpServer(config.getSocketPort(), this);
        prgressIndex++;
        logInitDone(LoggerRabbit.logger(), "HTTP监听服务器  ", prgressIndex, fullProgress - prgressIndex);

        //加载配置文件 如果以后有系统级的东西加载配置里，这个配置应该优先读取
        FileManagerConfig.doCommand(ConstantFile.FILE_COMMAND_LOAD);
        prgressIndex++;
        logInitDone(LoggerRabbit.logger(), "外部配置文件     ", prgressIndex, fullProgress - prgressIndex);

        //加载资源
        //日常语句
        FileManagerFreeTime.doCommand(ConstantFile.FILE_COMMAND_LOAD);
        //关键词匹配-全匹配
        FileManagerKeyWordNormal.doCommand(ConstantFile.FILE_COMMAND_LOAD);
        //关键词匹配-模糊匹配
        FileManagerKeyWordLike.doCommand(ConstantFile.FILE_COMMAND_LOAD);
        //高德地图接口区域代码信息
        FileManagerAmapAdcode.doCommand(ConstantFile.FILE_COMMAND_LOAD);
        //摩尔斯电码
        FileManagerMorseCode.loadFile();
        //pixiv图片所有tag
        FileManagerPixivTags.loadFile();
        //pixiv图片已整理的tag
        FileManagerPixivTags.loadRabbitFile();

        prgressIndex++;
        logInitDone(LoggerRabbit.logger(), "外部资源文件     ", prgressIndex, fullProgress - prgressIndex);

        //启动定时任务
        new RabbitBotJob().jobStart();
        prgressIndex++;
        logInitDone(LoggerRabbit.logger(), "定时任务       ", prgressIndex, fullProgress - prgressIndex);

        //指令管理器
        commandManager = new CommandManager(this, ".");
        eventManager.registerListener(new CommandListener(commandManager));
        prgressIndex++;
        logInitDone(LoggerRabbit.logger(), "指令管理器      ", prgressIndex, fullProgress - prgressIndex);

        LoggerRabbit.logger().timing.clear();
    }

    /**
     * 添加机器人账号
     *
     * @param name     名字
     * @param postUrl  发送URL (Eg. 127.0.0.1)
     * @param postPort 发送端口 (Eg. 31091)
     * @return 是否成功添加
     */
    public boolean addAccount(String name, String postUrl, int postPort) {
        return addAccount(name, postUrl, postPort, (ex) ->
        {
            LoggerRabbit.logger().error("HTTP发送错误: " + ex.getLocalizedMessage());
            LoggerRabbit.logger().error("- 检查一下是不是忘记开酷Q了, 或者写错地址了");
        });
    }

    /**
     * 添加机器人账号
     *
     * @param name         名字
     * @param postUrl      发送URL（Eg. 127.0.0.1）
     * @param postPort     发送端口（Eg. 31091）
     * @param errorHandler 错误处理（HttpException）
     * @return 是否成功添加
     */
    public boolean addAccount(String name, String postUrl, int postPort, Consumer<HttpException> errorHandler) {
        try {
            this.accountManager.addAccount(new BotAccount(name, this, postUrl, postPort));
            return true;
        } catch (HttpException ex) {
            errorHandler.accept(ex);
            return false;
        }
    }

    /**
     * 启动机器人
     */
    public void startBot() {
        if (!verifyHttpPluginVersion()) {
            LoggerRabbit.logger().error("验证失败, 请检查上面的错误信息再重试启动服务器.");
            throw new VerifyFailedException();
        }

        LoggerRabbit.logger().log(GREEN + "正在启动...");
        httpServer.start();
        LoggerRabbit.logger().log(GREEN + "======Rabbit ready======");
    }

    /**
     * 验证HTTP插件版本
     *
     * @return 是否通过验证
     */
    public boolean verifyHttpPluginVersion() {
        if (config.isNoVerify()) {
            LoggerRabbit.logger().warning("已跳过版本验证w");
            return true;
        }

        for (BotAccount botAccount : accountManager.getAccounts()) {
            String prefix = "账号 " + botAccount.getName() + ": ";

            try {
                RVersionInfo versionInfo = botAccount.getHttpApi().getVersionInfo().getData();

                if (!versionInfo.getPluginVersion().matches(HTTP_API_VERSION_DETECTION)) {
                    LoggerRabbit.logger().error(prefix + "HTTP插件版本不正确, 已停止启动");
                    LoggerRabbit.logger().error("- 当前版本: " + versionInfo.getPluginVersion());
                    LoggerRabbit.logger().error("- 兼容的版本: " + HTTP_API_VERSION_DETECTION);
                    return false;
                }

                if (!versionInfo.getCoolqEdition().equalsIgnoreCase("pro")) {
                    if (config.isLogInit()) {
                        LoggerRabbit.logger().warning(prefix + "版本正确, 不过用酷Q Pro的话效果更好哦!");
                    }
                }
            } catch (HttpException e) {
                if (e.getMessage().toLowerCase().contains("connection")) {
                    LoggerRabbit.logger().error("HTTP发送地址验证失败, 已停止启动");
                    LoggerRabbit.logger().error("- 请检查酷Q是否已经启动");
                    LoggerRabbit.logger().error("- 请检查酷Q的接收端口是否和Picq的发送端口一样");
                    LoggerRabbit.logger().error("- 请检查你的发送IP是不是写错了");
                    LoggerRabbit.logger().error("- 如果是向外, 请检查这个主机有没有网络连接");
                } else {
                    LoggerRabbit.logger().error("验证失败, HTTP发送错误: ");
                }
                LoggerRabbit.logger().error(e);
                return false;
            }

            if (config.isLogInit()) {
                LoggerRabbit.logger().log(AnsiColor.YELLOW + prefix + AnsiColor.GREEN + "  版本验证完成!");
            }
        }
        return true;
    }

    /**
     * 设置是否替换HyExp表达式
     *
     * @param value 是否替换
     */
    public void setUniversalHyExpSupport(boolean value) {
        setUniversalHyExpSupport(value, true);
    }

    /**
     * 设置是否替换HyExp表达式
     *
     * @param value    是否替换
     * @param safeMode 是否安全模式 (推荐是)
     */
    public void setUniversalHyExpSupport(boolean value, boolean safeMode) {
        hyExpressionResolver = value ? new HyExpressionResolver(safeMode) : null;
    }

    /**
     * 获取Debug信息w
     *
     * @return Debug 信息
     */
    public String printDebugSupportInfo() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        MessageBuilder builder = new MessageBuilder()
                .add("===== 配置 =====").newLine()
                .add(gson.toJson(config)).newLine()
                .add("===== 账号 =====").newLine()
                .add(gson.toJson(accountManager.getAccounts()));

        return builder.toString();
    }
}
