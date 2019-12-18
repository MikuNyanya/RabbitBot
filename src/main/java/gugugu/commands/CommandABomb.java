package gugugu.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import gugugu.constant.ConstantABomb;
import utils.NumberUtil;
import utils.RandomUtil;
import utils.StringUtil;

import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * <p>
 * 随机雷
 */
public class CommandABomb implements GroupCommand {
    //当前游戏状态
    private String GAME_STATUS = "0";
    //生成的一个炸弹
    private Integer bomb_num = null;
    //中雷人员
//    private InfoGroupUser bommUser = null;
    //记录当前游戏中，已经被发现的最大和最小数字范围
    private Integer now_min = 0;
    private Integer now_max = 100;

    //状态-游戏未开启
    private final String GAME_OFF = "0";
    //状态-游戏已开启
    private final String GAME_ON = "1";
    //状态-游戏已结束
    private final String GAME_BOMB_END = "2";
    //最大值
    private final int MAX_NUM_COUNT = 100;


    /**
     * 执行指令
     *
     * @param event   事件
     * @param sender  发送者的用户
     * @param group   群
     * @param command 指令名 ( 不包含指令参数 )
     * @param args    指令参数 ( 不包含指令名 )
     * @return 发送回去的消息 ( 当然也可以手动发送然后返回空 )
     */
    @Override
    public String groupMessage(EventGroupMessage event, GroupUser sender, Group group, String command, ArrayList<String> args) {
        try {
            if (null == args || args.size() <= 0) {
                return ConstantABomb.EXPLAIN;
            }
            String action = args.get(0);

            String msg = "";
            switch (action) {
                case ConstantABomb.START:
                    //开始游戏
                    msg = start();
                    break;
                case ConstantABomb.END:
                    //关闭游戏
                    msg = end();
                    break;
                case ConstantABomb.SELECT:
                    //选择数字
                    msg = select(event, args);
                    break;
                case ConstantABomb.BOMB:
                    //查看雷
                    msg = bomb();
                    break;
                default:
                    msg = String.format(ConstantABomb.COMMAND_ERROR, action);
                    break;
            }

            return msg;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "发生程序异常";
        }
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("aBomb", "ab");
    }

    //游戏开始
    private String start() {
        //不能重复开启游戏
        if (GAME_ON.equalsIgnoreCase(GAME_STATUS)) {
            return ConstantABomb.GAME_GAMING;
        }

        //初始化雷
        int radBomb = RandomUtil.roll(101);
        bomb_num = radBomb;
        now_min = 0;
        now_max = 100;

        //改变游戏状态
        GAME_STATUS = GAME_ON;

        return "===[雷]初始化完毕===\n开始吧诸位";
    }

    //结束游戏
    private String end() {
        //不能重复关闭游戏
        if (GAME_OFF.equalsIgnoreCase(GAME_STATUS)) {
            return ConstantABomb.GAME_ENDING;
        }
        //关闭游戏
        GAME_STATUS = GAME_OFF;
        //清除信息
        bomb_num = null;
//        bommUser = null;
        now_min = 0;
        now_max = 100;

        return RandomUtil.rollStrFromList(ConstantABomb.GAME_END_LIST);
    }

    //选择数字
    private String select(EventGroupMessage event, ArrayList<String> args) {
        //游戏未开始时指令无效
        if (GAME_OFF.equalsIgnoreCase(GAME_STATUS)) {
            return ConstantABomb.GAME_NOT_START;
        }
        //游戏已结束时指令无效
        if (GAME_BOMB_END.equalsIgnoreCase(GAME_STATUS)) {
            return ConstantABomb.GAME_BOMB_END;
        }

        //获取输入的数字
        if (args.size() < 2) {
            return ConstantABomb.GAME_PARAM_NUMBER_NOT_NULL;
        }
        String cNum = args.get(1);

        String groupUserName = event.getGroupUserInfo().getGroupUserName();

        //数值类型检验
        if (StringUtil.isEmpty(cNum) || !NumberUtil.isNumber(cNum)) {
            return String.format("[%s],%s", groupUserName, ConstantABomb.GAME_PARAM_SELECT_ONLY);
        }

        Integer numTemp = NumberUtil.toInt(cNum);
        //不能超出最大范围
        if (numTemp < 0 || numTemp > MAX_NUM_COUNT) {
            return String.format("[%s],%s", groupUserName, String.format(ConstantABomb.GAME_PARAM_SELECT_OVER, MAX_NUM_COUNT));
        }
        //不能超出安全范围
        if (numTemp < now_min || numTemp > now_max) {
            return String.format("[%s],%s", groupUserName, RandomUtil.rollStrFromList(ConstantABomb.GAME_NOOB_SELECT_REPEAT));
        }

        //判断踩雷了没
        if (bomb_num.equals(numTemp)) {
            //返回踩雷的结果
            String msg = String.format(RandomUtil.rollStrFromList(ConstantABomb.GAME_NOOB_SELECT_BOOM),
                    groupUserName, cNum);

            //结束游戏 变更游戏状态
            GAME_STATUS = GAME_BOMB_END;
            msg += ("\n\n" + String.format(RandomUtil.rollStrFromList(ConstantABomb.GAME_BOMB_END_LIST), groupUserName));
            return msg;
        }

        //返回安全结果
        String msg = String.format(RandomUtil.rollStrFromList(ConstantABomb.GAME_NOOB_SELECT_SAFE),
                groupUserName);

        if (numTemp > bomb_num) {
            now_max = numTemp - 1;
        } else {
            now_min = numTemp + 1;
        }
        if (now_min.equals(now_max)) {
            msg += ("\n\n" + String.format("雷的范围已被锁定:%s~%s，恭喜诸位，没人中雷，游戏结束啦~", now_min, now_max));
            GAME_STATUS = GAME_BOMB_END;
        } else {
            msg += ("\n\n" + String.format("雷区范围已更新:%s~%s", now_min, now_max));
        }
        return msg;
    }

    //查看当前的雷
    private String bomb() {
        //游戏未开始时指令无效
        if (GAME_OFF.equalsIgnoreCase(GAME_STATUS)) {
            return ConstantABomb.GAME_NOT_START;
        }
        //游戏已结束时指令无效
        if (GAME_BOMB_END.equalsIgnoreCase(GAME_STATUS)) {
            return ConstantABomb.GAME_BOMB_END;
        }
        return String.valueOf(bomb_num);
    }
}
