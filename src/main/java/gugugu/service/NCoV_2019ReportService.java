package gugugu.service;

import com.alibaba.fastjson.JSONObject;
import gugugu.entity.apirequest.dxynCoV.InfoAllnCoV;
import utils.DateUtil;
import utils.HttpUtil;

import java.io.IOException;
import java.util.Date;

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
        resultStr.append("\n确诊病例：" + info.getConfirmedCount());
        resultStr.append("\n疑似病例：" + info.getSuspectedCount());
        resultStr.append("\n死亡人数：" + info.getDeadCount());
        resultStr.append("\n治愈人数：" + info.getCuredCount());
        resultStr.append("\n=====" + DateUtil.toString(new Date(info.getModifyTime())) + "=====");

        RabbitBotService.sendEveryGroupMsg(resultStr.toString());
    }

}
