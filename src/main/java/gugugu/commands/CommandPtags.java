package gugugu.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;
import gugugu.constant.ConstantImage;
import utils.RandomUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MikuLink
 * @date 2012/03/18 11:33
 * for the Reisen
 * <p>
 * 展示部分tag
 */
public class CommandPtags implements EverywhereCommand {
    //展示的tag数量
    private static final int SHOW_TAG_QTY = 10;

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
        //随机返回一定数量的tag，直接随机
        StringBuilder tagStr = new StringBuilder();
        tagStr.append("=====Pixiv tag=====");
        if (0 == ConstantImage.PIXIV_TAG_LIST.size()) {
            tagStr.append(ConstantImage.PIXIV_TAG_IS_EMPTY);
            tagStr.append("===================");
            return tagStr.toString();
        }

        int i = 0;
        List<String> tempTagList = new ArrayList<>();
        do {
            if (ConstantImage.PIXIV_TAG_LIST.size() < SHOW_TAG_QTY) {
                tempTagList.addAll(ConstantImage.PIXIV_TAG_LIST);
                break;
            }
            //防止死循环
            if (i > 50) {
                break;
            }

            //从内存里随机出一个tag
            String tag = RandomUtil.rollStrFromList(ConstantImage.PIXIV_TAG_LIST);
            //随机到重复的则再随机一次
            if (tempTagList.contains(tag)) {
                continue;
            }

            tempTagList.add(tag);
            i++;
        } while (SHOW_TAG_QTY > i);

        for (String tagTemp : tempTagList) {
            tagStr.append("\n[" + tagTemp + "]");
        }

        tagStr.append("\n===================");

        return tagStr.toString();
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("PixivTags", "ptags");
    }
}
