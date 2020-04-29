package mikulink.rabbit.test.apirequest;

import gugugu.apirequest.imjad.PixivImjadMemberIllustGet;
import gugugu.entity.apirequest.imjad.ImjadPixivResult;
import org.junit.Test;

/**
 * create by MikuLink on 2020/04/29 15:06
 * for the Reisen
 */
public class PixivImjadMemberIllustGetTest {

    @Test
    public void test() {
        try {
            PixivImjadMemberIllustGet request = new PixivImjadMemberIllustGet();
            request.setMemberId(1950701L);//薯妈
            request.setPage(1);
            request.setPageSize(5);
            request.doRequest();
            boolean isOk = request.isSuccess();
            ImjadPixivResult result = request.getEntity();

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
