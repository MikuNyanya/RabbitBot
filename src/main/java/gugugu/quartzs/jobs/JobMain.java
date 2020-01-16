package gugugu.quartzs.jobs;

import gugugu.bots.BotRabbit;
import gugugu.constant.ConstantCommon;
import gugugu.constant.ConstantFreeTime;
import gugugu.constant.ConstantWeiboNews;
import gugugu.filemanage.FileManager;
import gugugu.service.RabbitBotService;
import gugugu.service.WeiboNewsService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import utils.DateUtil;
import utils.RandomUtil;

/**
 * create by MikuLink on 2019/12/3 12:58
 * for the Reisen
 * <p>
 * 1分钟执行一次的定时器
 */
public class JobMain implements Job {
    //正常间隔(毫秒) 目前为2小时
    private static final Long SPLIT_NORMAL = 1000L * 60 * 60 * 2;
    //随机间隔最大值(分钟) 目前最长延迟1小时
    private static final Integer SPLIT_RANDOM_MAX = 60;

    //日常语句最后发送时间
    private static Long free_time_last_send_time = System.currentTimeMillis();
    //日常语句下次发送的随机间隔时间
    private static Long free_time_random_send_time = 0L;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        //日常语句
        freeTimeRabbit();
        //微博最新消息
        weiboNews();
    }

    //日常兔子
    private void freeTimeRabbit() {
        //检测发送间隔 加上随机间隔时间
        if (System.currentTimeMillis() - free_time_last_send_time < (SPLIT_NORMAL + free_time_random_send_time)) {
            return;
        }

        //大晚上的就不发了
        int hour = DateUtil.getHour();
        if (hour < 8) {
            return;
        }

        //选出一条信息
        //从列表中删除获取的消息，实现伪随机，不然重复率太高了，体验比较差
        String msg = RandomUtil.rollAndDelStrFromList(ConstantFreeTime.MSG_TYPE_FREE_TIME);

        //删到六分之一时重新加载集合
        if (ConstantFreeTime.MSG_TYPE_FREE_TIME.size() < ConstantFreeTime.MSG_TYPE_FREE_TIME_MAX_SIZE / 6) {
            FileManager.loadFreeTime();
        }

        //给每个群发送消息
        RabbitBotService.sendEveryGroupMsg(msg);

        //刷新最后发送时间
        free_time_last_send_time = System.currentTimeMillis();
        //刷新下次发送的随机延迟时间
        free_time_random_send_time = 1000L * 60 * RandomUtil.roll(SPLIT_RANDOM_MAX + 1);
    }

    //微信最新消息
    private void weiboNews() {
        //功能开关
        if (!ConstantCommon.common_config.containsKey("weiboNewStatus") || "0".equals(ConstantCommon.common_config.get("weiboNewStatus"))) {
            return;
        }
        //检测发送间隔
        if (System.currentTimeMillis() - ConstantWeiboNews.weibo_news_last_send_time < ConstantWeiboNews.weibo_news_sprit_time) {
            return;
        }

        try {
            //执行一次微博消息推送
            WeiboNewsService.doPushWeiboNews();
        } catch (Exception ex) {
            BotRabbit.bot.getLogger().error("微博消息推送执行异常", ex);
        }

        //刷新最后发送时间
        ConstantWeiboNews.weibo_news_last_send_time = System.currentTimeMillis();
    }
}
