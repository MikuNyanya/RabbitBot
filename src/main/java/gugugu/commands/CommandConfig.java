package gugugu.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;
import gugugu.bots.BotRabbit;
import gugugu.constant.ConstantConfig;
import gugugu.constant.ConstantFile;
import gugugu.entity.ReString;
import gugugu.filemanage.FileManagerConfig;
import utils.RandomUtil;
import utils.StringUtil;

import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * <p>
 * 系统简述
 */
public class CommandConfig implements EverywhereCommand {
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

        if (null == args || args.size() < 1) {
            return ConstantConfig.ARGS_ERROR;
        }
        //解析副指令
        String action = args.get(0);
        //解析配置key
        String configName = null;
        if (args.size() >= 2) {
            configName = args.get(1);
        }
        //解析配置值
        String configValue = null;
        if (args.size() >= 3) {
            configValue = args.get(2);
        }

        ReString reString = accessCheck(sender.getId(), configName);
        if (!reString.isSuccess()) {
            return reString.getMessage();
        }

        String resultMsg = "";
        switch (action) {
            case ConstantConfig.SET:
                resultMsg = setConfig(configName, configValue);
                break;
            case ConstantConfig.DELETE:
            case ConstantConfig.DEL:
                resultMsg = delConfig(configName);
                break;
        }

        return resultMsg;
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("Config", "config", "配置");
    }

    /**
     * 设置参数
     *
     * @param configName  参数名称
     * @param configValue 参数值
     * @return 响应信息
     */
    private String setConfig(String configName, String configValue) {
        if (StringUtil.isEmpty(configName)) {
            return ConstantConfig.CONFIG_NAME_EMPTY;
        }
        if (StringUtil.isEmpty(configValue)) {
            return ConstantConfig.CONFIG_VALUE_EMPTY;
        }
        //设置配置文件
        ConstantConfig.common_config.put(configName, configValue);
        //更新配置文件
        FileManagerConfig.doCommand(ConstantFile.FILE_COMMAND_WRITE);
        return ConstantConfig.CONFIG_SET_SUCCESS;
    }

    /**
     * 删除配置
     *
     * @param configName 参数名称
     * @return 响应信息
     */
    private String delConfig(String configName) {
        if (StringUtil.isEmpty(configName)) {
            return ConstantConfig.CONFIG_NAME_EMPTY;
        }
        if (!ConstantConfig.common_config.containsKey(configName)) {
            return ConstantConfig.CONFIG_NOT_FOUND;
        }
        //删除配置文件
        ConstantConfig.common_config.remove(configName);
        //更新配置文件
        FileManagerConfig.doCommand(ConstantFile.FILE_COMMAND_WRITE);
        return ConstantConfig.CONFIG_SET_SUCCESS;
    }

    /**
     * 权限检查
     * 以后有页面了做成页面配置
     */
    private ReString accessCheck(Long qq, String configName) {
        if (qq == 403621469L && ConstantConfig.CONFIG_R18.equalsIgnoreCase(configName)) {
            return new ReString(true);
        }

        if (!BotRabbit.MASTER_QQ.contains(qq)) {
            return new ReString(false, RandomUtil.rollStrFromList(ConstantConfig.COMMAND_MASTER_ONLY));
        }

        return new ReString(true);
    }
}
