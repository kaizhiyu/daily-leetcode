package org.dc.array;

import java.util.Arrays;

/**
 * 674. 最长连续递增序列
 * 给定一个未经排序的整数数组，找到最长且 连续递增的子序列，并返回该序列的长度。
 * 连续递增的子序列 可以由两个下标 l 和 r（l < r）确定，如果对于每个 l <= i < r，都有 nums[i] < nums[i + 1] ，那么子序列 [nums[l], nums[l + 1], ..., nums[r - 1], nums[r]] 就是连续递增子序列。
 *
 * 示例 1：
 * 输入：nums = [1,3,5,4,7]
 * 输出：3
 * 解释：最长连续递增序列是 [1,3,5], 长度为3。
 * 尽管 [1,3,5,7] 也是升序的子序列, 但它不是连续的，因为 5 和 7 在原数组里被 4 隔开。
 *
 * 示例 2：
 * 输入：nums = [2,2,2,2,2]
 * 输出：1
 * 解释：最长连续递增序列是 [2], 长度为1。
 *
 * 提示：
 * 1 <= nums.length <= 104
 * -109 <= nums[i] <= 109
 *
 */
public class Leetcode674 {
    /**
     * 解题步骤：
     * 初始化数组dp,每个索引所对应的值均为1。
     * 从索引 0 的位置开始遍历，若后一个元素 大于 前一个元素，则 dp[i] = dp[i - 1] + 1; 递减则不做任何操作,相当于dp[i] = 1。
     * 循环结束，dp = [1, 2, 3, 1, 2, 3, 4, 1]，取最大值4。
     *
     * 复杂度分析：时间复杂度：O(n)；空间复杂度：O(n)。
     */
    public int findLengthOfLCIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int ans = 1;
        for (int i = 1; i < n; i++) {
            if (nums[i] > nums[i - 1]) {
                dp[i] = dp[i - 1] + 1;
            }

            ans = Math.max(ans, dp[i]);
        }

        return ans;
    }
}
