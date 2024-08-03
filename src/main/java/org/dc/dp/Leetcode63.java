package org.dc.dp;

import java.util.Arrays;

/**
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish”）。
 * 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
 * 网格中的障碍物和空位置分别用 1 和 0 来表示。
 *
 * 示例 1：
 * 输入：obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
 * 输出：2
 * 解释：3x3 网格的正中间有一个障碍物。
 * 从左上角到右下角一共有 2 条不同的路径：
 * 1. 向右 -> 向右 -> 向下 -> 向下
 * 2. 向下 -> 向下 -> 向右 -> 向右
 *
 * 示例 2：
 * 输入：obstacleGrid = [[0,1],[0,0]]
 * 输出：1
 *
 * 提示：
 * m == obstacleGrid.length
 * n == obstacleGrid[i].length
 * 1 <= m, n <= 100
 * obstacleGrid[i][j] 为 0 或 1
 *
 */
public class Leetcode63 {
    /**
     * 我们用 f(i,j) 来表示从坐标 (0,0) 到坐标 (i,j) 的路径总数，u(i,j) 表示坐标 (i,j) 是否可行，如果坐标 (i,j) 有障碍物，u(i,j)=0，否则 u(i,j)=1。
     * 因为「机器人每次只能向下或者向右移动一步」，所以从坐标 (0,0) 到坐标 (i,j) 的路径总数的值只取决于从坐标 (0,0) 到坐标 (i−1,j) 的路径总数和从坐标 (0,0) 到坐标 (i,j−1) 的路径总数，
     * 即 f(i,j) 只能通过 f(i−1,j) 和 f(i,j−1) 转移得到。当坐标 (i,j) 本身有障碍的时候，任何路径都到到不了 f(i,j)，此时 f(i,j)=0；
     * 下面我们来讨论坐标 (i,j) 没有障碍的情况：如果坐标 (i−1,j) 没有障碍，那么就意味着从坐标 (i−1,j) 可以走到 (i,j)，即 (i−1,j) 位置对 f(i,j) 的贡献为 f(i−1,j)，
     * 同理，当坐标 (i,j−1) 没有障碍的时候，(i,j−1) 位置对 f(i,j) 的贡献为 f(i,j−1)。综上所述，我们可以得到这样的动态规划转移方程：
     * f(i,j)={ 0 u(i,j)=0 | f(i−1,j)+f(i,j−1) u(i,j) != 0
     */
    int[][] memo;
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        memo = new int[m][n];
        for (int[] mm : memo) Arrays.fill(mm, -1);
        return dfs(obstacleGrid, m - 1, n - 1);
    }

    private int dfs(int[][] obstacleGrid, int i, int j) {
        if (i < 0 || j < 0) return 0;
        if (i == 0 && j == 0) return obstacleGrid[i][j] == 1 ? 0 : 1;

        if (memo[i][j] != -1) return memo[i][j];

        if (obstacleGrid[i][j] == 1) {
            memo[i][j] = 0;
        } else {
            memo[i][j] = dfs(obstacleGrid, i - 1, j) + dfs(obstacleGrid, i, j - 1);
        }

        return memo[i][j];
    }
}
