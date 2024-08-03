package org.dc.link;

/**
 * 给定一个单链表的头节点  head ，其中的元素 按升序排序 ，将其转换为 平衡二叉搜索树。
 *
 * 示例 1:
 * 输入: head = [-10,-3,0,5,9]
 * 输出: [0,-3,9,-10,null,5]
 * 解释: 一个可能的答案是[0，-3,9，-10,null,5]，它表示所示的高度平衡的二叉搜索树。
 *
 * 示例 2:
 * 输入: head = []
 * 输出: []
 *
 * 提示:
 * head 中的节点数在[0, 2 * 104] 范围内
 * -105 <= Node.val <= 105
 *
 */
public class Leetcode109 {
    /**
     * 将给定的有序链表转换为二叉搜索树的第一步是确定根节点。由于我们需要构造出平衡的二叉树，因此比较直观的想法是让根节点左子树中的节点个数与右子树中的节点个数尽可能接近。
     * 这样一来，左右子树的高度也会非常接近，可以达到高度差绝对值不超过 1 的题目要求。
     * 如何找出这样的一个根节点呢？我们可以找出链表元素的中位数作为根节点的值。
     *
     * 这里对于中位数的定义为：如果链表中的元素个数为奇数，那么唯一的中间值为中位数；如果元素个数为偶数，那么唯二的中间值都可以作为中位数，而不是常规定义中二者的平均值。
     * 根据中位数的性质，链表中小于中位数的元素个数与大于中位数的元素个数要么相等，要么相差 1。此时，小于中位数的元素组成了左子树，大于中位数的元素组成了右子树，它们分别对应着有序链表中连续的一段。
     * 在这之后，我们使用分治的思想，继续递归地对左右子树进行构造，找出对应的中位数作为根节点，以此类推。
     *
     * 可以证明，这样的构造方法得到的二叉搜索树是平衡的，
     * 如果二叉搜索树的左右子树都是平衡的，并且它们的高度差不超过 1，那么该二叉搜索树就是平衡的。
     * 我们使用数学归纳法，设链表的长度为 n，对应的二叉搜索树的高度为 H(n)：
     * 这里规定只包含根节点的二叉搜索树的高度为 1。
     * 当 n=1,2 时，对应的二叉搜索树都是平衡的，并且 H(1)=1，H(2)=2；
     * 当 n=3 时，对应的二叉搜索树包含一个根节点和左右子树各一个节点，它是平衡的，并且 H(3)=2；
     *
     * 假设当 n < n0 时，对应的二叉搜索树是平衡的，并且对于任意的 1 ≤ n <n0 −1，都有 H(n+1)−H(n)≤1，那么当 n=n0时：
     * 如果 n 为奇数，设 n=2k+3，那么二叉搜索树的左右子树的大小均为 k+1，高度相等，因此二叉搜索树是平衡的，并且有 H(n)=H(k+1)+1。而 n−1=2k+2，
     * 对应的二叉搜索树的左右子树的大小为 k 和 k+1，即 H(n−1)=H(k+1)+1，因此 H(n)=H(n−1)；
     *
     * 如果 n 为偶数，设 n=2k+2，那么二叉搜索树的左右子树的大小为 k 和 k+1，根据假设，高度差 H(k+1)−H(k)≤1，
     * 因此二叉搜索树是平衡的，并且有 H(n)=H(k+1)+1。而 n−1=2k+1，对应的二叉搜索树的左右子树的大小均为 k，即 H(n−1)=H(k)+1，因此 H(n)−H(n−1)=H(k+1)−H(k)≤1。
     *
     * 这样我们就证明了任意长度的链表对应的二叉搜索树是平衡的。
     */


