package gugugu.entity.apirequest.imgsearch.pixiv;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * create by MikuLink on 2020/2/19 14:50
 * for the Reisen
 */
@Setter
@Getter
public class ImjadPixivResult {

    /**
     * status : success
     * response : [{"id":55527150,"title":"いろんなの","caption":"久しぶりに思い出しながら色々絵かき。\r\nおもしろそうな職を見つけたので、来月から東京に転職することに。\r\n山と海に囲まれた場所を離れるのは何か寂しいけど、多くの人やものに出会えそうで楽しみ。\r\nまたアニメや絵描きに夢中になる日がきたらいいなとちょい思う。\r\nいろいろ楽しむ!","tags":["東方とかいろいろ","東方","洩矢諏訪子","古明地こいし","鈴仙・優曇華院・イナバ","ススキ","萃香にとり天子","東方Project","初音ミク","東方Project10000users入り"],"tools":["CLIP STUDIO PAINT"],"image_urls":{"px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p0_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p0_master1200.jpg","small":"https://i.pximg.net/c/150x150/img-master/img/2016/02/28/12/17/08/55527150_p0_master1200.jpg","medium":"https://i.pximg.net/c/600x600/img-master/img/2016/02/28/12/17/08/55527150_p0_master1200.jpg","large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p0.png"},"width":1521,"height":928,"stats":{"scored_count":17459,"score":174061,"views_count":249453,"favorited_count":{"public":18562,"private":1279},"commented_count":143},"publicity":0,"age_limit":"all-age","created_time":"2016-02-28 12:17:08","reuploaded_time":"2016-02-28 12:17:08","user":{"id":3251963,"account":"kitunegumo","name":"湯木間","is_following":false,"is_follower":false,"is_friend":false,"is_premium":null,"profile_image_urls":{"px_50x50":"https://i.pximg.net/user-profile/img/2012/12/27/20/20/23/5596289_d8b0b48bbe2c130f5636981358d0e9b1_50.jpg"},"stats":null,"profile":null},"is_manga":true,"is_liked":false,"favorite_id":0,"page_count":22,"book_style":"none","type":"illustration","metadata":{"pages":[{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p0.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p0_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p0_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p0_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p1.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p1_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p1_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p1_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p2.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p2_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p2_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p2_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p3.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p3_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p3_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p3_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p4.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p4_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p4_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p4_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p5.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p5_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p5_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p5_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p6.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p6_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p6_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p6_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p7.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p7_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p7_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p7_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p8.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p8_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p8_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p8_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p9.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p9_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p9_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p9_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p10.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p10_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p10_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p10_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p11.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p11_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p11_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p11_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p12.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p12_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p12_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p12_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p13.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p13_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p13_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p13_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p14.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p14_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p14_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p14_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p15.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p15_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p15_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p15_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p16.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p16_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p16_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p16_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p17.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p17_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p17_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p17_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p18.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p18_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p18_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p18_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p19.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p19_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p19_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p19_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p20.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p20_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p20_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p20_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p21.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p21_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p21_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p21_master1200.jpg"}}]},"content_type":null}]
     * count : 1
     */

    //结果 success 表示成功
    private String status;
    //结果数 图片id级别如果是单个pid下多图的，也是为1
    private int count;
    //图片搜索结果，如果是根据图片id获取的，一般只有一个结果
    private List<ImjadPixivResponse> response;
}
