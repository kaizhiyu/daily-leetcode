package org.dc.link;

/**
 * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
 *
 * 示例 1：
 * 输入：head = [1,2,3,4,5], n = 2
 * 输出：[1,2,3,5]
 *
 * 示例 2：
 * 输入：head = [1], n = 1
 * 输出：[]
 *
 * 示例 3：
 * 输入：head = [1,2], n = 1
 * 输出：[1]
 *
 * 提示：
 * 链表中结点的数目为 sz
 * 1 <= sz <= 30
 * 0 <= Node.val <= 100
 * 1 <= n <= sz
 *
 * 进阶：你能尝试使用一趟扫描实现吗？
 *
 */
public class Leetcode19 {
    /**
     * 复杂度分析
     * 时间复杂度：O(m)，其中 m 为链表的长度。
     * 空间复杂度：O(1)，仅用到若干额外变量。
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 由于可能会删除链表头部，用哨兵节点简化代码
        ListNode dummy = new ListNode(0, head);
        ListNode left = dummy, right = dummy;
        while (n-- > 0) {
            right = right.next; // 右指针先向右走 n 步，！！！ 这样就可以实现，右指针在倒数第一个节点的时候，左指针在倒数第 n + 1 个节点 ！！！
        }

        while (right.next != null) {
            left = left.next;
            right = right.next; // 左右指针一起走
        }

        // 倒数第 n + 1 节点的下一个节点就是 第 n 个节点，所以第 n+1 个节点直接跳过 第 n 个节点，指向第 n-1 个节点即可
        left.next = left.next.next; // 左指针的下一个节点就是倒数第 n 个节点

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
