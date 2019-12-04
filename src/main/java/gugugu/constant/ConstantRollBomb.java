package gugugu.constant;

import java.util.Arrays;
import java.util.List;

/**
 * create by MikuLink on 2019/12/3 19:51
 * for the Reisen
 * <p>
 * 随机雷 常量
 */
public class ConstantRollBomb extends ConstantCommon {
    //指令列表
    public static final String EXPLAIN = "[随机雷]，在.rbomb后跟随其他指令来进行操作：\n"
            + "start [numcnt] [bombcnt]/开始 [总数量] [雷数量](开始随机雷，如果不设置数量则使用默认值)\n"
            + "end/结束(结束随机雷)\n"
            + "c [num1] [num2]/选择 [数字1] [数字2](踩一个数字，可以一次选择多个数字)\n"
//            + "numcnt/总数(设置数字总数，不能超出200，默认为50)\n"
//            + "bombcnt [num]/雷数量 [num](设置雷数量，不能超过剩余安全值数量，默认为1个)\n"
            + "*下面列表类只会显示100个，超出的不显示\n"
            + "listnow/剩余列表(查看当前剩余数字,)\n"
//            + "listbomb/雷列表(查看本次雷列表)\n"
            + "listnoob/出局人员(查看已被炸shi的人)";

    //=====指令=====
    //开始
    public static final String START = "start";
    //结束
    public static final String END = "end";
    //选择一个数字
    public static final String SELECT = "c";
    //剩余数字列表
    public static final String LIST_NOW = "listnow";
    //剩余雷列表
    public static final String LIST_BOMB = "listbomb";
    //出局人员列表
    public static final String LIST_NOOB = "listnoob";

    //=====提示信息=====

    public static final List<String> GAME_BOMB_END_LIST = Arrays.asList(
            "那么，所有的雷都已经被吃掉了，游戏结束啦~\n你们的胃口可真好。。。\n现在可以使用listnoob查看踩雷记录\n看完记得关掉游戏哦",
            "已经没有雷了，游戏结束\n可以使用listnoob查看踩雷记录\n看完后请使用end指令关闭游戏",
            "结束啦，恭喜那些踩雷的幸运儿~"
    );

    public static final String GAME_PARAM_SELECT_USED = "数字已经被选择过一次了，试试别的";
    public static final String GAME_NUM_OVER_VALUE = "总数量过大";
    public static final String GAME_BOMB_OVER_VALUE = "雷数量不能超过总数量";
    public static final String GAME_LIST_NOT_EXISTS = "不存在该列表[%s]";

    public static final List<String> GAME_NOOB_SELECT_LIST = Arrays.asList(
            "你已经被炸shi了，不能继续踩数字了",
            "你已经出局了，没法继续选择数字了",
            "看别人玩吧，你已经出局了",
            "别闹，你已经挂了",
            "死人麻烦躺好，别动歪",
            "你已经死啦",
            "你已经死啦，菜",
            "我来看看...\n你已经没了，不能选数字",
            "you wanna 'select' noob? AHHHHHHH...\nyou're dead!"
    );
    public static final List<String> GAME_NOOB_SELECT_BOOM = Arrays.asList(
            "[%s]踩中了一颗雷(%s)",
            "我们来看看...\n哦！恭喜我们的[%s]获得了一颗雷(%s)!",
            "非常抱歉[%s]，你中了一颗雷(%s)，等待下一局吧",
            "可怜的[%s]\n这是颗雷(%s)\n要兔叽抱抱你嘛",
            "[%s],你看这颗雷(%s)，它又大又圆",
            "[%s]伙计，你踩到雷了，下场休息吧",
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
            "'[%s]踩中了一颗雷'\n本来兔叽是想这么说的。。。\n但是[%s]给的胡萝卜真的太多了。。。",
            "下面，即将为[%s]揭晓。。。\n嗯！没有雷！恭喜！",
            "没有雷哦，[%s]\n......\n别这幅表情嘛\n相信兔叽，下次会有的"
    );
}
