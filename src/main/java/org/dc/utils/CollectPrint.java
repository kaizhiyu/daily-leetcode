package org.dc.utils;

import java.util.Deque;

public class CollectPrint {
    public static <T> void printDeque(Deque<T> deque) {
        printDeque(deque, false, " ");
    }

    public static <T> void printDeque(Deque<T> deque, Boolean newLine, String splitFlag) {
        for (T element : deque) {
            if (newLine)
                System.out.println(element);
            else
                System.out.print(element + splitFlag);
        }
        System.out.println();
    }
}
