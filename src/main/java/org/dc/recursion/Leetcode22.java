package org.dc.recursion;

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
 *
 * 提示：
 * 1 <= n <= 8
 *
 */
public class Leetcode22 {
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<String>();
        backTrack(ans, new StringBuilder(), 0, 0, n);
        return ans;
    }

    /**
     * 如果左括号数量不大于 n，我们可以放一个左括号。如果右括号数量小于左括号的数量，我们可以放一个右括号。
     *
     * @param ans
     * @param cur
     * @param open
     * @param close
     * @param max
     */
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
}
