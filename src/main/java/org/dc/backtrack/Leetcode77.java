package org.dc.backtrack;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。
 * 你可以按 任何顺序 返回答案。
 *
 * 示例 1：
 * 输入：n = 4, k = 2
 * 输出：
 * [
 *   [2,4],
 *   [3,4],
 *   [2,3],
 *   [1,2],
 *   [1,3],
 *   [1,4],
 * ]
 *
 * 示例 2：
 * 输入：n = 1, k = 1
 * 输出：[[1]]
 *
 *
 * 提示：
 * 1 <= n <= 20
 * 1 <= k <= n
 *
 */
public class Leetcode77 {
    /**
     *
     * 解法1: 选择的视角
     */
    public List<List<Integer>> combine1(int n, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();

        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = i + 1;
        }

        dfs1(nums, ans, path, 0, k);
        return ans;
    }

    public void dfs1(int[] nums, List<List<Integer>> ans, List<Integer> path, int idx, int k) {
        if (path.size() == k) {
            ans.add(new ArrayList<>(path));
            return;
        }

        // 一直不选，可能存在超过长度的情况
        if (idx >= nums.length) {
            return;
        }

        // 不选
        dfs1(nums, ans, path, idx + 1, k);

        // 选
        path.add(nums[idx]);
        dfs1(nums, ans, path, idx + 1, k);
        path.remove(path.size() - 1);
    }

    /**
     *
     * 解法2：答案的视角
     *
     */
    public List<List<Integer>> combine2(int n, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();

        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = i + 1;
        }

        dfs2(nums, ans, path, k, 0);
        return ans;
    }

    public void dfs2(int[] nums, List<List<Integer>> ans, List<Integer> path, int k, int idx) {
        if (path.size() == k) {
            ans.add(new ArrayList<>(path));
            return;
        }

        for (int i = idx; i < nums.length; i++) {
            path.add(nums[i]);
            dfs2(nums, ans, path, k, i + 1);
            path.remove(path.size() - 1);
        }
    }
}
