package gugugu.entity.apirequest;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * create by MikuLink on 2020/1/8 19:19
 * for the Reisen
 */
@Getter
@Setter
public class InfoStatuses {
    //微博创建时间
    private String created_at;
    //推文内容
    private String text;
    //微博来源
    private String source;
    //推文图片列表
    private List<InfoPicUrl> pic_urls;
    //用户信息
    private InfoUser user;
    //转发微博
    private InfoRetweetedStatus retweeted_status;
}
