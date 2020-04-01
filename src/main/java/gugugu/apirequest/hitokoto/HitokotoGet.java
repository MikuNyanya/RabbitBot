package gugugu.apirequest.hitokoto;

import com.alibaba.fastjson.JSONObject;
import gugugu.apirequest.BaseRequest;
import gugugu.bots.LoggerRabbit;
import gugugu.entity.hitokoto.HitokotoInfo;
import lombok.Getter;
import lombok.Setter;
import utils.HttpUtil;
import utils.StringUtil;

import java.io.IOException;

/**
 * create by MikuLink on 2020/3/16 14:30
 * for the Reisen
 * 一言
 * https://developer.hitokoto.cn/sentence
 * 他们接口，有点初学者的感觉，数据也很乱
 */
@Setter
@Getter
@Deprecated
public class HitokotoGet extends BaseRequest {
    private static final String URL = "https://v1.hitokoto.cn";

    /**
     * a	动画
     * b	漫画
     * c	游戏
     * d	文学
     * e	原创
     * f	来自网络
     * g	其他
     * h	影视
     * i	诗词
     * j	网易云
     * k	哲学
     * l	抖机灵
     * <p>
     * 默认为动画
     * 接口那边可以选择多个，但传入方式有点奇怪
     * c=a&c=b
     * 而且实际用起来感觉随机性太大
     * 所以自己在业务里控制吧
     */
    private String c;

    //执行接口请求
    public void doRequest() throws IOException {
        //拼装参数
        addParam();

        body = HttpUtil.get(URL + HttpUtil.parseUrlEncode(param));

        //记录接口请求与返回日志
        LoggerRabbit.logger().debug(String.format("Api Request HitokotoGet,param:%s,resultBody:%s", JSONObject.toJSONString(param), body));
    }

    //拼装参数
    private void addParam() {
        param.put("c", c);
        //使用json返回
        param.put("encode", "json");
        //utf-8编码
        param.put("charset", "utf-8");
    }

    //获取一言数据
    public HitokotoInfo getEntity() {
        if (StringUtil.isEmpty(body)) {
            return null;
        }
        return JSONObject.parseObject(body, HitokotoInfo.class);
    }
}
