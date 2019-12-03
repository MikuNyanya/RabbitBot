package gugugu.quartzs;

import gugugu.quartzs.jobs.JobTimeRabbit;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * create by MikuLink on 2019/12/3 12:27
 * for the Reisen
 * <p>
 * 定时任务
 */
public class RabbitBotJob {
    //每小时执行一次
    private static final String CRON = "0 0 0/1 * * ?";

    /**
     * 启动定时任务
     */
    public void jobStart() {
        //创建一个JobDetail
        JobDetail jobDetail = JobBuilder.newJob(JobTimeRabbit.class)
                .withDescription("整点报时 JobDetail")
                .withIdentity("Time Rabbit JobDetail", "Rabbit Job")
                .build();

        //创建一个trigger触发规则
        Trigger trigger = TriggerBuilder.newTrigger()
                .withDescription("整点报时 Trigger")
                .startAt(new Date())
                .withIdentity("Time Rabbit Trigger", "Rabbit Job")
//                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(10, 5))   //写死间隔
                .withSchedule(CronScheduleBuilder.cronSchedule(CRON)) //cron表达式
                .build();

        try {
            //创建一个调度器，也就是一个Quartz容器
            //声明一个scheduler的工厂schedulerFactory
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            //通过schedulerFactory来实例化一个Scheduler
            Scheduler scheduler = schedulerFactory.getScheduler();
            //将Job和Trigger注册到scheduler容器中
            scheduler.scheduleJob(jobDetail, trigger);

            //启动定时器
            scheduler.start();
        } catch (SchedulerException sEx) {
            //todo 输出到日志里
            sEx.printStackTrace();
        }
    }
}
