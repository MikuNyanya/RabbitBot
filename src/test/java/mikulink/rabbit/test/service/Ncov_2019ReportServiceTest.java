package mikulink.rabbit.test.service;

import gugugu.service.NCoV_2019ReportService;
import org.junit.Test;

/**
 * create by MikuLink on 2020/2/1 0:01
 * for the Reisen
 */
public class Ncov_2019ReportServiceTest {

    @Test
    public void test() {
        try {
            NCoV_2019ReportService.reportInfoNow();

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
