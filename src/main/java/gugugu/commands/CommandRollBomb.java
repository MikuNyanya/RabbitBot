package gugugu.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import gugugu.constant.ConstantRollBomb;
import gugugu.entity.InfoGroupUser;
import utils.NumberUtil;
import utils.RandomUtil;
import utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * <p>
 * 随机雷
 */
public class CommandRollBomb implements GroupCommand {
    //当前游戏状态
    private String GAME_STATUS = "0";
    //剩余列表
    private List<Integer> list_now = new ArrayList<>();
    private List<Integer> list_bomb = new ArrayList<>();
    private Map<Integer, InfoGroupUser> map_noob = new HashMap<>();

    //状态-游戏未开启
    private final String GAME_OFF = "0";
    //状态-游戏已开启
    private final String GAME_ON = "1";
    //状态-游戏已结束
    private final String GAME_BOMB_END = "2";
    //最大总数
    private final int MAX_NUM_COUNT = 200;
    //本局总数
    private int NOW_NUM_COUNT = 200;


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
        if (null == args || args.size() <= 0) {
            return ConstantRollBomb.EXPLAIN;
        }
        String action = args.get(0);

        String msg = "";
        switch (action) {
            case ConstantRollBomb.START:
                //开始游戏
                msg = start(args);
                break;
            case ConstantRollBomb.END:
                //关闭游戏
                msg = end();
                break;
            case ConstantRollBomb.SELECT:
                //选择数字
                msg = select(event, args);
                break;
            case ConstantRollBomb.LIST_NOW:
            case ConstantRollBomb.LIST_BOMB:
                //列表展示
                msg = showList(action);
                break;
            case ConstantRollBomb.LIST_NOOB:
                //出局人员展示
                msg = showNoob();
                break;
            default:
                msg = String.format(ConstantRollBomb.COMMAND_ERROR, action);
                break;
        }

