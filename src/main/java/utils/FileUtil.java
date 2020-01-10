package utils;

import gugugu.constant.ConstantFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

    /**
     * 复制文件
     *
     * @param srcPathStr 原文件完整路径
     * @param desPathStr 目标文件夹路径
     * @throws IOException 文件处理异常
     */
    public static void copy(String srcPathStr, String desPathStr) throws IOException {
        //获取源文件的名称
        String newFileName = srcPathStr.substring(srcPathStr.lastIndexOf("\\") + 1);
        //目标文件地址
        desPathStr = desPathStr + File.separator + newFileName;

        //创建输入流对象
        FileInputStream inputStream = new FileInputStream(srcPathStr);
        //创建输出流对象
        FileOutputStream outputStream = new FileOutputStream(desPathStr);
        //创建搬运工具
        byte[] tempDatas = new byte[1024 * 8];

        //创建长度
        int len = 0;
        //向目标路径写入数据
        while ((len = inputStream.read(tempDatas)) != -1) {
            outputStream.write(tempDatas, 0, len);
        }
        //关闭流
        outputStream.close();
        inputStream.close();
    }

    /**
     * 移动文件
     * 原理是直接修改文件完整路径，以达到移动的目的
     *
     * @param srcPathStr 原文件完整路径
     * @param desPathStr 目标文件夹路径
     * @throws IOException 文件处理异常
     */
    public static boolean move(String srcPathStr, String desPathStr) throws IOException {
        if (StringUtil.isEmpty(srcPathStr)) {
            throw new IOException(ConstantFile.SRC_PATH_EMPTY);
        }
        if (StringUtil.isEmpty(desPathStr)) {
            throw new IOException(ConstantFile.DES_PATH_EMPTY);
        }
        File srcFile = new File(srcPathStr);
        if (!srcFile.exists()) {
            throw new IOException(ConstantFile.SRC_PATH_NOT_EXISTS);
        }
        //获取原文件名称+后缀
        String srcFileName = srcPathStr.substring(srcPathStr.lastIndexOf("\\") + 1);

        return srcFile.renameTo(new File(desPathStr + File.separator + srcFileName));
    }
}
