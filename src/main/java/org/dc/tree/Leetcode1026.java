package org.dc.tree;

/**
 *
 * 给定二叉树的根节点 root，找出存在于 不同 节点 A 和 B 之间的最大值 V，其中 V = |A.val - B.val|，且 A 是 B 的祖先。
 * （如果 A 的任何子节点之一为 B，或者 A 的任何子节点是 B 的祖先，那么我们认为 A 是 B 的祖先）

 * 示例 1：
 * 输入：root = [8,3,10,1,6,null,14,null,null,4,7,13]
 * 输出：7
 * 解释：
 * 我们有大量的节点与其祖先的差值，其中一些如下：
 * |8 - 3| = 5
 * |3 - 7| = 4
 * |8 - 1| = 7
 * |10 - 13| = 3
 * 在所有可能的差值中，最大值 7 由 |8 - 1| = 7 得出。
 *
 * 示例 2：
 * 输入：root = [1,null,2,null,0,3]
 * 输出：3
 *
 * 提示：
 * 树中的节点数在 2 到 5000 之间。
 * 0 <= Node.val <= 105
 *
 */
public class Leetcode1026 {
    /**
     * 方法一：「递」
     * 如果节点 A 在从根节点到节点 B 的路径上，则称 A 是 B 的祖先节点，称 B 是 A 的子孙节点。
     * 注：在这个定义中，B 的祖先节点可以是 B 自己。例如示例 1 中 6 的祖先节点自上而下依次为 8,3,6。
     * 注 2：虽然题目要求「不同节点」，但由于我们计算的是最大差值，相同节点算出来的差值为 0，所以相同节点不影响最大差值。
     *
     * 为了计算 A 和 B 的最大差值，是否需要记录从根到 B 这条路径上的所有节点值？
     * 对于题目给出的公式 V=∣A.val−B.val∣，为了让 V 尽量大，分类讨论：
     * 如果 A.val<B.val，那么 A.val 越小，V 越大。
     * 如果 A.val≥B.val，那么 A.val 越大，V 越大；
     * 因此，无需记录路径中的全部节点值，只需记录路径中的最小节点值 mn 和最大节点值 mx。每递归到一个节点 B，计算 max(∣mn−B.val∣,∣mx−B.val∣) 并更新答案的最大值。
     * 由于 mn≤B.val≤mx，上式可化简为 max(B.val−mn,mx−B.val)
     *
     */
    private int ans;
    public int maxAncestorDiff(TreeNode root) {
        dfs(root, root.val, root.val);
        return ans;
    }

    private void dfs(TreeNode node, int mn, int mx) {
        if (node == null) return;

        mn = Math.min(mn, node.val);
        mx = Math.max(mx, node.val);

        ans = Math.max(ans, Math.max(node.val - mn, mx - node.val));

        dfs(node.left, mn, mx);
        dfs(node.right, mn, mx);
    }

    /**
     * 优化
     * 换个角度看问题：对于一条从根出发向下的路径，我们要计算的实际上是这条路径上任意两点的最大差值。
     * 递归到空节点时，mx 是从根到叶子的路径上的最大值，mn 是从根到叶子的路径上的最小值，所以 mx−mn 就是从根到叶子的路径上任意两点的最大差值。
     * 所以无需每个节点都去更新答案，而是在递归到空节点时才去更新答案。
     *
     * 复杂度分析
     * 时间复杂度：O(n)，其中 n 为二叉树的节点个数。
     * 空间复杂度：O(n)。最坏情况下，二叉树退化成一条链，递归需要 O(n) 的栈空间。
     */
    private void dfs2(TreeNode node, int mn, int mx) {
        if (node == null) {
            ans = Math.max(ans, mx - mn);
            return;
        }
        mn = Math.min(mn, node.val);
        mx = Math.max(mx, node.val);
        dfs2(node.left, mn, mx);
        dfs2(node.right, mn, mx);
    }

    /**
     * 方法二：「归」
     * 方法一的思路是维护 B 的祖先节点中的最小值和最大值，我们还可以站在祖先 A 的视角，维护 A 子孙节点中的最小值 mn 和最大值 mx。
     * 换句话说，最小值和最大值不再作为入参，而是作为返回值，意思是以 A 为根的子树中的最小值 mn 和最大值 mx。
     *
     * 递归到节点 A 时，先递归左右子树，拿到左右子树的最小值和最大值。那么：
     * mn 等于当前节点值，左子树最小值，右子树最小值，这三者的最小值。
     * mx 等于当前节点值，左子树最大值，右子树最大值，这三者的最大值。
     * 然后计算 max(∣mn−A.val∣,∣mx−A.val∣) 并更新答案的最大值。
     * 由于 mn≤A.val≤mx，上式可化简为 max(A.val−mn,mx−A.val)
     *
     * 复杂度分析
     * 时间复杂度：O(n)，其中 n 为二叉树的节点个数。
     * 空间复杂度：O(n)。最坏情况下，二叉树退化成一条链，递归需要 O(n) 的栈空间。
     */
    private int[] dfs2(TreeNode node) {
        if (node == null) { // 需要保证空节点不影响 mn 和 mx
            return new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};
        }
        int[] p = dfs2(node.left);
        int[] q = dfs2(node.right);
        int mn = Math.min(node.val, Math.min(p[0], q[0]));
        int mx = Math.max(node.val, Math.max(p[1], q[1]));
        ans = Math.max(ans, Math.max(node.val - mn, mx - node.val));
        return new int[]{mn, mx};
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
