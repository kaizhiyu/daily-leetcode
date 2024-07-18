package org.dc.array;

/**
 *
 * 整数数组 nums 按升序排列，数组中的值 互不相同 。
 * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0],
 * nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
 * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
 * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
 *
 * 示例 1：
 * 输入：nums = [4,5,6,7,0,1,2], target = 0
 * 输出：4
 *
 * 示例 2：
 * 输入：nums = [4,5,6,7,0,1,2], target = 3
 * 输出：-1
 *
 * 示例 3：
 * 输入：nums = [1], target = 0
 * 输出：-1
 *
 * 提示：
 * 1 <= nums.length <= 5000
 * -104 <= nums[i] <= 104
 * nums 中的每个值都 独一无二
 * 题目数据保证 nums 在预先未知的某个下标上进行了旋转
 * -104 <= target <= 104
 *
 */
public class Leetcode33 {
    /**
     *
     * 核心思想
     * 把某个数 x 与最后一个数 nums[n−1] 比大小：
     * 如果 x>nums[n−1]，那么可以推出以下结论：1) nums 一定被分成左右两个递增段；2) 第一段的所有元素均大于第二段的所有元素；3) x 在第一段。
     * 如果 x≤nums[n−1]，那么 x 一定在第二段。（或者 nums 就是递增数组，此时只有一段。）
     *
     * 方法一：两次二分
     * 首先用 153. 寻找旋转排序数组中的最小值 的方法，找到 nums 的最小值的下标 i。
     *
     * 然后分类讨论：
     * 如果 target>nums[n−1]，那么 target 一定在第一段 [0,i−1] 中，在 [0,i−1] 中二分查找 target。
     * 如果 target≤nums[n−1]，那么：
     * 如果 i=0，说明 nums 是递增的，直接在 [0,n−1] 中二分查找 target。
     * 如果 i>0，那么 target 一定在第二段 [i,n−1] 中，在 [i,n−1] 中二分查找 target。
     * 这两种情况可以合并成：在 [i,n−1] 中二分查找 target。
     *
     *
     * 方法二：一次二分
     * 设 x=nums[mid] 是我们现在二分取到的数。
     * 现在需要判断 x 和 target 的位置关系，谁在左边，谁在右边？
     *
     * 核心思路
     * 如果 x 和 target 在不同的递增段：
     * 如果 target 在第一段（左），x 在第二段（右），说明 x 在 target 右边；
     * 如果 target 在第二段（右），x 在第一段（左），说明 x 在 target 左边。
     * 如果 x 和 target 在相同的递增段：
     * 比较 x 和 target 的大小即可。
     * 分类讨论
     * 下面只讨论 x 在 target 右边，或者等于 target 的情况。其余情况 x 一定在 target 左边。
     *
     * 如果 x>nums[n−1]，说明 x 在第一段中，那么 target 也必须在第一段中（否则 x 一定在 target 左边）且 x 必须大于等于 target。
     * 写成代码就是 target > nums[n - 1] && x >= target。
     * 如果 x≤nums[n−1]，说明 x 在第二段中（或者 nums 只有一段），那么 target 可以在第一段，也可以在第二段。
     * 如果 target 在第一段，那么 x 一定在 target 右边。
     * 如果 target 在第二段，那么 x 必须大于等于 target。
     * 写成代码就是 target > nums[n - 1] || x >= target。
     * 根据这两种情况，去判断 x 和 target 的位置关系，从而不断地缩小 target 所在位置的范围，二分找到 target。
     *
     * 细节
     * 二分的范围可以是 [0,n−2]。
     *
     * 这是因为，如果 target 在数组中的位置是 n−1，那么上面分类讨论中的代码，计算结果均为 false。这意味着每次二分更新的都是 left，那么最终答案自然就是 n−1。
     */
    public int search(int[] nums, int target) {
        int n = nums.length, i = findMin(nums);
        if (nums[n - 1] < target) {
            return lowerBind(nums, 0, i - 1, target);
        }

        return lowerBind(nums, i, n - 1, target);
    }

    public int findMin(int[] nums) {
        int left = 0, right = nums.length - 2;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (nums[mid] < nums[nums.length - 1]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    public int lowerBind(int[] nums, int left, int right, int target) {
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return nums[left] == target ? left : -1;
    }

    public int search2(int[] nums, int target) {
        int left = -1, right = nums.length - 1; // 开区间 (-1, n-1)
        while (left + 1 < right) { // 开区间不为空
            int mid = left + (right - left) / 2;
            if (isBlue(nums, target, mid)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return nums[right] == target ? right : -1;
    }

    private boolean isBlue(int[] nums, int target, int i) {
        int x = nums[i];
        int end = nums[nums.length - 1];
        if (x > end) {
            return target > end && x >= target;
        }
        return target > end || x >= target;
    }
}
