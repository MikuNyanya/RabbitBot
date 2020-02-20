package mikulink.rabbit.test.apirequest;

import gugugu.apirequest.weibo.WeiboHomeTimelineGet;
import gugugu.constant.ConstantCommon;
import gugugu.entity.apirequest.weibo.InfoWeiboHomeTimeline;
import org.junit.Test;

/**
 * create by MikuLink on 2020/1/8 19:06
 * for the Reisen
 */
public class WeiboHomeTimelineGetTest {

    @Test
    public void test() {
        try {
            WeiboHomeTimelineGet request = new WeiboHomeTimelineGet();
            request.setAccessToken(ConstantCommon.common_config.get("weiboToken"));
            request.setPage(1);
            request.setCount(3);

            request.doRequest();
            String body = request.getBody();
            InfoWeiboHomeTimeline entity = request.getEntity();

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
