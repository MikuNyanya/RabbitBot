package gugugu.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * create by MikuLink on 2019/12/3 19:51
 * for the Reisen
 * <p>
 * 常规句子
 */
public class ConstantFreeTime extends ConstantCommon {
    public static final String EXPLAIN = "在[.addnj]或者[.添加日常话语]后跟随语句进行添加，比如：\n"
            + ".addnj 这是一个日常语句\n"
            + "日常语句限制在100字以内\n"
            + "换行输入'\\n'";

    //异常情况处理
    public static final String MSG_TYPE_FREE_TIME_EMPTY = "啊。。。我的资料库里空空如也";
    public static final String MSG_ADD_EMPTY = "添加失败，请输入需要添加的日常语句";
    public static final String MSG_ADD_SUCCESS = "日常语句添加成功";
    public static final String MSG_ADD_OVER_LENGTH = "日常语句长度不能超过100";

    //日常语句
    public static ArrayList<String> MSG_TYPE_FREE_TIME = new ArrayList<>(Arrays.asList(
            "今天也是和平的一天呢~",
            "希望可以看到吗\n希望可以摸到吗\n希望可以得到吗\n。。。\n你是希望吗",
            "不得了啦！隔壁薯片又半价啦！！ヽ(#`Д´)ノ",
            "总有刁民想害兔叽ヽ(#`Д´)ノ",
            "兔叽也不是永恒的，总有一天，兔叽也会被所有人遗忘在一旁吧。。。",
            "『愿我能找到心之归宿，不再漂泊流浪，不再孤立无援』\n。。。\n漂流瓶里也会有这种东西吗？",
            "『今有雉兔同笼，上有三十五头，下有九十四足，问雉兔各几何？』\n难道他们不考虑兔子是两条腿的情况吗",
            "在外面用餐的时候，请尽量不要坐靠窗的位置喔。\n窗外或许会有爆炸也说不定",
            "为了实现世界和平，果然只有让人类灭亡吗。\n虽然战争是没了。。。弱肉强食算和平吗？",
            "『小孩没有判断能力』这句话，是哪张嘴讲出来的呢？\n反过来说，拥有正确判断能力的大人又有多少呢。",
            "就算不小心把硬盘的资料消除掉了，某种程度上也有可能复原吧？\n也就是说，经过格式化废弃的硬盘也可以取出资料呢。\n果然，物理性的破坏才是最佳选择呢。",
            "『出生没多久的幼童不会犯罪，所以原本人类是属于'善'的。』\n那么，让婴儿拥有大人的身体将会如何呢。\n那个人，能够做到尊重他人、遵守法理的'善'吗？\n唔，虽然我并不认为'遵守法律就是善'",
            "食用动物的肉会令动物比较痛苦，那么食用植物的茎和叶植物就没痛苦了吗？\n众生平等只是统制者掩人耳目的说法吧\n。。。\n所以要一起吃掉？"
    ));

    //针对ABABA句式的回复
    public static final List<String> MSG_TYPE_ABABA = Arrays.asList(
            "妮可妮可妮",
            "兔子兔子兔",
            "兔叽兔叽兔",
            "兔砸兔砸兔",
            "铃子铃子铃",
            "夕月夕月夕"
    );

}
