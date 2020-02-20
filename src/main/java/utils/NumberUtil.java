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
    public static boolean isNumberOnly(String str) {
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
     * 字符串转化为Integer数字
     *
     * @param str 输入字符串
     * @return 输出数字
     */
    public static Integer toInt(String str) {
        if (StringUtil.isEmpty(str) || !isNumberOnly(str)) {
            return 0;
        }
        return Integer.valueOf(str);
    }

    /**
     * 字符串转化为Double数字
     *
     * @param str 输入字符串
     * @return 输出数字
     */
    public static Double toDouble(String str) {
        if (StringUtil.isEmpty(str)) {
            return 0.0;
        }
        return Double.valueOf(str);
    }

    /**
     * 字符串转化为Long数字
     *
     * @param str 输入字符串
     * @return 输出数字
     */
    public static Long toLong(String str) {
        if (StringUtil.isEmpty(str) || !isNumberOnly(str)) {
            return 0L;
        }
        return Long.valueOf(str);
    }
}
