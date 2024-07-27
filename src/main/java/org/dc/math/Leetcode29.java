package org.dc.math;

/**
 *
 * 给你两个整数，被除数 dividend 和除数 divisor。将两数相除，要求 不使用 乘法、除法和取余运算。
 * 整数除法应该向零截断，也就是截去（truncate）其小数部分。例如，8.345 将被截断为 8 ，-2.7335 将被截断至 -2 。
 * 返回被除数 dividend 除以除数 divisor 得到的 商 。
 * 注意：假设我们的环境只能存储 32 位 有符号整数，其数值范围是 [−231,  231 − 1] 。本题中，如果商 严格大于 231 − 1 ，则返回 231 − 1 ；如果商 严格小于 -231 ，则返回 -231 。
 *
 * 示例 1:
 * 输入: dividend = 10, divisor = 3
 * 输出: 3
 * 解释: 10/3 = 3.33333.. ，向零截断后得到 3 。
 *
 * 示例 2:
 * 输入: dividend = 7, divisor = -3
 * 输出: -2
 * 解释: 7/-3 = -2.33333.. ，向零截断后得到 -2 。
 *
 *
 * 提示：
 * -231 <= dividend, divisor <= 231 - 1
 * divisor != 0
 */
public class Leetcode29 {
    /**
     * 这道题的难点在于：
     * 不能使用除法、乘法、mod 运算；
     * 不能使用 long；
     * 考虑溢出问题；
     * 我们先来看溢出问题，这个其实只要关注一项就可以了，即被除数为 Integer.MIN_VALUE 而除数为 -1 的情况，因为负数的最小值的绝对值比正数的最大值大 1，所以，直接取反是会溢出，这种情况特殊处理即可。
     * 然后，针对不能不使用 long 的问题，我们可以把两个数都转换成负数来处理，原因同上，负数我们不能轻易转成正数来处理。
     * 最后，我们再来处理不能使用除法、乘法、mod 运算的问题，言外之意就是我们可以使用加法、减法、位运算等等。
     * 我们这里可以考虑使用【倍增乘法】来实现，所谓倍增乘法，简单理解就是每次用被除数减去[除数的最大的2^x]，这样可以极大地增加处理的速度。
     *
     * 比如，假定被除数为 20，除数为 3，使用倍乘法的过程如下：
     * 计算 3 的 2^x 的最大值（不超过 20），为 3∗2^2 =12，拿 20−12 得到 8，做为新的被除数。
     * 计算 3 的 2^x的最大值（不超过 8），为 3∗2^1=6，拿 8−6 得到 2，做为新的被除数。
     * 判断新的被除数 2 小于 3 了，退出计算过程，最后的结果就是 2^2+2^1=6。
     * 当然，上述的描述过程我们使用了乘法，实际我们却不能使用乘法，所以，我们可以使用加法来处理
     */
    public int divide(int dividend, int divisor) {
        // 溢出情况
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        // 记录结果的符号
        int sign = -1;
        if ((dividend > 0 && divisor > 0) || (dividend < 0 && divisor < 0)) {
            sign = 1;
        }

        // 全部转换成负数，防止溢出
        dividend = dividend > 0 ? -dividend : dividend;
        divisor = divisor > 0 ? -divisor : divisor;

        int ans = 0;
        // 倍乘法，注意都是负数了，比较的时候与正数相反
        // 简单点理解，就是每次减去除数的 2^x 倍数，剩下的部分继续按这样的规则继续
        while (dividend <= divisor) {
            int temp = divisor,  count = 1;
            // 这里注意不要写成 tmp + tmp >= dividend，这样写加法有可能会溢出导致死循环
            while (temp >= dividend - temp) {
                // tmp 和 count 每次增加一倍，所以叫倍增
                temp += temp;
                count += count;
            }
            // 被除数减去除数的 2^x 倍数做为新的被除数
            dividend -= temp;
            // count 即 2^x
            ans += count;
        }

        return sign < 0 ? -ans : ans;
    }
}
