package org.dc.backtrack;

/**
 * 使用下面描述的算法可以扰乱字符串 s 得到字符串 t ：
 * 如果字符串的长度为 1 ，算法停止
 * 如果字符串的长度 > 1 ，执行下述步骤：
 * 在一个随机下标处将字符串分割成两个非空的子字符串。即，如果已知字符串 s ，则可以将其分成两个子字符串 x 和 y ，且满足 s = x + y 。
 * 随机 决定是要「交换两个子字符串」还是要「保持这两个子字符串的顺序不变」。即，在执行这一步骤之后，s 可能是 s = x + y 或者 s = y + x 。
 * 在 x 和 y 这两个子字符串上继续从步骤 1 开始递归执行此算法。
 * 给你两个 长度相等 的字符串 s1 和 s2，判断 s2 是否是 s1 的扰乱字符串。如果是，返回 true ；否则，返回 false 。
 *
 * 示例 1：
 * 输入：s1 = "great", s2 = "rgeat"
 * 输出：true
 * 解释：s1 上可能发生的一种情形是：
 * "great" --> "gr/eat" // 在一个随机下标处分割得到两个子字符串
 * "gr/eat" --> "gr/eat" // 随机决定：「保持这两个子字符串的顺序不变」
 * "gr/eat" --> "g/r / e/at" // 在子字符串上递归执行此算法。两个子字符串分别在随机下标处进行一轮分割
 * "g/r / e/at" --> "r/g / e/at" // 随机决定：第一组「交换两个子字符串」，第二组「保持这两个子字符串的顺序不变」
 * "r/g / e/at" --> "r/g / e/ a/t" // 继续递归执行此算法，将 "at" 分割得到 "a/t"
 * "r/g / e/ a/t" --> "r/g / e/ a/t" // 随机决定：「保持这两个子字符串的顺序不变」
 * 算法终止，结果字符串和 s2 相同，都是 "rgeat"
 * 这是一种能够扰乱 s1 得到 s2 的情形，可以认为 s2 是 s1 的扰乱字符串，返回 true
 *
 * 示例 2：
 * 输入：s1 = "abcde", s2 = "caebd"
 * 输出：false
 *
 * 示例 3：
 * 输入：s1 = "a", s2 = "a"
 * 输出：true
 *
 * 提示：
 * s1.length == s2.length
 * 1 <= s1.length <= 30
 * s1 和 s2 由小写英文字母组成
 *
 */
public class Leetcode87 {
    /**
     * 为了高效的递归，需要三个变量来表示s的任意子串，t的任意子串，所以用s中的起始索引位置si和t的起始位置ti以及一个长度len来作为变量，所以需要三维数组来缓存状态。
     * 因为只有长度相同的子串才可能「转化」，所以只有一个长度变量就可以了。这个状态表示方法是本题的精髓
     */
    private int[][][] memo;
    private int n;
    private char[] sc;
    private char[] tc;

    public boolean isScramble(String s1, String s2) {
        n = s1.length();
        sc = s1.toCharArray();
        tc = s2.toCharArray();
        memo = new int[n][n][n + 1]; // -1, false, 1, true, 0 not set

        return dfs(0, 0, n);
    }

    // s = new String(sc, si, len)，原始字串s中从下标si开始，长度为len的子串
    // t = new String(tc, ti, len)，原始字串t中从下标ti开始，长度为len的子串
    private boolean dfs(int si, int ti, int len) {
        if (memo[si][ti][len] != 0) {
            return memo[si][ti][len] == 1;
        }

        if (same(si, ti, len)) {
            memo[si][ti][len] = 1;
            return true;
        }

        if (!similar(si, ti, len)) {
            memo[si][ti][len] = -1;
            return false;
        }

        // 进行分割，也就是把当前的串分割为更小的两个子串
        // 因为我们知道长度，所以就改变长度就可以了，长度从1到len-1，这样就能分割出两个非空子串了
        for (int k = 1; k < len; k++) {
            // 前半x = new String(sc, si, k)
            // 后半y = new String(sc, si + k, len - k)
            // new String(sc, si, len) = x + y

            // 如果不交换的话，那么递归看dfs(sx, tx)和dfs(sy, ty)即可
            if (dfs(si, ti, k) && dfs(si + k, ti + k, len - k)) {
                memo[si][ti][len] = 1;
                return true;
            }

            // 如果交换，需要需要递归看dfs(sx, ty)和dfs(sy, tx)，因为长度不同肯定不会相似，所以要对齐下索引以让长度相同
            if (dfs(si, ti + len - k, k) && dfs(si + k, ti, len - k)) {
                memo[si][ti][len] = 1;
                return true;
            }
        }

        memo[si][ti][len] = -1;
        return false;
    }

    private boolean same(int si, int ti, int len) {
        for (int k = 0; k < len; k++) {
            if (sc[si + k] != tc[ti + k]) {
                return false;
            }
        }
        return true;
    }

    private boolean similar(int si, int ti, int len) {
        byte[] map = new byte[26];
        for (int k = 0; k < len; k++) {
            map[sc[si + k] - 'a']++;
        }
        for (int k = 0; k < len; k++) {
            map[tc[ti + k] - 'a']--;
        }

        for (int i = 0; i < 26; i++) {
            if (map[i] != 0) {
                return false;
            }
        }
        return true;
    }
}
