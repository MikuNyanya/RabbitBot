package gugugu.listeners;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import gugugu.constant.ConstantCommon;
import utils.RandomUtil;
import utils.StringUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * 所有群消息监听
 */
public class GroupListener extends IcqListener {
    //保存群最后一条消息
    private static Map<Long, String> LAST_MSG_MAP = new HashMap<>();
    private static final List<String> REPEATER_KILLER_LIST = Arrays.asList(
            "一只兔叽及时出现，打断了复读（￣▽￣）／",
            "Nope,不许复读",
            "一只兔叽一般路过，并顺便打断了复读=w="
    );
    private static final List<String> REPEATER_STOP_LIST = Arrays.asList(
            "请不要复读兔叽的话←_←",
            "啊。。。你够了=A=",
            "这么搞真的不会出BUG么。。。",
            "你是笨蛋嘛=A=",
            "ヽ(｀Д´)ﾉ︵ ┻━┻ ┻━┻"
    );

    @EventHandler
    public void onPMEvent(EventGroupMessage event) {
        //群复读
        groupRepeater(event);

        if (event.getMessage().equals("兔子")) {
            event.getHttpApi().sendGroupMsg(event.groupId, "\\兔子/");
        }

    }

    /**
     * 群复读
     *
     * @param event 群消息监控
     */
    private void groupRepeater(EventGroupMessage event) {
        //接收到的群消息
        String groupMsg = event.getMessage();
        Long groupId = event.getGroupId();

        //第一次消息初始化
        if (!LAST_MSG_MAP.containsKey(groupId)) {
            LAST_MSG_MAP.put(groupId, "");
        }

        //屏蔽正常指令
        if (StringUtil.isNotEmpty(groupMsg) && ConstantCommon.COMMAND_INDEX.equalsIgnoreCase(groupMsg.substring(0, 1))) {
            return;
        }

        //群复读，两个相同的消息，复读一次，并重置计数
        if (LAST_MSG_MAP.get(groupId).equals(groupMsg)) {
            if (REPEATER_KILLER_LIST.contains(groupMsg) || REPEATER_STOP_LIST.contains(groupMsg)) {
                //打断
                groupMsg = REPEATER_STOP_LIST.get(RandomUtil.roll(REPEATER_STOP_LIST.size() - 1));
            } else if (RandomUtil.rollBoolean(-80)) {
                //打断复读
                groupMsg = REPEATER_KILLER_LIST.get(RandomUtil.roll(REPEATER_KILLER_LIST.size() - 1));
            }
            event.getHttpApi().sendGroupMsg(event.groupId, groupMsg);
            LAST_MSG_MAP.put(groupId, "");
            return;
        }
        //覆盖最后消息
        LAST_MSG_MAP.put(groupId, groupMsg);
    }
}
