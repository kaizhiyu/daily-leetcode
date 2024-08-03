package org.dc.dp;

import java.util.Arrays;

/**
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
 * 问总共有多少条不同的路径？
 *
 * 示例 1：
 * 输入：m = 3, n = 7
 * 输出：28
 *
 * 示例 2：
 * 输入：m = 3, n = 2
 * 输出：3
 * 解释：
 * 从左上角开始，总共有 3 条路径可以到达右下角。
 * 1. 向右 -> 向下 -> 向下
 * 2. 向下 -> 向下 -> 向右
 * 3. 向下 -> 向右 -> 向下
 *
 * 示例 3：
 * 输入：m = 7, n = 3
 * 输出：28
 *
 * 示例 4：
 * 输入：m = 3, n = 3
 * 输出：6
 *
 * 提示：
 * 1 <= m, n <= 100
 * 题目数据保证答案小于等于 2 * 109
 */
public class Leetcode62 {
    /**
     * 记忆化搜索
     * 得出当前位置路径的计算方法：
     * dfs(i,j)=dfs(i−1,j)+dfs(i,j−1)
     * 需要注意的是:
     *
     * 当 i<0  或者 j<0 ，返回 0
     * 当 i==0 同时 j==0，返回 1
     * 其他情况：dfs(i,j) = dfs(i−1,j) + dfs(i,j−1)
     */
    int[][] memo;
    public int uniquePaths(int m, int n) {
        memo = new int[m][n];
        for (int[] mm : memo) Arrays.fill(mm, -1);
        return dfs(m - 1, n - 1);
    }

    private int dfs(int i, int j) {
        if (i < 0 || j < 0) return 0;
        if (i == 0 && j == 0) return 1;

        if (memo[i][j] != -1) return memo[i][j];

        memo[i][j] = dfs(i - 1, j) + dfs(i, j - 1);
        return memo[i][j];
    }

    /**
     * dp
     */
}
