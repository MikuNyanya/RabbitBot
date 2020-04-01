package gugugu.bots;

import cc.moecraft.icq.PicqConfig;
import cc.moecraft.logger.HyLogger;
import cc.moecraft.logger.LoggerInstanceManager;
import cc.moecraft.logger.environments.ConsoleColoredEnv;
import cc.moecraft.logger.environments.FileEnv;

/**
 * create by MikuLink on 2020/4/1 15:26
 * for the Reisen
 * 日志模块
 * 改为独立的日志模块了
 * 也许写框架的那位设计是对的，但至少我现在是抱有怀疑态度的，因为依赖性太强了
 */
public class LoggerRabbit {
    /**
     * Logger实例管理器
     */
    private static LoggerInstanceManager loggerInstanceManager;

    /**
     * Logger
     */
    private static HyLogger logger;

    /**
     * 使用默认配置初始化
     */
    public static void init() {
        init(null);
    }

    /**
     * 初始化日志
     *
     * @param config 配置，如果耦合需要再低一点，可以拆成独立配置
     */
    public static void init(PicqConfig config) {
        if (null != logger) {
            return;
        }
        if (null == loggerInstanceManager) {
            loggerInstanceManager = new LoggerInstanceManager();
            if (null == config) {
                config = new PicqConfig(BotRabbit.SOCKET_PORT);
                config.setDebug(true);
            }
            loggerInstanceManager.addEnvironment(new ConsoleColoredEnv(config.getColorSupportLevel()));
            if (!config.getLogPath().isEmpty()) {
                loggerInstanceManager.addEnvironment(new FileEnv(config.getLogPath(), config.getLogFileName()));
            }
        }
        logger = loggerInstanceManager.getLoggerInstance(BotRabbit.BOT_NAME_EN, config.isDebug());
    }

    /**
     * 获取日志对象
     *
     * @return 日志对象
     */
    public static HyLogger logger() {
        if (null != logger) {
            return logger;
        }
        init();
        return logger;
    }
}
