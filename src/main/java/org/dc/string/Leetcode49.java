package org.dc.string;

import java.util.*;

/**
 *
 * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
 * 字母异位词 是由重新排列源单词的所有字母得到的一个新单词。
 *
 * 示例 1:
 * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
 * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
 *
 * 示例 2:
 * 输入: strs = [""]
 * 输出: [[""]]
 *
 * 示例 3:
 * 输入: strs = ["a"]
 * 输出: [["a"]]
 *
 *
 * 提示：
 * 1 <= strs.length <= 104
 * 0 <= strs[i].length <= 100
 * strs[i] 仅包含小写字母
 *
 */
public class Leetcode49 {
    /**
     * 把每种字母出现次数都相同的字符串分到同一组。
     * 例如 aab,aba,baa 可以分到同一组，但 abb 不行。
     *
     * 思路
     * 注意到，如果把 aab,aba,baa 按照字母从小到大排序，我们可以得到同一个字符串 aab。
     * 而对于每种字母出现次数不同于 aab 的字符串，例如 abb 和 bab，排序后为 abb，不等于 aab。
     * 所以当且仅当两个字符串排序后一样，这两个字符串才能分到同一组。
     * 根据这一点，我们可以用哈希表来分组，把排序后的字符串当作 key，原字符串组成的列表（即答案）当作 value。
     * 最后把所有 value 加到一个列表中返回。
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            char[] s = str.toCharArray();
            Arrays.sort(s);
            // 排完序的作为key放在散列中
            map.computeIfAbsent(new String(s), k -> new ArrayList<>()).add(str);
        }

        return new ArrayList<>(map.values());
    }
}
