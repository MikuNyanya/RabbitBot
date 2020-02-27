package gugugu.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;
import gugugu.constant.ConstantMorseCode;
import gugugu.service.MorseCodeService;

import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2020/02/27 09:33
 * for the Reisen
 * <p>
 * 摩尔斯电码
 */
public class CommandMorseCode implements EverywhereCommand {
    /**
     * 执行指令
     *
     * @param event   事件
     * @param sender  发送者的用户
     * @param command 指令名 ( 不包含指令参数 )
     * @param args    指令参数 ( 不包含指令名 )
     * @return 发送回去的消息 ( 当然也可以手动发送然后返回空 )
     */
    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args) {
        if (null == args || args.size() == 0) {
            return ConstantMorseCode.MORSE_CODE_TEXT;
        }
        //校验参数
        if (args.size() < 2) {
            return ConstantMorseCode.INPUT_IS_EMPTY;
        }
        //第二指令
        String action = args.get(0);
        //后面所有的参数拼接起来作为传入字符串
        StringBuilder inputStr = new StringBuilder();
        for (int i = 1; i < args.size(); i++) {
            inputStr.append(args.get(i) + ConstantMorseCode.DEFAULT_SPLIT);
        }

        String result = "";
        switch (action) {
            case ConstantMorseCode.ENCODE:
            case ConstantMorseCode.CN_ENCODE:
                result = MorseCodeService.encode(inputStr.toString(), null);
                break;
            case ConstantMorseCode.DECODE:
            case ConstantMorseCode.CN_DECODE:
                result = MorseCodeService.decode(inputStr.toString(), null);
                break;
            default:
                result = ConstantMorseCode.ACTION_ERROR;
        }
        //最终结果转为小写
        return result.toLowerCase();
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("MorseCode", "morse", "摩尔斯");
    }
}
