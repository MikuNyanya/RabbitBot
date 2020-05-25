package gugugu.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;
import gugugu.constant.ConstantImage;
import utils.RandomUtil;
import utils.RegexUtil;
import utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MikuLink
 * @date 2020/04/30 11:33
 * for the Reisen
 * <p>
 * 搜索P站作者信息指令
 */
public class CommandPUsers implements EverywhereCommand {
    private static final int SHOW_COUNT = 15;

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
        String memberName = null;
        if (null != args && args.size() > 0) {
            memberName = args.get(0);
        }

        List<String> tempList = new ArrayList<>();

        //传入作者名称参数为空，随机返回指定数目作者信息
        if (StringUtil.isEmpty(memberName)) {
            int localMemberListSize = ConstantImage.PIXIV_MEMBER_LIST.size();
            if (localMemberListSize <= SHOW_COUNT) {
                tempList = ConstantImage.PIXIV_MEMBER_LIST;
            } else {
                List<Integer> randNumList = RandomUtil.roll(localMemberListSize - 1, SHOW_COUNT);
                if (null == randNumList) {
                    randNumList = new ArrayList<>();
                }
                for (Integer index : randNumList) {
                    tempList.add(ConstantImage.PIXIV_MEMBER_LIST.get(index));
                }
            }
        }

        //传入作者不为空，去模糊搜索用户名 todo 全搜索本地，不存在则请求pixiv搜索用户
        if (StringUtil.isNotEmpty(memberName)) {
            for (String localMemberStr : ConstantImage.PIXIV_MEMBER_LIST) {
                String[] memberStrs = localMemberStr.split(",");
                if (memberStrs.length < 2) {
                    continue;
                }
                boolean isRegex = RegexUtil.regex(memberStrs[1], memberName);
                if (isRegex) {
                    tempList.add(localMemberStr);
                }
            }
        }

        StringBuilder resultSb = new StringBuilder();
        resultSb.append("========Pixiv作者信息========");

        for (String memberStr : tempList) {
            String[] memberStrs = memberStr.split(",");
            if (memberStrs.length < 2) {
                continue;
            }
//            String resultMemberIdStr = memberIdStrSpace(String.format("\n作者id [%s]", memberStrs[0]));
//            resultSb.append(resultMemberIdStr);
//            resultSb.append(String.format("作者名称 [%s]", memberStrs[1]));

            resultSb.append(String.format("\n作者id [%s]\t作者名称 [%s]", memberStrs[0], memberStrs[1]));
        }

        return resultSb.toString();
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("PixivUsers", "pusers", "pmembers");
    }

    //返回时作者id长度固定，好让输出的结果比较整齐
//    private String memberIdStrSpace(String memberIdStr) {
//        if (memberIdStr.length() >= 25) {
//            return memberIdStr;
//        }
//        memberIdStr = memberIdStr + " ";
//        return memberIdStrSpace(memberIdStr);
//    }
}
