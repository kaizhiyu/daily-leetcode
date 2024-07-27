package org.dc.backtrack;

import org.dc.string.Leetcode97;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 一条包含字母 A-Z 的消息通过以下映射进行了 编码 ：
 * "1" -> 'A'
 * "2" -> 'B'
 * ...
 * "25" -> 'Y'
 * "26" -> 'Z'
 *
 * 然而，在 解码 已编码的消息时，你意识到有许多不同的方式来解码，因为有些编码被包含在其它编码当中（"2" 和 "5" 与 "25"）。
 *
 * 例如，"11106" 可以映射为：
 * "AAJF" ，将消息分组为 (1, 1, 10, 6)
 * "KJF" ，将消息分组为 (11, 10, 6)
 * 消息不能分组为  (1, 11, 06) ，因为 "06" 不是一个合法编码（只有 "6" 是合法的）。
 * 注意，可能存在无法解码的字符串。
 *
 * 给你一个只含数字的 非空 字符串 s ，请计算并返回 解码 方法的 总数 。如果没有合法的方式解码整个字符串，返回 0。
 * 题目数据保证答案肯定是一个 32 位 的整数。
 *
 * 示例 1：
 * 输入：s = "12"
 * 输出：2
 * 解释：它可以解码为 "AB"（1 2）或者 "L"（12）。
 *
 * 示例 2：
 * 输入：s = "226"
 * 输出：3
 * 解释：它可以解码为 "BZ" (2 26), "VF" (22 6), 或者 "BBF" (2 2 6) 。
 *
 * 示例 3：
 * 输入：s = "06"
 * 输出：0
 * 解释："06" 无法映射到 "F" ，因为存在前导零（"6" 和 "06" 并不等价）。
 *
 * 提示：
 * 1 <= s.length <= 100
 * s 只包含数字，并且可能包含前导零。
 */
public class Leetcode91 {
    // 记忆化搜索
    private int[] memo;
    public int numDecodings(String s) {
        memo = new int[s.length()];
        Arrays.fill(memo, -1);

        return dfs(s, 0);
    }

    private int dfs(String s, int i) {
        if (i == s.length()) {
            return 1;
        }

        if (memo[i] != -1) return memo[i];

        memo[i] = 0;
        for (int j = i; j < s.length(); j++) {
            String str = s.substring(i, j + 1);
            int digit = Integer.parseInt(str);
            if (digit <= 0 || digit >=27) {
                break;
            }
            memo[i] += dfs(s, j + 1);
        }
        return memo[i];
    }

    /**
     * 从后往前递推，只需要看两个位置，即当前位置和前一个位置即可。
     *
     * 定义dfs(i)代表了s[0..i]的解码方法个数
     *
     * 选择1:如果s[i] != ′0′，那么dfs(i)+=dfs(i−1)。
     * 选择2，如果无前导0，即s[i−1]!=′0′，同时s[i−1]和s[i]组成的数字num满足10<=num<=26,那么dfs(i)+=dfs(i−2)。
     * 边界条件： dfs(−1) = 1 以及 dfs(0) = s[i] != ′0′
     */

    public static void main(String[] args) {
        Leetcode91 leetcode91 = new Leetcode91();
        System.out.println(leetcode91.numDecodings("12"));
    }
}
