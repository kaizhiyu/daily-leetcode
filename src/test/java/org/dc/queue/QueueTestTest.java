package org.dc.queue;

import org.dc.utils.CollectPrint;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

public class QueueTestTest {
    @Test
    public void testQueueMethod() {
        Queue<Integer> queue = new LinkedList<>();

        queue.add(1);
        queue.add(2);

        System.out.println(queue.element());
        System.out.println(queue.peek());

        CollectPrint.printQueue(queue);
        System.out.println(queue.size());

        System.out.println(queue.isEmpty());
    }
}