package gugugu.service;

import gugugu.apirequest.hitokoto.HitokotoGet;
import gugugu.constant.ConstantHitokoto;
import gugugu.entity.hitokoto.HitokotoInfo;

import java.io.IOException;

/**
 * create by MikuLink on 2020/3/16 14:44
 * for the Reisen
 * 一言服务
 * 一言的数据质量太烂，不用了
 */
@Deprecated
public class HitokotoService {
    /**
     * 获取一条一言信息
     */
    public static String getHitokoto() throws IOException {
        HitokotoGet hitokotoGet = new HitokotoGet();
        //其他类型过于主观，只获取哲学的算了
        hitokotoGet.setC("k");
        hitokotoGet.doRequest();
        HitokotoInfo hitokotoInfo = hitokotoGet.getEntity();

        if (null == hitokotoInfo) {
            return ConstantHitokoto.HITOKOTO_INFO_EMPTY;
        }

        //来源
        String from = hitokotoInfo.getFrom();
        //来源人
        String fromWho = hitokotoInfo.getFrom_who();

        StringBuilder strBuild = new StringBuilder();
        strBuild.append("『" + hitokotoInfo.getHitokoto() + "』");
        //他们数据整理的太烂了，而且数据格式松散，不用出处了
//        if (StringUtil.isNotEmpty(from)) {
//            strBuild.append("\n-" + from);
//        }
//        if (StringUtil.isNotEmpty(fromWho)) {
//            strBuild.append("\n-" + fromWho);
//        }
        strBuild.append("\n=====hitokoto cn=====");
        return strBuild.toString();
    }
}
