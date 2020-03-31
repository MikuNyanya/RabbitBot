package gugugu.apirequest.saucenao;

import com.alibaba.fastjson.JSONObject;
import gugugu.apirequest.BaseRequest;
import gugugu.bots.BotRabbit;
import gugugu.entity.apirequest.saucenao.SaucenaoSearchResult;
import lombok.Getter;
import lombok.Setter;
import utils.HttpUtil;
import utils.StringUtil;

import java.io.IOException;

/**
 * create by MikuLink on 2020/2/19 12:38
 * for the Reisen
 * saucenao的图片搜索接口
 * https://saucenao.com/user.php?page=search-api
 */
@Getter
@Setter
public class SaucenaoImageSearch extends BaseRequest {
    private static final String URL = "https://saucenao.com/search.php";

    /**
     * 看不懂，大概是特别的搜索条件
     * search a specific index number or all without needing to generate a bitmask.
     */
    private Integer db;
    /**
     * 接口数据格式，一般用json
     * 0=normal html
     * 1=xml api(not implemented)
     * 2=json api
     */
    private Integer output_type = 2;
    /**
     * 搜索结果数目，一般一两个就行，网页上默认6个
     * Change the number of results requested.
     */
    private Integer numres;
    /**
     * 网络图片链接
     */
    private String url;

    //执行接口请求
    public void doRequest() throws IOException {
        //拼装参数
        addParam();
        //请求
        body = HttpUtil.get(URL + HttpUtil.parseUrlEncode(param));

        //记录接口请求与返回日志
        BotRabbit.bot.getLogger().debug(String.format("Api Request SaucenaoImageSearch,param:%s,resultBody:%s", JSONObject.toJSONString(param), body));
    }

    //拼装参数
    private void addParam() {
        param.put("api_key", accessToken);
        param.put("db", db);
        param.put("output_type", output_type);
        param.put("numres", numres);
        param.put("url", url);
    }

    //获取解析后的结果对象
    public SaucenaoSearchResult getEntity() {
        if (StringUtil.isEmpty(body)) {
            return null;
        }
        return JSONObject.parseObject(body, SaucenaoSearchResult.class);
    }
}
