package org.dc.link;

/**
 *
 * 给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。
 *
 * 示例 1：
 * 输入：head = [1,2,3,4]
 * 输出：[2,1,4,3]
 *
 * 示例 2：
 * 输入：head = []
 * 输出：[]
 *
 * 示例 3：
 * 输入：head = [1]
 * 输出：[1]
 *
 *
 * 提示：
 * 链表中节点的数目在范围 [0, 100] 内
 * 0 <= Node.val <= 100
 *
 */
public class Leetcode24 {
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode newHead = new ListNode(0, head);
        ListNode pre = newHead;
        ListNode cur = head;
        ListNode next = cur.next;
        while (cur != null && next != null) {
            cur.next = next.next;
            next.next = cur;
            pre.next = next;

            pre = cur;
            cur = cur.next;
            if (cur != null) {
                next = cur.next;
            } else {
                next = null;
            }
        }

        return newHead.next;
    }

    /**
     *
     */
    public ListNode swapPairs2(ListNode head) {
        ListNode dummy = new ListNode(0, head); // 用哨兵节点简化代码逻辑
        ListNode node0 = dummy;
        ListNode node1 = head;
        while (node1 != null && node1.next != null) { // 至少有两个节点
            ListNode node2 = node1.next;
            ListNode node3 = node2.next;

            node0.next = node2; // 0 -> 2
            node2.next = node1; // 2 -> 1
            node1.next = node3; // 1 -> 3

            node0 = node1; // 下一轮交换，0 是 1
            node1 = node3; // 下一轮交换，1 是 3
        }
        return dummy.next; // 返回新链表的头节点
    }

    /**
     * 递归
     * 直接用 swapPairs 当作递归函数：
     *
     * 递归边界：如果 head 或者 head.next 为空，说明剩余节点不足两个，无需交换，返回 head。
     * 先交换以 node3 为头节点的链表，即递归调用 swapPairs(node3)。
     * 把 node1 指向递归返回的链表头。
     * 把 node2 指向 node1。
     * 返回 node2，作为交换后的链表头节点
     *
     * 复杂度分析
     * 时间复杂度：O(n)，其中 n 为链表长度。
     * 空间复杂度：O(n)。递归需要 O(n) 的栈空间。
     */
    public ListNode swapPairs3(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode node1 = head;
        ListNode node2 = head.next;
        ListNode node3 = node2.next;

        node1.next = swapPairs3(node3); // 1 指向递归返回的链表头
        node2.next = node1; // 2 指向 1

        return node2; // 返回交换后的链表头节点
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}
