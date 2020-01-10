package utils;

import gugugu.constant.ConstantImage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * create by MikuLink on 2019/12/12 17:38
 * for the Reisen
 * 图片
 */
public class ImageUtil {

    /**
     * 下载图片到本地
     *
     * @param imageUrl 网络图片url
     * @return 图片名称
     */
    public static String downloadImage(String imageUrl) throws IOException {
        return downloadImage(imageUrl, ConstantImage.DEFAULT_IMAGE_SAVE_PATH);
    }

    /**
     * 下载图片到本地
     *
     * @param imageUrl 网络图片url
     * @param localUrl 本地储存地址
     * @return 图片路径
     */
    public static String downloadImage(String imageUrl, String localUrl) throws IOException {
        if (StringUtil.isEmpty(imageUrl)) {
            throw new IOException(ConstantImage.NETWORK_IMAGE_URL_IS_EMPRY);
        }

        //创建连接
        URL url = new URL(imageUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置请求方式
        conn.setRequestMethod("GET");
        //设置链接超时时间
        conn.setConnectTimeout(5 * 1000);

        //获取输出流
        InputStream inStream = conn.getInputStream();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        //把图片信息存下来，写入内存
        byte[] data = outStream.toByteArray();

        //使用网络图片的中段目录
//            String path = imageUrl.substring(imageUrl.indexOf("n/") + 1, imageUrl.lastIndexOf("/"));

        //创建本地文件
        File result = new File(localUrl);
        if (!result.exists()) {
            result.mkdirs();
        }

        //使用网络图片的名称和后缀
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

        //写入图片数据
        String fileFullNmae = result + File.separator + fileName;
        FileOutputStream fileOutputStream = new FileOutputStream(fileFullNmae);
        fileOutputStream.write(data);
        fileOutputStream.flush();
        fileOutputStream.close();

        //返回文件路径
        return fileFullNmae;
    }

    /**
     * 判断文件是否存在
     *
     * @param imageUrl 文件路径
     * @return  是否存在
     */
    public static boolean isExists(String imageUrl) {
        if (StringUtil.isEmpty(imageUrl)) {
            return false;
        }
        //创建本地文件
        File result = new File(imageUrl);
        return result.exists();
    }
}
