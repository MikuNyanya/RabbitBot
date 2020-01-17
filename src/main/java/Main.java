import cc.moecraft.logger.format.AnsiColor;
import gugugu.bots.BotRabbit;
import gugugu.filemanage.FileManager;
import gugugu.quartzs.RabbitBotJob;

/**
 * 入口
 */
public class Main {
    public static void main(String[] args) {
        //启动兔机
        BotRabbit.start();
        //加载配置
        FileManager.loadConfig();
        BotRabbit.bot.getLogger().log(AnsiColor.YELLOW + "配置文件加载完毕");
        //加载客制化内容
        FileManager.loadFreeTime();
        FileManager.loadKeyWordNormal();
        FileManager.loadKeyWordLike();
        BotRabbit.bot.getLogger().log(AnsiColor.YELLOW + "附加功能加载完毕");
        //启动定时任务
        new RabbitBotJob().jobStart();
        BotRabbit.bot.getLogger().log(AnsiColor.YELLOW + "定时任务已开启");
        BotRabbit.bot.getLogger().log(AnsiColor.YELLOW + "======Rabbit ready======");
    }
}
