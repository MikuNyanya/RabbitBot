package gugugu.constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by MikuLink on 2020/1/10 15:19
 * for the Reisen
 * 图片相关
 */
public class ConstantImage extends ConstantCommon {
    //默认的文件储存路径
    public static final String DEFAULT_IMAGE_SAVE_PATH = "src/main/resources/images";
    //酷Q的图片目录
    public static final String COOLQ_IMAGE_SAVE_PATH = "D:/酷Q Pro/data/image";

    //酷Q所需要的图片CQ码 占位符处是文件名（带后缀）
    public static String IMAGE_CQ = "[CQ:image,file=%s]";


    //鸽子图片的map key
    public static final String IMAGE_MAP_KEY_GUGUGU = "鸽了|咕咕|咕了";
    //图片列表，按key分类 list里面暂时存的是图片文件路径，未来计划改为网络图片链接
    public static Map<String, List<String>> map_images = new HashMap<>();
    //图片关键词列表
    public static final List<String> LIST_KEY_IMAGES = Arrays.asList(
            IMAGE_MAP_KEY_GUGUGU
    );


    public static final String NETWORK_IMAGE_URL_IS_EMPRY = "网络图片链接为空";

    //搜图相关
    public static final String IMAGE_SEARCH_NO_IMAGE_INPUT = "你得给我一张图";
    public static final String IMAGE_SEARCH_IMAGE_URL_PARSE_FAIL = "图片解析失败......啊咧？";

    public static final String SAUCENAO_API_KEY = "saucenaoKey";
    public static final String SAUCENAO_API_KEY_EMPTY = "Saucenao Api Key为空";
    public static final String SAUCENAO_API_SEARCH_FAIL = "Saucenao Api请求失败";
    public static final String SAUCENAO_API_REQUEST_ERROR = "Saucenao Api请求异常";
    public static final String SAUCENAO_SEARCH_FAIL = "Saucenao识图失败，没有任何结果";
    public static final String SAUCENAO_SEARCH_FAIL_PART = "Saucenao识图失败，没有在P站和Danbooru获得结果";
    public static final String SAUCENAO_SEARCH_FAIL_PARAM = "Saucenao识图失败，没有符合条件的结果:[源于P站或Danbooru，相似度50%以上]";

    public static final String IMJAD_PIXIV_ID_API_ERROR = "imjad Pixiv Api请求异常";
    public static final String IMJAD_PIXIV_ID_API_FAIL = "imjad Pixiv Api请求失败";

    //配置，p站
    public static final int PIXIV_IMAGE_PAGESIZE = 5;

    public static final String PIXIV_IMAGE_IGNORE = "pixivImageIgnore";
    public static final String PIXIV_IMAGE_IGNORE_WARNING = "[P站图片已忽略加载]";
    public static final String PIXIV_IMAGE_DOWNLOAD_FAIL = "[P站图片下载失败]";
    public static final String PIXIV_IMAGE_DELETE = "[P站图片已被删除]";
    public static final String PIXIV_IMAGE_RANK_ERROR = "P站日榜信息获取异常";
    public static final String PIXIV_IMAGE_RANK_JOB_ERROR = "P站日榜信息推送异常";
    public static final String PIXIV_IMAGE_ID_IS_EMPTY = "你得给我一个p站图片id";
    public static final String PIXIV_IMAGE_ID_IS_NUMBER_ONLY = "p站图片id只会是纯数字";

    public static final String PIXIV_ID_GET_FAIL_GROUP_MESSAGE = "根据P站图片id获取信息失败";
    public static final String PIXIV_ID_GET_ERROR_GROUP_MESSAGE = "根据P站图片id获取信息异常";

    public static final String DANBOORU_ID_GET_NOT_FOUND = "根据Danbooru图片id没有找到图片";
    public static final String DANBOORU_IMAGE_DOWNLOAD_FAIL = "Danbooru图片下载失败";
    public static final String DANBOORU_ID_GET_FAIL_GROUP_MESSAGE = "根据Danbooru图片id获取信息失败";
    public static final String DANBOORU_ID_GET_ERROR_GROUP_MESSAGE = "根据Danbooru图片id获取信息异常";
}
