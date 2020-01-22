package gugugu.apirequest;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 * create by MikuLink on 2020/1/8 18:47
 * for the Reisen
 * API请求基类，用于存放通用代码
 */
@Getter
@Setter
public class BaseRequest {
    //用于存放请求返回原始报文
    protected String body;
    //接口请求授权码
    protected String accessToken;
    //接口参数
    protected JSONObject param = new JSONObject();
}
