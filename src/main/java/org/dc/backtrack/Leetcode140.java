package org.dc.backtrack;

import java.util.*;

/**
 * 给定一个字符串 s 和一个字符串字典 wordDict ，在字符串 s 中增加空格来构建一个句子，使得句子中所有的单词都在词典中。以任意顺序 返回所有这些可能的句子。
 * 注意：词典中的同一个单词可能在分段中被重复使用多次。
 *
 * 示例 1：
 * 输入:s = "catsanddog", wordDict = ["cat","cats","and","sand","dog"]
 * 输出:["cats and dog","cat sand dog"]
 *
 * 示例 2：
 * 输入:s = "pineapplepenapple", wordDict = ["apple","pen","applepen","pine","pineapple"]
 * 输出:["pine apple pen apple","pineapple pen apple","pine applepen apple"]
 * 解释: 注意你可以重复使用字典中的单词。
 *
 * 示例 3：
 * 输入:s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
 * 输出:[]
 *
 * 提示：
 * 1 <= s.length <= 20
 * 1 <= wordDict.length <= 1000
 * 1 <= wordDict[i].length <= 10
 * s 和 wordDict[i] 仅有小写英文字母组成
 * wordDict 中所有字符串都 不同
 *
 */
public class Leetcode140 {
    /**
     * 记忆化搜索
     * 参考：
     * （1）https://leetcode.cn/problems/word-break-ii/solutions/468624/shou-hua-tu-jie-dan-ci-chai-fen-ii-cong-di-gui-dao/
     * （2）https://leetcode.cn/problems/word-break-ii/solutions/468979/python3ji-yi-hua-sou-suo-tian-jia-3xing-dai-ma-1ge/
     *
     */
    public List<String> wordBreak(String s, List<String> wordDict) {
        Map<Integer, List<String>> map = new HashMap<>();
        Set<String> dict = new HashSet<>(wordDict);

        return dfs(s, 0, map, dict);
    }

    private List<String> dfs(String s, int start, Map<Integer, List<String>> map, Set<String> dict) {
        if (start == s.length()) {
            return null;
        }

        if (map.containsKey(start)) {
            return map.get(start);
        }

        List<String> cur = new ArrayList<>();
        for (int i = start + 1; i <= s.length(); i++) {
            String word = s.substring(start, i);
            if (!dict.contains(word)) {
                continue;
            }

            List<String> list = dfs(s, i, map, dict);
            if (list != null) {
                for (String sub : list) {
                    cur.add(word + ' ' + sub);
                }
            } else {
                cur.add(word);
            }
        }

        map.put(start, cur);
        return cur;
    }
}
