package org.dc.link;

/**
 * 给你一个 非空 链表的头节点 head ，表示一个不含前导零的非负数整数。
 * 将链表 翻倍 后，返回头节点 head 。
 *
 * 示例 1：
 * 输入：head = [1,8,9]
 * 输出：[3,7,8]
 * 解释：上图中给出的链表，表示数字 189 。返回的链表表示数字 189 * 2 = 378 。
 *
 * 示例 2：
 * 输入：head = [9,9,9]
 * 输出：[1,9,9,8]
 * 解释：上图中给出的链表，表示数字 999 。返回的链表表示数字 999 * 2 = 1998 。
 *
 * 提示：
 * 链表中节点的数目在范围 [1, 104] 内
 * 0 <= Node.val <= 9
 * 生成的输入满足：链表表示一个不含前导零的数字，除了数字 0 本身。
 *
 */
public class Leetcode2816 {
    public ListNode doubleIt(ListNode head) {
        head = reverseList(head);
        ListNode res = double2(head); // 反转后，就变成【2. 两数相加】了
        return reverseList(res);
    }

    private ListNode reverseList(ListNode head) {
        ListNode pre = null, cur = head;
        while (cur != null) {
            ListNode nxt = cur.next;
            cur.next = pre;
            pre = cur;
            cur = nxt;
        }
        return pre;
    }

    private ListNode double2(ListNode l1) {
        ListNode dummy = new ListNode();
        ListNode cur = dummy;
        int carry = 0;
        while (l1 != null) {
            carry += l1.val * 2;
            cur = cur.next = new ListNode(carry % 10);
            carry = carry / 10;
            l1 = l1.next;
        }

        if (carry != 0) {
            cur.next = new ListNode(carry);
        }

        return dummy.next;
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}
