package gugugu.listeners;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.notice.groupmember.decrease.EventNoticeGroupMemberDecrease;
import cc.moecraft.icq.event.events.notice.groupmember.increase.EventNoticeGroupMemberIncrease;
import cc.moecraft.icq.sender.returndata.ReturnListData;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RGroupMemberInfo;
import cc.moecraft.icq.user.User;
import gugugu.bots.BotRabbit;
import gugugu.service.ImageService;

/**
 * @author MikuLink
 * @date 2020/02/18 14:19
 * for the Reisen
 * 群员数量减少事件监听
 */
public class GroupMemberDecreaseListener extends IcqListener {

    @EventHandler
    public void onPMEvent(EventNoticeGroupMemberDecrease event) {
        try {
            StringBuilder resultMsg = new StringBuilder();
            //新群员q号
            Long newMemberId = event.getUserId();

            //首先根据q号获取头像，获取不到就留空
            String newMemberQlogoCQ = ImageService.getQLogoCq(newMemberId);
            resultMsg.append(newMemberQlogoCQ);

            //获取新群员名称
//            User memberUser = BotRabbit.bot.getUserManager().getUserFromID(newMemberId);
//            String memberName = memberUser.getInfo().getNickname();
            resultMsg.append("\n本群兔子-1");

            //后面可以把其他东西写在配置里，给群主什么的用来加点公告性质的文字

            //发送群消息
            event.getHttpApi().sendGroupMsg(event.getGroupId(), resultMsg.toString());
        } catch (Exception ex) {
            BotRabbit.bot.getLogger().error("兔子退群提示功能发生错误", ex);
        }
    }
}
