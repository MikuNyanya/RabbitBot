package gugugu.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import gugugu.constant.ConstantRollBomb;
import utils.StringUtil;

import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * <p>
 * 随机数炸弹
 */
public class CommandRollBomb implements GroupCommand {
    private static String GAME_STATUS = "0";

    //状态-游戏未开启
    private static final String GAME_OFF = "0";
    //状态-游戏已开启
    private static final String GAME_ON = "1";


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
        switch (action){
            case ConstantRollBomb.START:
//                msg = start();
                break;

        }




        return msg;
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("RollBomb", "rbomb");
    }

    private String start(ArrayList<String> args){
        //不能重复开启游戏
        if(GAME_ON.equalsIgnoreCase(GAME_STATUS)){
            return ConstantRollBomb.GAME_GAMING;
        }
        //获取总数和雷数
        String numCnt = "100";
        String bombCnt = "1";
        if(args.size() > 1){
            numCnt = args.get(1);
        }
        if(args.size()>2){
            bombCnt = args.get(2);
        }
        //输入参数校验
//        if(StringUtil.is)
        return "";
    }


}
