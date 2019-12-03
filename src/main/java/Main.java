import gugugu.bots.BotRabbit;
import gugugu.quartzs.RabbitBotJob;

/**
 * 入口
 */
public class Main {
    public static void main(String[] args) {
        //启动兔机
        BotRabbit.start();
        //启动定时任务
        new RabbitBotJob().jobStart();
        System.out.println("======Rabbit ready======");
    }
}
