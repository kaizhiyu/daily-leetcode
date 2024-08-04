package org.dc.backtrack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Leetcode140Test {
    @Test
    public void testLeetcode140_1() {
        Leetcode140 leetcode140 = new Leetcode140();

        String s = "catsandog";
        List<String> wordDict = new ArrayList<String>(){{
                add("cats");
                add("dog");
                add("sand");
                add("and");
                add("cat");
        }};

        List<String> ans = leetcode140.wordBreak(s, wordDict);
        System.out.println(ans.size());
    }
}