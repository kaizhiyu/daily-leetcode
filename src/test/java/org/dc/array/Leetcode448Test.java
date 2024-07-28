package org.dc.array;

import org.junit.Test;

import java.util.List;

public class Leetcode448Test {
    @Test
    public void testLeetcode448() {
        Leetcode448 leetcode448 = new Leetcode448();
        List<Integer> list = leetcode448.findDisappearedNumbers(new int[]{4,3,2,7,8,2,3,1});
        System.out.println(list);
    }
}