package gugugu.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by MikuLink on 2019/12/3 19:51
 * for the Reisen
 * <p>
 * 关键词检索
 */
public class ConstantKeyWord extends ConstantCommon {
    public static final String EXPLAIN = "格式：[.addkwn 关键词1|关键词2 回复语句1 回复语句2]"
            + "在.addkwn后跟随关键词和回复语句进行添加，比如：\n"
            + ".addkwn 早上好|早安 兔叽向你道早安 这里是兔叽，早上好哦 再让兔叽睡5分钟。。。\n"
            + "*每个关键词之间使用|间隔\n"
            + "*每个关键词限制在50字以内\n"
            + "*每个回复语句之间使用1个空格间隔\n"
            + "*每个回复语句限制在100字以内\n"
            + "*换行输入'\\n'";

    //存放全匹配关键词的map，key为关键词，多个关键词以|区分，value为对应的回复列表
    public static Map<String, List<String>> key_wrod_normal = new HashMap<>();

    //关键词最大长度
    public static final int KEY_WORD_MAX_SIZE = 50;
    public static final String KEY_WORD_OVER = "关键词长度超出限制";
    public static final String KEY_WORD_EXISTS = "关键词[%s]，已经存在";
    public static final int KEY_WORD_RESPONSE_MAX_SIZE = 100;
    public static final String KEY_WORD_RESPONSE_OVER = "关键词长度超出限制";
    public static final String KEY_WORD_SAVE_SUCCESS = "关键词回复保存成功";

}
