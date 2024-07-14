package org.dc.string;

import org.junit.Test;

public class StringBuilderTestTest {
    @Test
    public void testStringBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append("1212");

        sb.deleteCharAt(3);
        System.out.println(sb);

        sb.deleteCharAt(2);
        System.out.println(sb);

        sb.deleteCharAt(1);
        System.out.println(sb);

        sb.deleteCharAt(0);
        System.out.println(sb);
    }
}