package org.dc.string;

/**
 *
 * 「外观数列」是一个数位字符串序列，由递归公式定义：
 *
 * countAndSay(1) = "1"
 * countAndSay(n) 是 countAndSay(n-1) 的行程长度编码。
 *
 * 行程长度编码（RLE）是一种字符串压缩方法，其工作原理是通过将连续相同字符（重复两次或更多次）替换为字符重复次数（运行长度）和字符的串联。例如，要压缩字符串 "3322251" ，
 * 我们将 "33" 用 "23" 替换，将 "222" 用 "32" 替换，将 "5" 用 "15" 替换并将 "1" 用 "11" 替换。因此压缩后字符串变为 "23321511"。
 * 给定一个整数 n ，返回 外观数列 的第 n 个元素。
 *
 * 示例 1：
 * 输入：n = 4
 * 输出："1211"
 * 解释：
 * countAndSay(1) = "1"
 * countAndSay(2) = "1" 的行程长度编码 = "11"
 * countAndSay(3) = "11" 的行程长度编码 = "21"
 * countAndSay(4) = "21" 的行程长度编码 = "1211"
 *
 * 示例 2：
 * 输入：n = 1
 * 输出："1"
 * 解释：
 * 这是基本情况。
 *
 * 提示：
 * 1 <= n <= 30
 *
 */
public class Leetcode38 {
    /**
     * 本质上只是依次统计字符串中连续相同字符的个数。
     * 例如字符串 1112234445666 我们依次统计连续相同字符的个数为: 3 个连续的字符 1, 2 个连续的 2，1 个连续的字符 3，3 个连续的字符 4，1 个连续的字符 5，3 个连续的字符 6。
     * 我们对其进行整理为 (3)1(2)2(1)3(3)4(1)5(3)6，我们将括号内的数字也转换为字符串为 312213341536。
     * 题目中给定的递归公式定义的数字字符串序列如下：
     * countAndSay(1)="1"；
     * countAndSay(n) 是对 countAndSay(n-1) 的描述，然后转换成另一个数字字符串。
     *
     * 我们定义字符串 Si表示 countAndSay(i)，我们如果要求得 Sn，则我们需先求出 Sn−1，然后按照上述描述的方法生成，
     * 即从左到右依次扫描字符串 Sn−1中连续相同的字符的最大数目，然后将字符的统计数目转化为数字字符串再连接上对应的字符。我们已知 S1为 "1"，我们按照上述方法依次生成 S2,S3,…,Sn−1,Sn即可。
     */
    public String countAndSay(int n) {
        String str = "1";
        for (int i = 1; i < n; i++) {
            StringBuilder sb = new StringBuilder();
            int start = 0;
            int pos = 0;

            while (pos < str.length()) {
                // 找到相同的字符
                while (pos < str.length() && str.charAt(pos) == str.charAt(start)) {
                    pos++;
                }
                sb.append(Integer.toString(pos - start)).append(str.charAt(start));
                start = pos;
            }
            str = sb.toString();
        }

        return str;
    }
}
