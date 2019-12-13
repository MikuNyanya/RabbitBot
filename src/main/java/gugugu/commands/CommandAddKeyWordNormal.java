package gugugu.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import gugugu.constant.ConstantKeyWord;
import gugugu.filemanage.FileManager;
import gugugu.filemanage.FileManagerKeyWordLike;
import gugugu.filemanage.FileManagerKeyWordNormal;
import utils.StringUtil;

import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * <p>
 * 添加全匹配的关键词
 */
public class CommandAddKeyWordNormal implements GroupCommand {
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
            return ConstantKeyWord.EXPLAIN;
        }
        if (args.size() < 2) {
            return ConstantKeyWord.PARAM_ERROR;
        }

        //index
        int i = 0;

        //所有参数检验
        for (String str : args) {
            i++;
            //关键词
            if (i == 1) {
                String key = args.get(0);
                //检查关键词格式
                for (String oneKey : key.split("\\|")) {
                    //关键词长度
                    if (oneKey.length() > ConstantKeyWord.KEY_WORD_MAX_SIZE) {
                        return ConstantKeyWord.KEY_WORD_OVER;
                    }
                    //判断关键词是否已存在 需要判断全匹配和模糊匹配两种
                    if (StringUtil.isNotEmpty(FileManagerKeyWordLike.keyWordLikeRegex(oneKey))
                            || StringUtil.isNotEmpty(FileManagerKeyWordNormal.keyWordNormalRegex(oneKey))) {
                        return String.format(ConstantKeyWord.KEY_WORD_EXISTS, oneKey);
                    }
                }
                continue;
            }

            //下面是回复
            //长度校验
            if (str.length() > ConstantKeyWord.KEY_WORD_RESPONSE_MAX_SIZE) {
                return ConstantKeyWord.KEY_WORD_RESPONSE_OVER;
            }
        }
        String[] tempStr = new String[args.size()];
        args.toArray(tempStr);
        //写入文件并添加到list
        FileManager.addKeyWordNormal(tempStr);

        return ConstantKeyWord.KEY_WORD_SAVE_SUCCESS;
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("AddKeyWordNormal", "addkwn");
    }
}
