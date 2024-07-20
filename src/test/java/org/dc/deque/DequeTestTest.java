package org.dc.deque;

import org.dc.utils.CollectPrint;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class DequeTestTest {
    @Test
    public void testDequeAddLastAndRemoveLast() {
        Deque<Integer> deque = new ArrayDeque<>();

        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addLast(4);
        deque.addLast(5);
        deque.addLast(6);
        deque.addLast(7);
        CollectPrint.printDeque(deque);

        deque.removeLast();
        CollectPrint.printDeque(deque);
    }

    @Test
    public void testDequePollAndPop() {
        Deque<Integer> deque = new LinkedList<>();

//        deque.pop();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addLast(4);
        deque.addLast(5);
        deque.addLast(6);
        deque.addLast(7);
        CollectPrint.printDeque(deque);

        deque.pop();
        CollectPrint.printDeque(deque);

        deque.poll();
        CollectPrint.printDeque(deque);

        deque.pollLast();
        CollectPrint.printDeque(deque);

        deque.offerLast(8);
        CollectPrint.printDeque(deque);

        deque.push(9);
        CollectPrint.printDeque(deque);
    }
}