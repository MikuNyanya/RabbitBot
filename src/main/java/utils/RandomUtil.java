package utils;

import java.util.Random;

/**
 * create by MikuLink on 2019/12/3 14:47
 * for the Reisen
 * <p>
 * 随机数工具
 */
public class RandomUtil {
    /**
     * 随机一个正整数
     * 最小为0
     * 最大以传参为准
     *
     * @param maxNum 随机出的最大数
     * @return 一个符合条件的随机数
     */
    public static int roll(int maxNum) {
        //由于不会随机到入参本身，所以需要+1
        return new Random().nextInt(maxNum + 1);
    }

    /**
     * 重载 随机一个正整数
     * 0-100 包含0和100
     *
     * @return 一个0-100的随机数
     */
    public static int roll() {
        return roll(100);
    }

    /**
     * 随机是或否
     * 可以使用参数调整权重
     *
     * @param addition 加成，取值±100，100必定为true，-100必定为false
     * @return 是或否
     */
    public static boolean rollBoolean(int addition) {
        //随机0-199整数(加上0一共200个数字)
        int randNum = new Random().nextInt(200);
        //把范围限制在-100~100之间
        randNum -= 100;
        //两个绝对的结果
        if (randNum == 100) return true;
        if (randNum == -100) return false;

        //算上加成
        randNum += addition;
        return randNum >= 0;
    }
}
