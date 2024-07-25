package org.dc.string;

import org.junit.Test;

public class Leetcode97Test {
    @Test
    public void testIsInterleave() {
        Leetcode97 leetcode97 = new Leetcode97();

        String s1 = "aabcc";
        String s2 = "dbbca";
        String s3 = "aadbbcbcac";
        System.out.println(leetcode97.isInterleave(s1, s2, s3));
    }
}