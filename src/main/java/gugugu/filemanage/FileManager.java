package gugugu.filemanage;

import gugugu.constant.ConstantFile;

import java.io.File;

/**
 * create by MikuLink on 2019/12/5 15:44
 * for the Reisen
 * <p>
 * 文件管理器
 * 所有的文件对象统一从这里获取，以便管理
 * 所有文件使用懒加载
 */
public class FileManager {
    //常规语句 文件
    protected static File freeTimeFile = null;
    //关键词响应 文件
    protected static File keyWordNormalFile = null;
    //模糊关键词 文件
    protected static File keyWordLikeFile = null;

    /**
     * 加载日常语句文件
     */
    public static void loadFreeTime() {
        FileManagerFreeTime.doCommand(ConstantFile.FILE_COMMAND_LOAD);
    }

    /**
     * 写入一条日常语句
     *
     * @param args 日常语句 可以一次性传递多个
     */
    public static void addFreeTimes(String... args) {
        FileManagerFreeTime.doCommand(ConstantFile.FILE_COMMAND_WRITE, args);
    }

    /**
     * 加载全匹配关键词回复文件
     */
    public static void loadKeyWordNormal() {
        FileManagerKeyWordNormal.doCommand(ConstantFile.FILE_COMMAND_LOAD);
    }

    /**
     * 写入全匹配关键词回复
     *
     * @param args 每次只能传递一个，参数中第一个为关键词，多个关键词用\分隔，后面的参数都是回复
     */
    public static void addKeyWordNormal(String... args) {
        FileManagerKeyWordNormal.doCommand(ConstantFile.FILE_COMMAND_WRITE, args);
    }

    /**
     * 加载模糊匹配关键词回复文件
     */
    public static void loadKeyWordLike() {
        FileManagerKeyWordLike.doCommand(ConstantFile.FILE_COMMAND_LOAD);
    }

    /**
     * 写入模糊匹配关键词回复
     *
     * @param args 每次只能传递一个，参数中第一个为关键词，多个关键词组用\分隔，关键词组里多个关键词用&分隔，后面的参数都是回复
     */
    public static void addKeyWordLike(String... args) {
        FileManagerKeyWordLike.doCommand(ConstantFile.FILE_COMMAND_WRITE, args);
    }
}
