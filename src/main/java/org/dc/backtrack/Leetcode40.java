package org.dc.backtrack;

import java.util.*;

/**
 *
 * 给定一个候选人编号的集合 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 * candidates 中的每个数字在每个组合中只能使用 一次 。
 * 注意：解集不能包含重复的组合。
 *
 * 示例 1:
 * 输入: candidates = [10,1,2,7,6,1,5], target = 8,
 * 输出:
 * [
 * [1,1,6],
 * [1,2,5],
 * [1,7],
 * [2,6]
 * ]
 *
 * 示例 2:
 * 输入: candidates = [2,5,2,1,2], target = 5,
 * 输出:
 * [
 * [1,2,2],
 * [5]
 * ]
 *
 *
 * 提示:
 * 1 <= candidates.length <= 100
 * 1 <= candidates[i] <= 50
 * 1 <= target <= 30
 *
 *
 *
 */
public class Leetcode40 {
    /**
     * 与第 39 题（组合之和）的差别
     * 这道题与上一问的区别在于：
     * 第 39 题：candidates 中的数字可以无限制重复被选取；
     * 第 40 题：candidates 中的每个数字在每个组合中只能使用一次。
     * 相同点是：相同数字列表的不同排列视为一个结果。
     *
     * 如何去掉重复的集合（重点）
     * 为了使得解集不包含重复的组合。有以下 2 种方案：
     *
     * 使用 哈希表 天然的去重功能，但是编码相对复杂；
     * 这里我们使用和第 39 题和第 15 题（三数之和）类似的思路：不重复就需要按 顺序 搜索， 在搜索的过程中检测分支是否会出现重复结果 。注意：这里的顺序不仅仅指数组 candidates 有序，还指按照一定顺序搜索结果。
     * 参考 https://leetcode.cn/problems/combination-sum-ii/solutions/14753/hui-su-suan-fa-jian-zhi-python-dai-ma-java-dai-m-3/
     *
     * 重点考虑的是同一层相同元素的处理上
     * 由第 39 题我们知道，数组 candidates 有序，也是 深度优先遍历 过程中实现「剪枝」的前提。
     * 将数组先排序的思路来自于这个问题：去掉一个数组中重复的元素。很容易想到的方案是：先对数组 升序 排序，重复的元素一定不是排好序以后相同的连续数组区域的第 1 个元素。
     * 也就是说，剪枝发生在：同一层数值相同的结点第 2、3 ... 个结点，因为数值相同的第 1 个结点已经搜索出了包含了这个数值的全部结果，同一层的其它结点，候选数的个数更少，
     * 搜索出的结果一定不会比第 1 个结点更多，并且是第 1 个结点的子集。（说明：这段文字很拗口，大家可以结合具体例子，在纸上写写画画进行理解。）
     *
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        Deque<Integer> path = new ArrayDeque<>();

        Arrays.sort(candidates);

        dfs(candidates, target, path, ans, 0);

        return ans;
    }

    private void dfs(int[] candidates, int target, Deque<Integer> path, List<List<Integer>> ans, int idx) {
        if (target == 0) {
            ans.add(new ArrayList<>(path));
            return ;
        }

        for (int i = idx; i < candidates.length; i++) {
            if (target < candidates[i]) {
                break;
            }

            if (i > idx && candidates[i] == candidates[i-1]) {
                continue;
            }

            path.addLast(candidates[i]);
            dfs(candidates, target - candidates[i], path, ans, i + 1);
            path.removeLast();
        }
    }
}
