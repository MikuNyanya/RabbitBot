package gugugu.constant;

import java.util.Arrays;
import java.util.List;

/**
 * create by MikuLink on 2019/12/3 19:51
 * for the Reisen
 * <p>
 * 随机雷 常量
 */
public class ConstantABomb extends ConstantCommon {
    //指令列表
    public static final String EXPLAIN = "[一个正在睡觉的雷]\n在.abomb或者.ab后跟随其他指令来进行操作：\n"
            + "start/开始 [总数量] [雷数量](你们即将要去打扰一颗正在睡觉的雷)\n"
            + "end/结束(放过那颗雷吧)\n"
            + "c [num1]/选择 [数字](选择一个数字)";

    //=====指令=====
    //开始
    public static final String START = "start";
    //结束
    public static final String END = "end";
    //选择一个数字
    public static final String SELECT = "c";
    //查看雷
    public static final String BOMB = "bomb";

    //=====提示信息=====
    public static final List<String> GAME_BOMB_END_LIST = Arrays.asList(
            "那么，雷已经被吃掉了，游戏结束啦~\n[%s]的胃口可真好。。。",
            "游戏结束，恭喜踩雷的幸运儿[%s]~"
    );

    public static final List<String> GAME_NOOB_SELECT_BOOM = Arrays.asList(
            "[%s]踩中了一颗雷(%s)",
            "我们来看看...\n哦！恭喜我们的[%s]获得了一颗雷(%s)!",
            "非常抱歉[%s]，你中了一颗雷(%s)，下一局吧",
            "可怜的[%s]\n这是颗雷(%s)\n要兔叽抱抱你嘛",
            "[%s],你看这颗雷(%s)，它又大又圆",
            "[%s]伙计，你踩到雷了，祝你下次运气好点",
            "好险啊，[%s]距离雷只差一点点了...\n啊，骗你的，你已经踩上去了\n这颗雷(%s)让兔叽转告你，它很开心",
            "真可惜，[%s]，你选中了一颗雷(%s)。。。",
            "真可惜，[%s]，你选中了一颗雷(%s)\n但是没关系！只要你愿意支付兔叽三根胡萝。。。\n啊，主人。。。这里什么事情也没有！",
            "下面，即将为[%s]揭晓。。。\nIs the bomb!(%s)",
            "'[%s]!You shall not pass!'\n——甘道夫雷(%s)",
            "'丢人！[%s]马上给我退出战场！'\n——凛冬雷(%s)",
            "'只要是活着的东西，就算是[%s]也杀给你看！'\n——两仪式雷(%s)",
            "'[%s]？是踩雷的friends呢！'\n——加帕里雷(%s)",
            "'来陪我玩吧！要把[%s]玩坏哦！'\n——芙兰雷(%s)",
            "'[%s]感到罪恶爬上了脊背'\n——undertale雷(%s)"
    );
    public static final List<String> GAME_NOOB_SELECT_SAFE = Arrays.asList(
            "[%s]安全上垒",
            "[%s]没有踩中雷",
            "[%s]没有踩中雷\n再来试一次？",
            "我们来看看...\n哦！恭喜我们的[%s]，没有踩中炸弹!",
            "好险啊，[%s]距离雷只差这么一点了(比划)\n那么下次还会这么好运吗",
            "真可惜，[%s]没有踩中任何一颗雷\n这样收视率会下降的...",
            "由于支付了兔叽五根胡萝卜\n[%s]这次并没有踩雷",
            "'踩中了一颗雷！'\n本来兔叽是想这么说的。。。\n但是[%s]给的胡萝卜真的太多了。。。",
            "嗯！[%s],没有雷！恭喜！",
            "没有雷哦，[%s]\n......\n别这幅表情嘛\n相信兔叽，下次会有的"
    );
    public static final List<String> GAME_NOOB_SELECT_REPEAT = Arrays.asList(
            "你选择的数字已被排除了",
            "这个数字已经在安全范围了",
            "请不要选择一个已被排除的安全数字",
            "干嘛，怕了吗\n不然为什么要选择一个没有危险的数字",
            "雷区请前方右拐，这里是安全区",
            "天堂向左，雷区向右！"
    );
}
