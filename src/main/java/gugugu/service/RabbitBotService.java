package gugugu.service;

import cc.moecraft.icq.accounts.BotAccount;
import cc.moecraft.icq.sender.IcqHttpApi;
import gugugu.bots.BotRabbit;

import java.util.Map;

/**
 * create by MikuLink on 2020/1/9 17:00
 * for the Reisen
 * 机器人服务
 */
public class RabbitBotService {

    /**
     * 给每个群发送信息
     *
     * @param msg 信息体
     */
    public static void sendEveryGroupMsg(String msg) {
        //获取链接，参数是机器人的qq号
        IcqHttpApi icqHttpApi = BotRabbit.bot.getAccountManager().getIdIndex().get(BotRabbit.BOT_QQ).getHttpApi();

        //给每个群发送消息
        Map<Long, Map<BotAccount, Long>> groupList = BotRabbit.bot.getAccountManager().getGroupAccountIndex();
        for (Long groupId : groupList.keySet()) {
            icqHttpApi.sendGroupMsg(groupId, msg);
        }
    }

    /**
     * 给指定群发送信息
     *
     * @param groupId 群号
     * @param msg     信息体
     */
    public static void sendGroupMsg(Long groupId, String msg) {
        //获取链接，参数是机器人的qq号
        IcqHttpApi icqHttpApi = BotRabbit.bot.getAccountManager().getIdIndex().get(BotRabbit.BOT_QQ).getHttpApi();

        //给指定群发送消息
        icqHttpApi.sendGroupMsg(groupId, msg);
    }
}
