package org.dc.backtrack;

import org.junit.Test;

import java.util.List;

public class Leetcode77Test {
    @Test
    public void testMethod1() {
        Leetcode77 leetcode77 = new Leetcode77();

        List<List<Integer>> ans = leetcode77.combine1(4, 2);

        for (List<Integer> record : ans) {
            for (Integer re : record) {
                System.out.print(re);
            }
            System.out.println();
        }
    }

    @Test
    public void testMethod2() {
        Leetcode77 leetcode77 = new Leetcode77();

        List<List<Integer>> ans = leetcode77.combine2(4, 2);

        for (List<Integer> record : ans) {
            for (Integer re : record) {
                System.out.print(re);
            }
            System.out.println();
        }
    }
}