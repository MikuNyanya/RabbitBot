package gugugu.listeners;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * 所有群消息监听
 */
public class GroupListener extends IcqListener
{
    @EventHandler
    public void onPMEvent(EventGroupMessage event)
    {
        if (event.getMessage().equals("兔子")) {
            event.getHttpApi().sendGroupMsg(event.groupId, "兔子");
        }

    }
}
