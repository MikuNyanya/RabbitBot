package gugugu.apirequest.imgsearch;

import com.alibaba.fastjson.JSONObject;
import gugugu.apirequest.BaseRequest;
import gugugu.bots.BotRabbit;
import gugugu.entity.apirequest.imgsearch.pixiv.ImjadPixivResult;
import lombok.Getter;
import lombok.Setter;
import utils.HttpUtil;
import utils.HttpsUtil;
import utils.StringUtil;

import java.io.IOException;

/**
 * create by MikuLink on 2020/2/19 12:38
 * for the Reisen
 * 根据tag获取信息
 * https://api.imjad.cn/
 * https://zhuanlan.zhihu.com/p/35243511
 */
@Getter
@Setter
public class PixivImjadIllustSearch extends BaseRequest {
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
     * 搜索类型
     * tag 非精准tag
     * exact_tag 精准tag
     * text 标题和描述
     */
    private String mode;
    /**
     * 关键词
     */
    private String word;

    //构造
    public PixivImjadIllustSearch() {
    }

    //构造
    public PixivImjadIllustSearch(String mode, String word, int page, int pageSize) {
        this.mode = mode;
        this.word = word;
        this.page = page;
        this.pageSize = pageSize;
    }

    //执行接口请求
    public void doRequest() throws IOException {
        //拼装参数
        addParam();
        //请求
        byte[] resultBytes = HttpsUtil.doGet(URL + HttpUtil.parseUrlEncode(param), HttpUtil.getProxy());
        body = new String(resultBytes);

        //记录接口请求与返回日志
        BotRabbit.bot.getLogger().debug(String.format("Api Request PixivImjadIllustSearch,param:%s,resultBody:%s", JSONObject.toJSONString(param), body));
    }

    //拼装参数
    private void addParam() {
        //类型：搜索
        param.put("type", "search");
        param.put("page", page);
        param.put("per_page", pageSize);
        param.put("mode", mode);
        param.put("word", word);
    }

    //获取解析后的结果对象
    public ImjadPixivResult getEntity() {
        if (StringUtil.isEmpty(body)) {
            return null;
        }
        return JSONObject.parseObject(body, ImjadPixivResult.class);
    }
}
