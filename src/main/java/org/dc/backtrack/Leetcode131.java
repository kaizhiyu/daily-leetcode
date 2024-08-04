package org.dc.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * 分割回文串
 *
 * 给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是回文串。返回 s 所有可能的分割方案。
 *
 * 示例 1：
 * 输入：s = "aab"
 * 输出：[["a","a","b"],["aa","b"]]
 *
 * 示例 2：
 * 输入：s = "a"
 * 输出：[["a"]]
 *
 *
 * 提示：
 * 1 <= s.length <= 16
 * s 仅由小写英文字母组成
 *
 */
public class Leetcode131 {
    /**
     * 从输入的角度考虑问题
     * 想象s="aab" 可以在字符之间插入逗号表示分割的字符，比如"a,a,b"
     *
     */
    public List<List<String>> partition1(String s) {
        dfs(s, 0, 0);
        return ans;
    }

    // start 表示当前这段回文子串的开始位置
    private void dfs(String s, int i, int start) {
        if (i == s.length()) {
            ans.add(new ArrayList<>(path)); // 复制 path
            return;
        }

        // 不选 i 和 i+1 之间的逗号（i=n-1 时一定要选）
        if (i < s.length() - 1) {
            dfs(s, i + 1, start);
        }

        // 选 i 和 i+1 之间的逗号（把 s[i] 作为子串的最后一个字符）
        if (isPalindrome(s, start, i)) {
            path.add(s.substring(start, i + 1));
            dfs(s, i + 1, i + 1); // 下一个子串从 i+1 开始
            path.remove(path.size() - 1); // 恢复现场
        }
    }


    /**
     * 从答案角度考虑：（可以认为字符串两个字符中间可以插入，分割字符串）
     * 枚举第一个逗号的位置（或者没有）
     * 枚举第二个逗号的位置（或者没有）
     *
     * 回溯三问：
     * 当前操作？选择回文子串s[i..j] 加入path
     * 子问题？从下标>=i的后缀中构造回文子串
     * 下一个子问题？从下标>=j+1的后缀中构造回文子串
     *
     * 复杂度分析
     * 时间复杂度：O(n2^n)，其中 n 为 s 的长度。答案的长度至多为逗号子集的个数，即 O(2^n)，因此会递归 O(2^n) 次，再算上判断回文和加入答案时需要 O(n) 的时间，所以时间复杂度为 O(n2^n)。
     * 空间复杂度：O(n)。返回值的空间不计。
     */
    private final List<List<String>> ans = new ArrayList<>();
    private final List<String> path = new ArrayList<>();

    public List<List<String>> partition2(String s) {
        dfs(s, 0);
        return ans;
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
}
