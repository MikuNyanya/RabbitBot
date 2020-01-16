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
        System.out.println("======Config ready======");
        //加载客制化内容
        FileManager.loadFreeTime();
        FileManager.loadKeyWordNormal();
        FileManager.loadKeyWordLike();
        System.out.println("======Append ready======");
        //启动定时任务
        new RabbitBotJob().jobStart();
        System.out.println("======Rabbit ready======");
    }
}
