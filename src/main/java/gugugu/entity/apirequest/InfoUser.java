package gugugu.entity.apirequest;

import lombok.Getter;
import lombok.Setter;

/**
 * create by MikuLink on 2020/1/8 19:44
 * for the Reisen
 */
@Getter
@Setter
public class InfoUser {
    //用户id
    private Long id;
    //用户名称
    private String name;
    //用户简介
    private String description;
}
