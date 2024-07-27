package org.dc.link;

/**
 * 给你两个 非空 链表来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储一位数字。将这两数相加会返回一个新的链表。
 * 你可以假设除了数字 0 之外，这两个数字都不会以零开头。
 *
 * 示例1：
 * 输入：l1 = [7,2,4,3], l2 = [5,6,4]
 * 输出：[7,8,0,7]
 *
 * 示例2：
 * 输入：l1 = [2,4,3], l2 = [5,6,4]
 * 输出：[8,0,7]
 *
 * 示例3：
 * 输入：l1 = [0], l2 = [0]
 * 输出：[0]
 *
 * 提示：
 * 链表的长度范围为 [1, 100]
 * 0 <= node.val <= 9
 * 输入数据保证链表代表的数字无前导 0
 */
public class Leetcode445 {
    /**
     * 迭代
     *
     * 复杂度分析
     * 时间复杂度：O(n)，其中 n 为 l1 长度和 l2 长度的最大值。
     * 空间复杂度：O(1)。返回值不计入。
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        l1 = reverse(l1);
        l2 = reverse(l2);

        ListNode l3 = addTwo(l1, l2);
        return reverse(l3);
    }

    private ListNode reverse(ListNode node) {
        ListNode pre = null, cur = node;
        while (cur != null) {
            ListNode nxt = cur.next;
            cur.next = pre;

            pre = cur;
            cur = nxt;
        }
        return pre;
    }

    private ListNode addTwo(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode();
        ListNode cur = dummy;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            if (l1 != null) carry += l1.val;
            if (l2 != null) carry += l2.val;

            cur = cur.next = new ListNode(carry % 10);
            carry = carry / 10;

            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }

        return dummy.next;
    }

    /**
     * 递归
     * 反转链表 l1。
     * 反转链表 l2。
     * 调用 2. 两数相加 的代码，得到链表 l3。
     * 反转链表 l3，返回 l3 作为答案。
     *
     * 复杂度分析
     * 时间复杂度：O(n)，其中 n 为 l1 长度和 l2 长度的最大值。
     * 空间复杂度：O(n)。递归需要 O(n) 的栈空间。
     */
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        l1 = reverseList(l1);
        l2 = reverseList(l2); // l1 和 l2 反转后，就变成【2. 两数相加】了
        ListNode l3 = addTwo(l1, l2, 0);
        return reverseList(l3);
    }

    private ListNode reverseList(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode newHead = reverseList(head.next);
        head.next.next = head; // 把下一个节点指向自己
        head.next = null; // 断开指向下一个节点的连接，保证最终链表的末尾节点的 next 是空节点
        return newHead;
    }

    // l1 和 l2 为当前遍历的节点，carry 为进位
    private ListNode addTwo(ListNode l1, ListNode l2, int carry) {
        if (l1 == null && l2 == null) // 递归边界：l1 和 l2 都是空节点
            return carry != 0 ? new ListNode(carry) : null; // 如果进位了，就额外创建一个节点

        if (l1 == null) { // 如果 l1 是空的，那么此时 l2 一定不是空节点
            l1 = l2;
            l2 = null; // 交换 l1 与 l2，保证 l1 非空，从而简化代码
        }

        carry += l1.val + (l2 != null ? l2.val : 0); // 节点值和进位加在一起
        l1.val = carry % 10; // 每个节点保存一个数位
        l1.next = addTwo(l1.next, (l2 != null ? l2.next : null), carry / 10); // 进位
        return l1;
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}
