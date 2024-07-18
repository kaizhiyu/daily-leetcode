package org.dc.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
 *
 * 示例 1：
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 *
 * 示例 2：
 * 输入：nums = [0,1]
 * 输出：[[0,1],[1,0]]
 *
 * 示例 3：
 * 输入：nums = [1]
 * 输出：[[1]]
 *
 * 提示：
 * 1 <= nums.length <= 6
 * -10 <= nums[i] <= 10
 * nums 中的所有整数 互不相同
 *
 * 回溯法：一种通过探索所有可能的候选解来找出所有的解的算法。如果候选解被确认不是一个解（或者至少不是最后一个解），回溯算法会通过在上一步进行一些变化抛弃该解，即回溯并且再次尝试。
 */
public class Leetcode46 {
    /**
     * 这个问题可以看作有 n 个排列成一行的空格，我们需要从左往右依此填入题目给定的 n 个数，每个数只能使用一次。那么很直接的可以想到一种穷举的算法，
     * 即从左往右每一个位置都依此尝试填入一个数，看能不能填完这 n 个空格，在程序中我们可以用「回溯法」来模拟这个过程。
     *
     * 我们定义递归函数 backtrack(first,output) 表示从左往右填到第 first 个位置，当前排列为 output。 那么整个递归函数分为两个情况：
     *
     * 如果 first=n，说明我们已经填完了 n 个位置（注意下标从 0 开始），找到了一个可行的解，我们将 output 放入答案数组中，递归结束。
     * 如果 first<n，我们要考虑这第 first 个位置我们要填哪个数。根据题目要求我们肯定不能填已经填过的数，因此很容易想到的一个处理手段是我们定义一个标记数组 vis 来标记已经填过的数，
     * 那么在填第 first 个数的时候我们遍历题目给定的 n 个数，如果这个数没有被标记过，我们就尝试填入，并将其标记，
     * 继续尝试填下一个位置，即调用函数 backtrack(first+1,output)。回溯的时候要撤销这一个位置填的数以及标记，并继续尝试其他没被标记过的数。
     * 使用标记数组来处理填过的数是一个很直观的思路，但是可不可以去掉这个标记数组呢？毕竟标记数组也增加了我们算法的空间复杂度。
     *
     * 答案是可以的，我们可以将题目给定的 n 个数的数组 nums 划分成左右两个部分，左边的表示已经填过的数，右边表示待填的数，我们在回溯的时候只要动态维护这个数组即可。
     *
     * 具体来说，假设我们已经填到第 first 个位置，那么 nums 数组中 [0,first−1] 是已填过的数的集合，[first,n−1] 是待填的数的集合。我们肯定是尝试用 [first,n−1]
     * 里的数去填第 first 个数，假设待填的数的下标为 i，那么填完以后我们将第 i 个数和第 first 个数交换，即能使得在填第 first+1 个数的时候 nums 数组的 [0,first]
     * 部分为已填过的数，[first+1,n−1] 为待填的数，回溯的时候交换回来即能完成撤销操作。
     * 举个简单的例子，假设我们有 [2,5,8,9,10] 这 5 个数要填入，已经填到第 3 个位置，已经填了 [8,9] 两个数，那么这个数组目前为 [8,9|2,5,10] 这样的状态，
     * 分隔符区分了左右两个部分。假设这个位置我们要填 10 这个数，为了维护数组，我们将 2 和 10 交换，即能使得数组继续保持分隔符左边的数已经填过，右边的待填 [8,9,10|2,5] 。
     *
     *
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();

        List<Integer> output = new ArrayList<Integer>();
        for (int num : nums) {
            output.add(num);
        }

        int n = nums.length;
        backtrack(n, output, res, 0);
        return res;
    }

    public void backtrack(int n, List<Integer> output, List<List<Integer>> res, int first) {
        // 所有数都填完了
        if (first == n) {
            res.add(new ArrayList<Integer>(output));
        }

        for (int i = first; i < n; i++) {
            // 动态维护数组
            Collections.swap(output, first, i);

            // 继续递归填下一个数
            backtrack(n, output, res, first + 1);

            // 撤销操作
            Collections.swap(output, first, i);
        }
    }

    private int[] flag;
    private List<List<Integer>> res = new ArrayList<List<Integer>>();
    public List<List<Integer>> permute1(int[] nums) {
        if (nums == null) {
            return null;
        }
        flag = new int[nums.length];
        domain(nums, new ArrayList<Integer>());
        return res;
    }

    private void domain(int[] nums, List<Integer> cur) {
        if (cur.size() == nums.length) {
            res.add(new ArrayList<>(cur));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (flag[i] == 0) {
                cur.add(nums[i]);

                flag[i] = 1;
                domain(nums,cur);
                flag[i] = 0;

                cur.remove(cur.size() - 1);
            }
        }
    }
}
