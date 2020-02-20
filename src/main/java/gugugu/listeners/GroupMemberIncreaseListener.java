package gugugu.listeners;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
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
 * 奇怪的群员增加了(
 * 群员数量增加事件监听
 */
public class GroupMemberIncreaseListener extends IcqListener {

    @EventHandler
    public void onPMEvent(EventNoticeGroupMemberIncrease event) {
        try {
            StringBuilder resultMsg = new StringBuilder();
            //群号
            Long groupId = event.getGroupId();
            //新群员q号
            Long newMemberId = event.getUserId();
            //操作人q号
            Long operatorId = event.getOperatorId();

            //首先根据q号获取头像，获取不到就留空
            String newMemberQlogoCQ = ImageService.getQLogoCq(newMemberId);
            resultMsg.append(newMemberQlogoCQ);

            //获取新群员名称
            User memberUser = BotRabbit.bot.getUserManager().getUserFromID(newMemberId);
            String memberName = memberUser.getInfo().getNickname();
            resultMsg.append(String.format("\n欢迎新成员[%s]", memberName));

            //获取当前群里总人数
            ReturnListData<RGroupMemberInfo> Result = event.getHttpApi().getGroupMemberList(event.groupId);
            int groupMemberCount = Result.getData().size();
            resultMsg.append(String.format("\n你是第%s只兔子，本群兔子增员中——", groupMemberCount));

            //后面可以把其他东西写在配置里，给群主什么的用来加点公告性质的文字

            //发送群消息
            event.getHttpApi().sendGroupMsg(groupId, resultMsg.toString());
        } catch (Exception ex) {
            BotRabbit.bot.getLogger().error("新兔子入群提示功能发生错误", ex);
        }
    }
}
