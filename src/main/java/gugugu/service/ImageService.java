package gugugu.service;

import gugugu.apirequest.imgsearch.PixivImjadIllustGet;
import gugugu.apirequest.imgsearch.SaucenaoImageSearch;
import gugugu.bots.BotRabbit;
import gugugu.constant.ConstantCommon;
import gugugu.constant.ConstantImage;
import gugugu.entity.apirequest.imgsearch.pixiv.ImjadPixivResponse;
import gugugu.entity.apirequest.imgsearch.pixiv.ImjadPixivResult;
import gugugu.entity.apirequest.imgsearch.saucenao.SaucenaoSearchInfoResult;
import gugugu.entity.apirequest.imgsearch.saucenao.SaucenaoSearchResult;
import utils.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
     * 根据q号获取头像CQ码，获取不到会返回空
     * 大概100x100
     * 使用的是第三方接口
     *
     * @param qq q号
     * @return 生成好的cq码
     */
    public static String getQLogoCq(Long qq) {
        if (null == qq) {
            return null;
        }
        String url = String.format("http://q.qlogo.cn/g?b=qq&s=100&nk=%s", qq);
        //链接无后缀，是重定向后直接返回图片的，所以使用qq名称作为图片名
        String qlogoImgName = String.format("qlogo_%s.jpg", qq);

        try {
            //检查酷Q目录下image是否存在该图片，存在就删掉重新下载，毕竟头像会变的
            String coolqImgPath = ConstantImage.COOLQ_IMAGE_SAVE_PATH + File.separator + qlogoImgName;
            boolean imageExists = FileUtil.exists(coolqImgPath);
            if (imageExists) {
                FileUtil.delete(coolqImgPath);
            }

            //下载头像
            String qlogoLocalPath = ImageUtil.downloadImage(url, ConstantImage.DEFAULT_IMAGE_SAVE_PATH + File.separator + "qlogo", qlogoImgName);

            //返回cq
            return parseCQBuLocalImagePath(qlogoLocalPath);
        } catch (Exception ex) {
            BotRabbit.bot.getLogger().error("qq[" + qq + "]获取头像异常:" + ex.toString(), ex);
            return null;
        }
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

    /**
     * 使用Saucenao搜图
     *
     * @param imgUrl 网络图片链接
     * @return 搜索结果
     */
    public static String searchImgFromSaucenao(String imgUrl) {
        String apiKey = ConstantCommon.common_config.get(ConstantImage.SAUCENAO_API_KEY);
        if (StringUtil.isEmpty(apiKey)) {
            BotRabbit.bot.getLogger().warning(ConstantImage.SAUCENAO_API_KEY_EMPTY);
            return ConstantImage.SAUCENAO_API_KEY_EMPTY;
        }
        SaucenaoSearchInfoResult searchResult = null;
        try {
            //调用API
            SaucenaoImageSearch request = new SaucenaoImageSearch();
            request.setAccessToken(apiKey);
            request.setNumres(1);
            request.setUrl(imgUrl);

            request.doRequest();
            SaucenaoSearchResult saucenaoSearchResult = request.getEntity();
            if (null == saucenaoSearchResult) {
                return ConstantImage.SAUCENAO_API_SEARCH_FAIL;
            }

            //解析结果 header基本不用看，看结果就行，取第一个
            List<SaucenaoSearchInfoResult> infoResultList = saucenaoSearchResult.getResults();
            if (null == infoResultList || infoResultList.size() <= 0) {
                return ConstantImage.SAUCENAO_SEARCH_FAIL;
            }
            for (SaucenaoSearchInfoResult infoResult : infoResultList) {
                //暂时只识别pixiv和Danbooru的
                int indexId = infoResult.getHeader().getIndex_id();
                if (5 != indexId && 9 != indexId) {
                    return ConstantImage.SAUCENAO_SEARCH_FAIL_PART;
                }

                //过滤掉相似度50一下的 todo 写为可外部调整的配置
                String similarity = infoResult.getHeader().getSimilarity();
                if (50.0 > NumberUtil.toDouble(similarity)) {
                    continue;
                }
                searchResult = infoResult;
                break;
            }
        } catch (Exception ex) {
            BotRabbit.bot.getLogger().error(ConstantImage.SAUCENAO_API_REQUEST_ERROR + ex.toString(), ex);
            return ConstantImage.SAUCENAO_API_REQUEST_ERROR;
        }

        if (null == searchResult) {
            //没有符合条件的图片，识图失败
            return ConstantImage.SAUCENAO_SEARCH_FAIL_PARAM;
        }

        //获取信息，并返回结果
        if (5 == searchResult.getHeader().getIndex_id()) {
            //pixiv
            return parsePixivImgRequest(searchResult);
        } else {
            //Danbooru
        }
        return ConstantImage.SAUCENAO_SEARCH_FAIL_PART;
    }

    /**
     * 拼装识图结果_p站
     * 搜索结果只会取一个
     *
     * @param infoResult 识图结果
     * @return 拼装好的群消息
     */
    private static String parsePixivImgRequest(SaucenaoSearchInfoResult infoResult) {
        //p站图片id
        int pixivId = infoResult.getData().getPixiv_id();

        //根据pid获取图片列表
        ImjadPixivResult pixivResult = null;
        try {
            pixivResult = getImgsByPixivId(pixivId);

            if (null == pixivResult || !"success".equalsIgnoreCase(pixivResult.getStatus())) {
                return ConstantImage.PIXIV_ID_GET_FAIL_GROUP_MESSAGE + "(" + pixivId + ")";
            }

            //Saucenao搜索结果相似度
            String similarity = infoResult.getHeader().getSimilarity();

            List<ImjadPixivResponse> responses = pixivResult.getResponse();
            ImjadPixivResponse response = responses.get(0);
            //图片标题
            String title = response.getTitle();
            //图片简介
            String caption = response.getCaption();
            //作者名称
            String memberName = response.getUser().getName();
            //图片创建时间
            String createDate = response.getCreated_time();

            //图片cq码
            String pixivImgCQ = null;
            String pixivImgLocalPath = getPixivImgByPixivImgUrl(response.getImage_urls().getMedium());
            if (StringUtil.isEmpty(pixivImgLocalPath)) {
                pixivImgCQ = ConstantImage.PIXIV_IMAGE_DOWNLOAD_FAIL;
            } else {
                pixivImgCQ = parseCQBuLocalImagePath(pixivImgLocalPath);
            }

            StringBuilder resultStr = new StringBuilder();
            resultStr.append(pixivImgCQ);
            resultStr.append("\n[相似度] " + similarity + "%");
            resultStr.append("\n[P站id] " + pixivId);
            resultStr.append("\n[标题] " + title);
            resultStr.append("\n[作者] " + memberName);
            resultStr.append("\n[上传时间] " + createDate);
//            resultStr.append("\n[图片简介] " + caption);
            return resultStr.toString();
        } catch (IOException ioEx) {
            BotRabbit.bot.getLogger().error("ImageService " + ConstantImage.IMJAD_PIXIV_ID_API_ERROR + ioEx.toString(), ioEx);
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
        if (!request.isSuccess()) {
            return null;
        }
        return request.getEntity();
    }

    /**
     * 根据p站图片链接下载图片
     * 带图片后缀的那种，比如
     * https://i.pximg.net/img-original/img/2018/03/31/01/10/08/67994735_p0.png
     *
     * @param url p站图片链接
     * @return 下载后的本地连接
     */
    private static String getPixivImgByPixivImgUrl(String url) throws IOException {
        //加入p站防爬链
        HashMap<String, String> header = new HashMap<>();
        header.put("referer", "http://www.pixiv.net/");
        //下载图片
        return ImageUtil.downloadImage(header, url, ConstantImage.DEFAULT_IMAGE_SAVE_PATH + File.separator + "pixiv", null);
    }
}
