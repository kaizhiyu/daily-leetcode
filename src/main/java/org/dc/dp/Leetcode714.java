package org.dc.dp;

import java.util.Arrays;

/**
 * 714. 买卖股票的最佳时机含手续费 【medium】
 *
 * 给定一个整数数组 prices，其中 prices[i]表示第 i 天的股票价格 ；整数 fee 代表了交易股票的手续费用。
 * 你可以无限次地完成交易，但是你每笔交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。
 * 返回获得利润的最大值。
 * 注意：这里的一笔交易指买入持有并卖出股票的整个过程，每笔交易你只需要为支付一次手续费。
 *
 * 示例 1：
 * 输入：prices = [1, 3, 2, 8, 4, 9], fee = 2
 * 输出：8
 * 解释：能够达到的最大利润:
 * 在此处买入 prices[0] = 1
 * 在此处卖出 prices[3] = 8
 * 在此处买入 prices[4] = 4
 * 在此处卖出 prices[5] = 9
 * 总利润: ((8 - 1) - 2) + ((9 - 4) - 2) = 8
 *
 * 示例 2：
 * 输入：prices = [1,3,7,5,10,3], fee = 3
 * 输出：6
 *
 * 提示：
 * 1 <= prices.length <= 5 * 104
 * 1 <= prices[i] < 5 * 104
 * 0 <= fee < 5 * 104
 */
public class Leetcode714 {
    /**
     * 一、递归搜索 + 保存计算结果 = 记忆化搜索
     * 本题的思路和 122. 买卖股票的最佳时机 II 一样，只需要在买入（或者卖出）的时候，额外减去手续费 fee 作为开销。
     * 为什么在买入/卖出减去都可以呢？因为最终手上一定是没有股票的（不然买了就是白白浪费钱），那么有买入就必定有卖出，在买入/卖出其一减去手续费就行。
     *
     * 复杂度分析
     * 时间复杂度：O(n)，其中 n 为 prices 的长度。
     * 空间复杂度：O(n)。
     */
    private int[] prices;
    private int[][] memo;
    private int fee;
    public int maxProfit(int[] prices, int fee) {
        this.prices = prices;
        this.fee = fee;

        int n = prices.length;
        memo = new int[n][2];
        for (int i = 0; i < n; i++) Arrays.fill(memo[i], -1);
        return dfs(n - 1, 0);
    }

    private int dfs(int i, int hold) {
        if (i < 0) return hold == 1 ? Integer.MIN_VALUE / 2 : 0;
        if (memo[i][hold] != -1) return memo[i][hold];

        if (hold == 1) return memo[i][hold] = Math.max(dfs(i - 1, 1), dfs(i - 1, 0) - prices[i]);
        return memo[i][hold] = Math.max(dfs(i - 1, 0), dfs(i - 1, 1) + prices[i] - fee);
    }

    /**
     * 1:1 翻译成递推
     *
     * 复杂度分析
     * 时间复杂度：O(n)，其中 n 为 prices 的长度。
     * 空间复杂度：O(n)。
     */
    public int maxProfit2(int[] prices, int fee) {
        int n = prices.length;
        int[][] f = new int[n + 1][2];
        f[0][1] = Integer.MIN_VALUE / 2;
        for (int i = 0; i < n; i++) {
            f[i + 1][0] = Math.max(f[i][0], f[i][1] + prices[i] - fee);
            f[i + 1][1] = Math.max(f[i][1], f[i][0] - prices[i]);
        }
        return f[n][0];
    }

    /**
     * 空间优化
     *
     * 复杂度分析
     * 时间复杂度：O(n)，其中 n 为 prices 的长度。
     * 空间复杂度：O(1)。仅用到若干额外变量。
     */
    public int maxProfit3(int[] prices, int fee) {
        int f0 = 0, f1 = Integer.MIN_VALUE / 2;
        for (int p : prices) {
            int newF0 = Math.max(f0, f1 + p - fee);
            f1 = Math.max(f1, f0 - p);
            f0 = newF0;
        }
        return f0;
    }
}
