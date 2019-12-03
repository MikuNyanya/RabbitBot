package gugugu.constant;

/**
 * create by MikuLink on 2019/12/3 19:51
 * for the Reisen
 */
public class ConstantRollBomb extends ConstantCommon{
    //=====指令=====
    //开始
    public static final String START = "start";

    //指令列表
    public static final String EXPLAIN = "[随机炸弹数字]，在.rbomb后跟随其他指令来进行操作：\n"
            + "start [numcnt] [bombcnt]/开始 [总数量] [炸弹数量](开始随机炸弹，如果不设置数量则使用默认值)\n"
            + "end/结束(结束随机炸弹)\n"
            + "select [num1] [num2]/选择 [数字1] [数字2](踩一个数字，可以一次选择多个数字)\n"
            + "bombcnt [num]/炸弹数量 [num](设置炸弹数量，不能超过剩余安全值数量，默认为1个)\n"
            + "numcnt/总数(设置数字总数，不能超出500，默认为50)\n"
            + "*下面列表类只会显示100个，超出的不显示\n"
            + "listnow/剩余列表(查看当前剩余数字,)\n"
            + "listbomb/炸弹列表(查看本次炸弹列表)\n"
            + "listnoob/出局人员(查看已被炸shi的人)\n";

    //=====提示信息=====
    public static final String GAME_NOT_START = "游戏未开启";
    public static final String GAME_GAMING = "游戏已开启，正在进行中";


}
