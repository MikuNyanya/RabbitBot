package gugugu.service;

import gugugu.apirequest.imgsearch.PixivImjadIllustGet;
import gugugu.apirequest.imgsearch.PixivImjadIllustRankGet;
import gugugu.apirequest.imgsearch.PixivImjadIllustSearch;
import gugugu.bots.BotRabbit;
import gugugu.constant.ConstantCommon;
import gugugu.constant.ConstantImage;
import gugugu.entity.InfoPixivRankImage;
import gugugu.entity.apirequest.imgsearch.pixiv.*;
import gugugu.entity.apirequest.imgsearch.saucenao.SaucenaoSearchInfoResult;
import utils.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Proxy;
import java.util.*;

/**
 * @author MikuLink
 * @date 2020/02/20 18:33
 * for the Reisen
 * p站相关服务
 */
public class PixivService {

    /**
     * 获取当前P站日榜数据
     *
     * @param page     页数
     * @param pageSize 每页大小
     * @return 日榜图片信息
     */
    public static List<InfoPixivRankImage> getPixivIllustRank(Integer page, Integer pageSize) throws IOException {
        List<InfoPixivRankImage> rankImageList = new ArrayList<>();

        //请求接口获取日榜信息
        PixivImjadIllustRankGet request = new PixivImjadIllustRankGet();
        //日榜
        request.setMode("daily");
        //插画
        request.setContent("illust");
        request.setPage(page);
        request.setPageSize(pageSize);
        //时间 12点前获取不到昨日排行，所以这种时候获取前天的
        int beforeDay = -1;
        if (DateUtil.getHour() < 12) {
            beforeDay = -2;
        }
        Date paramDate = DateUtil.dateChange(new Date(), Calendar.DAY_OF_MONTH, beforeDay);
        request.setDate(DateUtil.toString(paramDate, "yyyy-MM-dd"));

        request.doRequest();

        //解析返回报文
        ImjadPixivRankResult result = request.getEntity();
        List<ImjadPixivRankWork> imageList = result.getResponse().get(0).getWorks();
        for (ImjadPixivRankWork image : imageList) {
            ImjadPixivRankWorkWork imageDetail = image.getWork();
            //下载图片
            String imgCQ = getPixivImgCQByPixivImgUrl(imageDetail.getImage_urls().getLarge(), Long.valueOf(imageDetail.getId()));

            //拼接成对象并返回
            InfoPixivRankImage rankImageInfo = new InfoPixivRankImage();
            //图片cq码
            rankImageInfo.setImgCQ(imgCQ);
            //排行
            rankImageInfo.setRank(image.getRank());
            //过去排行
            rankImageInfo.setPreviousRank(image.getPrevious_rank());
            //pid
            rankImageInfo.setPid((long) imageDetail.getId());
            //标题
            rankImageInfo.setTitle(imageDetail.getTitle());
            //简介
            rankImageInfo.setCaption(imageDetail.getCaption());
            //创建时间
            rankImageInfo.setCreatedTime(imageDetail.getCreated_time());
            //总P数
            rankImageInfo.setPageCount(imageDetail.getPage_count());
            //作者id
            rankImageInfo.setUserId((long) imageDetail.getUser().getId());
            //作者名称
            rankImageInfo.setUserName(imageDetail.getUser().getName());

            rankImageList.add(rankImageInfo);
        }

        return rankImageList;
    }

    /**
     * 根据标签，搜索出一张图片
     * 会从所有结果中随机出一张
     * 根据图片分数会有不同的随机权重
     *
     * @param tag 标签 参数在上一层过滤好再进来
     * @return 拼装好的群消息
     */
    public static String getPixivIllustByTag(String tag) throws IOException {
        if (StringUtil.isEmpty(tag)) {
            return "";
        }

        int pageSize = 30;

        //1.查询这个tag下的总结果
        PixivImjadIllustSearch request = new PixivImjadIllustSearch("tag", tag, 1, 1);
        request.doRequest();
        ImjadPixivResult result = request.getEntity();
        ImjadPixivPagination pagination = result.getPagination();
        //总结果数量
        int total = pagination.getTotal();

        //2.随机获取结果中的一条
        //先按照指定页数算出有多少页，随机其中一页
        int totalPage = NumberUtil.toIntUp(total / pageSize * 1.0);
        //随机一个页数
        int randomPage = RandomUtil.roll(totalPage);
        if (0 >= randomPage) {
            randomPage = 1;
        }

        //获取该页数的数据
        request = new PixivImjadIllustSearch("tag", tag, randomPage, pageSize);
        request.doRequest();
        //这里面至少会有1条有效数据
        result = request.getEntity();

        //累积得分
        Integer scoredCount = 0;
        Map<Long, Integer> scoredMap = new HashMap<>();
        Map<Object, Double> additionMap = new HashMap<>();
        Map<Long, ImjadPixivResponse> imgRspMap = new HashMap<>();

        List<ImjadPixivResponse> responses = result.getResponse();
        for (ImjadPixivResponse response : responses) {
            Integer scored = response.getStats().getScored_count();
            scoredCount += scored;
            scoredMap.put(response.getId(), scored);
            imgRspMap.put(response.getId(), response);
        }

        //计算权重
        for (Long pixivId : scoredMap.keySet()) {
            Integer score = scoredMap.get(pixivId);
            //结果肯定介于0-1之间，然后换算成百分比，截取两位小数
            Double addition = NumberUtil.keepDecimalPoint((score * 1.0) / scoredCount * 100.0, 2);
            additionMap.put(pixivId, addition);
        }

        //根据权重随机出一个元素
        Object obj = RandomUtil.rollObjByAddition(additionMap);

        //3.获取该图片信息
        Long pixivId = (Long) obj;
        ImjadPixivResponse respInfo = imgRspMap.get(pixivId);

        //4.返回结果
        return parsePixivImgInfoByApiInfo(respInfo, null);
    }

