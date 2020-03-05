package gugugu.commands.groups;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import gugugu.constant.ConstantCapsuleToy;
import gugugu.entity.GroupUserInfo;
import gugugu.filemanage.FileManager;
import utils.RandomUtil;
import utils.StringUtil;

import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * <p>
 * 添加扭蛋
 */
public class CommandCapsuleToy implements GroupCommand {
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
            //操作间隔判断
            String timeCheck = timeCheck(event.getGroupUserInfo());
            if (StringUtil.isNotEmpty(timeCheck)) {
                return timeCheck;
            }
            //进行扭蛋
            String capsuleToy = capsuleToySelect();
            if (StringUtil.isEmpty(capsuleToy)) {
                return ConstantCapsuleToy.CAPSULE_TOY_HAS_NOTHING;
            }
            //刷新操作间隔
            ConstantCapsuleToy.CAPSULE_TOY_SPLIT_MAP.put(event.getGroupUserInfo().getUserId(), System.currentTimeMillis());
            //群名片
            String groupUserName = event.getGroupUserInfo().getGroupUserName();
            return String.format(ConstantCapsuleToy.MSG_CAPSULE_TOY_RESULT, groupUserName, capsuleToy);
        }

        //添加扭蛋部分
        //判断副指令
        String commandSecond = args.get(0);
        if (!ConstantCapsuleToy.ADD.equalsIgnoreCase(commandSecond)) {
            return ConstantCapsuleToy.COMMAND_SECOND_ERROR;
        }
        //判断有没有添加的信息
        if (args.size() < 2) {
            return ConstantCapsuleToy.EXPLAIN_ADD;
        }
        String capsuleToy = args.get(1);

        //添加扭蛋
        String addResult = capsuleToyAdd(capsuleToy);
        return addResult;
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("CapsuleToy", "扭蛋");
    }

    /**
     * 选择一个扭蛋
     *
     * @return 随机扭蛋
     */
    private String capsuleToySelect() {
        //集合为空时，重新加载一次扭蛋文件
        if (ConstantCapsuleToy.MSG_CAPSULE_TOY.size() == 0) {
            FileManager.loadCapsuleToy();
        }
        //扭个蛋
        String capsuleToy = RandomUtil.rollStrFromList(ConstantCapsuleToy.MSG_CAPSULE_TOY);
        //删除这个扭蛋，实现伪随机
        ConstantCapsuleToy.MSG_CAPSULE_TOY.remove(capsuleToy);
        //元素少于1/6的时候，重新加载
        if (ConstantCapsuleToy.MSG_CAPSULE_TOY.size() < ConstantCapsuleToy.CAPSULE_TOY_SPLIT_MAX_SIZE / 6) {
            FileManager.loadCapsuleToy();
        }
        return capsuleToy;
    }

    /**
     * 添加一个扭蛋
     *
     * @param capsuleToy 新的扭蛋
     * @return 添加结果
     */
    private String capsuleToyAdd(String capsuleToy) {
        //判空
        if (StringUtil.isEmpty(capsuleToy)) {
            return ConstantCapsuleToy.CAPSULE_TOY_ADD_EMPTY;
        }

        //判重
        if (ConstantCapsuleToy.MSG_CAPSULE_TOY.size() == 0) {
            FileManager.loadCapsuleToy();
        }
        if (ConstantCapsuleToy.MSG_CAPSULE_TOY.contains(capsuleToy)) {
            return String.format(ConstantCapsuleToy.CAPSULE_TOY_ADD_RE, capsuleToy);
        }

        //添加扭蛋
        FileManager.addCapsuleToy(capsuleToy);
        return String.format(ConstantCapsuleToy.MSG_CAPSULE_TOY_ADD_SUCCESS, capsuleToy);
    }

    /**
     * 操作间隔控制
     *
     * @return
     */
    private String timeCheck(GroupUserInfo groupUser) {
        Long qq = groupUser.getUserId();
        String name = groupUser.getGroupUserName();

        if (!ConstantCapsuleToy.CAPSULE_TOY_SPLIT_MAP.containsKey(qq)) {
            return null;
        }
        Long lastTime = ConstantCapsuleToy.CAPSULE_TOY_SPLIT_MAP.get(qq);
        if (null == lastTime) {
            return null;
        }
        Long nowTime = System.currentTimeMillis();
        Long splitTime = nowTime - lastTime;
        //判断是否允许操作
        if (splitTime <= ConstantCapsuleToy.CAPSULE_TOY_SPLIT_TIME) {
            return String.format(ConstantCapsuleToy.CAPSULE_TOY_SPLIT_ERROR, name, (ConstantCapsuleToy.CAPSULE_TOY_SPLIT_TIME - splitTime) / 1000);
        }
        //其他情况允许操作
        return null;
    }

}
