package gugugu.constant;

import java.util.*;

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
    public static final String IMAGE_MAP_KEY_GUGUGU = "鸽|咕";
    //图片列表，按key分类 list里面暂时存的是图片文件路径，未来计划改为网络图片链接
    public static Map<String,List<String>> map_images = new HashMap<>();
    //图片关键词列表
    public static final List<String> LIST_KEY_IMAGES = Arrays.asList(
            IMAGE_MAP_KEY_GUGUGU
    );


    public static final String NETWORK_IMAGE_URL_IS_EMPRY = "网络图片链接为空";
}
