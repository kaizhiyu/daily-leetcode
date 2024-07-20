package org.dc.backtrack;

import java.util.*;

/**
 *
 * 按照国际象棋的规则，皇后可以攻击与之处在同一行或同一列或同一斜线上的棋子。
 * n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 * 给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。
 * 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
 *
 * 示例 1：
 * 输入：n = 4
 * 输出：[[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
 * 解释：如上图所示，4 皇后问题存在两个不同的解法。
 *
 * 示例 2：
 * 输入：n = 1
 * 输出：[["Q"]]
 *
 * 提示：
 * 1 <= n <= 9
 *
 */
public class Leetcode51 {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> ans = new ArrayList<>();
        // 定义：第i行的皇后在第col[i]列 举个例子，第1行的皇后在第2列，所以col[0] = 1
        int[] col = new int[n];
        // 回溯的路上，元素是否已经使用
        boolean[] onPath = new boolean[n];
        // 右上对角是否已经有值，归纳一下，r + c 是个定值
        boolean[] diag1 = new boolean[n * 2 - 1];
        // 左上对角是否已经有值，归纳一下 r - c 是个定值，但是存在负值的情况，所以扩大一倍容量，r - c + n - 1 >= 0
        boolean[] diag2 = new boolean[n * 2 - 1];

        dfs(0, n, col, onPath, diag1, diag2, ans);

        return ans;
    }

    private void dfs(int r, int n, int[] col, boolean[] onPath, boolean[] diag1, boolean[] diag2, List<List<String>> ans) {
        if (r == n) {
            List<String> board = new ArrayList<>(n);
            for (int c : col) {
                char[] row = new char[n];
                Arrays.fill(row, '.');
                row[c] = 'Q';
                board.add(new String(row));
            }
            ans.add(board);
            return;
        }

        for (int c = 0; c < n; c++) {
            int rc = r - c + n - 1;
            if (!onPath[c] && !diag1[r + c] && !diag2[rc]) {
                col[r] = c;
                onPath[c] = diag1[r + c] = diag2[rc] = true;
                dfs(r + 1, n, col, onPath, diag1, diag2, ans);
                onPath[c] = diag1[r + c] = diag2[rc] = false;
            }
        }
    }
}
