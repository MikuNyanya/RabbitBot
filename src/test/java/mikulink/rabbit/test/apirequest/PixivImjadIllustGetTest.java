package mikulink.rabbit.test.apirequest;

import gugugu.apirequest.imgsearch.PixivImjadIllustGet;
import gugugu.entity.apirequest.imgsearch.pixiv.ImjadPixivResult;
import org.junit.Test;

/**
 * create by MikuLink on 2020/02/26 15:06
 * for the Reisen
 */
public class PixivImjadIllustGetTest {

    @Test
    public void test() {
        try {
            PixivImjadIllustGet request = new PixivImjadIllustGet();
            request.setBody("{\"status\":\"failure\",\"has_error\":true,\"errors\":{\"system\":{\"message\":\"\\u5bfe\\u8c61\\u306e\\u30a4\\u30e9\\u30b9\\u30c8\\u306f\\u898b\\u3064\\u304b\\u308a\\u307e\\u305b\\u3093\\u3067\\u3057\\u305f\\u3002(illust_id:74790362)\",\"code\":206}}}");

            request.setPixivId(74790362L);
//            request.doRequest();
            boolean isOk = request.isSuccess();
            ImjadPixivResult result = request.getEntity();

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
