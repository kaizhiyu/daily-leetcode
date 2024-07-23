package org.dc.string;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StringMethodTest {
    @Test
    public void testSubstring() {
        String str = "0123456";
        System.out.println(str.substring(0, 1));
        System.out.println(str.substring(1, 1));
        System.out.println(str.substring(1, 2));
    }

    @Test
    public void testJoin() {
        List<String> list = new ArrayList<>();
        list.add("1234");
        list.add("4321");
        list.add("6789");

        System.out.println(String.join("-", list));
    }
}