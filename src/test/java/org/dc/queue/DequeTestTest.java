package org.dc.queue;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;

public class DequeTestTest {
    @Test
    public void testDequeAddLastAndRemoveLast() {
        Deque<Integer> deque = new ArrayDeque<>();

        deque.addLast(1);
        printDeque(deque);

        deque.addLast(2);
        deque.addLast(3);
        deque.addLast(4);
        deque.addLast(5);
        deque.addLast(6);
        deque.addLast(7);
        printDeque(deque);

        deque.removeLast();
        printDeque(deque);
    }


    public static <T> void printDeque(Deque<T> deque) {
        for (T element : deque) {
            System.out.println(element);
        }
    }

}