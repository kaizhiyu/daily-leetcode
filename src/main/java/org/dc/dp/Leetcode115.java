package org.dc.dp;

import java.util.Arrays;

/**
 * 给你两个字符串 s 和 t ，统计并返回在 s 的 子序列 中 t 出现的个数，结果需要对 109 + 7 取模。
 *
 * 示例 1：
 * 输入：s = "rabbbit", t = "rabbit"
 * 输出：3
 * 解释：
 * 如下所示, 有 3 种可以从 s 中得到 "rabbit" 的方案。
 * rabbbit
 * rabbbit
 * rabbbit
 *
 * 示例 2：
 * 输入：s = "babgbag", t = "bag"
 * 输出：5
 * 解释：
 * 如下所示, 有 5 种可以从 s 中得到 "bag" 的方案。
 * babgbag
 * babgbag
 * babgbag
 * babgbag
 * babgbag
 *
 *
 * 提示：
 * 1 <= s.length, t.length <= 1000
 * s 和 t 由英文字母组成
 *
 */
public class Leetcode115 {
    /**
     * 读懂题目后，用自己的话“翻译”一下题目，有时候会更容易有思路。
     * 题目：求 s 的子序列中 t 出现的个数，blabla...
     * 翻译：在 s 串身上 “挑选” 字符，去匹配 t 串的字符，求挑选的方式数
     *
     * 递归思路
     * 抓住 “选”，s 要照着 t 来挑选，逐字符考察选或不选，分别来到什么状态？
     * 举例，s 为babgbag，t 为bag，末尾字符相同，于是 s 有两种选择：
     * 用s[s.length-1]去匹配掉t[t.length-1]，问题规模缩小：继续考察babgba和ba
     * 不这么做，但t[t.length-1]仍需被匹配，于是在babgba中继续挑，考察babgba和bag
     *
     * s串在自己身上“挑选”字符去匹配t串
     *
     * 是否用它去匹配，是两种不同的挑选方式，各自做下去所产生的方式数，相加，是大问题的解。
     * 现在我们拆解出规模小一点的子问题。完善一下，定义出递归函数：
     * 返回：从开头到s[i]的子串中，出现『从开头到t[j]的子串』的次数。
     * 即，从 前者 选字符，去匹配 后者，的方案数。
     * 看了s[i]==t[j]的情况，那s[i]!=t[j]的情况呢？s[i]不匹配t[j]，唯有拿s[i]之前的子串去匹配
     *
     * 现在两种情况下的递归公式都好写了。递归树底部的 base case 呢？
     *
     * 随着递归压栈，子问题规模（子串长度）在变小：
     * 小到 t 变成空串，此时 s 为了匹配它，方式只有1种：什么字符也不用挑（或 s 也是空串，什么都不做就匹配了，方式数也是1）
     * 小到 s 变成空串，但 t 不是，s 怎么也匹配不了 t，方式数为 0
     * 递归函数的参数可以传子串或索引，但用索引描述子问题，不用每次都切割字符串，也更容易迁移到 dp 解法。
     *
     * 定义dfs(i,j),代表了以下标i 和下标j结尾的的字符串s和t，在 s 的 子序列 中 t 出现的个数。
     *
     * 如果s[i]=t[j]，我们可以给他匹配也可以不匹配(因为s[0...i−1]可以还存在结果])，也就是：dfs(i,j)=dfs(i−1,j−1)+dfs(i−1,j)
     * 如果s[i]!=t[j], 那么直接跳过s的当前位置，也就是： dfs(i,j)=dfs(i−1,j)
     *
     * 未优化
     *
     *
     * 参考：
     * https://leetcode.cn/problems/distinct-subsequences/solutions/1/ji-yi-hua-sou-suo-di-tui-kong-jian-you-h-8lg2/
     * https://leetcode.cn/problems/distinct-subsequences/solutions/661537/shou-hua-tu-jie-xiang-jie-liang-chong-ji-4r2y/
     */
    public int numDistinct(String s, String t) {
        return helper(s, s.length() - 1, t, t.length() - 1);
    }

    private int helper(String s, int i, String t, int j) {
        if (j < 0) {
            return 1;
        }

        if (i < 0) {
            return 0;
        }

        if (s.charAt(i) == t.charAt(j)) {
            return helper(s, i - 1, t, j) + helper(s, i - 1, t, j - 1);
        } else {
            return helper(s, i - 1, t, j);
        }
    }

    /**
     * 加入记忆化
     */
    int[][] memos;
    public int numDistinct2(String s, String t) {
        memos = new int[s.length()][t.length()];

        for (int[] memo : memos) {
            Arrays.fill(memo, -1);
        }

        return helper2(s, s.length() - 1, t, t.length() - 1);
    }

    private int helper2(String s, int i, String t, int j) {
        if (j < 0) {
            return 1;
        }

        if (i < 0) {
            return 0;
        }

        if (memos[i][j] !=  -1) {
            return memos[i][j];
        }

        if (s.charAt(i) == t.charAt(j)) {
            memos[i][j] = helper2(s, i - 1, t, j) + helper(s, i - 1, t, j - 1);
        } else {
            memos[i][j] = helper2(s, i - 1, t, j);
        }
        return memos[i][j];
    }

    /**
     * 递归是自上而下调用，子问题自下而上被解决，最后解决了整个问题，而dp是从base case 出发，通过在dp数组记录中间结果，自下而上地顺序地解决子问题。
     * dp就好像一种不带重复计算的递归，想出dp往往也是像想出递归那样，都需要从子问题入手，正确定义子问题，递归想出结束条件，dp想出base case，递归想出递归公式，
     * dp想出递推公式。递归加入记忆化后，往往稍作修改，就是dp的解法。
     *
     * dp[i][j]：从开头到s[i-1]的子串中，出现『从开头到t[j-1]的子串』的 次数。即：前 i 个字符的 s 子串中，出现前 j 个字符的 t 子串的次数。
     * 状态转移方程：
     *
     * 当s[i-1] != t[j-1]时，有dp[i][j] = dp[i-1][j]
     * 当s[i-1] == t[j-1]时，有dp[i][j] = dp[i][j] = dp[i-1][j-1] + dp[i-1][j]
     *
     * base case
     * j==0时，dp[i][0] = 1
     * i==0时，dp[0][j] = 0
     */

    /**
     * 复盘总结
     * 把握住：挑选，既然挑选，就会出现对单个字符的不同选择，作出一种选择后，就变成了一个规模小一点的子问题，是我们要计算的状态，我们找到递推关系。
     * 递归自上而下将问题拆解，然后自下而上逐个解决，dp呢，从base case出发，顺序地一个个计算子问题，像填表格一样。
     * 二者的难点都在于定义递归函数（定义dp子问题），有时候不妨先想想递归，加入记忆化，它解决的子问题个数和dp是一样的，本质上记忆化递归的时间复杂度和dp是一样的。
     */
}