    /**
     * 拼装识图结果_p站
     * 搜索结果只会取一个
     *
     * @param infoResult 识图结果
     * @return 拼装好的群消息
     */
    public static String parsePixivImgRequest(SaucenaoSearchInfoResult infoResult) {
        return searchPixivImgById(infoResult, null);
    }

    /**
     * 根据p站图片id查询p站图片
     *
     * @param pid p站图片id
     * @return 拼装好的群消息
     */
    public static String searchPixivImgById(Long pid) {
        return searchPixivImgById(null, pid);
    }

    /**
     * 查询p站图片id并返回结果
     * 参数二选一
     *
     * @param infoResult Saucenao搜图结果
     * @param pid        p站id
     * @return 拼装好的群消息
     */
    private static String searchPixivImgById(SaucenaoSearchInfoResult infoResult, Long pid) {
        //p站图片id
        Integer pixivId = null;
        if (null != infoResult) {
            pixivId = infoResult.getData().getPixiv_id();
        } else {
            pixivId = NumberUtil.toInt(pid.toString());
        }


        //根据pid获取图片列表
        ImjadPixivResult pixivResult = null;
        try {
            pixivResult = getImgsByPixivId(pixivId);

            if (null == pixivResult) {
                return ConstantImage.PIXIV_ID_GET_FAIL_GROUP_MESSAGE + "(" + pixivId + ")";
            }
            if (null != pixivResult.getHas_error() && pixivResult.getHas_error()) {
                //接口错误信息
                return pixivResult.getErrors().getSystem().getMessage() + "(" + pixivId + ")";
            }

            //Saucenao搜索结果相似度
            String similarity = null;
            if (null != infoResult) {
                similarity = infoResult.getHeader().getSimilarity();
            }

            List<ImjadPixivResponse> responses = pixivResult.getResponse();
            ImjadPixivResponse response = responses.get(0);

            return parsePixivImgInfoByApiInfo(response, similarity);
        } catch (IOException ioEx) {
            BotRabbit.bot.getLogger().error("PixivService " + ConstantImage.IMJAD_PIXIV_ID_API_ERROR + ioEx.toString(), ioEx);
            return ConstantImage.PIXIV_ID_GET_ERROR_GROUP_MESSAGE;
        }
    }

    /**
     * 根据pid获取p站图片地址
     * 因为有多P，所以返回的是集合
     *
     * @param pixivId p站图片id
     * @return 接口返回结果
     */
    private static ImjadPixivResult getImgsByPixivId(int pixivId) throws IOException {
        PixivImjadIllustGet request = new PixivImjadIllustGet();
        request.setPixivId(pixivId);
        request.doRequest();
        return request.getEntity();
    }

    /**
     * 根据p站图片链接下载图片
     * 带图片后缀的那种，比如
     * https://i.pximg.net/img-original/img/2018/03/31/01/10/08/67994735_p0.png
     *
     * @param url     p站图片链接
     * @param pixivId p站图片id，用于防爬链，必须跟url中的id一致
     * @return 下载后的本地连接
     */
    private static String getPixivImgByPixivImgUrl(String url, Long pixivId) throws IOException {
        BotRabbit.bot.getLogger().debug("Pixiv image download:" + url);
        //加入p站防爬链
        //目前一共遇到的
        //1.似乎是新连接，最近UI改了 https://i.pximg.net/img-original/img/2020/02/17/22/07/00/79561788_p0.jpg
        //Referer: https://www.pixiv.net/artworks/79561788
        //2.没研究出来的链接，还是403，但是把域名替换成正常链接的域名，可以正常获取到数据 https://i-cf.pximg.net/img-original/img/2020/02/17/22/07/00/79561788_p0.jpg
        HashMap<String, String> header = new HashMap<>();
        if (url.contains("i-cf.pximg.net")) {
            url = url.replace("i-cf.pximg.net", "i.pximg.net");
        }
        header.put("referer", "https://www.pixiv.net/artworks/" + pixivId);
        // 创建代理
        Proxy proxy = HttpUtil.getProxy();
        //下载图片
        return ImageUtil.downloadImage(header, url, ConstantImage.DEFAULT_IMAGE_SAVE_PATH + File.separator + "pixiv", null, proxy);
    }

