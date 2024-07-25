package org.dc.string;

import java.util.Arrays;

/**
 * 给定三个字符串 s1、s2、s3，请你帮忙验证 s3 是否是由 s1 和 s2 交错 组成的。
 *
 * 两个字符串 s 和 t 交错 的定义与过程如下，其中每个字符串都会被分割成若干 非空 子字符串：
 * s = s1 + s2 + ... + sn
 * t = t1 + t2 + ... + tm
 * |n - m| <= 1
 * 交错 是 s1 + t1 + s2 + t2 + s3 + t3 + ... 或者 t1 + s1 + t2 + s2 + t3 + s3 + ...
 * 注意：a + b 意味着字符串 a 和 b 连接。
 *
 * 示例 1：
 * 输入：s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
 * 输出：true
 *
 * 示例 2：
 * 输入：s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
 * 输出：false
 *
 * 示例 3：
 * 输入：s1 = "", s2 = "", s3 = ""
 * 输出：true
 *
 *
 * 提示：
 * 0 <= s1.length, s2.length <= 100
 * 0 <= s3.length <= 200
 * s1、s2、和 s3 都由小写英文字母组成
 *
 * 进阶：您能否仅使用 O(s2.length) 额外的内存空间来解决它?
 *
 */
public class Leetcode97 {
    // 记忆化搜索
    boolean[][] memo;
    public boolean isInterleave(String s1, String s2, String s3) {
        int s1l = s1.length();
        int s2l = s2.length();
        int s3l = s3.length();

        if (s1l + s2l != s3l) return false;
        if (s1l == 0) return s2.equals(s3);
        if (s2l == 0) return s1.equals(s3);

        memo = new boolean[s1l][s2l];
        for (int i = 0; i < s1l; i++) {
            Arrays.fill(memo[i], false);
        }

        return dfs(s1, s1l - 1, s2, s2l - 1, s3);
    }

    // s1[0..i] + s2[0..j] 是否和 s3[0..i + j -1] 匹配
    private boolean dfs(String s1, int i, String s2, int j, String s3) {
        if (i < 0) {
            for (int k = 0; k <= j; k++) {
                if (s2.charAt(k) != s3.charAt(k)) return false;
            }
            return true;
        }

        if (j < 0) {
            for (int k = 0; k <= i; k++) {
                if (s1.charAt(k) != s3.charAt(k)) return false;
            }
            return true;
        }

        if (memo[i][j]) {
            return memo[i][j];
        }

        memo[i][j] = s1.charAt(i) == s3.charAt(i + j + 1) && dfs(s1, i - 1, s2, j, s3)
                || s2.charAt(j) == s3.charAt(i + j + 1) && dfs(s1, i, s2, j - 1, s3);
        return memo[i][j];
    }

    // 递推
//    public boolean isInterleave2(String s1, String s2, String s3) {
//
//    }

    // 空间优化
}
