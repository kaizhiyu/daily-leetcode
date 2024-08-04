package org.dc.backtrack;

/**
 * 52. N 皇后 II  【HARD】
 *
 * n 皇后问题 研究的是如何将 n 个皇后放置在 n × n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 * 给你一个整数 n ，返回 n 皇后问题 不同的解决方案的数量。
 *
 * 示例 1：
 * 输入：n = 4
 * 输出：2
 * 解释：如上图所示，4 皇后问题存在两个不同的解法。
 *
 * 示例 2：
 * 输入：n = 1
 * 输出：1
 *
 * 提示：
 *
 * 1 <= n <= 9
 *
 */
public class Leetcode52 {
    private int n, ans;
    private boolean[] onPath, diag1, diag2;

    public int totalNQueens(int n) {
        this.n = n;
        onPath = new boolean[n];
        diag1 = new boolean[n * 2 - 1];
        diag2 = new boolean[n * 2 - 1];
        dfs(0);
        return ans;
    }

    private void dfs(int r) {
        if (r == n) {
            ans++;
            return;
        }
        for (int c = 0; c < n; ++c) {
            int rc = r - c + n - 1;
            if (!onPath[c] && !diag1[r + c] && !diag2[rc]) {
                onPath[c] = diag1[r + c] = diag2[rc] = true;
                dfs(r + 1);
                onPath[c] = diag1[r + c] = diag2[rc] = false; // 恢复现场
            }
        }
    }
}
