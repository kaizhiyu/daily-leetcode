package org.dc.backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 分割回文串 II  【HARD】
 *
 * 给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是回文串。
 * 返回符合要求的 最少分割次数 。
 *
 * 示例 1：
 * 输入：s = "aab"
 * 输出：1
 * 解释：只需一次分割就可将 s 分割成 ["aa","b"] 这样两个回文子串。
 *
 * 示例 2：
 * 输入：s = "a"
 * 输出：0
 *
 * 示例 3：
 * 输入：s = "ab"
 * 输出：1
 *
 * 提示：
 * 1 <= s.length <= 2000
 * s 仅由小写英文字母组成
 *
 */
public class Leetcode132 {
    /**
     * 参考leetcode131，获得所有的结果，然后挨个判断
     * 内存空间超了
     */
    private final List<List<String>> ans = new ArrayList<>();
    private final List<String> path = new ArrayList<>();

    public int minCut(String s) {
        dfs(s, 0);

        int count = Integer.MAX_VALUE;
        for (List<String> record : ans) {
            if (record.size() - 1 < count) {
                count = record.size() - 1;
            }
        }

        return count;
    }

    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    private void dfs(String s, int i) {
        if (i == s.length()) {
            ans.add(new ArrayList<>(path));
            return;
        }

        for (int j = i; j < s.length(); j++) {
            if (isPalindrome(s, i, j)) {
                path.add(s.substring(i, j + 1));
                dfs(s, j + 1);
                path.remove(path.size() - 1);
            }
        }
    }

    /**
     * 记忆化搜索
     * 从后往前思考，枚举切分点。
     *
     * `s = "abcc"`，从后往前思考
     * 分割一次,s[0..2] = "abc",s[3..3] = "c"可以看到后面分割出来的是符合
     * 问题会转换为求s[0..2]的最小分割次数。
     *
     * 也可以这样分割：s[0..1] = "ab",s[2..3] = "cc"
     * 问题转换为求s[0..1]的最小分割次数
     *
     * 问题可以转换为子问题，那么我们用dp。
     * 定义 dfs(i)代表了s[0...i]的最小分割次数
     * 如果 isPalindrome(s[k...i]) , 那么 dfs(i) = 1 + dfs(k−1) ，k的范围为0<=k<=i, 在所有k的取值中取最小值即可。
     * 边界条件: dfs(−1)=0
     *
     * "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab"
     * 会超出时间限制
     */
    int[] memo;
    public int minCut2(String s) {
        memo = new int[s.length()];
        Arrays.fill(memo, -1);

        return dfs2(s, s.length() - 1);
    }

    private int dfs2(String s, int i) {
        if (i < 0) return -1;
        if (memo[i] != -1) {
            return memo[i];
        }

        // 默认i无解
        memo[i] = Integer.MAX_VALUE;
        for (int k = i; k >= 0; k--) {
            if (isPalindrome(s, k, i)) {
                memo[i] = Math.min(memo[i], dfs2(s, k - 1) + 1);
            }
        }
        return memo[i];
    }

    /**
     * 1:1翻译成递推
     * 该方法执行通过
     */
    public int minCut3(String s) {
        int n = s.length();
        int[] f = new int[n + 1];
        Arrays.fill(f, -1);

        for (int i = 0; i < n; i++) {
            f[i + 1] = Integer.MAX_VALUE;
            for (int k = i; k >= 0; k--) {
                if (isPalindrome(s, k, i)) {
                    f[i + 1] = Math.min(f[i + 1], 1 + f[k]);
                }
            }
        }

        return f[n];
    }
}
