package cc.moecraft.test.icq.listeners;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;

/**
 * 此类由 Hykilpikonna 在 2018/05/24 创建!
 * Created by Hykilpikonna on 2018/05/24!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class TestGroupListener extends IcqListener {
    @EventHandler
    public void onPMEvent(EventGroupMessage event) {

        if (event.getMessage().equals("1")) {
            event.getHttpApi().sendGroupMsg(event.groupId, "1");
        }

    }

}
