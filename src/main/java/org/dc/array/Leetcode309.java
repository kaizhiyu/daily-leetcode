package org.dc.array;

import java.util.Arrays;

/**
 * 给定一个整数数组prices，其中第  prices[i] 表示第 i 天的股票价格 。​
 *
 * 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
 *
 * 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 *
 * 示例 1:
 * 输入: prices = [1,2,3,0,2]
 * 输出: 3
 * 解释: 对应的交易状态为: [买入, 卖出, 冷冻期, 买入, 卖出]
 *
 * 示例 2:
 * 输入: prices = [1]
 * 输出: 0
 *
 * 提示：
 * 1 <= prices.length <= 5000
 * 0 <= prices[i] <= 1000
 *
 */
public class Leetcode309 {
    /**
     * 在 122. 买卖股票的最佳时机 II 的基础上，只需修改一处：在计算持有股票的状态时，把 dfs(i−1,0) 改成 dfs(i−2,0)。道理和 198. 打家劫舍 是一样的，
     * 因为第 i 天买股票的话第 i−1 天不能卖，只能从第 i−2 天没有股票的状态转移过来。注意 dfs(i−2,0) 并不意味着第 i−2 天一定卖了股票，而是在没有股票下的最优状态。
     *
     * 请注意，这会导致边界条件多了一个 dfs(−2,0)=0，后面空间优化中的 pre0 指的就是这个状态。
     * 请注意，dfs(−2,1) 是访问不到的，所以下面翻译成递推时，无需初始化这个状态（不需要写 f[0][1]=−∞）。
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
        if (hold == 1) return memo[i][hold] = Math.max(dfs(i - 1, 1), dfs(i - 2, 0) - prices[i]);
        return memo[i][hold] = Math.max(dfs(i - 1, 0), dfs(i - 1, 1) + prices[i]);
    }

    public int maxProfit2(int[] prices) {
        int n = prices.length;
        int[][] f = new int[n + 2][2];
        f[1][1] = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            f[i + 2][0] = Math.max(f[i + 1][0], f[i + 1][1] + prices[i]);
            f[i + 2][1] = Math.max(f[i + 1][1], f[i][0] - prices[i]);
        }
        return f[n + 1][0];
    }
}
