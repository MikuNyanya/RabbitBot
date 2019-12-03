package utils;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * <p>
 * 字符串工具类
 */
public class StringUtil {
    /**
     * 是否为空
     * null或者空字符串都会判定为true
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 是否不为空
     * null或者空字符串都会判定为false
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 去掉字符串中的所有空格
     *
     * @param str
     * @return
     */
    public static String trimAll(String str) {
        if (str == null)
            return "";
        return str.replaceAll(" ", "");
    }

    /**
     * 去除首尾空格
     *
     * @param str
     * @return
     */
    public static String trim(String str) {
        if (null == str)
            return "";
        return str.trim();
    }

}
