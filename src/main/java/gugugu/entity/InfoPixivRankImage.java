package gugugu.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * create by MikuLink on 2020/2/20 17:50
 * for the Reisen
 * P站 热榜 结果信息
 */
@Setter
@Getter
public class InfoPixivRankImage {
    /**
     * 图片P站id
     */
    private Long pid;
    /**
     * 图片CQ码
     */
    private String imgCQ;
    /**
     * 图片标题
     */
    private String title;
    /**
     * 图片简介
     */
    private String caption;
    /**
     * 排名
     */
    private Integer rank;
    /**
     * 上次排名
     */
    private Integer previousRank;
    /**
     * 创建时间
     */
    private String createdTime;
    /**
     * 作画工具
     */
    private String tools;
    /**
     * pid下图片总数
     */
    private Integer pageCount;
    /**
     * 作者id
     */
    private Long userId;
    /**
     * 作者名称
     */
    private String userName;


}
