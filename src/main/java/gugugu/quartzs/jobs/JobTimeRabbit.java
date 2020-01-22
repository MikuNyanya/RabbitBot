package gugugu.quartzs.jobs;

import gugugu.bots.BotRabbit;
import gugugu.commands.groups.CommandRP;
import gugugu.constant.ConstantCommon;
import gugugu.service.RabbitBotService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import utils.DateUtil;

import java.util.Date;

/**
 * create by MikuLink on 2019/12/3 12:58
 * for the Reisen
 * <p>
 * 1小时执行一次的定时器
 */
public class JobTimeRabbit implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        //报时兔子
        timeRabbit();

        //0点清理
        //RP缓存
        clearRPMap();
    }

    //报时兔子
    private void timeRabbit() {
        //附加短语
        String msgEx = getMsgEx();

        //群报时，时间间隔交给定时器，这里返回值取当前时间即可
        String msg = String.format("这里是%s报时：%s%s", BotRabbit.BOT_NAME, DateUtil.toString(new Date()), msgEx);

        //给每个群发送报时
        RabbitBotService.sendEveryGroupMsg(msg);
    }

    //获取附加短语，可以放一些彩蛋性质的东西，会附带在报时消息尾部
    private String getMsgEx() {
        int hour = DateUtil.getHour();

        switch (hour) {
            //半夜0点
            case 0:
                return ConstantCommon.NEXT_LINE + "新的一天开始啦ヽ(#`Д´)ノ";
            //凌晨4点
            case 4:
                return ConstantCommon.NEXT_LINE + "还有人活着嘛~";
            //早上7点
            case 7:
                return ConstantCommon.NEXT_LINE + "早上好,该起床了哦~~";
            //中午11点
            case 11:
                return ConstantCommon.NEXT_LINE + "开始做饭了吗，外卖点了吗";
            //中午12点
            case 12:
                return ConstantCommon.NEXT_LINE + "午安，该是吃午饭的时间了";
            //下午18点
            case 18:
                return ConstantCommon.NEXT_LINE + "到了下班的时间啦!";
            //晚上23点
            case 23:
                return ConstantCommon.NEXT_LINE + "已经很晚了，早点休息哦~~";
        }
        return "";
    }

    //清除RP缓存，不然第二天RP值不会重置
    private void clearRPMap() {
        //0点清除
        if (DateUtil.getHour() != 0) {
            return;
        }

        CommandRP.MAP_RP.clear();
        BotRabbit.bot.getLogger().debug("每日人品缓存已清除");
    }
}
