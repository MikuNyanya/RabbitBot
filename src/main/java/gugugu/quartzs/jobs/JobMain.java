package gugugu.quartzs.jobs;

import cc.moecraft.icq.accounts.BotAccount;
import cc.moecraft.icq.sender.IcqHttpApi;
import gugugu.bots.BotRabbit;
import gugugu.constant.ConstantFreeTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import utils.DateUtil;
import utils.RandomUtil;

import java.util.Map;

/**
 * create by MikuLink on 2019/12/3 12:58
 * for the Reisen
 * <p>
 * 1分钟执行一次的定时器
 */
public class JobMain implements Job {
    //正常间隔(毫秒) 目前为10分钟
    private static final Long SPLIT_NORMAL = 1000L * 60 * 10;
    //随机间隔最大值(分钟) 目前最长延迟10分钟
    private static final Integer SPLIT_RANDOM_MAX = 10;

    //最后发送时间
    private static Long free_time_last_send_time = System.currentTimeMillis();
    //下次发送的随机间隔时间
    private static Long free_time_random_send_time = 0L;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //日常语句
        FreeTimeRabbit();
    }

    //日常兔子
    private void FreeTimeRabbit() {
        //检测发送间隔 加上随机间隔时间
        if (System.currentTimeMillis() - free_time_last_send_time < (SPLIT_NORMAL + free_time_random_send_time)) {
            return;
        }

        //大晚上的就不发了
        int hour = DateUtil.getHour();
        if (hour < 7) {
            return;
        }

        //获取链接，参数是机器人的qq号
        IcqHttpApi icqHttpApi = BotRabbit.bot.getAccountManager().getIdIndex().get(1020992834L).getHttpApi();

        //选出一条信息
        String msg = RandomUtil.rollStrFromList(ConstantFreeTime.MSG_TYPE_FREE_TIME);

        //给每个群发送报时
        Map<Long, Map<BotAccount, Long>> groupList = BotRabbit.bot.getAccountManager().getGroupAccountIndex();
        for (Long groupId : groupList.keySet()) {
            icqHttpApi.sendGroupMsg(groupId, msg);
        }

        //刷新最后发送时间
        free_time_last_send_time = System.currentTimeMillis();
        //刷新下次发送的随机延迟时间
        free_time_random_send_time = 1000L * 60 * RandomUtil.roll(SPLIT_RANDOM_MAX + 1);
    }
}