    /**
     * 根据p站图片资源链接生成CQ码
     *
     * @param url p站图片链接
     * @return cq码或者错误信息
     */
    private static String getPixivImgCQByPixivImgUrl(String url, Long pixivId) throws IOException {
        //先检测是否已下载，如果已下载直接返回CQ，以p站图片名称为key
        String pixivImgFileName = url.substring(url.lastIndexOf("/") + 1);
        String localPixivFilePath = ConstantImage.DEFAULT_IMAGE_SAVE_PATH + File.separator + "pixiv" + File.separator + pixivImgFileName;
        if (FileUtil.exists(localPixivFilePath)) {
            return ImageService.parseCQBuLocalImagePath(localPixivFilePath);
        }

        //是否不加载p站图片，由于从p站本体拉数据，还没代理，很慢
        String pixiv_image_ignore = ConstantCommon.common_config.get(ConstantImage.PIXIV_IMAGE_IGNORE);
        if ("1".equalsIgnoreCase(pixiv_image_ignore)) {
            return ConstantImage.PIXIV_IMAGE_IGNORE_WARNING;
        }

        String imgCQ = null;
        try {
            String localUrl = getPixivImgByPixivImgUrl(url, pixivId);
            if (StringUtil.isNotEmpty(localUrl)) {
                imgCQ = ImageService.parseCQBuLocalImagePath(localUrl);
            }
            if (StringUtil.isEmpty(imgCQ)) {
                imgCQ = ConstantImage.PIXIV_IMAGE_DOWNLOAD_FAIL;
            }
        } catch (FileNotFoundException fileNotFoundEx) {
            //图片被删了
            BotRabbit.bot.getLogger().error("PixivService getPixivImgCQByPixivImgUrl " + ConstantImage.PIXIV_IMAGE_DELETE + fileNotFoundEx.toString(), fileNotFoundEx);
            imgCQ = ConstantImage.PIXIV_IMAGE_DELETE;
        }
        return imgCQ;
    }

    /**
     * 接口返回的图片信息拼装为群消息
     *
     * @param response   接口返回对象
     * @param similarity 相似度
     * @return 群消息
     * @throws IOException api异常
     */
    private static String parsePixivImgInfoByApiInfo(ImjadPixivResponse response, String similarity) throws IOException {
        //图片标题
        String title = response.getTitle();
        //图片简介
        String caption = response.getCaption();
        //作者名称
        String memberName = response.getUser().getName();
        //图片创建时间
        String createDate = response.getCreated_time();

        //图片cq码
        String pixivImgCQ = getPixivImgCQByPixivImgUrl(response.getImage_urls().getLarge(), response.getId());

        StringBuilder resultStr = new StringBuilder();
        resultStr.append(pixivImgCQ);
        if (null != response.getMetadata()) {
            resultStr.append("\n该Pid包含多张图片");
        }
        if (StringUtil.isNotEmpty(similarity)) {
            resultStr.append("\n[相似度] " + similarity + "%");
        }
        resultStr.append("\n[P站id] " + response.getId());
        resultStr.append("\n[标题] " + title);
        resultStr.append("\n[作者] " + memberName);
        resultStr.append("\n[上传时间] " + createDate);
//            resultStr.append("\n[图片简介] " + caption);
        return resultStr.toString();
    }

    /**
     * p站图片信息转化为群消息
     *
     * @param infoPixivRankImage p站图片对象
     * @return 群消息
     */
    public static String parsePixivImgInfoToGroupMsg(InfoPixivRankImage infoPixivRankImage) {
        StringBuilder resultStr = new StringBuilder();
        resultStr.append(infoPixivRankImage.getImgCQ());
        resultStr.append("\n[排名] " + infoPixivRankImage.getRank());
        resultStr.append("\n[昨日排名] " + infoPixivRankImage.getPreviousRank());
        resultStr.append("\n[P站id] " + infoPixivRankImage.getPid());
        resultStr.append("\n[标题] " + infoPixivRankImage.getTitle());
        resultStr.append("\n[作者] " + infoPixivRankImage.getUserName());
        resultStr.append("\n[创建时间] " + infoPixivRankImage.getCreatedTime());
        return resultStr.toString();
    }
}
