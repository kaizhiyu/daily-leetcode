package org.dc.string;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列。
 *
 * 比如输入字符串为 "PAYPALISHIRING" 行数为 3 时，排列如下：
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："PAHNAPLSIIGYIR"。
 *
 * 请你实现这个将字符串进行指定行数变换的函数：
 * string convert(string s, int numRows);
 *
 */
public class Leetcode6 {
    /**
     * 按顺序遍历字符串 s ：
     *
     * res[i] += c： 把每个字符 c 填入对应行 si；
     * i += flag： 更新当前字符 c 对应的行索引；
     * flag = - flag： 在达到 Z 字形转折点时，执行反向。
     */
    public String convert(String s, int numRows) {
        if (s.length() <= 1 || numRows == 1) {
            return s;
        }

        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            rows.add(new StringBuilder());
        }

        int i = 0, flag = -1; // 才开始flag = -1设置是为了下面的统一处理
        for (char c : s.toCharArray()) {
            rows.get(i).append(c);
            // 此解题方案的精髓，比如 0 1 2 1 0 1 2 0 这种波浪处理方案
            if (i == 0 || i == numRows - 1) {
                flag = -flag;
            }
            i += flag;
        }

        StringBuilder res = new StringBuilder();
        for (StringBuilder row : rows) {
            res.append(row);
        }
        return res.toString();
    }
}
