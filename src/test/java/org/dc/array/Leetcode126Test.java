package org.dc.array;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Leetcode126Test {
    @Test
    public void testLeetcode126() {
        Leetcode126 leetcode126 = new Leetcode126();

        String beginWord = "hit";
        String endWord = "cog";
        // ["hot","dot","dog","lot","log","cog"]
        List<String> wordList = new ArrayList<String>(){{
            add("hot");
            add("dot");
            add("dog");
            add("lot");
            add("log");
            add("cog");
        }};

        List<List<String>> ans = leetcode126.findLadders(beginWord, endWord, wordList);
        for (List<String> as : ans) {
            for (String s : as) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
    }
}