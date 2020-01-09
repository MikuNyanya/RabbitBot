package gugugu.service;

import gugugu.apirequest.WeiboHomeTimelineGet;
import gugugu.constant.ConstantWeiboNews;
import gugugu.entity.apirequest.InfoStatuses;
import gugugu.entity.apirequest.InfoWeiboHomeTimeline;
import gugugu.exceptions.RabbitException;
import utils.StringUtil;

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
    public static void sendWeiboNewsToEveryGroup(List<InfoStatuses> statuses) throws InterruptedException {
        if (null == statuses || statuses.size() == 0) {
            return;
        }

        //发送微博
        for (InfoStatuses info : statuses) {
            String msg = String.format("微博号：%s\n微博号id：%s\n推文时间：%s\n正文：%s",
                    info.getUser().getName(), info.getUser().getId(), info.getCreated_at(), info.getText());
            RabbitBotService.sendEveryGroupMsg(msg);
            //每次发送之间间隔一点时间，免得瞬间刷屏
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
}
