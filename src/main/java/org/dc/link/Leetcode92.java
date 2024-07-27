package org.dc.link;

/**
 * 给你单链表的头指针 head 和两个整数 left 和 right ，其中 left <= right 。请你反转从位置 left 到位置 right 的链表节点，返回 反转后的链表 。
 *
 * 示例 1：
 * 输入：head = [1,2,3,4,5], left = 2, right = 4
 * 输出：[1,4,3,2,5]
 *
 * 示例 2：
 * 输入：head = [5], left = 1, right = 1
 * 输出：[5]
 *
 * 提示：
 * 链表中节点数目为 n
 * 1 <= n <= 500
 * -500 <= Node.val <= 500
 * 1 <= left <= right <= n
 *
 * 进阶： 你可以使用一趟扫描完成反转吗？
 *
 */
public class Leetcode92 {
    /**
     * 1）因为首节点可能也要翻转，所以需要额外创建dummy节点
     * 2）根据left找到左边节点，需要保留 p0 节点后面翻转后的连接作准备
     * 3）[left] -- [right-left+1] 中间的节点要做到翻转
     * 4）基于第2步保留的 p0 做整体的处理
     *
     * 复杂度分析
     *  * 时间复杂度：O(n)，其中 n 为链表节点个数。
     *  * 空间复杂度：O(1)，仅用到若干额外变量。
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0, head), p0 = dummy;
        for (int i = 0; i < left - 1; i++) {
            p0 = p0.next;
        }

        ListNode pre = null, cur = p0.next;
        for (int i = 0; i < right - left + 1; i++) {
            ListNode nxt = cur.next;
            cur.next = pre;
            pre = cur;
            cur = nxt;
        }

        p0.next.next = cur;
        p0.next = pre;
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
