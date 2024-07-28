package org.dc.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
 * 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
 *
 * 示例 1：
 * 输入：nums = [1,2,3]
 * 输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
 *
 * 示例 2：
 * 输入：nums = [0]
 * 输出：[[],[0]]
 *
 * 提示：
 * 1 <= nums.length <= 10
 * -10 <= nums[i] <= 10
 * nums 中的所有元素 互不相同
 *
 */
public class Leetcode78 {
    /**
     *
     * 方法一：输入的视角
     * 对于输入的 nums，考虑每个 nums[i] 是选还是不选，由此组合出 2n 个不同的子集。
     * dfs 中的 i 表示当前考虑到 nums[i] 选或不选。
     *
     * 复杂度分析
     * 时间复杂度：O(n2n)，其中 n 为 nums 的长度。每次都是选或不选，递归次数为一个满二叉树的节点个数，
     * 那么一共会递归 O(2n) 次（等比数列和），再算上加入答案时复制 path 需要 O(n) 的时间，所以时间复杂度为 O(n2n)。
     * 空间复杂度：O(n)。返回值的空间不计。
     */
    public List<List<Integer>> subsets1(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();

        dfs1(nums, ans, path, 0);
        return ans;
    }

    public void dfs1(int[] nums, List<List<Integer>> ans, List<Integer> path, int idx) {
        if (idx == nums.length) {
            ans.add(new ArrayList<>(path));
            return;
        }

        dfs1(nums, ans, path, idx+1);

        path.add(nums[idx]);
        dfs1(nums, ans, path, idx+1);
        path.remove(path.size() - 1);
    }

    /**
     *
     * 方法二：答案的视角
     * 枚举子集（答案）的第一个数选谁，第二个数选谁，第三个数选谁，依此类推。
     * dfs 中的 i 表示现在要枚举选 nums[i] 到 nums[n−1] 中的一个数，添加到 path 末尾。
     * 如果选 nums[j] 添加到 path 末尾，那么下一个要添加到 path 末尾的数，就要在 nums[j+1] 到 nums[n−1] 中枚举了。
     * 注意：不需要在回溯中判断 i=n 的边界情况，因为此时不会进入循环，if i == n: return 这句话写不写都一样。
     *
     * 复杂度分析
     * 时间复杂度：O(n2n)，其中 n 为 nums 的长度。答案的长度为子集的个数，即 2n，同时每次递归都把一个数组放入答案，
     * 因此会递归 2n次，再算上加入答案时复制 path 需要 O(n) 的时间，所以时间复杂度为 O(n2n)。
     * 空间复杂度：O(n)。返回值的空间不计。
     */
    public List<List<Integer>> subsets2(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();

        dfs2(nums, ans, path, 0);
        return ans;
    }

    public void dfs2(int[] nums, List<List<Integer>> ans, List<Integer> path, int idx) {
        ans.add(new ArrayList<>(path));

        for (int i = idx; i < nums.length; i++) {
            path.add(nums[i]);
            dfs2(nums, ans, path, i + 1);
            path.remove(path.size() - 1);
        }
    }
}
