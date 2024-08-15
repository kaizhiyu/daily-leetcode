package org.dc.dp;

import java.util.Arrays;

/**
 * 188. 买卖股票的最佳时机 IV 【HARD】
 *
 * 给你一个整数数组 prices 和一个整数 k ，其中 prices[i] 是某支给定的股票在第 i 天的价格。
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。也就是说，你最多可以买 k 次，卖 k 次。
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 *
 * 示例 1：
 * 输入：k = 2, prices = [2,4,1]
 * 输出：2
 * 解释：在第 1 天 (股票价格 = 2) 的时候买入，在第 2 天 (股票价格 = 4) 的时候卖出，这笔交易所能获得利润 = 4-2 = 2 。
 *
 * 示例 2：
 * 输入：k = 2, prices = [3,2,6,5,0,3]
 * 输出：7
 * 解释：在第 2 天 (股票价格 = 2) 的时候买入，在第 3 天 (股票价格 = 6) 的时候卖出, 这笔交易所能获得利润 = 6-2 = 4 。
 *      随后，在第 5 天 (股票价格 = 0) 的时候买入，在第 6 天 (股票价格 = 3) 的时候卖出, 这笔交易所能获得利润 = 3-0 = 3 。
 *
 * 提示：
 * 1 <= k <= 100
 * 1 <= prices.length <= 1000
 * 0 <= prices[i] <= 1000
 *
 */
public class Leetcode188 {
    /**
     * 一、递归搜索 + 保存计算结果 = 记忆化搜索
     * 在 122. 买卖股票的最佳时机 II 的基础上，添加一个参数 j，表示当前可以至多交易 j 次。 (j表示交易是一个完整动作，买入 + 卖出)
     * ⚠注意：由于最后未持有股票，手上的股票一定会卖掉，所以代码中的 j-1 可以是在买股票的时候，也可以是在卖股票的时候，这两种写法都是可以的。
     *
     * 定义 dfs(i, j, 0)表示到第i天结束时完成至多j笔交易，未持有股票的最大利润
     * dfs(i, j, 1)表示到第i天结束时完成至多j笔交易，持有股票的最大利润
     *             <- 卖出 -- dfs(i, j, 0) = dfs(i-1, j, 1) + prices[i]
     * 未持有                           持有
     *             -- 买入 -> dfs(i, j, 1) = dfs(i-1, j-1, 0) - prices[i]
     *   ｜｜ dfs(i, j, 0) = dfs(i-1, j, 0)
     *                                ｜｜ dfs(i, j, 1) = dfs(i-1, j, 1)
     * 什么也不做                     什么也不做
     *
     * dfs(i, j, 0) = max( dfs(i-1, j, 0) , dfs(i-1, j, 1) + prices[i] )
     * dfs(i, j, 1) = max( dfs(i-1, j, 1) , dfs(i-1, j-1, 0) - prices[i] )
     *
     * 递归边界：
     * dfs(,-1,) = -∞ ，任何情况下，j都不能为负
     * dfs(-1,j,0) = 0, 第0天开始未持有股票，利润为0
     * dfs(-1,j,1) = -∞, 第0天开始不可能持有股票
     *
     * 递归入口：
     * max(dfs(n-1, k, 0), dfs(n-1, k, 1)) = dfs(n-1, k, 0)
     *
     *
     * 复杂度分析
     * 时间复杂度：O(nk)，其中 n 为 prices 的长度。
     * 空间复杂度：O(nk)。
     */
    private int[] prices;
    private int[][][] memo;
    public int maxProfit(int k, int[] prices) {
        this.prices = prices;
        int n = prices.length;

        memo = new int[n][k + 1][2];
        for (int[][] mat : memo) {
            for (int[] row : mat) {
                Arrays.fill(row, -1); // -1 表示还没有计算过
            }
        }

        return dfs(n-1, k, 0);
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
    public int maxProfit2(int k, int[] prices) {
        int n = prices.length;
        int[][][] f = new int[n + 1][k + 2][2];
        for (int[][] mat : f) {
            for (int[] row : mat) {
                Arrays.fill(row, Integer.MIN_VALUE / 2); // 防止溢出
            }
        }
        for (int j = 1; j <= k + 1; j++) {
            f[0][j][0] = 0;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= k + 1; j++) {
                f[i + 1][j][0] = Math.max(f[i][j][0], f[i][j][1] + prices[i]);
                f[i + 1][j][1] = Math.max(f[i][j][1], f[i][j - 1][0] - prices[i]);
            }
        }
        return f[n][k + 1][0];
    }


    // 引申 参考： https://www.bilibili.com/video/BV1ho4y1W7QK/?spm_id_from=333.788&vd_source=90ccec7f63fd55273154473f8d9d8ae0
    // 如果改成「恰好」完成 k 笔交易要怎么做？
    // 递归到 i<0 时，只有 j=0 才是合法的，j>0 是不合法的。
    // f[0][1][0] = 0 , 其余为-∞，注意前面加了个状态，f[0][1]才是恰好完成0次的状态


    // 如果改成「至少」完成 k 笔交易要怎么做？
    // 递归到「至少 0 次」时，它等价于「交易次数没有限制」，那么这个状态的计算方式和 122. 买卖股票的最佳时机 II 是一样的。

    // f[i][-1][.] 等价于 f[i][0][.]
    // 所以每个f[i]的前面不需要添加状态
    // 至少0次等价于可以无限次交易
    // 所以f[i][0][.]就是无限次交易下的最大利润，转移方程也一样
    // f[0][0][0] = 0 , 其余 = -∞
    // f[i+1][0][0] = max (f[i][0][0], f[i][0][1] + prices[i])
    // f[i+1][0][0] = max (f[i][0][0], f[i][0 ][0] - prices[i])
}
