package org.dc.matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * 给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
 * 示例 1：
 * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * 输出：[1,2,3,6,9,8,7,4,5]
 *
 * 示例 2：
 * 输入：matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
 * 输出：[1,2,3,4,8,12,11,10,9,5,6,7]
 *
 * 提示：
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 10
 * -100 <= matrix[i][j] <= 100
 *
 */
public class Leetcode54 {
    /**
     *
     * 根据题目示例 matrix = [[1,2,3],[4,5,6],[7,8,9]] 的对应输出 [1,2,3,6,9,8,7,4,5] 可以发现，顺时针打印矩阵的顺序是 “从左向右、从上向下、从右向左、从下向上” 循环。
     * 因此，考虑设定矩阵的 “左、上、右、下” 四个边界，模拟以上矩阵遍历顺序。
     * 算法流程：
     * 空值处理： 当 matrix 为空时，直接返回空列表 [] 即可。
     * 初始化： 矩阵 左、右、上、下 四个边界 l , r , t , b ，用于打印的结果列表 res 。
     * 循环打印： “从左向右、从上向下、从右向左、从下向上” 四个方向循环打印。
     * 根据边界打印，即将元素按顺序添加至列表 res 尾部。
     * 边界向内收缩 1 （代表已被打印）。
     * 判断边界是否相遇（是否打印完毕），若打印完毕则跳出。
     * 返回值： 返回 res 即可。
     *
     * 打印方向	1. 根据边界打印	    2. 边界向内收缩	3. 是否打印完毕
     * 从左向右	左边界l ，右边界 r	上边界 t 加 1	是否 t > b
     * 从上向下	上边界 t ，下边界b	右边界 r 减 1	是否 l > r
     * 从右向左	右边界 r ，左边界l	下边界 b 减 1	是否 t > b
     * 从下向上	下边界 b ，上边界t	左边界 l 加 1	是否 l > r
     *
     * 复杂度分析：
     * 时间复杂度 O(MN) ： M,N 分别为矩阵行数和列数。
     * 空间复杂度 O(1) ： 四个边界 l , r , t , b 使用常数大小的额外空间。
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix.length == 0) {
            return new ArrayList<>();
        }

        int l = 0, r = matrix[0].length - 1, t = 0, b = matrix.length - 1, x = 0;
        Integer[] res = new Integer[(r + 1) * (b + 1)];
        while (true) {
            // left -> right
            for (int i = l; i <= r; i++) {
                res[x++] = matrix[t][i];
            }
            if (++t > b) {
                break;
            }

            // top -> bottom
            for (int i = t; i <=b; i++) {
                res[x++] = matrix[i][r];
            }
            if (l > --r) {
                break;
            }

            // rigth -> left
            for (int i = r; i >=l; i--) {
                res[x++] = matrix[b][i];
            }
            if (t > --b) {
                break;
            }

            // bottom -> top
            for (int i = b; i >= t; i--) {
                res[x++] = matrix[i][l];
            }
            if (++l > r) {
                break;
            }
        }

        return Arrays.asList(res);
    }

    /**
     *
     * 可以模拟螺旋矩阵的路径。初始位置是矩阵的左上角，初始方向是向右，当路径超出界限或者进入之前访问过的位置时，顺时针旋转，进入下一个方向。
     * 判断路径是否进入之前访问过的位置需要使用一个与输入矩阵大小相同的辅助矩阵 visited，其中的每个元素表示该位置是否被访问过。当一个元素被访问时，将 visited 中的对应位置的元素设为已访问。
     * 如何判断路径是否结束？由于矩阵中的每个元素都被访问一次，因此路径的长度即为矩阵中的元素数量，当路径的长度达到矩阵中的元素数量时即为完整路径，将该路径返回。
     *
     */
    public List<Integer> spiralOrder2(int[][] matrix) {
        List<Integer> order = new ArrayList<Integer>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return order;
        }
        int rows = matrix.length, columns = matrix[0].length;
        boolean[][] visited = new boolean[rows][columns];
        int total = rows * columns;
        int row = 0, column = 0;
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int directionIndex = 0;
        for (int i = 0; i < total; i++) {
            order.add(matrix[row][column]);
            visited[row][column] = true;
            int nextRow = row + directions[directionIndex][0], nextColumn = column + directions[directionIndex][1];
            if (nextRow < 0 || nextRow >= rows || nextColumn < 0 || nextColumn >= columns || visited[nextRow][nextColumn]) {
                directionIndex = (directionIndex + 1) % 4;
            }
            row += directions[directionIndex][0];
            column += directions[directionIndex][1];
        }
        return order;
    }
}
