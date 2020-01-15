package gugugu.constant;

import java.util.ArrayList;
import java.util.List;

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

    //存放鸽子图路径，目前为项目资源目录下的images，未来想改为网络图片url
    public static List<String> gugugu_path_list = new ArrayList<>();

    public static final String NETWORK_IMAGE_URL_IS_EMPRY = "网络图片链接为空";
}
