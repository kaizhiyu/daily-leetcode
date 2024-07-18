package org.dc.array;

import org.junit.Test;

public class Leetcode74Test {
    @Test
    public void testBinarySearchFirstColumn() {
        int[][] num = {
            {1,   3,  5,  7},
            {10, 11, 16, 20},
            {23, 30, 34, 60}
        };


        int[][] num1 = {
                {1}
        };

        Leetcode74 leetcode74 = new Leetcode74();
        System.out.println(leetcode74.binarySearchFirstColumn1(num, 3));
        System.out.println(leetcode74.binarySearchFirstColumn2(num, 3));

        System.out.println(leetcode74.binarySearchFirstColumn1(num1, 1));
        System.out.println(leetcode74.binarySearchFirstColumn2(num1, 1));
    }
}