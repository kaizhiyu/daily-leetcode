package org.dc.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 *
 * 示例 1：
 * 输入：n = 3
 * 输出：["((()))","(()())","(())()","()(())","()()()"]
 *
 * 示例 2：
 * 输入：n = 1
 * 输出：["()"]
 *
 * 提示：
 * 1 <= n <= 8
 *
 */
public class Leetcode22 {
    //
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<String>();
        backTrack(ans, new StringBuilder(), 0, 0, n);
        return ans;
    }

    public void backTrack(List<String> ans, StringBuilder cur, int open, int close, int max) {
        if (cur.length() == max * 2) {
            ans.add(cur.toString());
            return;
        }

        if (open < max) {
            cur.append("(");
            backTrack(ans, cur, open + 1, close, max);
            cur.deleteCharAt(cur.length() - 1);
        }

        if (close < open) {
            cur.append(")");
            backTrack(ans, cur, open, close + 1, max);
            cur.deleteCharAt(cur.length() - 1);
        }
    }

    /**
     * 从输入角度，选或者不选
     *
     * 复杂度分析
     * 时间复杂度：分析回溯问题的时间复杂度，有一个通用公式：路径长度×搜索树的叶子数。对于本题，它等于 O(n⋅C(2n,n))。但由于左右括号的约束，实际上没有这么多叶子，根据 Catalan 数，只有
     * C(2n,n) / n+1 个叶子节点，所以实际的时间复杂度为 O(C(2n,n))。
     * 空间复杂度：O(n)。返回值的空间不计入。
     */
    private int n;
    private char[] path;
    private final List<String> ans = new ArrayList<>();

    public List<String> generateParenthesis2(int n) {
        this.n = n;
        path = new char[n * 2];
        dfs(0, 0);
        return ans;
    }

    private void dfs(int i, int open) {
        if (i == n * 2) {
            ans.add(new String(path));
            return;
        }

        // 可以填左括号
        if (open < n) {
            path[i] = '('; // 因为可以直接覆盖，所以不用恢复
            dfs(i + 1, open + 1);
        }

        // 可以填右括号
        if (i - open < open) {
            path[i] = ')'; // 因为可以直接覆盖，所以不用恢复
            dfs(i + 1, open); // 因为左括号没有添加，所以open保持
        }
    }
}
