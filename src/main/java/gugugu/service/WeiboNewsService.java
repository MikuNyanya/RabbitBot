package gugugu.service;

import gugugu.apirequest.WeiboHomeTimelineGet;
import gugugu.constant.ConstantImage;
import gugugu.constant.ConstantWeiboNews;
import gugugu.entity.apirequest.InfoPicUrl;
import gugugu.entity.apirequest.InfoStatuses;
import gugugu.entity.apirequest.InfoWeiboHomeTimeline;
import gugugu.exceptions.RabbitException;
import utils.FileUtil;
import utils.ImageUtil;
import utils.StringUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * create by MikuLink on 2020/1/9 16:42
 * for the Reisen
 * 与微博推文交互的服务
 */
public class WeiboNewsService {

    /**
     * 获取微信的最新消息
     *
     * @param pageSize 每页大小，默认为5
     * @return 微博查询接口返回的对象
     * @throws IOException 接口异常
     */
    public static InfoWeiboHomeTimeline getWeiboNews(Integer pageSize) throws IOException, RabbitException {
        //检查授权码
        if (StringUtil.isEmpty(ConstantWeiboNews.accessToken)) {
            throw new RabbitException(ConstantWeiboNews.NO_ACCESSTOKEN);
        }

        if (null == pageSize) {
            pageSize = 5;
        }

        WeiboHomeTimelineGet request = new WeiboHomeTimelineGet();
        request.setAccessToken(ConstantWeiboNews.accessToken);
        request.setPage(1);
        //每次获取最近的5条
        request.setCount(pageSize);
        request.setSince_id(ConstantWeiboNews.sinceId);

        //发送请求
        request.doRequest();
        return request.getEntity();
    }

    /**
     * 给给每个群推送微博
     *
     * @param statuses 从微博API获取的推文列表
     */
    public static void sendWeiboNewsToEveryGroup(List<InfoStatuses> statuses) throws InterruptedException, IOException {
        if (null == statuses || statuses.size() == 0) {
            return;
        }

        //发送微博
        for (InfoStatuses info : statuses) {
            //过滤转发微博
            if (null != info.getRetweeted_status()) {
                continue;
            }
            //解析微博报文
            String msg = parseWeiboBody(info);
            //给每个群发送消息
            RabbitBotService.sendEveryGroupMsg(msg);
            //每次发送之间间隔一点时间，免得瞬间刷屏太厉害
            Thread.sleep(1000L * 5);
        }
    }

    /**
     * 执行一次微博群消息推送
     */
    public static void doPushWeiboNews() throws IOException, InterruptedException, RabbitException {
        InfoWeiboHomeTimeline weiboNews = getWeiboNews(30);
        Long sinceId = weiboNews.getSince_id();
        //刷新最后推文标识，但如果一次请求中没有获取到新数据，since_id会为0
        if (0 != sinceId) {
            ConstantWeiboNews.sinceId = sinceId;
        }
        sendWeiboNewsToEveryGroup(weiboNews.getStatuses());
    }

    /**
     * 解析微博信息
     *
     * @param info 微博信息
     * @return 转化好的字符串，可以直接丢给酷Q发群里
     * @throws IOException 处理异常
     */
    private static String parseWeiboBody(InfoStatuses info) throws IOException {
        //解析推主头像
        String userImgCQ = getWeiboImageCQ(info.getUser().getProfile_image_url());

        //格式
        StringBuilder msgBuilder = new StringBuilder();
        //头像
        msgBuilder.append(userImgCQ + "\n");
        //推主名
        msgBuilder.append("[" + info.getUser().getName() + "]\n");
        //微博id
        msgBuilder.append("[" + info.getUser().getId() + "]\n");
        //推文时间
        msgBuilder.append("[" + info.getCreated_at() + "]\n");
        msgBuilder.append("====================\n");
        //正文
        msgBuilder.append(info.getText() + "\n");

        //拼接推文图片
        List<InfoPicUrl> picList = info.getPic_urls();
        if (null == picList || picList.size() <= 0) {
            return msgBuilder.toString();
        }

        for (InfoPicUrl picUrl : picList) {
            if (StringUtil.isEmpty(picUrl.getThumbnail_pic())) {
                continue;
            }
            //获取原图地址
            String largeImageUrl = getImgLarge(picUrl.getThumbnail_pic());
            //转化为CQ码
            String largeImageCQ = getWeiboImageCQ(largeImageUrl);
            msgBuilder.append(largeImageCQ + "\n");
        }
        return msgBuilder.toString();
    }

    /**
     * 可以尝试获取微博正文中的原图
     *
     * @param imageUrl 图片链接
     * @return 原图链接
     */
    private static String getImgLarge(String imageUrl) {
        //一般缩略图是这样的 http://wx4.sinaimg.cn/thumbnail/006QZngZgy1gaqfywbnqlg30dw0hzu0z.gif
        //原图链接是这样的   http://wx4.sinaimg.cn/large/006QZngZgy1gaqfywbnqlg30dw0hzu0z.gif
        //发现不一样的地方只有中段，thumbnail是缩略图 bmiddle是压过的图 large是原图
        //GIF目前发现只有原图会动，所以把中部替换掉就能获取到原图链接
        return imageUrl.replace("thumbnail", "large");
    }

    //解析微博图片为CQ码
    private static String getWeiboImageCQ(String imageUrl) throws IOException {
        if (StringUtil.isEmpty(imageUrl)) {
            return null;
        }
        //针对头像的不标准链接特殊处理
        if (imageUrl.contains("?")) {
            imageUrl = imageUrl.substring(0, imageUrl.lastIndexOf("?"));
        }
        String imageName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

        String coolqImageFullName = ConstantImage.COOLQ_IMAGE_SAVE_PATH + File.separator + imageName;
        //先判断之前有没有解析过该图片，有的话直接返回CQ码
        boolean isExists = ImageUtil.isExists(coolqImageFullName);
        if (isExists) {
            return String.format(ConstantImage.IMAGE_CQ, imageName);
        }

        //先把图片下载下来
        String localImageUrl = ImageUtil.downloadImage(imageUrl);

        //然后丢到酷Q的image目录下，不然不能发送，酷Q不能从其他目录发送图片
        boolean result = FileUtil.move(localImageUrl, ConstantImage.COOLQ_IMAGE_SAVE_PATH);
        if (!result) {
            return null;
        }

        return String.format(ConstantImage.IMAGE_CQ, imageName);
    }
}
