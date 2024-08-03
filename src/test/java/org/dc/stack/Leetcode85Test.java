package org.dc.stack;

import org.junit.Test;

public class Leetcode85Test {
    @Test
    public void testBaseMethod() {
        Leetcode85 leetcode85 = new Leetcode85();

        char[][] matrix = new char[][]{
                {'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'}
        };
        System.out.println(leetcode85.maximalRectangle(matrix));
    }
}