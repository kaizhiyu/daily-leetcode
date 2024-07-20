package org.dc.sum;

/**
 * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。子数组是数组中的一个连续部分。
 *
 * 示例 1：
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6
 *
 * 示例 2：
 * 输入：nums = [1]
 * 输出：1
 *
 * 示例 3：
 * 输入：nums = [5,4,-1,7,8]
 * 输出：23
 *
 * 提示：
 * 1 <= nums.length <= 105
 * -104 <= nums[i] <= 104
 *
 * 进阶：如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的 分治法 求解。
 *
 */
public class Leetcode53 {
    /**
     *
     * 思路
     * 这道题用动态规划的思路并不难解决，比较难的是后文提出的用分治法求解，但由于其不是最优解法，所以先不列出来
     * 动态规划的是首先对数组进行遍历，当前最大连续子序列和为 sum，结果为 ans
     * 如果 sum > 0，则说明 sum 对结果有增益效果，则 sum 保留并加上当前遍历数字
     * 如果 sum <= 0，则说明 sum 对结果无增益效果，需要舍弃，则 sum 直接更新为当前遍历数字
     * 每次比较 sum 和 ans的大小，将最大值置为ans，遍历结束返回结果
     * 时间复杂度：O(n)
     *
     */
    public int maxSubArray(int[] nums) {
        int ans = nums[0];
        int sum = 0;

        for (int num: nums) {
            if (sum > 0) {
                sum += num;
            } else {
                sum = num;
            }
            ans = Math.max(ans, sum);
        }

        return ans;
    }

    /**
     * 用 f(i) 代表以第 i 个数结尾的「连续子数组的最大和」
     * 我们可以考虑 nums[i] 单独成为一段还是加入 f(i−1) 对应的那一段，这取决于 nums[i] 和 f(i−1)+nums[i] 的大小，我们希望获得一个比较大的，于是可以写出这样的动态规划转移方程：
     * f(i) = max{f(i−1) + nums[i], nums[i]}
     *
     * 不难给出一个时间复杂度 O(n)、空间复杂度 O(n) 的实现，即用一个 f 数组来保存 f(i) 的值，用一个循环求出所有 f(i)。
     * 考虑到 f(i) 只和 f(i−1) 相关，于是我们可以只用一个变量 pre 来维护对于当前 f(i) 的 f(i−1) 的值是多少，从而让空间复杂度降低到 O(1)，这有点类似「滚动数组」的思想。
     *
     * 复杂度
     * 时间复杂度：O(n)，其中 n 为 nums 数组的长度。我们只需要遍历一遍数组即可求得答案。
     * 空间复杂度：O(1)。我们只需要常数空间存放若干变量。
     */
    public int maxSubArray2(int[] nums) {
        int pre = 0, maxAns = nums[0];
        for (int x : nums) {
            pre = Math.max(pre + x, x);
            maxAns = Math.max(maxAns, pre);
        }
        return maxAns;
    }
}
