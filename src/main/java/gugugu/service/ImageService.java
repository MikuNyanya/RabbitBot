package gugugu.service;

import gugugu.constant.ConstantImage;
import utils.FileUtil;
import utils.ImageUtil;
import utils.RandomUtil;
import utils.StringUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * create by MikuLink on 2020/1/15 11:17
 * for the Reisen
 * 一些图片处理的方法
 * 有些图片处理的代码会跟随业务代码
 * 这里主要存放共用且业务划分不明显的处理逻辑
 */
public class ImageService {
    /**
     * 随机获取一张鸽子图
     * 伪随机
     *
     * @return 生成好的CQ码
     */
    public static String getGuguguRandom() throws IOException {
        String guguguPath = ConstantImage.DEFAULT_IMAGE_SAVE_PATH + "/gugugu";
        //如果集合为空，读取文件列表
        //只存放路径，由于使用伪随机，所以list里面的内容会一直减少
        List<String> imageGuguguList = ConstantImage.map_images.get(ConstantImage.IMAGE_MAP_KEY_GUGUGU);
        if (null == imageGuguguList || imageGuguguList.size() <= 0) {
            String[] guguguImages = FileUtil.getList(guguguPath);
            if (null == guguguImages || guguguImages.length <= 0) {
                return "";
            }
            imageGuguguList = new ArrayList<>();
            //过滤掉文件夹，和后缀不是图片的文件
            for (String imagePath : guguguImages) {
                if (!ImageUtil.isImage(imagePath)) {
                    continue;
                }
                //刷新内存中的列表
                imageGuguguList.add(imagePath);
            }
            ConstantImage.map_images.put(ConstantImage.IMAGE_MAP_KEY_GUGUGU, imageGuguguList);
        }

        //列表中有图片，随机一个，使用伪随机
        String guguguImageFullName = RandomUtil.rollAndDelStrFromList(imageGuguguList);
        //生成CQ码并返回
        return parseCQBuLocalImagePath(guguguPath + File.separator + guguguImageFullName);
    }

    /**
     * 生成本地图片的CQ码
     * 如果使用CQ码通过酷Q发送图片，该图片必须在酷Q的data\image文件夹下
     *
     * @param localImagePath 本地文件路径，带文件和后缀的那种
     * @return 生成的CQ码
     */
    public static String parseCQBuLocalImagePath(String localImagePath) throws IOException {
        if (StringUtil.isEmpty(localImagePath)) {
            return "";
        }
        //本地图片是否存在
        if (!FileUtil.exists(localImagePath)) {
            return "";
        }

        //获取图片名称
        String imageFullName = FileUtil.getFileName(localImagePath);

        //检查酷Q目录下image是否存在该图片（同名文件就行，严格点需要做MD5校验确认是否是同一文件，但没必要）
        boolean imageExists = FileUtil.exists(ConstantImage.COOLQ_IMAGE_SAVE_PATH + File.separator + imageFullName);
        if (imageExists) {
            return ImageUtil.parseCQ(imageFullName);
        }

        //把图片复制一份到酷Q目录下
        FileUtil.copy(localImagePath, ConstantImage.COOLQ_IMAGE_SAVE_PATH);

        //随机挑选一张
        return ImageUtil.parseCQ(imageFullName);
    }
}
