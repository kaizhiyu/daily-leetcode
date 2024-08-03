package org.dc.link;

/**
 * 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
 *
 * 示例 1：
 * 输入：head = [4,2,1,3]
 * 输出：[1,2,3,4]
 *
 * 示例 2：
 * 输入：head = [-1,5,3,4,0]
 * 输出：[-1,0,3,4,5]
 *
 *  示例 3：
 * 输入：head = []
 * 输出：[]
 *
 * 提示：
 * 链表中节点的数目在范围 [0, 5 * 104] 内
 * -105 <= Node.val <= 105
 * 进阶：你可以在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序吗？
 *
 */
public class Leetcode148 {
    /**
     * 使用leetcode147插入排序解决问题
     *
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummy = new ListNode(-1, head);

        // next负责指向新元素，last负责执行新元素的前一个元素，
        // 循环不变量 ： last之前的已经排序；next往后待排序
        ListNode nxt = head.next;
        ListNode last = head;
        ListNode tempHead = dummy;

        while (nxt != null) {
            if (last.val <= nxt.val) {
                nxt = nxt.next;
                last = last.next;
                continue;
            }

            tempHead = dummy;
            while (tempHead.next.val <= nxt.val) {
                tempHead = tempHead.next;
            }

            last.next = nxt.next;
            nxt.next = tempHead.next;
            tempHead.next = nxt;
            nxt = last.next;
        }

        return dummy.next;
    }

    /**
     * 归并排序
     * 找到一个链表的中间节点的方法；合并两个已排好序的链表为一个新的有序链表
     */
    public ListNode sortList2(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode slow = head;      // 慢指针
        ListNode fast = head.next; // 快指针

        // 快慢指针找到链表中点
        while (fast != null && fast.next != null) {
            slow = slow.next; // 慢指针走一步
            fast = fast.next.next; // 快指针走两步
        }

        ListNode rightHead = slow.next; //链表第二部分的头节点
        slow.next = null; //cut 链表

        ListNode left = sortList2(head); //递归排序前一段链表
        ListNode right = sortList2(rightHead); //递归排序后一段链表
        return merge(left,right);
    }
    public ListNode merge(ListNode h1,ListNode h2) { //合并两个有序链表
        ListNode dummy = new ListNode(-1);
        ListNode p = dummy;
        while (h1 != null && h2 != null) {
            if (h1.val < h2.val) {
                p.next = h1;
                h1 = h1.next;
            } else {
                p.next = h2;
                h2 = h2.next;
            }
            p = p.next;
        }

        if (h1!=null)    p.next = h1;
        else if(h2!=null) p.next = h2;
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
