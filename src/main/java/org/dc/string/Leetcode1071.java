package org.dc.string;

/**
 *
 * 对于字符串 s 和 t，只有在 s = t + t + t + ... + t + t（t 自身连接 1 次或多次）时，我们才认定 “t 能除尽 s”。
 * 给定两个字符串 str1 和 str2 。返回 最长字符串 x，要求满足 x 能除尽 str1 且 x 能除尽 str2 。
 *
 * 示例 1：
 * 输入：str1 = "ABCABC", str2 = "ABC"
 * 输出："ABC"
 *
 * 示例 2：
 * 输入：str1 = "ABABAB", str2 = "ABAB"
 * 输出："AB"
 *
 * 示例 3：
 * 输入：str1 = "LEET", str2 = "CODE"
 * 输出：""
 *
 *
 * 提示：
 * 1 <= str1.length, str2.length <= 1000
 * str1 和 str2 由大写英文字母组成
 *
 */
public class Leetcode1071 {
    /**
     *
     * 需要知道一个性质：如果 str1 和 str2 拼接后等于 str2和 str1 拼接起来的字符串（注意拼接顺序不同），那么一定存在符合条件的字符串 X。
     * 先证必要性，即如果存在符合条件的字符串 X ，则 str1 和 str2 拼接后等于 str2和 str1 拼接起来的字符串。
     * 如果字符串 X 符合条件，那么 str1=X+X+...+X+X=n*X ，str2=X+X+..+X+X=m*X，n*X 表示 n 个字符串 X 拼接，m*X 同理，
     * 则 str1 与 str2 拼接后的字符串即为 (n+m)*X，而 str2 与 str1 拼接后的字符串即为 (m+n)*X，等于 (n+m)*X，所以必要性得证。
     * 再看充分性，简单来说，我们可以如下图一样先将两个拼接后的字符串放在一起。不失一般性，我们假定 str1 的长度大于 str2
     *
     * 要么存在x的长度即为最大公约数
     * str1 = k1x, str2 = k2x，即 str1 的长度（记作 n1） 等于 k1 倍 x 的长度，str2 的长度（记作 n2）等于 k2 倍 x 的长度。因此 x 的长度（记作 x_len）就为 n1 和 n2 的公约数。
     * 根据性质：最大公约数等于其他公约数的乘积，因此 x_len 一定等于 n1 和 n2 的最大公约数。
     */
    public String gcdOfStrings(String str1, String str2) {
        // 假设str1是N个x，str2是M个x，那么str1+str2肯定是等于str2+str1的。
        if (!(str1 + str2).equals(str2 + str1)) {
            return "";
        }

        // 辗转相除法求gcd。
        return str1.substring(0, gcd(str1.length(), str2.length()));
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
