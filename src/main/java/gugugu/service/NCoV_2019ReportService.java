package gugugu.service;

import com.alibaba.fastjson.JSONObject;
import gugugu.bots.BotRabbit;
import gugugu.constant.ConstantImage;
import gugugu.entity.apirequest.dxynCoV.InfoAllnCoV;
import gugugu.entity.apirequest.dxynCoV.NCoVWorldDetailInfo;
import gugugu.entity.apirequest.dxynCoV.NCovWorldInfo;
import utils.DateUtil;
import utils.HttpUtil;
import utils.ImageUtil;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author MikuLink
 * @date 2020/1/31 22:33
 * for the Reisen
 * 新型冠状病毒 2019-nCoV
 * 这是一个临时功能，疫情过去后就没用了
 * 或者人都死光了，也就没啥用了
 * <p>
 * 数据来源于与丁香医生的一个数据统计页面，似乎是手机端页面
 */
public class NCoV_2019ReportService {
    private static final String DXY_nCoV_URL = "https://3g.dxy.cn/newh5/view/pneumonia?scene=2&clicktime=1579579384&enterid=1579579384&from=groupmessage&isappinstalled=0";
    private static final String DXY_nCoV_PC_URL = "https://ncov.dxy.cn/ncovh5/view/pneumonia";

    //爬虫，直接爬，获取当前疫情数据
    public static void reportInfoNow() throws IOException {
        //通过请求获取到返回的页面
        String htmlStr = HttpUtil.get(DXY_nCoV_URL);
        //直接暴力解析吧
        String rootStr = htmlStr.substring(htmlStr.indexOf("window.getStatisticsService =") + 29);
        rootStr = rootStr.substring(0, rootStr.indexOf("}catch(e)")).trim();
        InfoAllnCoV info = JSONObject.parseObject(rootStr, InfoAllnCoV.class);

        //拼接字符串
        StringBuilder resultStr = new StringBuilder();
        resultStr.append(info.getNote1());
        resultStr.append("\n" + info.getNote2());
        resultStr.append("\n" + info.getNote3());
        resultStr.append("\n" + info.getRemark3());
        resultStr.append("\n" + info.getRemark1());
        resultStr.append("\n" + info.getRemark2());
        resultStr.append("\n总计确诊病例：" + info.getConfirmedCount());
        resultStr.append("\n当前确诊病例：" + info.getCurrentConfirmedCount());
        resultStr.append("\n当前疑似病例：" + info.getSuspectedCount());
        resultStr.append("\n当前重症病例：" + info.getSeriousCount());
        resultStr.append("\n总计死亡人数：" + info.getDeadCount());
        resultStr.append("\n总计治愈人数：" + info.getCuredCount());
        resultStr.append("\n=====" + DateUtil.toString(new Date(info.getModifyTime())) + "=====");

        try {
            RabbitBotService.sendEveryGroupMsg(resultStr.toString());
        } catch (Exception ex) {
            BotRabbit.bot.getLogger().error("nCoV疫情消息发送异常" + ex.toString(), ex);
        }
    }

    //爬虫，直接爬，获取当前全球疫情数据
    public static String reportInfoNowWorld() throws IOException {
        //通过请求获取到返回的页面
        String htmlStr = HttpUtil.get(DXY_nCoV_PC_URL);
        //直接暴力解析吧
        //全球总览
        String worldStr = htmlStr.substring(htmlStr.indexOf("window.getStatisticsService =") + "window.getStatisticsService =".length());
        worldStr = worldStr.substring(0, worldStr.indexOf("}catch"));
        NCovWorldInfo nCovWorldInfo = JSONObject.parseObject(worldStr, NCovWorldInfo.class);
        NCovWorldInfo.ForeignStatisticsBean foreignStatisticsBean = nCovWorldInfo.getForeignStatistics();
        //拼接字符串
        StringBuilder worldInfoStr = new StringBuilder();
        //主要国家趋势图
        String localPath = ImageUtil.downloadImage(nCovWorldInfo.getImportantForeignTrendChart().get(0).getImgUrl(), ConstantImage.DEFAULT_IMAGE_SAVE_PATH + "/ncov", null);
        worldInfoStr.append(ImageService.parseCQByLocalImagePath(localPath));
        worldInfoStr.append("外国疫情总览");
        worldInfoStr.append(String.format("\n当前确诊：%s(%s%s)", foreignStatisticsBean.getCurrentConfirmedCount(), foreignStatisticsBean.getCurrentConfirmedIncr() < 0 ? "" : "+", foreignStatisticsBean.getCurrentConfirmedIncr()));
        worldInfoStr.append(String.format("\n总计确诊：%s(%s%s)", foreignStatisticsBean.getConfirmedCount(), foreignStatisticsBean.getConfirmedIncr() < 0 ? "" : "+", foreignStatisticsBean.getConfirmedIncr()));
        worldInfoStr.append(String.format("\n总计治愈：%s(%s%s)", foreignStatisticsBean.getCuredCount(), foreignStatisticsBean.getCuredIncr() < 0 ? "" : "+", foreignStatisticsBean.getCuredIncr()));
        worldInfoStr.append(String.format("\n总计死亡：%s(%s%s)", foreignStatisticsBean.getDeadCount(), foreignStatisticsBean.getDeadIncr() < 0 ? "" : "+", foreignStatisticsBean.getDeadIncr()));

        //区域详情
        int maxDetail = 5;
        worldInfoStr.append("\n\n外国重疫区Top" + maxDetail);
        String rootStr = htmlStr.substring(htmlStr.indexOf("window.getListByCountryTypeService2true =") + "window.getListByCountryTypeService2true =".length());
        rootStr = rootStr.substring(0, rootStr.indexOf("}catch")).trim();
        List<NCoVWorldDetailInfo> info = JSONObject.parseArray(rootStr, NCoVWorldDetailInfo.class);
        int i = 1;
        for (NCoVWorldDetailInfo detailInfo : info) {
            worldInfoStr.append(String.format("\n%s\t现确诊:%s\t总确诊:%s\t总治愈:%s\t总死亡:%s\t死亡率:%s %%",
                    detailInfo.getProvinceName(),
                    detailInfo.getCurrentConfirmedCount(),
                    detailInfo.getConfirmedCount(),
                    detailInfo.getCuredCount(),
                    detailInfo.getDeadCount(),
                    detailInfo.getDeadRate()));
            i++;
            if (i > maxDetail) {
                break;
            }
        }
        worldInfoStr.append("\n====数据来源丁香园 " + DateUtil.toString(new Date(nCovWorldInfo.getModifyTime())) + "====");
        return worldInfoStr.toString();
    }

}
