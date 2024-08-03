package org.dc.dp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 给你一个字符串 s 和一个字符串列表 wordDict 作为字典。如果可以利用字典中出现的一个或多个单词拼接出 s 则返回 true。
 * 注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。
 *
 * 示例 1：
 * 输入: s = "leetcode", wordDict = ["leet", "code"]
 * 输出: true
 * 解释: 返回 true 因为 "leetcode" 可以由 "leet" 和 "code" 拼接成。
 *
 * 示例 2：
 * 输入: s = "applepenapple", wordDict = ["apple", "pen"]
 * 输出: true
 * 解释: 返回 true 因为 "applepenapple" 可以由 "apple" "pen" "apple" 拼接成。
 *      注意，你可以重复使用字典中的单词。
 *
 * 示例 3：
 * 输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 * 输出: false
 *
 * 提示：
 * 1 <= s.length <= 300
 * 1 <= wordDict.length <= 1000
 * 1 <= wordDict[i].length <= 20
 * s 和 wordDict[i] 仅由小写英文字母组成
 * wordDict 中的所有字符串 互不相同
 */
public class Leetcode139 {
    /**
     * 记忆化搜索
     * 对于每一个位置 i，只有两种选择，拆分 or 不拆分。那就把每个可能的位置都拆开试试。
     * 枚举每个位置 i 作为起点, 在 i 之后的每个位置 j 都试图拆分，如果 s[i, j) 和 s[j, n)都在字典里面，那么s[i, n)是可以拆分的。
     *
     *
     * 参考：（1）https://leetcode.cn/problems/word-break/solutions/740456/139-bao-li-di-gui-ji-yi-hua-di-gui-dong-lf3vu/
     * （2）https://leetcode.cn/problems/word-break/solutions/2855628/ji-yi-hua-sou-suo-di-tui-by-yi-cheng-8i-7ffp/
     */
    Boolean[] memo;
    Set<String> set;
    public boolean wordBreak(String s, List<String> wordDict) {
        set = new HashSet<>(wordDict);
        memo = new Boolean[s.length() + 1];
        return dfs(s, 0);
    }

    private boolean dfs(String s, int start) {
        if (start == s.length()) {
            return true;
        }

        if (memo[start] != null) {
            return memo[start];
        }

        for (int end = start + 1; end <= s.length(); end++) {
            if (set.contains(s.substring(start, end))
                    && dfs(s, end)) {
                memo[start] = true;
                return true;
            }
        }

        memo[start] = false;
        return false;
    }

    /**
     * dp
     */
    public boolean wordBreak2(String s, List<String> wordDict) {
        Set<String> wordDictSet = new HashSet<>(wordDict);

        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[s.length()];
    }
}
