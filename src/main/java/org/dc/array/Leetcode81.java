package org.dc.array;

/**
 * 已知存在一个按非降序排列的整数数组 nums ，数组中的值不必互不相同。
 * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转 ，
 * 使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。
 * 例如， [0,1,2,4,4,4,5,6,6,7] 在下标 5 处经旋转后可能变为 [4,5,6,6,7,0,1,2,4,4] 。
 *
 * 给你 旋转后 的数组 nums 和一个整数 target ，请你编写一个函数来判断给定的目标值是否存在于数组中。如果 nums 中存在这个目标值 target ，则返回 true ，否则返回 false 。
 * 你必须尽可能减少整个操作步骤。
 *
 * 示例 1：
 * 输入：nums = [2,5,6,0,0,1,2], target = 0
 * 输出：true
 *
 * 示例 2：
 * 输入：nums = [2,5,6,0,0,1,2], target = 3
 * 输出：false
 *
 * 提示：
 * 1 <= nums.length <= 5000
 * -104 <= nums[i] <= 104
 * 题目数据保证 nums 在预先未知的某个下标上进行了旋转
 * -104 <= target <= 104
 *
 * 进阶：
 * 此题与 搜索旋转排序数组 相似，但本题中的 nums  可能包含 重复 元素。这会影响到程序的时间复杂度吗？会有怎样的影响，为什么？
 *
 */
public class Leetcode81 {
    /*
            check解法：参考灵茶山艾府33题红蓝染色法
            为什么不能用题33一样的代码呢？（参考宫水三叶）
                因为「二分」的本质是二段性，并非单调性。只要一段满足某个性质，另外一段不满足某个性质，就可以用「二分」。
                本题元素并不唯一。这意味着我们无法直接根据与 nums[nums.length-1] 的大小关系，将数组划分为两段，即无法通过「二分」来找到旋转点。
                即 nums[nums.length-1] 并不是唯一了，那么把他在区间内变成唯一的就行了
                也就是说 需要在初始时就收缩空间，把L与R收缩到仅有一个的nums[nums.length-1]的区间。


     */
    public boolean search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        if (nums[r] == target) {
            return true;
        }

        while (l <= r && nums[l] == nums[nums.length - 1]) l++;
        while (l <= r && nums[r] == nums[nums.length - 1]) r--;

        while (l <= r) {
            // 循环不变量
            int mid = l + (r - l) / 2;
            if (isBlue(nums, mid, target)) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }

        return r != nums.length - 1 && nums[l] == target;
    }

    /**
     * 设 x=nums[mid] 是我们现在二分取到的数。
     * 现在需要判断 x 和 target 的 「位置」 关系，谁在左边，谁在右边？
     *
     * 核心思路
     * 如果 x 和 target 在不同的递增段：
     * 如果 target 在第一段（左），x 在第二段（右），说明 x 在 target 右边；
     * 如果 target 在第二段（右），x 在第一段（左），说明 x 在 target 左边。
     * 如果 x 和 target 在相同的递增段：
     * 比较 x 和 target 的大小即可。
     *
     * 分类讨论
     * 下面只讨论 x 在 target 右边，或者等于 target 的情况。其余情况 x 一定在 target 左边。
     * 如果 x>nums[n−1]，说明 x 在第一段中，那么 target 也必须在第一段中（否则 x 一定在 target 左边）且 x 必须大于等于 target。
     * 写成代码就是 target > nums[n - 1] && x >= target。
     * 如果 x≤nums[n−1]，说明 x 在第二段中（或者 nums 只有一段），那么 target 可以在第一段，也可以在第二段。
     * 如果 target 在第一段，那么 x 一定在 target 右边。
     * 如果 target 在第二段，那么 x 必须大于等于 target。
     * 写成代码就是 target > nums[n - 1] || x >= target。
     *
     * 根据这两种情况，去判断 x 和 target 的 「位置」 关系，从而不断地缩小 target 所在位置的范围，二分找到 target。
     *
     */
    private boolean isBlue(int[] nums, int mid, int target) {
        int x = nums[mid];
        int end = nums[nums.length - 1];

        // x 在左侧区域，此时target也需要在左侧区域，且x需要大于等于target，只有这样才能保证x等于target或者在target的右侧（才能染成蓝色）
        if (x > end) {
            return target > end && x >= target;
        }

        // x在右侧区域，target在左侧，则天然满足x在target左侧（蓝色），或者target也在右侧，但是x需要大于等于target
        return target > end || x >= target;
    }
}