        return msg;
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("RollBomb", "rbomb");
    }

    //游戏开始
    private String start(ArrayList<String> args) {
        //不能重复开启游戏
        if (GAME_ON.equalsIgnoreCase(GAME_STATUS)) {
            return ConstantRollBomb.GAME_GAMING;
        }
        //获取总数和雷数
        String numCntStr = "100";
        String bombCntStr = "1";
        if (args.size() > 1) {
            numCntStr = args.get(1);
        }
        if (args.size() > 2) {
            bombCntStr = args.get(2);
        }
        //输入参数校验
        if (!NumberUtil.isNumber(numCntStr) || !NumberUtil.isNumber(bombCntStr)) {
            return ConstantRollBomb.GAME_PARAM_NUMBER_ONLY;
        }

        Integer numCnt = NumberUtil.toInt(numCntStr);
        Integer bombCnt = NumberUtil.toInt(bombCntStr);

        //边界校验
        if (numCnt > MAX_NUM_COUNT) {
            return ConstantRollBomb.GAME_NUM_OVER_VALUE;
        }
        if (numCnt <= 0) {
            return ConstantRollBomb.GAME_PARAM_NUMBER_TO_SMALL;
        }
        if (bombCnt >= numCnt) {
            return ConstantRollBomb.GAME_BOMB_OVER_VALUE;
        }

        //初始化池子
        int i = 0;
        list_now.clear();
        do {
            list_now.add(i);
            i++;
        } while (i <= numCnt);

        //初始化雷池
        list_bomb.clear();
        i = 0;
        do {
            int radBomb = RandomUtil.roll(numCnt);
            //如果随机到了重复的雷，再随机一次
            if (list_bomb.contains(radBomb)) {
                continue;
            }
            list_bomb.add(radBomb);
            i++;
        } while (i < bombCnt);

        //初始化
        map_noob.clear();

        //存储本局总数
        NOW_NUM_COUNT = numCnt;

        //改变游戏状态
        GAME_STATUS = GAME_ON;

        return String.format("===[随机雷]初始化完毕===\n池子总数量:%s\n雷数：%s", numCnt, bombCnt);
    }

    //结束游戏
    private String end() {
        //不能重复关闭游戏
        if (GAME_OFF.equalsIgnoreCase(GAME_STATUS)) {
            return ConstantRollBomb.GAME_ENDING;
        }
        //关闭游戏
        GAME_STATUS = GAME_OFF;
        //清除所有list
        list_now.clear();
        list_bomb.clear();
        map_noob.clear();

        return ConstantRollBomb.GAME_END_LIST.get(RandomUtil.roll(ConstantRollBomb.GAME_END_LIST.size() - 1));
    }

    //选择数字
    private String select(EventGroupMessage event, ArrayList<String> args) {
        //游戏未开始时指令无效
        if (GAME_OFF.equalsIgnoreCase(GAME_STATUS)) {
            return ConstantRollBomb.GAME_NOT_START;
        }
        //游戏已结束时指令无效
        if (GAME_BOMB_END.equalsIgnoreCase(GAME_STATUS)) {
            return ConstantRollBomb.GAME_BOMB_END;
        }

        Long groupUserId = event.getGroupUserInfo().getUserId();
        String groupUserName = event.getGroupUserInfo().getGroupUserName();
        //已经出局的人无效
        for (Integer key : map_noob.keySet()) {
            if (groupUserId.equals(map_noob.get(key).getUserId())) {
                return String.format("[%s]\n%s",
                        groupUserName,
                        ConstantRollBomb.GAME_NOOB_SELECT_LIST.get(RandomUtil.roll(ConstantRollBomb.GAME_NOOB_SELECT_LIST.size() - 1))
                );
            }
        }

        //移除第二层指令
        args.remove(0);

        for (String arg : args) {
            //数值类型检验
            if (StringUtil.isEmpty(arg) || !NumberUtil.isNumber(arg)) {
                return String.format("[%s],%s", groupUserName, ConstantRollBomb.GAME_PARAM_SELECT_ONLY);
            }

            Integer numTemp = NumberUtil.toInt(arg);
            //不能超出范围
            if (numTemp < 0 || numTemp > NOW_NUM_COUNT) {
                return String.format("[%s],%s", groupUserName, String.format(ConstantRollBomb.GAME_PARAM_SELECT_OVER, NOW_NUM_COUNT));
            }
            //被选择过的数字不能选择
            if (!list_bomb.contains(numTemp) && !list_now.contains(numTemp)) {
                return String.format("[%s],[%s]%s", groupUserName, arg, ConstantRollBomb.GAME_PARAM_SELECT_USED);
            }

            //判断踩雷了没
            if (list_bomb.contains(numTemp)) {
                //移除这颗雷
                list_bomb.remove(numTemp);
                //记录在出局列表中
                map_noob.put(numTemp, event.getGroupUserInfo());
                //返回踩雷的结果
                String msg = String.format(ConstantRollBomb.GAME_NOOB_SELECT_BOOM.get(RandomUtil.roll(ConstantRollBomb.GAME_NOOB_SELECT_BOOM.size() - 1)),
                        groupUserName, arg);
                //如果没雷了，就结束游戏
                if (list_bomb.size() <= 0) {
                    //变更游戏状态
                    GAME_STATUS = GAME_BOMB_END;
                    msg += ("\n\n" + ConstantRollBomb.GAME_BOMB_END_LIST.get(RandomUtil.roll(ConstantRollBomb.GAME_BOMB_END_LIST.size() - 1)));
                }
                return msg;
            }

            //安全上垒
            //移除这个数字
            list_now.remove(numTemp);
        }

        //返回安全结果
        return String.format(ConstantRollBomb.GAME_NOOB_SELECT_SAFE.get(RandomUtil.roll(ConstantRollBomb.GAME_NOOB_SELECT_SAFE.size() - 1)),
                groupUserName);
    }

    //展示列表
    private String showList(String commandSecond) {
        //游戏未开始时指令无效
        if (GAME_OFF.equalsIgnoreCase(GAME_STATUS)) {
            return ConstantRollBomb.GAME_NOT_START;
        }

        //区分列表种类
        List<Integer> temp = null;
        String commandSecondName = "";
        switch (commandSecond) {
            case ConstantRollBomb.LIST_NOW:
                commandSecondName = "剩余数字";
                temp = list_now;
                break;
            case ConstantRollBomb.LIST_BOMB:
                commandSecondName = "剩余雷";
                temp = list_bomb;
                break;
            default:
                return String.format(ConstantRollBomb.GAME_LIST_NOT_EXISTS, commandSecond);
        }

        //输出列表
        int i = 1;
        StringBuilder msg = new StringBuilder();

        for (Integer num : temp) {
            msg.append(",");
            msg.append(num);
            if (i >= 100) {
                break;
            }
            i++;
        }
        msg = new StringBuilder(msg.substring(1));
        msg.insert(0, String.format("===%s===\n", commandSecondName));
        return msg.toString();
    }

    //展示菜鸡
    private String showNoob() {
        //游戏未开始时指令无效
        if (GAME_OFF.equalsIgnoreCase(GAME_STATUS)) {
            return ConstantRollBomb.GAME_NOT_START;
        }

        //输出列表
        int i = 1;
        StringBuilder msg = new StringBuilder();
        msg.append("===菜鸡列表===");
        for (Integer num : map_noob.keySet()) {
            InfoGroupUser tempGroupUserInfo = map_noob.get(num);
            msg.append(String.format("\n[%s]->雷(%s)", tempGroupUserInfo.getGroupUserName(), num));

            //边界限制，反正群里没这么多活跃人数
            if (i >= 50) {
                break;
            }
            i++;
        }

        return msg.toString();
    }

}
