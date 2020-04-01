package gugugu.apirequest.imjad;

import com.alibaba.fastjson.JSONObject;
import gugugu.apirequest.BaseRequest;
import gugugu.bots.LoggerRabbit;
import gugugu.entity.apirequest.imjad.ImjadPixivResult;
import lombok.Getter;
import lombok.Setter;
import utils.HttpUtil;
import utils.HttpsUtil;
import utils.StringUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * create by MikuLink on 2020/2/19 12:38
 * for the Reisen
 * 根据p站图片id获取插画图片信息
 * https://api.imjad.cn/
 * https://zhuanlan.zhihu.com/p/35243511
 */
@Getter
@Setter
public class PixivImjadIllustGet extends BaseRequest {
    private static final String URL = "https://api.imjad.cn/pixiv/v1/";

    /**
     * p站图片id
     */
    private Long pixivId;

    //执行接口请求
    public void doRequest() throws IOException {
        //拼装参数
        addParam();
        //请求
        byte[] resultBytes = HttpsUtil.doGet(URL + HttpUtil.parseUrlEncode(param), null);
        body = new String(resultBytes);

        //记录接口请求与返回日志
        LoggerRabbit.logger().debug(String.format("Api Request PixivImjadIllustGet,param:%s,resultBody:%s", JSONObject.toJSONString(param), body));
    }

    //拼装参数
    private void addParam() {
        //类型：插画
        param.put("type", "illust");
        param.put("id", pixivId);
    }

    //获取解析后的结果对象
    public ImjadPixivResult getEntity() {
        if (StringUtil.isEmpty(body)) {
            return null;
        }
        return JSONObject.parseObject(body, ImjadPixivResult.class);
    }

    public boolean isSuccess() {
        if (StringUtil.isEmpty(body)) {
            return false;
        }
        Map<?, ?> rootMap = JSONObject.parseObject(body, HashMap.class);
        if (null == rootMap
                || !rootMap.containsKey("status")
                || !"success".equalsIgnoreCase(String.valueOf(rootMap.get("status")))) {
            return false;
        }
        return true;
    }
}
