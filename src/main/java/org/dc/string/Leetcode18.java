package org.dc.string;

/**
 *
 * 给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串的第一个匹配项的下标（下标从 0 开始）。如果 needle 不是 haystack 的一部分，则返回  -1 。
 *
 * 示例 1：
 * 输入：haystack = "sadbutsad", needle = "sad"
 * 输出：0
 * 解释："sad" 在下标 0 和 6 处匹配。
 * 第一个匹配项的下标是 0 ，所以返回 0 。
 *
 * 示例 2：
 * 输入：haystack = "leetcode", needle = "leeto"
 * 输出：-1
 * 解释："leeto" 没有在 "leetcode" 中出现，所以返回 -1 。
 *
 *
 * 提示：
 * 1 <= haystack.length, needle.length <= 104
 * haystack 和 needle 仅由小写英文字符组成
 *
 */
public class Leetcode18 {
    public int strStr1(String haystack, String needle) {
        int hl = haystack.length(), nl = needle.length();

        for (int i = 0; i + nl <= hl; i++) {
            boolean flag = true;
            for (int j = 0; j < nl; j++) {
                if (haystack.charAt(i + j) != needle.charAt(j)) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                return i;
            }
        }
        return -1;
    }

    public int strStr(String haystack, String needle) {

        int needleLength = needle.length();
        if (needleLength == 0) {
            return 0;
        }

        int[] next = new int[needleLength];
        // 定义好两个指针right与left
        // 在for循环中初始化指针right为1，left=0,开始计算next数组，right始终在left指针的后面
        for (int right = 1, left = 0; right < needleLength; right++) {
            // 如果不相等就让left指针回退，到0时就停止回退
            while (left > 0 && needle.charAt(left) != needle.charAt(right)) {
                left = next[left - 1];
            }

            if (needle.charAt(left) == needle.charAt(right)) {
                left++;
            }

            next[right] = left;
        }

        for (int i = 0, j = 0; i < haystack.length(); i++) {
            while (j > 0 && haystack.charAt(i) != needle.charAt(j)) {
                j = next[j - 1];
            }

            if (haystack.charAt(i)==needle.charAt(j)) {
                j++;
            }

            if (j==needleLength) {
                return i-needleLength+1;
            }
        }

        return -1;
    }
}
