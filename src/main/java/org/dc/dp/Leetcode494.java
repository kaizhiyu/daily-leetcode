package org.dc.dp;

import java.util.Arrays;

/**
 * 给你一个非负整数数组 nums 和一个整数 target 。
 * 向数组中的每个整数前添加 '+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式 ：
 * 例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，然后串联起来得到表达式 "+2-1" 。
 * 返回可以通过上述方法构造的、运算结果等于 target 的不同 表达式 的数目。
 *
 * 示例 1：
 * 输入：nums = [1,1,1,1,1], target = 3
 * 输出：5
 * 解释：一共有 5 种方法让最终目标和为 3 。
 * -1 + 1 + 1 + 1 + 1 = 3
 * +1 - 1 + 1 + 1 + 1 = 3
 * +1 + 1 - 1 + 1 + 1 = 3
 * +1 + 1 + 1 - 1 + 1 = 3
 * +1 + 1 + 1 + 1 - 1 = 3
 *
 * 示例 2：
 * 输入：nums = [1], target = 1
 * 输出：1
 *
 * 提示：
 * 1 <= nums.length <= 20
 * 0 <= nums[i] <= 1000
 * 0 <= sum(nums[i]) <= 1000
 * -1000 <= target <= 1000
 */
public class Leetcode494 {
    /**
     * 设 nums 的元素和为 s，其中添加正号的元素之和为 p，其余添加负号的元素（绝对值）之和为 q，那么有
     * p+q=s
     * 又因为表达式运算结果等于 target，所以有
     * p−q=target
     *
     * 综上所述，取 s−∣target∣/2 作为 0-1 背包的背包容量是最优的。（注意 target 可以是负数）
     *
     * 问：dfs(i - 1, c) + dfs(i - 1, c - nums[i]) 中的加法是什么意思？
     * 答：这叫加法原理，如果事件 A 和事件 B 是互斥的（即不能同时发生，不选 nums[i] 的同时，又选了 nums[i]），那么发生事件 A 或事件 B 的总数等于事件 A 的数量加上事件 B 的数量。
     *
     * 复杂度分析
     * 时间复杂度：O(nm)，其中 n 为 nums 的长度，m 为 nums 的元素和减去 target 的绝对值。由于每个状态只会计算一次，动态规划的时间复杂度 = 状态个数 × 单个状态的计算时间。本题状态个数等于 O(nm)，单个状态的计算时间为 O(1)，所以动态规划的时间复杂度为 O(nm)。
     * 空间复杂度：O(nm)。保存多少状态，就需要多少空间。
     */
    private int[] nums;
    private int[][] memo;

    public int findTargetSumWays(int[] nums, int target) {
        int s = 0;
        for (int x : nums) {
            s += x;
        }

        // p - (s - p) = t
        s -= Math.abs(target);
        if (s < 0 || s % 2 == 1) {
            return 0;
        }
        int m = s / 2; // 背包容量

        this.nums = nums;
        int n = nums.length;
        memo = new int[n][m + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示没有计算过
        }
        return dfs(n - 1, m);
    }

    private int dfs(int i, int c) {
        if (i < 0) {
            return c == 0 ? 1 : 0;
        }

        if (memo[i][c] != -1) { // 之前计算过
            return memo[i][c];
        }

        if (c < nums[i]) {
            return memo[i][c] = dfs(i - 1, c); // 只能不选
        }

        return memo[i][c] = dfs(i - 1, c) + dfs(i - 1, c - nums[i]); // 不选 + 选
    }
}
