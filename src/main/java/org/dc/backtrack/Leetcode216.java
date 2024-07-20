package org.dc.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 找出所有相加之和为 n 的 k 个数的组合，且满足下列条件：
 * 只使用数字1到9
 * 每个数字 最多使用一次
 * 返回 所有可能的有效组合的列表 。该列表不能包含相同的组合两次，组合可以以任何顺序返回。
 *
 * 示例 1:
 * 输入: k = 3, n = 7
 * 输出: [[1,2,4]]
 * 解释:
 * 1 + 2 + 4 = 7
 * 没有其他符合的组合了。
 *
 * 示例 2:
 * 输入: k = 3, n = 9
 * 输出: [[1,2,6], [1,3,5], [2,3,4]]
 * 解释:
 * 1 + 2 + 6 = 9
 * 1 + 3 + 5 = 9
 * 2 + 3 + 4 = 9
 * 没有其他符合的组合了。
 *
 * 示例 3:
 * 输入: k = 4, n = 1
 * 输出: []
 * 解释: 不存在有效的组合。
 * 在[1,9]范围内使用4个不同的数字，我们可以得到的最小和是1+2+3+4 = 10，因为10 > 1，没有有效的组合。
 *
 *
 * 提示:
 * 2 <= k <= 9
 * 1 <= n <= 60
 *
 */
public class Leetcode216 {
    List<List<Integer>> ans = new ArrayList<>();
    List<Integer> path = new ArrayList<>();
    /**
     *
     * 从答案视角出发
     * 设还要选 d = k - m 个数，path 长为 m
     * 设需要选择和为 t 的数字，初始为n，每选一个数字j，则t减少j
     * 答案范围 [0, 9]
     *
     * 剪枝：
     * 剩余数目不够 i < d
     * t 小于 0
     * 剩余数字即便全部选最大的，和也不够t，举个例子，i = 5，还需要选择 d = 3 个数，t > 5 + 4 + 3 则直接返回，t > i + ... + ( i -d + 1) = ( i + i - d + 1 ) * d / 2
     *
     * 复杂度分析
     * 时间复杂度：分析回溯问题的时间复杂度，有一个通用公式：路径长度×搜索树的叶子数。对于本题，它等于 O(k⋅C(9,k))（去掉剪枝就是 77. 组合）。
     * 空间复杂度：O(k)。返回值不计入。
     */
    public List<List<Integer>> combinationSum3(int k, int n) {
        dfs1(k, n, 9);
        return ans;
    }

    public void dfs1(int k, int t, int i) {
        int d = k - path.size(); // 还要选 d 个数

        if (t < 0 || t > (i * 2 -d +1) * d / 2) { // 此处必剪枝，否则时间复杂度过不去
            return;
        }

        if (d == 0 && t == 0) { // 已经达到条件，保存
            ans.add(new ArrayList<>(path));
            return;
        }

        for (int j = i; j > d - 1; j--) {
            path.add(j);
            dfs1(k, t - j, j - 1);
            path.remove(path.size() - 1);
        }
    }

    /**
     *
     * 从输入视角出发，
     * 选或者不选
     *
     */
    public List<List<Integer>> combinationSum3_1(int k, int n) {
        dfs2(k, n, 9);
        return ans;
    }

    public void dfs2(int k, int t, int i) {
        int d = k - path.size(); // 还要选 d 个数
        if (t < 0 || t > (i * 2 - d + 1) * d / 2) { // 剪枝
            return;
        }

        if (d == 0 && t == 0) { // 找到一个合法组合
            ans.add(new ArrayList<>(path));
            return;
        }

        if (d > i) { // 数目不够，直接返回
            return;
        }

        // 不选
        dfs2(k, t, i - 1);

        // 选
        path.add(i);
        dfs2(k, t - i, i - 1);
        path.remove(path.size() - 1);
    }
}
