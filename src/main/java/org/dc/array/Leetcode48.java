package org.dc.array;

/**
 * 给定一个 n × n 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。
 * 你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像。
 *
 * 示例 1：
 * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * 输出：[[7,4,1],[8,5,2],[9,6,3]]
 *
 * 示例 2：
 * 输入：matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
 * 输出：[[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]
 *
 *
 * 提示：
 * n == matrix.length == matrix[i].length
 * 1 <= n <= 20
 * -1000 <= matrix[i][j] <= 1000
 *
 */
public class Leetcode48 {
    /**
     * 方法一：辅助矩阵
     * 如下图所示，矩阵顺时针旋转 90º 后，可找到以下规律：
     * 「第 i 行」元素旋转到「第 n−1−i 列」元素；
     * 「第 j 列」元素旋转到「第 j 行」元素；
     *
     * 因此，对于矩阵任意第 i 行、第 j 列元素 matrix[i][j] ，矩阵旋转 90º 后「元素位置旋转公式」为：
     * matrix[i][j] → matrix[j][n−1−i]
     * 原索引位置 → 旋转后索引位置
     *
     * 根据以上「元素旋转公式」，考虑遍历矩阵，将各元素依次写入到旋转后的索引位置。但仍存在问题：在写入一个元素 matrix[i][j]→matrix[j][n−1−i] 后，
     * 原矩阵元素 matrix[j][n−1−i] 就会被覆盖（即丢失），而此丢失的元素就无法被写入到旋转后的索引位置了。
     * 为解决此问题，考虑借助一个「辅助矩阵」暂存原矩阵，通过遍历辅助矩阵所有元素，将各元素填入「原矩阵」旋转后的新索引位置即可。
     *
     * 如以上代码所示，遍历矩阵所有元素的时间复杂度为 O(N^2) ；由于借助了一个辅助矩阵，空间复杂度为 O(N^2) 。
     */
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        int[][] temp = new int[n][];
        for (int i = 0; i < n; i++) {
            temp[i] = matrix[i].clone();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[j][n - 1 - i] = temp[i][j];
            }
        }
    }

    /**
     * 矩阵大小 n 为偶数时，取前 n / 2 行、前 n / 2 列的元素为起始点；当矩阵大小 n 为奇数时，取前 n/2 行、前 n+1 / 2 列的元素为起始点。
     *
     * 令 matrix[i][j]=A ，根据文章开头的元素旋转公式，可推导得适用于任意起始点的元素旋转操作：
     * 暂存 tmp = matrix[i][j]
     * matrix[i][j] ← matrix[n−1−j][i] ← matrix[n−1−i][n−1−j] ← matrix[j][n−1−i] ← tmp
     */
    public void rotate2(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < (n + 1) / 2; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[n - 1 - j][i];
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
                matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
                matrix[j][n - 1 - i] = tmp;
            }
        }
    }
}
