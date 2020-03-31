package gugugu.apirequest.tracemoe;

import com.alibaba.fastjson.JSONObject;
import gugugu.apirequest.BaseRequest;
import gugugu.bots.BotRabbit;
import gugugu.entity.apirequest.tracemoe.WhatAnimeResult;
import lombok.Getter;
import lombok.Setter;
import utils.HttpUtil;
import utils.HttpsUtil;
import utils.StringUtil;

import java.io.IOException;

/**
 * create by MikuLink on 2020/2/19 12:38
 * for the Reisen
 * 以图搜番
 * 官网https://trace.moe/
 * 官方文档 https://soruly.github.io/trace.moe/#/
 */
@Getter
@Setter
public class WhatAnimeApi extends BaseRequest {
    private static final String URL = "https://trace.moe/api/search";

    /**
     * 图片链接
     */
    private String imgUrl;

    //执行接口请求
    public void doRequest() throws IOException {
        //拼装参数
        addParam();
        //请求
        byte[] resultBytes = HttpsUtil.doGet(URL + HttpUtil.parseUrlEncode(param));
        body = new String(resultBytes);

        //记录接口请求与返回日志
        BotRabbit.bot.getLogger().debug(String.format("Api Request WhatAnimeApi,param:%s,resultBody:%s", JSONObject.toJSONString(param), body));
    }

    //拼装参数
    private void addParam() {
        param.put("url", imgUrl);
    }

    //获取解析后的结果对象
    public WhatAnimeResult getEntity() {
        if (StringUtil.isEmpty(body)) {
            return null;
        }
        return JSONObject.parseObject(body, WhatAnimeResult.class);
    }
}
