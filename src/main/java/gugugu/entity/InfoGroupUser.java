package gugugu.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import gugugu.constant.ConstantCommon;
import lombok.Getter;
import utils.StringUtil;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * 群用户信息对象
 */
@Getter
public class InfoGroupUser {
    /**
     * 年龄
     */
    @SerializedName("age")
    @Expose
    private Long age;

    /**
     * QQ名称
     */
    @SerializedName("nickname")
    @Expose
    private String nickname;

    /**
     * 性别，不知道为什么是反的
     */
    @SerializedName("sex")
    @Expose
    private String sex;

    /**
     * QQ号
     */
    @SerializedName("user_id")
    @Expose
    private Long userId;

    /**
     * 群名片
     * 如果为空则说明没设置群名片，使用nickname字段即可
     */
    @SerializedName("card")
    @Expose
    private String card;

    /**
     * 群等级
     */
    @SerializedName("level")
    @Expose
    private String level;

    /**
     * 群头衔
     */
    @SerializedName("title")
    @Expose
    private String title;

    /**
     * 权限
     * owner    群主
     * admin    管理员
     * member   群员
     */
    @SerializedName("role")
    @Expose
    private String role;

    public String getSex() {
        if ("female".equalsIgnoreCase(sex)) {
            return "男";
        } else if ("male".equalsIgnoreCase(sex)) {
            return "女";
        } else {
            return sex;
        }
    }

    //不设置群名片的话，card则为空，这时候直接使用本命
    public String getGroupUserName() {
        if (StringUtil.isEmpty(card)) {
            return nickname;
        }
        return card;
    }

    //是否为管理，群主也算进去
    public boolean isAdmin() {
        if (StringUtil.isEmpty(role)) {
            return false;
        }
        return ConstantCommon.OWNER.equalsIgnoreCase(role) || ConstantCommon.ADMIN.equalsIgnoreCase(role);
    }
}
