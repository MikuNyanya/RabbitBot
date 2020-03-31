package mikulink.rabbit.test.apirequest;

import gugugu.apirequest.imjad.PixivImjadQRCode;
import org.junit.Test;

/**
 * create by MikuLink on 2020/03/31 15:06
 * for the Reisen
 */
public class PixivImjadQrcodeGetTest {

    @Test
    public void test() {
        try {
            PixivImjadQRCode request = new PixivImjadQRCode();
            request.setText("兔子万岁！");
//            request.setLogo("");
            request.setLevel("Q");
            request.setEncode("json");
            request.doRequest();

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
