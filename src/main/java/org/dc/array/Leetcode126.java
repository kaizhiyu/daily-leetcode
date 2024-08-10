package org.dc.array;

import java.util.*;

/**
 * 按字典 wordList 完成从单词 beginWord 到单词 endWord 转化，一个表示此过程的 转换序列 是形式上像 beginWord -> s1 -> s2 -> ... -> sk 这样的单词序列，并满足：
 * 每对相邻的单词之间仅有单个字母不同。
 * 转换过程中的每个单词 si（1 <= i <= k）必须是字典 wordList 中的单词。注意，beginWord 不必是字典 wordList 中的单词。
 * sk == endWord
 * 给你两个单词 beginWord 和 endWord ，以及一个字典 wordList 。请你找出并返回所有从 beginWord 到 endWord 的最短转换序列，如果不存在这样的转换序列，返回一个空列表。
 * 每个序列都应该以单词列表 [beginWord, s1, s2, ..., sk] 的形式返回。
 *
 * 示例 1：
 * 输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
 * 输出：[["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
 * 解释：存在 2 种最短的转换序列：
 * "hit" -> "hot" -> "dot" -> "dog" -> "cog"
 * "hit" -> "hot" -> "lot" -> "log" -> "cog"
 *
 * 示例 2：
 * 输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
 * 输出：[]
 * 解释：endWord "cog" 不在字典 wordList 中，所以不存在符合要求的转换序列。
 *
 * 提示：
 * 1 <= beginWord.length <= 5
 * endWord.length == beginWord.length
 * 1 <= wordList.length <= 500
 * wordList[i].length == beginWord.length
 * beginWord、endWord 和 wordList[i] 由小写英文字母组成
 * beginWord != endWord
 * wordList 中的所有单词 互不相同
 *
 */
public class Leetcode126 {
    /**
     * 无权图，每个顶点之间的权重视为 1。题目要我们找 最短转换序列，提示我们需要使用 广度优先遍历。广度优先遍历就是用于找无权图的最短路径。
     *
     * 与绝大多数使用广度优先遍历，只要求我们返回最短路径是多少的问题（本题的前置问题 127. 单词接龙 ）相比，本题要求返回 所有 从 beginWord 到 endWord 的最短转换序列，
     * 提示我们需要使用搜索算法（回溯算法、深度优先遍历）完成。
     *
     * 难点和注意事项
     * 需要注意的是，由于要找最短路径，连接 dot 与 lot 之间的边就不可以被记录下来，同理连接 dog 与 log 之间的边也不可以被记录。这是因为经过它们的边一定不会是最短路径。
     * 由于位于广度优先遍历同一层的单词如果它们之间有边连接，不可以被记录下来。因此需要一个哈希表记录遍历到的单词在第几层。
     *
     * 在广度优先遍历的时候，我们需要记录：从当前的单词 currWord 只变化了一个字符以后，
     * 且又在单词字典中的单词 nextWord 之间的单向关系（虽然实际上无向图，但是广度优先遍历是有方向的，我们解决这个问题可以只看成有向图），
     * 记为 from，它是一个映射关系：键是单词，值是广度优先遍历的时候从哪些单词可以遍历到「键」所表示的单词，使用哈希表来保存。
     */
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) {
            return res;
        }
        dict.remove(beginWord);

        Map<String, Integer> steps = new HashMap<>();
        steps.put(beginWord, 0);
        Map<String, Set<String>> from = new HashMap<>();
        boolean found = bfs(beginWord, endWord, dict, steps, from);

        if (found) {
            Deque<String> path = new ArrayDeque<>();
            path.add(endWord);
            dfs(from, beginWord, endWord, path, res);
        }
        return res;
    }

    private boolean bfs(String beginWord, String endWord, Set<String> dict, Map<String, Integer> steps, Map<String, Set<String>> from) {
        int step = 0;
        boolean found = false;

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        while (!queue.isEmpty()) {
            step++;

            // 1.获取这一层的所有节点，开始扩散处理
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String currWord = queue.poll();
                char[] charArray = currWord.toCharArray();
                for (int j = 0; j < charArray.length; j++) {
                    // 2.调整当前位置的字符
                    char origin = charArray[j];
                    for (char c = 'a'; c <= 'z'; c++) {
                        charArray[j] = c;
                        String nextWord = new String(charArray);

                        // 3.如果已经存在，且在一层，比如从dog(3) -> cog(4) || log(3) -> cog(4) 这两条路到 cog 的时候，需要记录 cog -> [dog, log]
                        if (steps.containsKey(nextWord) && steps.get(nextWord) == step) {
                            from.get(nextWord).add(currWord);
                        }

                        // 4.判断nextWord是否是允许的字符，此步放在第3步的原因是当 dog(3) -> cog(4) 执行时候，cog已经从 dict 列表中删除，
                        // 如果此步放在3前，则 log(3) -> cog(4) 就不会被记录
                        if (!dict.contains(nextWord)) {
                            continue;
                        }

                        // 5.记录处理
                        dict.remove(nextWord);
                        queue.offer(nextWord);
                        from.putIfAbsent(nextWord, new HashSet<>());
                        from.get(nextWord).add(currWord);
                        steps.put(nextWord, step);

                        // 6.最后一步判断
                        if (nextWord.equals(endWord)) {
                            found = true;
                        }
                    }
                    // 7.恢复当前位置的字符
                    charArray[j] = origin;
                }
            }
            // 8.只要在一层发现，下面一层就没必要继续了
            if (found) {
                break;
            }
        }

        return found;
    }

    private void dfs(Map<String, Set<String>> from, String beginWord, String cur, Deque<String> path, List<List<String>> res) {
        if (cur.equals(beginWord)) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (String precursor : from.get(cur)) {
            path.addFirst(precursor);
            dfs(from, beginWord, precursor, path, res);
            path.removeFirst();
        }
    }
}
