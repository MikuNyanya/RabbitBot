package gugugu.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;
import gugugu.constant.ConstantImage;
import gugugu.service.ImageService;
import utils.StringUtil;

import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2020/02/19 16:10
 * for the Reisen
 * <p>
 * 搜图指令
 */
public class CommandImageSearch implements EverywhereCommand {
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
            return ConstantImage.IMAGE_SEARCH_NO_IMAGE_INPUT;
        }
        //获取传入图片，解析CQ中的网络图片链接
        String imgCQ = args.get(0);
        if (!imgCQ.contains("[CQ:image")) {
            return ConstantImage.IMAGE_SEARCH_NO_IMAGE_INPUT;
        }
        String imgUrl = getCQImageUrl(imgCQ);
        if (StringUtil.isEmpty(imgUrl)) {
            return ConstantImage.IMAGE_SEARCH_IMAGE_URL_PARSE_FAIL;
        }
        return ImageService.searchImgFromSaucenao(imgUrl);
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("ImageSearch", "搜图");
    }

    //暂时没找到cq的工具，先暴力解析
    private String getCQImageUrl(String cqStr) {
        if (null == cqStr) {
            return null;
        }
        if (!cqStr.contains("[CQ:image")) {
            return null;
        }

        String[] strs = cqStr.split(",");

        for (String str : strs) {
            if (StringUtil.isEmpty(str)) {
                continue;
            }
            if (!str.startsWith("url=")) {
                continue;
            }
            return str.substring("url=".length(), str.indexOf("]"));
        }
        return null;
    }
}
