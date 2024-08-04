package org.dc.backtrack;

/**
 * 通配符匹配 HARD
 *
 * 给你一个输入字符串 (s) 和一个字符模式 (p) ，请你实现一个支持 '?' 和 '*' 匹配规则的通配符匹配：
 * '?' 可以匹配任何单个字符。
 * '*' 可以匹配任意字符序列（包括空字符序列）。
 * 判定匹配成功的充要条件是：字符模式必须能够 完全匹配 输入字符串（而不是部分匹配）。
 *
 * 示例 1：
 * 输入：s = "aa", p = "a"
 * 输出：false
 * 解释："a" 无法匹配 "aa" 整个字符串。
 *
 * 示例 2：
 * 输入：s = "aa", p = "*"
 * 输出：true
 * 解释：'*' 可以匹配任意字符串。
 *
 * 示例 3：
 * 输入：s = "cb", p = "?a"
 * 输出：false
 * 解释：'?' 可以匹配 'c', 但第二个 'a' 无法匹配 'b'。
 *
 * 提示：
 * 0 <= s.length, p.length <= 2000
 * s 仅由小写英文字母组成
 * p 仅由小写英文字母、'?' 或 '*' 组成
 *
 */
public class Leetcode44 {
    /**
     * ?必须表示为一个字符，不能是''。*可以表示为任意个任意字符。遇到*时，可以把它当做''，可以当做一个任意字符，也可以变成多个字符适应别人。
     * 从后往前考察，s、p串的匹配问题拆分成：末尾字符的匹配 + 剩余子串的匹配，后者只是规模更小的子问题。
     * 如下图，我们罗列了一些末尾字符的匹配情况：
     * 普通字符的相同。
     * ？匹配掉任意一个字符。
     * *匹配掉""，*被消耗了，s串保持不变。
     * p 末尾的*拉出一个去干掉 s 末尾的 b，p 串保持不变。
     * dp[i][j]：s 串的前 i 个字符是否和 p 串的前 j 个字符匹配
     *
     * 写出状态转移方程
     * 末尾字符相同，则考察剩余子串
     * p 的末尾是问号，它消耗掉 s 末尾的一个字符，考察剩余子串
     * p 的末尾是星号，分为两种情况，星号匹配多个字符、星号匹配空字符串。
     *
     * base cases
     * s、p都是空字符串，必然匹配，dp[0][0]=true
     * s 是空字符串，p要想match，只有靠p[j-1]即是*，它变成''，所以dp[0][j] = dp[0][j - 1]
     * s 为非空字符串，p为空字符串，肯定不匹配，dp[i][j] = false,i>=1;
     *
     */
    Boolean[][] memo;
    public boolean isMatch(String s, String p) {
        memo = new Boolean[s.length()][p.length()];
        return dfs(s, s.length() - 1, p, p.length() - 1);
    }

    private boolean dfs(String s, int i, String p, int j) {
        if (i < 0 && j < 0) return true;
        if (i < 0 && p.charAt(j) != '*') return false;
        if (i < 0) return dfs(s, i, p, j - 1);
        if (j < 0) return false;

        if (memo[i][j] != null) {
            return memo[i][j];
        }

        if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?') {
            memo[i][j] = dfs(s, i - 1, p, j - 1);
        } else if (p.charAt(j) == '*') {
            // * 可以匹配多个 ｜｜ 去掉
            memo[i][j] = dfs(s, i - 1, p, j) || dfs(s, i, p, j - 1);
        } else {
            memo[i][j] = false;
        }
        return memo[i][j];
    }

    /**
     * 1:1翻译成递推
     * 参考：
     * https://leetcode.cn/problems/wildcard-matching/solutions/2817432/ling-shen-ti-dan-ji-yi-hua-dao-di-tui-da-qdrw/
     */
}
