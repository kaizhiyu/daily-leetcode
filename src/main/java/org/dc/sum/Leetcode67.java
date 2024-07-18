package org.dc.sum;

/**
 *
 * 给你两个二进制字符串 a 和 b ，以二进制字符串的形式返回它们的和。
 *
 * 示例 1：
 * 输入:a = "11", b = "1"
 * 输出："100"
 *
 * 示例 2：
 * 输入：a = "1010", b = "1011"
 * 输出："10101"
 *
 *
 * 提示：
 * 1 <= a.length, b.length <= 104
 * a 和 b 仅由字符 '0' 或 '1' 组成
 * 字符串如果不是 "0" ，就不含前导零
 *
 */
public class Leetcode67 {
    public String addBinary(String a, String b) {
        int aL = a.length();
        int bL = b.length();

        StringBuilder sb = new StringBuilder();
        int inc = 0;
        while (aL > 0 || bL > 0) {
            int as = aL > 0 ? a.charAt(aL - 1) - '0' : 0;
            int bs = bL > 0 ? b.charAt(bL - 1) - '0' : 0;

            int res = as + bs + inc;
            sb.append(res % 2);
            inc = res / 2;

            if (aL > 0) {
                aL--;
            }

            if (bL > 0) {
                bL--;
            }
        }

        if (inc > 0) {
            sb.append(inc);
        }

        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        String a = "11", b = "1";
        System.out.println(new Leetcode67().addBinary(a, b));
    }
}
