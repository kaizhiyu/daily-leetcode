package org.dc.dp;

import java.util.Arrays;

/**
 * 122. 买卖股票的最佳时机 II 【medium】
 *
 * 给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。
 * 在每一天，你可以决定是否购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。你也可以先购买，然后在 同一天 出售。
 * 返回 你能获得的 最大 利润 。
 *
 * 示例 1：
 * 输入：prices = [7,1,5,3,6,4]
 * 输出：7
 * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5 - 1 = 4。
 * 随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6 - 3 = 3。
 * 最大总利润为 4 + 3 = 7 。
 *
 * 示例 2：
 * 输入：prices = [1,2,3,4,5]
 * 输出：4
 * 解释：在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5 - 1 = 4。
 * 最大总利润为 4 。
 *
 * 示例 3：
 * 输入：prices = [7,6,4,3,1]
 * 输出：0
 * 解释：在这种情况下, 交易无法获得正利润，所以不参与交易可以获得最大利润，最大利润为 0。
 *
 * 提示：
 * 1 <= prices.length <= 3 * 104
 * 0 <= prices[i] <= 104
 *
 */
public class Leetcode122 {
    /**
     * 关键词：天数，是否持有股票
     *
     * 子问题：到第i天结束时，持有/未持有股票的最大利润
     * 下一个子问题：到第i-1天结束时，持有/未持有股票的最大利润
     *
     * 状态机：
     *             <- 卖出 --
     * 未持有                           持有
     *  ｜｜        -- 买入 ->          ｜｜
     * 什么也不做                     什么也不做
     *
     * 定义：dfs(i,0)表示到第i天结束时，未持有股票的最大利润
     * dfs(i,1)表示到第i天结束时，持有股票的最大利润
     *
     * 由于第i-1天的结束也就是第i天的开始，dfs(i-1,)也表示到第i天开始时的最大利润    ！！！这一点特别重要 ！！！
     *
     * dfs(i, 0) = max(dfs(i-1, 0), dfs(i-1, 1) + prices[i])
     * dfs(i, 1) = max(dfs(i-1, 1), dfs(i-1, 0) - prices[i])
     *
     * 递归边界：
     * dfs(-1, 0) = 0,  第0天开始未持有股票，利润未0
     * dfs(-1, 1) = -∞, 第0天开始不可能持有股票
     *
     * 递归入口：
     * max(dfs(n-1, 0), dfs(n-1, 1)) = dfs(n-1, 0) <- 递归入口
     *
     */
    private int[] prices;
    private int[][] memo;

    public int maxProfit(int[] prices) {
        this.prices = prices;
        int n = prices.length;
        memo = new int[n][2];

        for (int i = 0; i < n; i++)
            Arrays.fill(memo[i], -1); // -1 表示还没有计算过

        return dfs(n - 1, 0);
    }

    private int dfs(int i, int hold) {
        if (i < 0) return hold == 1 ? Integer.MIN_VALUE : 0;
        if (memo[i][hold] != -1) return memo[i][hold]; // 之前计算过
        if (hold == 1) return memo[i][hold] = Math.max(dfs(i - 1, 1), dfs(i - 1, 0) - prices[i]);
        return memo[i][hold] = Math.max(dfs(i - 1, 0), dfs(i - 1, 1) + prices[i]);
    }

    /**
     * 1:1翻译成递推
     * f[i][0] = max(f[i-1][0], f[i-1][1] + prices[i])
     * f[i][1] = max(f[i-1][1], f[i-1][0] - prices[i])
     */
    public int maxProfit2(int[] prices) {
        int n = prices.length;
        int[][] f = new int[n + 1][2];
        f[0][1] = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            f[i + 1][0] = Math.max(f[i][0], f[i][1] + prices[i]);
            f[i + 1][1] = Math.max(f[i][1], f[i][0] - prices[i]);
        }
        return f[n][0];
    }


    public int maxProfit3(int[] prices) {
        int f0 = 0, f1 = Integer.MIN_VALUE;
        for (int p : prices) {
            int newF0 = Math.max(f0, f1 + p);
            f1 = Math.max(f1, f0 - p);
            f0 = newF0;
        }
        return f0;
    }
}
