package org.dc.deque;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Deque（Double-Ended Queue，双端队列）是Java中一个非常有用的数据结构，它允许我们在队列的两端进行元素的插入和删除操作。
 * Deque接口继承了Queue接口，并添加了一些额外的操作，使其在处理数据时更加灵活。
 *
 * 栈操作：Deque可以作为栈使用，通过在队列头部进行元素的插入和删除操作。例如，可以使用Deque来实现深度优先搜索（DFS）算法。
 * 双端队列操作：Deque可以在两端进行元素的插入和删除操作，这使得它在实现某些算法和数据结构时非常有用，例如在处理链表或实现特定的数据流操作时。
 * 优先级队列：可以使用Deque来实现优先级队列，通过将元素按照优先级插入到不同的位置，从而实现元素的优先级处理。
 *
 * Deque（java.util.Deque）接口代表着双向队列，意思就是可以从队列的两端增加或者删除元素，Deque就是双向Queue的意思。
 * LinkedList类是非常标准的Deque和Queue的实现，它在内部使用链接列表来建模queue或deque。
 * ArrayDeque类内部存储元素是数组，如果元素数超过数组中的空间，则分配一个新的数组，并移动所有元素，换句话说，ArrayDeque根据需要增长，即使它将元素存储在数组中。
 *
 * add()        -> 在 Deque 的尾部添加元素, 如果元素不能插入到Deque，那么add()方法将抛异常，这个和 offer()方法不一样，如果不能添加元素offer()方法将返回false。add()方法实际是继承Queue接口
 * addLast()    -> 在 Deque 的尾部添加元素，这是Deque接口与从Queue接口继承的add（）方法等效。如果Deque中不能添加元素则addLast()方法会抛异常， offerLast()方法则会返回false。
 * addFirst()   -> 在 Deque 的头部添加元素，如果Deque不能添加元素，addFirst()方法会抛异常， offerFirst()方法则返回 false。
 * offer()      -> 在 Deque 的尾部添加元素，如果元素没满则添加成功返回true，否则返回false。
 * offerFirst() -> 在 Deque 的尾部添加元素，和 offer()类似，方法名 offerLast（）只是稍微说明了元素添加到Deque的位置，如果Deque中添加元素成功则返回true否则返回false，和addLast()添加失败的抛异常不一样。
 * offerLast()  -> 在 Deque 的头部添加元素，如果添加成功返回true，否则返回false。
 * push()       -> 在 Deque 的头部添加元素，如果Deque中的元素满了，则会抛异常，这和addFirst()方法比较相似。
 *
 * remove()     -> 移除Deque的第一个元素并返回，如果 Deque 是空则抛异常，这一点和poll()返回null不一样。
 * poll()       -> 移除Deque的第一个元素，如果Deque为空则poll()返回null，这和remove()方法抛异常不一样。
 * pop()        -> 移除Deque的第一个元素，如果Deque是空则抛异常：
 *
 *
 * 参考：
 * 1）https://zhuanlan.zhihu.com/p/681537296
 * 2）https://www.jianshu.com/p/7b1f219ca9a5
 */
public class DequeTest {
    Deque deque = new LinkedList();
    // Deque deque = new ArrayDeque();

    // 从栈顶到栈底构造递增单调栈，一旦发现有转折，也就是打破
    public static int trap(int[] height) {
        int ans = 0 ;

        Deque<Integer> stack = new LinkedList<>();
        int n = height.length;

        /**
         * 从左到右遍历数组，遍历到下标 i 时，如果栈内至少有两个元素，记栈顶元素为 top，top 的下面一个元素是 left，则一定有 height[left]≥height[top]。
         * 如果 height[i]>height[top]，则得到一个可以接雨水的区域，该区域的宽度是 i−left−1，
         * 高度是 min(height[left],height[i])−height[top]，根据宽度和高度即可计算得到该区域能接的雨水量。
         *
         */
        for (int i = 0 ; i < n ; i++) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()] ) {
                int top = stack.pop();
                if (stack.isEmpty()) {
                    break;
                }

                int left = stack.peek();
                int currWidth = i - left - 1;
                int currHeight = Math.min(height[left], height[i]) - height[top];
                ans += currWidth * currHeight;
            }

            stack.push(i);
        }

        return ans;
    }

    public static void main(String[] args) {
        int[] testArray = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println(DequeTest.trap(testArray));
    }
}