    /**
     * 方法一：分治
     * 我们可以直接模拟「前言」部分的构造方案。
     * 具体地，设当前链表的左端点为 left，右端点 right，包含关系为「左闭右开」，即 left 包含在链表中而 right 不包含在链表中。我们希望快速地找出链表的中位数节点 mid。
     * 为什么要设定「左闭右开」的关系？由于题目中给定的链表为单向链表，访问后继元素十分容易，但无法直接访问前驱元素。
     * 因此在找出链表的中位数节点 mid 之后，如果设定「左闭右开」的关系，我们就可以直接用 (left,mid) 以及 (mid.next,right) 来表示左右子树对应的列表了。
     * 并且，初始的列表也可以用 (head,null) 方便地进行表示，其中 null 表示空节点。
     *
     * 找出链表中位数节点的方法多种多样，其中较为简单的一种是「快慢指针法」。初始时，快指针 fast 和慢指针 slow 均指向链表的左端点 left。
     * 我们将快指针 fast 向右移动两次的同时，将慢指针 slow 向右移动一次，直到快指针到达边界（即快指针到达右端点或快指针的下一个节点是右端点）。此时，慢指针对应的元素就是中位数。
     *
     * 在找出了中位数节点之后，我们将其作为当前根节点的元素，并递归地构造其左侧部分的链表对应的左子树，以及右侧部分的链表对应的右子树。
     *
     * 复杂度分析
     * 时间复杂度：O(nlogn)，其中 n 是链表的长度。
     * 设长度为 n 的链表构造二叉搜索树的时间为 T(n)，递推式为 T(n)=2⋅T(n/2)+O(n)，根据主定理，T(n)=O(nlogn)。
     * 空间复杂度：O(logn)，这里只计算除了返回答案之外的空间。平衡二叉树的高度为 O(logn)，即为递归过程中栈的最大深度，也就是需要的空间。
     */
    public TreeNode sortedListToBST(ListNode head) {
        return buildTree(head, null);
    }

    public TreeNode buildTree(ListNode left, ListNode right) {
        if (left == right) { // 只有都为null的时候，才会相等，即此时是叶子节点
            return null;
        }

        ListNode mid = getMedian(left, right);
        TreeNode root = new TreeNode(mid.val);
        root.left = buildTree(left, mid);
        root.right = buildTree(mid.next, right);
        return root;
    }

    public ListNode getMedian(ListNode left, ListNode right) {
        ListNode slow = left, fast = left;
        while (fast != right && fast.next != right) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 方法二：分治 + 中序遍历优化
     * 方法一的时间复杂度的瓶颈在于寻找中位数节点。由于构造出的二叉搜索树的中序遍历结果就是链表本身，因此我们可以将分治和中序遍历结合起来，减少时间复杂度。
     *
     * 具体地，设当前链表的左端点编号为 left，右端点编号为 right，包含关系为「双闭」，即 left 和 right 均包含在链表中。
     * 链表节点的编号为 [0,n)。中序遍历的顺序是「左子树 - 根节点 - 右子树」，那么在分治的过程中，我们不用急着找出链表的中位数节点，
     * 而是使用一个占位节点，等到中序遍历到该节点时，再填充它的值。
     *
     * 我们可以通过计算编号范围来进行中序遍历：
     * 中位数节点对应的编号为 mid=(left+right+1)/2；
     * 根据方法一中对于中位数的定义，编号为 (left+right)/2 的节点同样也可以是中位数节点。
     * 左右子树对应的编号范围分别为 [left,mid−1] 和 [mid+1,right]。
     * 如果 left>right，那么遍历到的位置对应着一个空节点，否则对应着二叉搜索树中的一个节点。
     * 这样一来，我们其实已经知道了这棵二叉搜索树的结构，并且题目给定了它的中序遍历结果，那么我们只要对其进行中序遍历，就可以还原出整棵二叉搜索树了。
     *
     * 复杂度分析
     * 时间复杂度：O(n)，其中 n 是链表的长度。
     * 设长度为 n 的链表构造二叉搜索树的时间为 T(n)，递推式为 T(n)=2⋅T(n/2)+O(1)，根据主定理，T(n)=O(n)。
     * 空间复杂度：O(logn)，这里只计算除了返回答案之外的空间。平衡二叉树的高度为 O(logn)，即为递归过程中栈的最大深度，也就是需要的空间。
     */
    ListNode globalHead;

    public TreeNode sortedListToBST2(ListNode head) {
        globalHead = head;
        int length = getLength(head);
        return buildTree(0, length - 1);
    }

    public int getLength(ListNode head) {
        int ret = 0;
        while (head != null) {
            ++ret;
            head = head.next;
        }
        return ret;
    }

    public TreeNode buildTree(int left, int right) {
        if (left > right) {
            return null;
        }
        int mid = (left + right + 1) / 2;
        TreeNode root = new TreeNode();
        root.left = buildTree(left, mid - 1);
        root.val = globalHead.val;
        globalHead = globalHead.next;
        root.right = buildTree(mid + 1, right);
        return root;
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}

