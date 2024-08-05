package org.dc.string;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个单词数组 words 和一个长度 maxWidth ，重新排版单词，使其成为每行恰好有 maxWidth 个字符，且左右两端对齐的文本。
 * 你应该使用 “贪心算法” 来放置给定的单词；也就是说，尽可能多地往每行中放置单词。必要时可用空格 ' ' 填充，使得每行恰好有 maxWidth 个字符。
 * 要求尽可能均匀分配单词间的空格数量。如果某一行单词间的空格不能均匀分配，则左侧放置的空格数要多于右侧的空格数。
 * 文本的最后一行应为左对齐，且单词之间不插入额外的空格。
 *
 * 注意:
 * 单词是指由非空格字符组成的字符序列。
 * 每个单词的长度大于 0，小于等于 maxWidth。
 * 输入单词数组 words 至少包含一个单词。
 *
 * 示例 1:
 * 输入: words = ["This", "is", "an", "example", "of", "text", "justification."], maxWidth = 16
 * 输出:
 * [
 *    "This    is    an",
 *    "example  of text",
 *    "justification.  "
 * ]
 *
 * 示例 2:
 * 输入:words = ["What","must","be","acknowledgment","shall","be"], maxWidth = 16
 * 输出:
 * [
 *   "What   must   be",
 *   "acknowledgment  ",
 *   "shall be        "
 * ]
 * 解释: 注意最后一行的格式应为 "shall be    " 而不是 "shall     be",
 *      因为最后一行应为左对齐，而不是左右两端对齐。
 *      第二行同样为左对齐，这是因为这行只包含一个单词。
 *
 * 示例 3:
 * 输入:words = ["Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"]，maxWidth = 20
 * 输出:
 * [
 *   "Science  is  what we",
 *   "understand      well",
 *   "enough to explain to",
 *   "a  computer.  Art is",
 *   "everything  else  we",
 *   "do                  "
 * ]
 *
 * 提示:
 * 1 <= words.length <= 300
 * 1 <= words[i].length <= 20
 * words[i] 由小写英文字母和符号组成
 * 1 <= maxWidth <= 100
 * words[i].length <= maxWidth
 *
 */
public class Leetcode68 {
    List<String> ans = new ArrayList<>(); // 本题答案列表
    int[] lens; // 记录每个单词长度，方便后续补齐空格操作
    int maxRowLen; // 替代 maxWidth，减少函数传参

    public List<String> fullJustify(String[] words, int maxWidth) {
        maxRowLen = maxWidth;
        int n = words.length;

        // 1.记录每个单词的长度
        lens = new int[n];
        for (int i = 0; i < n; i++) {
            lens[i] = words[i].length();
        }

        // 2.单词分组，确定哪些单词在同一行
        int rowLen = 0;
        for (int i = 0; i < n; i++) {
            int start = i;
            while (i < n && rowLen + lens[i] <= maxRowLen) {
                rowLen += (lens[i] + 1); // 因为每一个单词后面必然会跟着一个空格，
                i++;
            }
            // 因为多加1
            int end = --i;
            addAnd(words, start, end);
            rowLen = 0;
        }

        return ans;
    }

    private void addAnd(String[] words, int start, int end) {
        StringBuilder sb = new StringBuilder();
        // 情况一：一行只有一个单词，直接空格补齐
        if (start == end) {
            sb.append(words[start]);
            int space = maxRowLen - lens[start];
            for (int j = 1; j <= space; j++) {
                sb.append(" ");
            }
            ans.add(sb.toString());
            return;
        }

        // 情况二： 如果是最后一行，左对齐，即单词间一个空格，最后空格补齐
        if (end == words.length - 1) {
            int space = maxRowLen;
            for (int i = start; i < end; i++) {
                sb.append(words[i]).append(" ");
                space -= (lens[i] + 1);
            }

            sb.append(words[end]);
            space -= lens[end];
            for (int j = 1; j <= space; j++) {
                sb.append(" ");
            }
            ans.add(sb.toString());
            return;
        }

        int spaceAll = maxRowLen;
        for (int i = start; i <= end; i++) {
            spaceAll -= lens[i];
        }
        // 平均空格数
        int spaceMean = spaceAll / (end - start);

        // 剩余空格数
        int spaceLast = spaceAll - spaceMean * (end - start);

        for (int i = start; i < end; i++) {
            sb.append(words[i]);
            // 在每个单词后面插入平均空格数
            for (int j = 1; j <= spaceMean; j++) {
                sb.append(" ");
            }
            // 如果有剩余空格数，插一个
            if (spaceLast > 0) {
                sb.append(" ");
                spaceLast--;
            }
        }

        sb.append(words[end]);
        ans.add(sb.toString());
    }
}
