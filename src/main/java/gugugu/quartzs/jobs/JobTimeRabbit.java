package gugugu.quartzs.jobs;

import gugugu.bots.BotRabbit;
import gugugu.bots.LoggerRabbit;
import gugugu.commands.groups.CommandRP;
import gugugu.constant.ConstantCommon;
import gugugu.constant.ConstantImage;
import gugugu.entity.pixiv.PixivRankImageInfo;
import gugugu.service.*;
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
//        nCoV();

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
            LoggerRabbit.logger().error("报时兔子 消息发送异常" + ex.toString(), ex);
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
        LoggerRabbit.logger().debug("每日人品缓存已清除");
    }

    //天气兔子
    private void weatherRabbit() {
        //每天9点，13点，19点进行自动播报
        int hour = DateUtil.getHour();
        if (hour != 9 && hour != 13 && hour != 19) {
            return;
        }

        try {
            //获取天气情况
            String msg = WeatherService.getWeatherByCityName("宿州市");

            //给每个群发送报时
            RabbitBotService.sendEveryGroupMsg(msg);
        } catch (Exception ioEx) {
            LoggerRabbit.logger().error("天气兔子发生异常:" + ioEx.toString(), ioEx);
        }
    }

    //nCoV疫情兔子
    private void nCoV() {
        //每天下午5点推送一次，中国境内还好，外国就看戏吧
        int hour = DateUtil.getHour();
        if (hour != 17) {
            return;
        }

        try {
            //执行一次疫情消息推送
            String groupMsg = NCoV_2019ReportService.reportInfoNowWorld();
            RabbitBotService.sendEveryGroupMsg(groupMsg);
        } catch (Exception ex) {
            LoggerRabbit.logger().error("nCoV疫情消息推送执行异常:" + ex.toString(), ex);
        }
    }

    //P站日榜兔子
    private void pixivRankDay() {
        //每天晚上20点推送日榜信息，不然7点我还没到家，背着兔叽在路上没网络
        int hour = DateUtil.getHour();
        if (hour != 20) {
            return;
        }

        try {
            //获取日榜
            List<PixivRankImageInfo> imageList = null;
            //是否走爬虫
            String pixiv_config_use_api = ConstantCommon.common_config.get(ConstantImage.PIXIV_CONFIG_USE_API);
            if (ConstantImage.OFF.equalsIgnoreCase(pixiv_config_use_api)) {
                imageList = PixivBugService.getPixivIllustRank(ConstantImage.PIXIV_IMAGE_PAGESIZE);
            } else {
                imageList = PixivService.getPixivIllustRank(1, ConstantImage.PIXIV_IMAGE_PAGESIZE);
            }
            for (PixivRankImageInfo imageInfo : imageList) {
                //拼接一个发送一个，中间间隔5秒
                String resultStr = PixivService.parsePixivImgInfoToGroupMsg(imageInfo);
                RabbitBotService.sendEveryGroupMsg(resultStr, 2L);
                Thread.sleep(1000L * 2);
            }
        } catch (Exception ex) {
            LoggerRabbit.logger().error(ConstantImage.PIXIV_IMAGE_RANK_JOB_ERROR + ex.toString(), ex);
        }
    }
}
