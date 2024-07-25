package org.dc.dp;

import java.util.Arrays;

/**
 *
 * 给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。如果不存在 公共子序列 ，返回 0 。
 * 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
 * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
 * 两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。
 *
 * 示例 1：
 * 输入：text1 = "abcde", text2 = "ace"
 * 输出：3
 * 解释：最长公共子序列是 "ace" ，它的长度为 3 。
 *
 * 示例 2：
 * 输入：text1 = "abc", text2 = "abc"
 * 输出：3
 * 解释：最长公共子序列是 "abc" ，它的长度为 3 。
 *
 * 示例 3：
 * 输入：text1 = "abc", text2 = "def"
 * 输出：0
 * 解释：两个字符串没有公共子序列，返回 0 。
 *
 *
 * 提示：
 * 1 <= text1.length, text2.length <= 1000
 * text1 和 text2 仅由小写英文字符组成。
 *
 */
public class Leetcode1143 {
    /**
     * 方法一：
     * 启发思路：子序列本质是选或者不选，考虑最后一对字母，分别叫x和y，有四种情况，x y 不选 ， 不选x 选 y ，选 x 不选 y，x y 选
     *
     * 回溯三问：
     * 当前操作？考虑 s[i] 和 t[j] 选或者不选
     * 子问题？s的前i个字母和t的前j个字母的LCS长度
     * 下一个子问题？
     * s的前i-1个字母和t的前j-1个字母的LCS长度
     * s的前i-1个字母和t的前j个字母的LCS长度
     * s的前i个字母和t的前j-1个字母的LCS长度
     *
     * 递归搜索 + 保存计算结果 = 记忆化搜索
     * 复杂度分析
     * 时间复杂度：O(nm)
     * 空间复杂度：O(nm)
     */
    private int[][] cache;
    public int longestCommonSubsequence1(String text1, String text2) {
        char[] s1 = text1.toCharArray();
        char[] s2 = text2.toCharArray();
        cache = new int[s1.length][s2.length];

        for (int[] row : cache) {
            Arrays.fill(row, -1);
        }

        return dfs(s1, s1.length - 1, s2, s2.length - 1);
    }

    private int dfs(char[] s1, int i, char[] s2, int j) {
        if (i < 0 || j < 0) {
            return 0;
        }

        if (cache[i][j] != -1) {
            return cache[i][j];
        }

        if (s1[i] == s2[j]) {
            return dfs(s1, i - 1, s2, j - 1) + 1;
        }

        cache[i][j] = Math.max(dfs(s1, i - 1, s2, j), dfs(s1, i, s2, j - 1));
        return cache[i][j];
    }

    /**
     * 方法二：1:1 翻译成递推
     */
    public int longestCommonSubsequence(String text1, String text2) {
        char[] s = text1.toCharArray(), t = text2.toCharArray();
        int n = s.length, m = t.length;
        int[][] f = new int[n + 1][m + 1];

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                f[i + 1][j + 1] = s[i] == t[j]
                        ? f[i][j] + 1
                        : Math.max(f[i][j + 1], f[i + 1][j]);
            }
        }

        return f[n][m];
    }

}
