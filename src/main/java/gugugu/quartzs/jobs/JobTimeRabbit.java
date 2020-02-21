package gugugu.quartzs.jobs;

import gugugu.bots.BotRabbit;
import gugugu.commands.groups.CommandRP;
import gugugu.constant.ConstantCommon;
import gugugu.constant.ConstantImage;
import gugugu.entity.InfoPixivRankImage;
import gugugu.service.NCoV_2019ReportService;
import gugugu.service.PixivService;
import gugugu.service.RabbitBotService;
import gugugu.service.WeatherService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import utils.DateUtil;

import java.util.Date;
import java.util.List;

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
        //天气
        weatherRabbit();

        //0点清理
        //RP缓存
        clearRPMap();

        //疫情总览信息推送
        nCoV();

        //pixiv日榜，最好放在最后执行，要下载图片
        //也可以另起一个线程，但我懒
        pixivRankDay();
    }

    //报时兔子
    private void timeRabbit() {
        //附加短语
        String msgEx = getMsgEx();

        //群报时，时间间隔交给定时器，这里返回值取当前时间即可
        String msg = String.format("这里是%s报时：%s%s", BotRabbit.BOT_NAME, DateUtil.toString(new Date()), msgEx);
        try {
            //给每个群发送报时
            RabbitBotService.sendEveryGroupMsg(msg);
        } catch (Exception ex) {
            BotRabbit.bot.getLogger().error("报时兔子 消息发送异常" + ex.toString(), ex);
        }
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

    //天气兔子
    private void weatherRabbit() {
        //每天9点，13点，19点进行自动播报
        int hour = DateUtil.getHour();
        if (hour != 9 && hour != 13 && hour != 19) {
            return;
        }

        try {
            //获取天气情况，固定获取上海市
            String msg = WeatherService.getWeatherByCityName("上海市");

            //给每个群发送报时
            RabbitBotService.sendEveryGroupMsg(msg);
        } catch (Exception ioEx) {
            BotRabbit.bot.getLogger().error("天气兔子发生异常:" + ioEx.toString(), ioEx);
        }
    }

    //nCoV疫情兔子
    private void nCoV() {
        //每天凌晨3点，早上10点，下午2点，下午7点，下午11点推送
        //大晚上的就不发了
        int hour = DateUtil.getHour();
        if (hour != 3 && hour != 10 & hour != 14 & hour != 19 & hour != 23) {
            return;
        }

        try {
            //执行一次疫情消息推送
            NCoV_2019ReportService.reportInfoNow();
        } catch (Exception ex) {
            BotRabbit.bot.getLogger().error("nCoV疫情消息推送执行异常:" + ex.toString(), ex);
        }
    }

    //P站日榜兔子
    private void pixivRankDay() {
        //每天晚上19点推送日榜信息
        int hour = DateUtil.getHour();
        if (hour != 19) {
            return;
        }

        try {
            //获取日榜前3
            List<InfoPixivRankImage> imageList = PixivService.getPixivIllustRank(1, 3);
            for (InfoPixivRankImage imageInfo : imageList) {
                //拼接一个发送一个，中间间隔5秒
                String resultStr = PixivService.parsePixivImgInfoToGroupMsg(imageInfo);
                RabbitBotService.sendEveryGroupMsg(resultStr, 2L);
                Thread.sleep(1000L * 2);
            }
        } catch (Exception ex) {
            BotRabbit.bot.getLogger().error(ConstantImage.PIXIV_IMAGE_RANK_JOB_ERROR + ex.toString(), ex);
        }
    }
}
