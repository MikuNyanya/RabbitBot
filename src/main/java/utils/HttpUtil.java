package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

/**
 * create by MikuLink on 2019/7/26 14:54
 * for the Reisen
 */
public class HttpUtil {
    //请求方式
    private static final String REQUEST_METHOD_GET = "GET";
    private static final String REQUEST_METHOD_POST = "POST";
    //链接超时时间
    private static final int CONNECT_TIME_OUT = 10000;
    //读取超时时间
    private static final int READ_TIME_OUT = 10000;
    //编码格式，UTF-8
    private static final String CHARSET_UTF8 = "UTF-8";

    /**
     * get请求
     *
     * @param connUrl 完整的请求链接
     * @return 接口返回报文
     * @throws IOException 请求异常
     */
    public static String get(String connUrl) throws IOException {
        //使用url对象打开一个链接
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(connUrl).openConnection();
        httpURLConnection.setRequestMethod(REQUEST_METHOD_GET);
        //设置链接超时时间
        httpURLConnection.setConnectTimeout(CONNECT_TIME_OUT);
        //设置返回超时时间
        httpURLConnection.setReadTimeout(READ_TIME_OUT);
        //开始链接
        httpURLConnection.connect();

        //获取错误流
//        httpURLConnection.getErrorStream();
        //获取响应流
        InputStream rspInputStream = httpURLConnection.getInputStream();
        String rspStr = parseInputStreamStr(rspInputStream);

        //关闭流
        rspInputStream.close();
        //断开连接
        httpURLConnection.disconnect();
        return rspStr;
    }

    //转化流
    private static String parseInputStreamStr(InputStream inputStream) throws IOException {
        //编码这里暂时固定utf-8，以后根据返回的编码来
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();
        String tempStr = null;
        //逐行读取
        while ((tempStr = reader.readLine()) != null) {
            stringBuilder.append(tempStr);
            stringBuilder.append("\r\n");
        }
        return stringBuilder.toString();
    }


    /**
     * 转化为urlencode
     * 采用UTF-8格式
     *
     * @param params 转化的参数
     * @return 转化后的urlencode
     * @throws IOException 转化异常
     */
    public static String parseUrlEncode(Map<String, Object> params) throws IOException {
        return parseUrlEncode(params, CHARSET_UTF8);
    }

    /**
     * 转化为urlencode
     *
     * @param params  转化的参数
     * @param charset 编码格式
     * @return 转化后的urlencode
     * @throws IOException 转化异常
     */
    public static String parseUrlEncode(Map<String, Object> params, String charset) throws IOException {
        //非空判断
        if (params == null || params.isEmpty()) {
            return null;
        }

        StringBuilder urlEncode = new StringBuilder();
        Set<Map.Entry<String, Object>> entries = params.entrySet();

        for (Map.Entry<String, Object> entry : entries) {
            String name = entry.getKey();
            String value = null;
            if (null != entry.getValue()) {
                value = entry.getValue().toString();
            }
            // 忽略参数名或参数值为空的参数
            if (StringUtil.isEmpty(name) || StringUtil.isEmpty(value)) {
                continue;
            }

            //多个参数之间使用&连接
            urlEncode.append("&");
            //转化格式，并拼接参数和值
            urlEncode.append(name).append("=").append(URLEncoder.encode(value, charset));
        }
        if (urlEncode.length() <= 0) {
            return "";
        }
        return "?" + urlEncode.substring(1);
    }


    //简易爬虫 这块代码作为参考
    public static void stealBug() throws Exception{
        //目标页面
        String danbooru = "https://danbooru.donmai.us/posts?page=1&tags=reisen_udongein_inaba";
        //通过请求获取到返回的页面
        String htmlStr = HttpUtil.get(danbooru);
        //使用jsoup解析html
        Document document = Jsoup.parse(htmlStr);
        //选择目标节点，类似于JS的选择器
        Element element = document.getElementById("posts-container");
        Elements imgList = element.getElementsByClass("post-preview");
        for (Element imgElement : imgList) {
            //拿到所需要的数据
            String imgUrl = imgElement.attributes().get("data-large-file-url");
            ImageUtil.downloadImage(imgUrl);
        }
    }
}
