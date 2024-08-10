package org.dc.collection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MapMethod {
    public static void main(String[] args) {
//        Map<String, Set<String>> from = new HashMap<>();
//
//        from.putIfAbsent("1", new HashSet<>());

        Map<String, String> from = new HashMap<>();
        String value = from.putIfAbsent("1", "1");
        System.out.println(value);

        String v2 = from.putIfAbsent("1", "2");
        System.out.println(v2);
        System.out.println(from.get("1"));


    }
}
