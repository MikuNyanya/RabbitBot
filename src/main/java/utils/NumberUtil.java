package utils;

/**
 * create by MikuLink on 2019/12/3 20:07
 * for the Reisen
 * <p>
 * 数字工具类
 */
public class NumberUtil {

    /**
     * 判断一个字符串是否为纯数字组成
     *
     * @param str 需要判断的字符串
     * @return 是否为纯数字
     */
    public static boolean isNumber(String str) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        char[] cc = str.toCharArray();
        for (char c : cc) {
            if (!RegexUtil.regex(String.valueOf(c), "[0-9]")) {
                return false;
            }
        }

        return true;
    }

    /**
     * 字符串转化为数字
     *
     * @param str 输入字符串
     * @return 输出数字
     */
    public static Integer toInt(String str) {
        if (StringUtil.isEmpty(str) || !isNumber(str)) {
            return 0;
        }
        return Integer.valueOf(str);
    }
}
