package org.dc.dp;

import java.util.Arrays;

/**
 *
 * 给你两个单词 word1 和 word2， 请返回将 word1 转换成 word2 所使用的最少操作数  。
 * 你可以对一个单词进行如下三种操作：
 * 插入一个字符
 * 删除一个字符
 * 替换一个字符
 *
 * 示例 1：
 * 输入：word1 = "horse", word2 = "ros"
 * 输出：3
 * 解释：
 * horse -> rorse (将 'h' 替换为 'r')
 * rorse -> rose (删除 'r')
 * rose -> ros (删除 'e')
 *
 * 示例 2：
 * 输入：word1 = "intention", word2 = "execution"
 * 输出：5
 * 解释：
 * intention -> inention (删除 't')
 * inention -> enention (将 'i' 替换为 'e')
 * enention -> exention (将 'n' 替换为 'x')
 * exention -> exection (将 'n' 替换为 'c')
 * exection -> execution (插入 'u')
 *
 *
 * 提示：
 * 0 <= word1.length, word2.length <= 500
 * word1 和 word2 由小写英文字母组成
 *
 */
public class Leetcode72 {
    /**
     * s = horse
     * t = ros
     *
     * dfs(i, j) = s[i] == t[j] ? dfs(i-1, j-1) : min(dfs(i-1, j) , dfs(i, j-1), dfs(i-1, j-1)) + 1
     * dfs(i-1, j)    删除
     * dfs(i, j-1)    插入
     * dfs(i-1, j-1)  替换
     *
     * 一、递归搜索 + 保存计算结果 = 记忆化搜索
     *
     */
    private char[] s, t;
    private int[][] memo;

    public int minDistance(String word1, String word2) {
        s = word1.toCharArray();
        t = word2.toCharArray();
        int n = s.length, m = t.length;

        memo = new int[n][m];

        for (int i = 0; i < n; i++)
            Arrays.fill(memo[i], -1); // -1 表示还没有计算过

        return dfs(n - 1, m - 1);
    }

    private int dfs(int i, int j) {
        // 一个字符串为空，需要把另外一个不为空的字符串都去掉，所以返回 i/j + 1
        if (i < 0) return j + 1;
        if (j < 0) return i + 1;

        if (memo[i][j] != -1) return memo[i][j]; // 之前算过了

        if (s[i] == t[j]) return memo[i][j] = dfs(i - 1, j - 1);

        return memo[i][j] = Math.min(Math.min(dfs(i - 1, j), dfs(i, j - 1)), dfs(i - 1, j - 1)) + 1;
    }

    /**
     * 1:1 翻译成递推
     */
    public int minDistance2(String text1, String text2) {
        char[] s = text1.toCharArray(), t = text2.toCharArray();
        int n = s.length, m = t.length;
        int[][] f = new int[n + 1][m + 1];
        for (int j = 1; j <= m; ++j) f[0][j] = j;

        for (int i = 0; i < n; ++i) {
            f[i + 1][0] = i + 1;
            for (int j = 0; j < m; ++j)
                f[i + 1][j + 1] = s[i] == t[j] ? f[i][j] :
                        Math.min(Math.min(f[i][j + 1], f[i + 1][j]), f[i][j]) + 1;
        }
        return f[n][m];
    }
}
