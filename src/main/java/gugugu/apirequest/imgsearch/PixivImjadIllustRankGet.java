package gugugu.apirequest.imgsearch;

import com.alibaba.fastjson.JSONObject;
import gugugu.apirequest.BaseRequest;
import gugugu.bots.BotRabbit;
import gugugu.entity.apirequest.imgsearch.pixiv.ImjadPixivRankResult;
import lombok.Getter;
import lombok.Setter;
import utils.HttpUtil;
import utils.HttpsUtil;
import utils.StringUtil;

import java.io.IOException;

/**
 * create by MikuLink on 2020/2/19 12:38
 * for the Reisen
 * 获取pixiv插画排行
 * https://api.imjad.cn/
 * https://zhuanlan.zhihu.com/p/35243511
 */
@Getter
@Setter
public class PixivImjadIllustRankGet extends BaseRequest {
    private static final String URL = "https://api.imjad.cn/pixiv/v1/";

    /**
     * 页数
     */
    private Integer page;
    /**
     * 每页大小
     */
    private Integer pageSize;
    /**
     * 类型 illust 代表插画
     */
    private String content;
    /**
     * 排行类型
     * daily 日榜
     * weekly 月榜
     */
    private String mode;
    /**
     * 日期
     * 每天0点-12点左右获取不到昨日的排行
     * 这时候可以获取前一天的
     * yyyy-MM-dd
     */
    private String date;

    //执行接口请求
    public void doRequest() throws IOException {
        //拼装参数
        addParam();
        //请求
        byte[] resultBytes = HttpsUtil.doGet(URL + HttpUtil.parseUrlEncode(param));
        body = new String(resultBytes);

        //记录接口请求与返回日志
        BotRabbit.bot.getLogger().debug(String.format("Api Request PixivImjadIllustRankGet,param:%s,resultBody:%s", JSONObject.toJSONString(param), body));
    }

    //拼装参数
    private void addParam() {
        //类型：热榜
        param.put("type", "rank");
        param.put("page", page);
        param.put("per_page", pageSize);
        param.put("mode", mode);
        param.put("content", content);
        param.put("date",date);
    }

    //获取解析后的结果对象
    public ImjadPixivRankResult getEntity() {
        if (StringUtil.isEmpty(body)) {
            return null;
        }
        return JSONObject.parseObject(body, ImjadPixivRankResult.class);
    }
}
