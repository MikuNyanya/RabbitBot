package utils;

import java.io.File;
import java.io.IOException;

/**
 * create by MikuLink on 2019/12/5 16:00
 * for the Reisen
 * <p>
 * 文件工具
 */
public class FileUtil {
    /**
     * 检验文件，不存在则创建
     *
     * @param filePath 文件路径
     * @return 指定路径的文件
     * @throws IOException 文件处理异常
     */
    public static File fileCheck(String filePath) throws IOException {
        File file = new File(filePath);

        //存在则直接返回
        if (file.exists()) {
            return file;
        }
        //不存在的时候，先创建目录
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            //可创建多级目录，并且只能创建目录，无法用来创建文件
            parentFile.mkdirs();
        }
        file.createNewFile();

        return file;
    }
}
