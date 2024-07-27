package org.dc.link;

/**
 *
 * 给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。
 * k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 * 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
 *
 * 示例 1：
 * 输入：head = [1,2,3,4,5], k = 2
 * 输出：[2,1,4,3,5]
 *
 * 示例 2：
 * 输入：head = [1,2,3,4,5], k = 3
 * 输出：[3,2,1,4,5]
 *
 * 提示：
 * 链表中的节点数目为 n
 * 1 <= k <= n <= 5000
 * 0 <= Node.val <= 1000
 *
 * 进阶：你可以设计一个只用 O(1) 额外内存空间的算法解决此问题吗？
 *
 */
public class Leetcode25 {
    /**
     * 1）统计节点
     * 2）分批处理，每个批次k个节点翻转，但是这里面就有几个重要的点：
     *  每次处理前需要记录节点，以便于翻转后该节点 p0 的 next 的值；
     *  pre、cur、next 方便于节点的翻转
     *
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        int n = 0;
        for (ListNode cur = head; cur != null; cur = cur.next) {
            ++n; // 统计节点个数
        }

        ListNode dummy = new ListNode(0, head), p0 = dummy;
        ListNode pre = null, cur = head;

        for (; n >= k; n -= k) {
            for (int i = 0; i < k; i++) {
                ListNode nxt = cur.next;
                cur.next = pre;
                pre = cur;
                cur = nxt;
            }

            ListNode temp = p0.next;
            p0.next.next = cur;
            p0.next = pre;
            p0 = temp;
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
