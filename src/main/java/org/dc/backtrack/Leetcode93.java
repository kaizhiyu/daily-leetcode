package org.dc.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * 有效 IP 地址 正好由四个整数（每个整数位于 0 到 255 之间组成，且不能含有前导 0），整数之间用 '.' 分隔。
 * 例如："0.1.2.201" 和 "192.168.1.1" 是 有效 IP 地址，但是 "0.011.255.245"、"192.168.1.312" 和 "192.168@1.1" 是 无效 IP 地址。
 * 给定一个只包含数字的字符串 s ，用以表示一个 IP 地址，返回所有可能的有效 IP 地址，这些地址可以通过在 s 中插入 '.' 来形成。你 不能 重新排序或删除 s 中的任何数字。你可以按 任何 顺序返回答案。
 *
 * 示例 1：
 * 输入：s = "25525511135"
 * 输出：["255.255.11.135","255.255.111.35"]
 *
 * 示例 2：
 * 输入：s = "0000"
 * 输出：["0.0.0.0"]
 *
 * 示例 3：
 * 输入：s = "101023"
 * 输出：["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
 *
 * 提示：
 * 1 <= s.length <= 20
 * s 仅由数字组成
 *
 */
public class Leetcode93 {
    /**
     * 枚举每个答案
     * 该方法的重点在于认为每两个字符中间都可以插入. 意味着每次选择是否选择一段字符，不过需要实时的判断是否满足条件，从而剪枝。
     *
     */
    public List<String> restoreIpAddresses(String s) {
        List<String> ans = new ArrayList<>();
        List<String> path = new ArrayList<>();

        dfs1(s, 0, ans, path);
        return ans;
    }

    private void dfs1(String s, int i, List<String> ans, List<String> path) {
        if (s.length() == i && path.size() == 4) {
            ans.add(String.join(".", path));
            return;
        }

        for (int j = i; j < s.length(); j++) {
            String str = s.substring(i, j + 1);
            int num = Integer.parseInt(str);
            if (num > 255
                    || path.size() == 4
                    || (j > i && s.charAt(i) == '0')
                    || j - i > 4) {
                return;
            }

            path.add(str);
            dfs1(s, j + 1, ans, path);
            path.remove(path.size() - 1);
        }
    }

    /**
     * 选或者不选
     */
    public List<String> restoreIpAddresses2(String s) {
        List<String> ans = new ArrayList<>();
        List<String> path = new ArrayList<>();

        dfs2(s, 0, 0, ans, path);
        return ans;
    }

    private void dfs2(String s, int start, int i, List<String> ans, List<String> path) {
        if (s.length() == i) {
            if (path.size() == 4) {
                ans.add(String.join(".", path));
            }
            return;
        }

        String str = s.substring(start, i + 1);
        int num = Integer.parseInt(str);
        if(num > 255 || path.size() == 4 || (start < i && s.charAt(start) == '0') || i - start > 4) return;

        // 不选
        dfs2(s, start, i + 1, ans, path);

        path.add(str);
        dfs2(s, i + 1, i + 1, ans, path);
        path.remove(path.size() - 1);
    }
}
