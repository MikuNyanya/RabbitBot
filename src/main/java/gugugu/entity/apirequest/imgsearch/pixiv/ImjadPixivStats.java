package gugugu.entity.apirequest.imgsearch.pixiv;

import lombok.Getter;
import lombok.Setter;

/**
 * create by MikuLink on 2020/2/19 14:55
 * for the Reisen
 * 一些数据，大概是观看数，点赞数，收藏数之类的
 */
@Setter
@Getter
public class ImjadPixivStats {
    /**
     * scored_count : 17459
     * score : 174061
     * views_count : 249453
     * favorited_count : {"public":18562,"private":1279}
     * commented_count : 143
     */

    private int scored_count;
    private int score;
    //点击数
    private int views_count;
    private int commented_count;
    //收藏数
    private ImjadPixivFavoritedCount favorited_count;
}