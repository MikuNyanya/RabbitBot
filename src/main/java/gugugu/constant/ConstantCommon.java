package gugugu.constant;

import java.util.Arrays;
import java.util.List;

/**
 * create by MikuLink on 2019/12/3 14:39
 * for the Reisen
 * <p>
 * 公共常量
 */
public class ConstantCommon {
    //换行符
    public static final String NEXT_LINE = "\n";
    //指令前缀
    public static final String COMMAND_INDEX = ".";

    public static final String COMMAND_ERROR = "指令[%s]无效";
    public static final String GAME_NOT_START = "游戏未开启";
    public static final String GAME_BOMB_END = "游戏已结束";
    public static final String GAME_GAMING = "游戏已开启，正在进行中";
    public static final String GAME_ENDING = "游戏并没有开启，不需要结束";
    public static final String GAME_PARAM_NUMBER_NOT_NULL = "你得选择一个数字";
    public static final String GAME_PARAM_NUMBER_ONLY = "参数必须为纯数字";
    public static final String GAME_PARAM_NUMBER_TO_SMALL = "设置的数字必须大于0";
    public static final String GAME_PARAM_SELECT_ONLY = "只能选择纯数字";
    public static final String GAME_PARAM_SELECT_OVER = "数字不能超出范围(0~%s)";
    public static final List<String> GAME_END_LIST = Arrays.asList(
            "游戏即将结束\n希望你们玩的还算愉快",
            "不玩了嘛，那我关了",
            "好的，游戏要关了哦~",
            "关，都可以关。。。\n游戏关了",
            "结束了\n不再来一局嘛~"
    );
}
