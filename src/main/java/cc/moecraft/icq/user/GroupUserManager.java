package cc.moecraft.icq.user;

import cc.moecraft.icq.PicqBotX;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 此类由 Hykilpikonna 在 2018/05/26 创建!
 * Created by Hykilpikonna on 2018/05/26!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
@RequiredArgsConstructor
public class GroupUserManager {
    private final PicqBotX bot;

    // <GroupID, <UserID, UserCache>>
    public Map<Long, Map<Long, GroupUser>> groupCache = new HashMap<>();

    /**
     * 用 ID 和群对象获取用户对象
     *
     * @param id    用户 ID
     * @param group 群对象
     * @return 用户对象
     */
    public GroupUser getUserFromID(long id, Group group) {
        // 没有群缓存
        if (!groupCache.containsKey(id)) {
            groupCache.put(group.getId(), new HashMap<>());
        }

        // 获取用户缓存
        Map<Long, GroupUser> userCache = groupCache.get(id);

        //修复上个作者的空指针问题
        if (null == userCache) {
            userCache = new HashMap<>();
        }

        // 获取用户
        if (userCache.containsKey(id)) {
            return userCache.get(id);
        }
        userCache.put(id, new GroupUser(bot, id, group));
        return getUserFromID(id, group);
    }
}
