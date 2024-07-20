package org.dc.backtrack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 *
 * 示例 1：
 * 输入：digits = "23"
 * 输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
 *
 * 示例 2：
 * 输入：digits = ""
 * 输出：[]
 *
 * 示例 3：
 * 输入：digits = "2"
 * 输出：["a","b","c"]
 *
 *
 * 提示：
 * 0 <= digits.length <= 4
 * digits[i] 是范围 ['2', '9'] 的一个数字。
 *
 */
public class Leetcode17 {
    /**
     *
     * 方法一
     *
     */
    public List<String> letterCombinations1(String digits) {
        List<String> res = new ArrayList<>();
        if (digits == null || digits.length() < 1) {
            return res;
        }

        Map<Character, String> dic = new HashMap<Character, String>() {{
            put('2', "abc");
            put('3', "def");
            put('4', "ghi");
            put('5', "jkl");
            put('6', "mno");
            put('7', "pqrs");
            put('8', "tuv");
            put('9', "wxyz");
        }};

        backTrack(res, dic, digits, 0, new StringBuffer());
        return res;
    }

    public void backTrack(List<String> res, Map<Character, String> dic, String digits, int index, StringBuffer combination) {
        if (index == digits.length()) {
            res.add(combination.toString());
            return;
        }

        char digit = digits.charAt(index);
        String letters = dic.get(digit);
        for (int i = 0; i < letters.length(); i++) {
            combination.append(letters.charAt(i));
            backTrack(res, dic, digits, index + 1, combination);
            combination.deleteCharAt(index);
        }
    }

    /**
     * 方法二
     *
     * 复杂度分析
     * 时间复杂度：O(n4n)，其中 n 为 digits 的长度。最坏情况下每次需要枚举 4 个字母，递归次数为一个满四叉树的节点个数，
     * 那么一共会递归 O(4n) 次（等比数列和），再算上加入答案时需要 O(n) 的时间，所以时间复杂度为 O(n4n)。
     * 空间复杂度：O(n)。返回值的空间不计。
     */
    private static final String[] MAPPING = new String[]{"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    private final List<String> ans = new ArrayList<>();
    private char[] digits, path;

    public List<String> letterCombinations(String digits) {
        int n = digits.length();
        if (n == 0) {
            return new ArrayList<>();
        }
        this.digits = digits.toCharArray();
        path = new char[n]; // 本题 path 长度固定为 n
        dfs(0);
        return ans;
    }

    private void dfs(int i) {
        // 结束条件
        if (i == digits.length) {
            ans.add(new String(path));
            return;
        }

        // 第 i 部分 直接+1变成 第 i+1 部分
        for (char c : MAPPING[digits[i] - '0'].toCharArray()) {
            path[i] = c; // 直接覆盖
            dfs(i + 1);
        }
    }
}
