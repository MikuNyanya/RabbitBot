package gugugu.constant;

/**
 * create by MikuLink on 2019/12/3 19:51
 * for the Reisen
 * <p>
 * 存一些文件相关信息 不过应该写成配置文件的
 */
public class ConstantFile extends ConstantCommon {
    //配置 文件相对路径
    public static final String CONFIG_FILE_PATH = "src/main/resources/files/config";
    //扭蛋 文件相对路径
    public static final String CAPSULE_TOY_FILE_PATH = "src/main/resources/files/capsule_toy.txt";
    //客制化 常规语句 文件相对路径
    public static final String APPEND_FREE_TIME_FILE_PATH = "src/main/resources/files/free_time.txt";
    //客制化 关键词响应语句 文件相对路径
    public static final String APPEND_KEYWORD_FILE_NORMAL_PATH = "src/main/resources/files/key_word_normal.txt";
    //客制化 模糊关键词响应语句 文件相对路径
    public static final String APPEND_KEYWORD_FILE_LIKE_PATH = "src/main/resources/files/key_word_like.txt";
    //客制化 高德地图城市代码 文件相对路径
    public static final String APPEND_AMAPADCODE_FILE_PATH = "src/main/resources/files/AMap_adcode_citycode.txt";
    //客制化 摩尔斯电码对照表 文件相对路径
    public static final String APPEND_MORSECODE_FILE_PATH = "src/main/resources/files/morsecode.txt";

    //加载文件 到系统
    public static final String FILE_COMMAND_LOAD = "load";
    //对文件写入
    public static final String FILE_COMMAND_WRITE = "write";

    public static final String SRC_PATH_EMPTY = "原路径为空";
    public static final String DES_PATH_EMPTY = "目标路径为空";
    public static final String SRC_PATH_NOT_EXISTS = "原路径或文件不存在";
    public static final String DES_PATH_NOT_EXISTS = "目标路径或文件不存在";
}
