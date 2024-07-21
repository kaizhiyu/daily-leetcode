package org.dc.utils;

import java.util.Deque;
import java.util.Queue;

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

    public static <T> void printQueue(Queue<T> queue) {
        printQueue(queue, false, " ");
    }

    // 泛型方法用于打印队列中的所有元素
    public static <T> void printQueue(Queue<T> queue, Boolean newLine, String splitFlag) {
        while (!queue.isEmpty()) {
            T element = queue.poll();
            if (newLine)
                System.out.println(element);
            else
                System.out.print(element + splitFlag);
        }
        System.out.println();
    }

}
