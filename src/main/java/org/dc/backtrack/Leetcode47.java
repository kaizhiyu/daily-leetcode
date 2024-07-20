package org.dc.backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * 给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
 *
 * 示例 1：
 * 输入：nums = [1,1,2]
 * 输出：
 * [[1,1,2],
 *  [1,2,1],
 *  [2,1,1]]
 *
 * 示例 2：
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 *
 *
 * 提示：
 * 1 <= nums.length <= 8
 * -10 <= nums[i] <= 10
 *
 */
public class Leetcode47 {
    /**
     *
     * 方法一：搜索回溯
     * 此题是「46. 全排列」的进阶，序列中包含了重复的数字，要求我们返回不重复的全排列，那么我们依然可以选择使用搜索回溯的方法来做。
     *
     * 我们将这个问题看作有 n 个排列成一行的空格，我们需要从左往右依次填入题目给定的 n 个数，每个数只能使用一次。那么很直接的可以想到一种穷举的算法，
     * 即从左往右每一个位置都依此尝试填入一个数，看能不能填完这 n 个空格，在程序中我们可以用「回溯法」来模拟这个过程。
     *
     * 我们定义递归函数 backtrack(idx,perm) 表示当前排列为 perm，下一个待填入的位置是第 idx 个位置（下标从 0 开始）。那么整个递归函数分为两个情况：
     * 如果 idx=n，说明我们已经填完了 n 个位置，找到了一个可行的解，我们将 perm 放入答案数组中，递归结束。
     * 如果 idx<n，我们要考虑第 idx 个位置填哪个数。根据题目要求我们肯定不能填已经填过的数，因此很容易想到的一个处理手段是我们定义一个标记数组 vis 来标记已经填过的数，
     * 那么在填第 idx 个数的时候我们遍历题目给定的 n 个数，如果这个数没有被标记过，我们就尝试填入，并将其标记，继续尝试填下一个位置，即调用函数 backtrack(idx+1,perm)。
     * 搜索回溯的时候要撤销该个位置填的数以及标记，并继续尝试其他没被标记过的数。
     *
     * 但题目解到这里并没有满足「全排列不重复」 的要求，在上述的递归函数中我们会生成大量重复的排列，因为对于第 idx 的位置，如果存在重复的数字 i，
     * 我们每次会将重复的数字都重新填上去并继续尝试导致最后答案的重复，因此我们需要处理这个情况。
     *
     * 要解决重复问题，我们只要设定一个规则，保证在填第 idx 个数的时候重复数字只会被填入一次即可。而在本题解中，我们选择对原数组排序，
     * 保证相同的数字都相邻，然后每次填入的数一定是这个数所在重复数集合中「从左往右第一个未被填过的数字
     *
     *
     * 加上 !vis[i - 1]来去重主要是通过限制一下两个相邻的重复数字的访问顺序
     * 举个栗子，对于两个相同的数11，我们将其命名为1a1b, 1a表示第一个1，1b表示第二个1；
     * 那么，不做去重的话，会有两种重复排列 1a1b, 1b1a，
     * 我们只需要取其中任意一种排列； 为了达到这个目的，限制一下1a, 1b访问顺序即可。 比如我们只取1a1b那个排列的话，
     * 只有当visit nums[i-1]之后我们才去visit nums[i]， 也就是如果!visited[i-1]的话则continue
     *
     */
    boolean[] vis;

    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        List<Integer> perm = new ArrayList<Integer>();
        vis = new boolean[nums.length];
        Arrays.sort(nums);
        backtrack(nums, ans, 0, perm);
        return ans;
    }

    public void backtrack(int[] nums, List<List<Integer>> ans, int idx, List<Integer> perm) {
        if (idx == nums.length) {
            ans.add(new ArrayList<Integer>(perm));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (vis[i] || (i > 0 && nums[i] == nums[i - 1] && !vis[i-1])) {
                continue;
            }

            perm.add(nums[i]);
            vis[i] = true;
            backtrack(nums, ans, idx + 1, perm);
            vis[i] = false;
            perm.remove(idx);
        }
    }

    /**
     *
     * 用 dfs(i,left) 来回溯，设当前枚举到 candidates[i]，剩余要选的元素之和为 left，按照选或不选分类讨论：
     * 不选：递归到 dfs(i+1,left)。
     *  选：递归到 dfs(i,left−candidates[i])。  !!!! 注意 i 不变，表示在下次递归中可以继续选 candidates[i] !!!!
     * 注：这个思路类似 完全背包。
     *
     * 如果递归中发现 left=0 则说明找到了一个合法组合，复制一份 path 加入答案。
     * 递归边界：如果 i=n 或者 left<0 则返回。
     * 递归入口：dfs(0,target)
     *
     */
    public List<List<Integer>> combinationSum1(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        dfs(0, target, candidates, ans, path);
        return ans;
    }

    private void dfs(int i, int left, int[] candidates, List<List<Integer>> ans, List<Integer> path) {
        if (left == 0) {
            // 找到一个合法组合
            ans.add(new ArrayList<>(path));
            return;
        }

        if (i == candidates.length || left < 0) {
            return;
        }

        // 不选
        dfs(i + 1, left, candidates, ans, path);

        // 选
        path.add(candidates[i]);
        dfs(i, left - candidates[i], candidates, ans, path);
        path.remove(path.size() - 1); // 恢复现场
    }

    /**
     *
     * 剪枝优化
     * 把 candidates 从小到大排序，如果递归中发现 left<candidates[i]，由于后面的数字只会更大，所以无法把 left 减小到 0，可以直接返回。
     *
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();

        Arrays.sort(candidates);

        dfs2(0, target, candidates, ans, path);

        return ans;
    }

    private void dfs2(int i, int left, int[] candidates, List<List<Integer>> ans, List<Integer> path) {
        if (left == 0) {
            // 找到一个合法组合
            ans.add(new ArrayList<>(path));
            return;
        }

        if (i == candidates.length || left < candidates[i]) {
            return;
        }

        // 不选
        dfs2(i + 1, left, candidates, ans, path);

        // 选
        path.add(candidates[i]);
        dfs2(i, left - candidates[i], candidates, ans, path);
        path.remove(path.size() - 1); // 恢复现场
    }



    /**
     *
     * 「答案视角」。同样用 dfs(i,left) 来回溯，设当前枚举到 candidates[i]，剩余要选的元素之和为 left，考虑枚举下个元素是谁：
     *
     * 在 [i,n−1] 中枚举要填在 path 中的元素 candidates[j]，然后递归到 dfs(j,left−candidates[j])。注意这里是递归到 j 不是 j+1，表示 candidates[j] 可以重复选取。
     *
     */
    public List<List<Integer>> combinationSum3(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        dfs3(0, target, candidates, ans, path);
        return ans;
    }

    private void dfs3(int i, int left, int[] candidates, List<List<Integer>> ans, List<Integer> path) {
        if (left == 0) {
            // 找到一个合法组合
            ans.add(new ArrayList<>(path));
            return;
        }

        if (left < candidates[i]) {
            return;
        }

        // 枚举选哪个
        for (int j = i; j < candidates.length; j++) {
            path.add(candidates[j]);
            dfs3(j, left - candidates[j], candidates, ans, path);
            path.remove(path.size() - 1); // 恢复现场
        }
    }
}
