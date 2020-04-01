package gugugu.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;
import gugugu.bots.LoggerRabbit;
import gugugu.constant.ConstantQRCode;
import gugugu.service.QRCodeService;
import utils.StringUtil;

import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2020/03/31 17:33
 * for the Reisen
 * <p>
 * 生成二维码
 */
public class CommandQRCode implements EverywhereCommand {
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
            return ConstantQRCode.EXPLAIN;
        }
        //二维码内容
        String text = args.get(0);
        //图片内容处理
        if (text.contains("[CQ:image")) {
            //此时内容是网络图片链接
            text = StringUtil.getCQImageUrl(text);
        }
        String bgColor = null;
        String fgColor = null;
        //由于需要网络图片链接，目前没有可用的图床，只好直接用酷Q的，无法对图片进行修正
        String logo = null;

        //其他设定
        int paramSize = args.size();
        if (paramSize >= 2) {
            bgColor = args.get(1);
        }
        if (paramSize >= 3) {
            fgColor = args.get(2);
        }
        if (paramSize >= 4) {
            logo = args.get(3);
            if (!logo.contains("[CQ:image")) {
                return ConstantQRCode.QRCODE_LOGO_NOT_IMAGE;
            }
            logo = StringUtil.getCQImageUrl(logo);
        }

        String result = "";
        try {
            result = QRCodeService.doQRCodeRequest(text, bgColor, fgColor, logo);
        } catch (Exception ex) {
            LoggerRabbit.logger().error(ConstantQRCode.QRCODE_ERROR + ex.toString(), ex);
            result = ConstantQRCode.QRCODE_ERROR;
        }
        return result;
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("生成二维码", "二维码", "qrcode");
    }
}
