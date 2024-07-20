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

    @Test
    public void testInsertMethod() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.insert(0, "hello word");
        stringBuilder.insert(0, "/");

        stringBuilder.insert(0, "ni hao");
        stringBuilder.insert(0, "/");

        System.out.println(stringBuilder);
    }
}