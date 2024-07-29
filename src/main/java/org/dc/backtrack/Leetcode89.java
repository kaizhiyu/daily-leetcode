package org.dc.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * n 位格雷码序列 是一个由 2n 个整数组成的序列，其中：
 * 每个整数都在范围 [0, 2n - 1] 内（含 0 和 2n - 1）
 * 第一个整数是 0
 * 一个整数在序列中出现 不超过一次
 * 每对 相邻 整数的二进制表示 恰好一位不同 ，且
 * 第一个 和 最后一个 整数的二进制表示 恰好一位不同
 * 给你一个整数 n ，返回任一有效的 n 位格雷码序列 。
 *
 * 示例 1：
 * 输入：n = 2
 * 输出：[0,1,3,2]
 * 解释：
 * [0,1,3,2] 的二进制表示是 [00,01,11,10] 。
 * - 00 和 01 有一位不同
 * - 01 和 11 有一位不同
 * - 11 和 10 有一位不同
 * - 10 和 00 有一位不同
 * [0,2,3,1] 也是一个有效的格雷码序列，其二进制表示是 [00,10,11,01] 。
 * - 00 和 10 有一位不同
 * - 10 和 11 有一位不同
 * - 11 和 01 有一位不同
 * - 01 和 00 有一位不同
 *
 * 示例 2：
 * 输入：n = 1
 * 输出：[0,1]
 *
 * 提示：
 * 1 <= n <= 16
 *
 * 参考： https://leetcode.cn/problems/gray-code/solutions/2349630/tu-jie-hui-su-dfsjie-fa-by-raccooncc-fxsr/
 */
public class Leetcode89 {
    public List<Integer> grayCode(int n) {
        List<Integer> ans = new ArrayList<>();
        dfs(ans, 0, 0, 0, n);
        return ans;
    }

    void dfs(List<Integer> ans, int node, int isRightSubtree, int depth, int n) {
        if (depth == n) {
            ans.add(node);
            return;
        }

        // 使用 isRightSubtree 字段来控制右子树的左路径
        // ^ 为异或，1 ^ 1 = 0; 0 ^ 0 =
        dfs(ans, (node << 1) | isRightSubtree, 0, depth + 1, n);
        dfs(ans, (node << 1) | isRightSubtree ^ 1, 1, depth + 1, n);
    }

    public static void main(String[] args) {
        System.out.println(1 ^ 0);
        System.out.println(0 ^ 1);
        System.out.println(0 ^ 0);
    }
}
