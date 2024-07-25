package org.dc.dp;

import java.util.ArrayList;
import java.util.List;

/**
 * level hard
 *
 * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
 *
 * '.' 匹配任意单个字符
 * '*' 匹配零个或多个前面的那一个元素
 * 所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。
 */
public class Leetcode10 {
    /**
     * s(0...i) 下标从0到i的子串，p(0...j)下标从0到j的子串
     * s(0...i-1) 和 p(0...j-1) 是否匹配，使用 dp[i][j] 来表示，true 匹配 false 不匹配
     */
    public boolean isMatch1(String s, String p) {
        char[] cs = s.toCharArray();
        char[] cp = p.toCharArray();

        // dp[i][j]:表示s的前i个字符，p的前j个字符是否能够匹配
        boolean[][] dp = new boolean[cs.length + 1][cp.length + 1];

        // 初期值
        // s为空，p为空，能匹配上
        dp[0][0] = true;
        // p为空，s不为空，必为false(boolean数组默认值为false，无需处理)

        // s为空，p不为空，由于*可以匹配0个字符，所以有可能为true
        for (int j = 1; j <= cp.length; j++) {
            if (cp[j - 1] == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }

        // 填格子
        for (int i = 1; i <= cs.length; i++) {
            for (int j = 1; j <= cp.length; j++) {
                // 文本串和模式串末位字符能匹配上
                if (cs[i - 1] == cp[j - 1] || cp[j - 1] == '.') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (cp[j - 1] == '*') {
                    if (cs[i - 1] == cp[j - 2] || cp[j - 2] == '.') {
                        dp[i][j] = dp[i][j - 2]
                                || dp[i - 1][j - 2]
                                || dp[i - 1][j];
                    } else {
                        dp[i][j] = dp[i][j - 2];
                    }
                }
            }
        }

        return dp[cs.length][cp.length];
    }

    /**
     * 我们用 f[i][j] 表示 s 的前 i 个字符与 p 中的前 j 个字符是否能够匹配。在进行状态转移时，我们考虑 p 的第 j 个字符的匹配情况：
     *
     * 如果 p 的第 j 个字符是一个小写字母，那么我们必须在 s 中匹配一个相同的小写字母，即
     * f[i][j] = f[i−1][j−1](s[i]=p[j]) or false(s[i]!=p[j])
     *
     * 也就是说，如果 s 的第 i 个字符与 p 的第 j 个字符不相同，那么无法进行匹配；否则我们可以匹配两个字符串的最后一个字符，完整的匹配结果取决于两个字符串前面的部分。
     *
     * 如果 p 的第 j 个字符是 *，那么就表示我们可以对 p 的第 j−1 个字符匹配任意自然数次。
     * 在匹配 0 次的情况下，我们有 f[i][j] = f[i][j−2] 也就是我们「浪费」了一个字符 + 星号的组合，没有匹配任何 s 中的字符。
     *
     * 在匹配 1,2,3,⋯ 次的情况下，类似地我们有
     * f[i][j] = f[i−1][j−2], if s[i]=p[j−1]
     * f[i][j] = f[i−2][j−2], if s[i−1]=s[i]=p[j−1]
     * f[i][j] = f[i−3][j−2], if s[i−2]=s[i−1]=s[i]=p[j−1]
     * ⋯⋯
     * 如果我们通过这种方法进行转移，那么我们就需要枚举这个组合到底匹配了 s 中的几个字符，会增导致时间复杂度增加，并且代码编写起来十分麻烦。
     * 我们不妨换个角度考虑这个问题：字母 + 星号的组合在匹配的过程中，本质上只会有两种情况：
     *
     * 匹配 s 末尾的一个字符，将该字符扔掉，而该组合还可以继续进行匹配；
     * 不匹配字符，将该组合扔掉，不再进行匹配。
     * 如果按照这个角度进行思考，我们可以写出很精巧的状态转移方程：
     * f[i][j]= f[i−1][j] || f[i][j−2] (s[i]=p[j−1]) or f[i][j−2]（s[i]!=p[j−1]）
     * 在任意情况下，只要 p[j] 是 .，那么 p[j] 一定成功匹配 s 中的任意一个小写字母。
     *
     * 其中 matches(x,y) 判断两个字符是否匹配的辅助函数。只有当 y 是 . 或者 x 和 y 本身相同时，这两个字符才会匹配。
     *
     * 细节
     * 动态规划的边界条件为 f[0][0]=true，即两个空字符串是可以匹配的。最终的答案即为 f[m][n]，其中 m 和 n 分别是字符串 s 和 p 的长度。
     * 由于大部分语言中，字符串的字符下标是从 0 开始的，因此在实现上面的状态转移方程时，需要注意状态中每一维下标与实际字符下标的对应关系。
     */
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();

        boolean[][] f = new boolean[m + 1][n + 1];
        f[0][0] = true;
        for (int i = 0; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (p.charAt(j - 1) == '*') {
                    f[i][j] = f[i][j - 2];
                    if (matches(s, p, i, j - 1)) {
                        f[i][j] = f[i][j] || f[i - 1][j];
                    }
                } else {
                    if (matches(s, p, i, j)) {
                        f[i][j] = f[i - 1][j - 1];
                    }
                }
            }
        }
        return f[m][n];
    }

    public boolean matches(String s, String p, int i, int j) {
        if (i == 0) {
            return false;
        }

        if (p.charAt(j - 1) == '.') {
            return true;
        }

        return s.charAt(i - 1) == p.charAt(j - 1);
    }
}
