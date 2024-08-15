package org.dc.dp;

import java.util.Arrays;

/**
 * 123. 买卖股票的最佳时机 III
 * 188 k=2的情况
 *
 * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 *
 * 示例 1:
 * 输入：prices = [3,3,5,0,0,3,1,4]
 * 输出：6
 * 解释：在第 4 天（股票价格 = 0）的时候买入，在第 6 天（股票价格 = 3）的时候卖出，这笔交易所能获得利润 = 3-0 = 3 。
 *      随后，在第 7 天（股票价格 = 1）的时候买入，在第 8 天 （股票价格 = 4）的时候卖出，这笔交易所能获得利润 = 4-1 = 3 。
 *
 * 示例 2：
 * 输入：prices = [1,2,3,4,5]
 * 输出：4
 * 解释：在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
 *      注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。
 *      因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
 *
 * 示例 3：
 * 输入：prices = [7,6,4,3,1]
 * 输出：0
 * 解释：在这个情况下, 没有交易完成, 所以最大利润为 0。
 *
 * 示例 4：
 * 输入：prices = [1]
 * 输出：0
 *
 * 提示：
 * 1 <= prices.length <= 105
 * 0 <= prices[i] <= 105
 *
 */
public class Leetcode123 {
    // 此题使用记忆化搜索会超时
    private int[] prices;
    private int[][][] memo;
    public int maxProfit2(int[] prices) {
        this.prices = prices;
        int n = prices.length;

        memo = new int[n][3][2];
        for (int[][] mat : memo) {
            for (int[] row : mat) {
                Arrays.fill(row, -1); // -1 表示还没有计算过
            }
        }

        return dfs(n-1, 2, 0);
    }

    private int dfs(int i, int j, int hold) {
        if (j < 0) {
            return Integer.MIN_VALUE / 2; // 除 2 防止溢出
        }
        if (i < 0) {
            return hold == 1 ? Integer.MIN_VALUE / 2 : 0;
        }
        if (memo[i][j][hold] != -1) { // 之前计算过
            return memo[i][j][hold];
        }
        if (hold == 1) {
            return memo[i][j][hold] = Math.max(dfs(i - 1, j, 1), dfs(i - 1, j - 1, 0) - prices[i]);
        }
        return memo[i][j][hold] = Math.max(dfs(i - 1, j, 0), dfs(i - 1, j, 1) + prices[i]);
    }

    /**
     * 1:1递推
     *
     * f[i][j][0] = max(f[i-1][j][0], f[i-1][j][1] + prices[i])
     * f[i][j][1] = max(f[i-1][j][1], f[i-1][j-1][0] - prices[i])
     *
     * 但这样没有状态表示f[-1][.][.] 和 f[.][-1][.]
     * 那就在 f 和每个 f[i] 的最前面插入一个状态
     *
     * 最终递推式
     * f[.][0][.] = -∞
     * f[0][j][0] = 0
     * f[0][j][1] = -∞
     *
     * f[i+1][j][0] = max(f[i][j][0], f[i][j][1] + prices[i])
     * f[i+1][j][1] = max(f[i][j][1], f[i][j-1][0] - prices[i])
     *
     * 答案为 f[n][k+1][0]
     *
     * 复杂度分析
     * 时间复杂度：O(nk)，其中 n 为 prices 的长度。
     * 空间复杂度：O(nk)。
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[][][] f = new int[n + 1][4][2];
        for (int[][] mat : f) {
            for (int[] row : mat) {
                Arrays.fill(row, Integer.MIN_VALUE / 2); // 防止溢出
            }
        }

        // 第1天开始，不论任何次数，利润都为0
        for (int j = 1; j <= 3; j++) {
            f[0][j][0] = 0;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= 3; j++) {
                f[i + 1][j][0] = Math.max(f[i][j][0], f[i][j][1] + prices[i]);
                f[i + 1][j][1] = Math.max(f[i][j][1], f[i][j - 1][0] - prices[i]);
            }
        }
        return f[n][3][0];
    }
}
