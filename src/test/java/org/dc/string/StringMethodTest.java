package org.dc.string;

import org.junit.Test;

public class StringMethodTest {
    @Test
    public void testSubstring() {
        String str = "0123456";
        System.out.println(str.substring(0, 1));
        System.out.println(str.substring(1, 1));
        System.out.println(str.substring(1, 2));
    }
}