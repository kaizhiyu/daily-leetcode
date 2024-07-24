package org.dc.tree;

/**
 * 给定一个二叉树，找出其最小深度。
 * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
 * 说明：叶子节点是指没有子节点的节点。
 *
 * 示例 1：
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：2
 *
 * 示例 2：
 * 输入：root = [2,null,3,null,4,null,5,null,6]
 * 输出：5
 *
 * 提示：
 * 树中节点数的范围在 [0, 105] 内
 * -1000 <= Node.val <= 1000
 *
 */
public class Leetcode111 {
    /**
     * 方法一：自顶向下「递」
     * [3, 9, 20, 15, 7]
     * 我们可以在 DFS 这棵树的同时，额外传入一个计数器 cnt，表示路径上的节点个数，例如上图从根到叶子的路径 3→20→15：
     * 递归前，cnt=0。
     * 从 3 开始递归，cnt 加一，现在 cnt=1。
     * 向下递归到 20，cnt 加一，现在 cnt=2。
     * 向下递归到 15，cnt 加一，现在 cnt=3。由于 15 是叶子，用 3 更新答案的最小值。
     */
    public int minDeep = Integer.MAX_VALUE;

    public int minDepth(TreeNode root) {
        dfs(root, 0);
        return root != null ? minDeep : 0;
    }

    private void dfs(TreeNode root, int deep) {
        if (root == null) {
            return;
        }

        deep++;
        if (root.left == root.right) {
            minDeep = Math.min(minDeep, deep);
        }

        dfs(root.left, deep);
        dfs(root.right, deep);
    }

    /**
     * 优化
     * 如果递归中发现 cnt≥ans，由于继续向下递归也不会让 ans 变小，直接返回。
     * 这一技巧叫做「最优性剪枝」。
     *
     * 复杂度分析
     * 时间复杂度：O(n)，其中 n 为二叉树的节点个数。
     * 空间复杂度：O(n)。最坏情况下，二叉树退化成一条链，递归需要 O(n) 的栈空间。
     */
    private void dfs2(TreeNode node, int cnt) {
        if (node == null || ++cnt >= minDeep) {
            return;
        }
        if (node.left == node.right) { // node 是叶子
            minDeep = cnt;
            return;
        }
        dfs2(node.left, cnt);
        dfs2(node.right, cnt);
    }

    /**
     * 方法二：自底向上「归」
     * 定义 dfs(node) 表示以节点 node 为根的子树的最小深度。
     *
     * 分类讨论：
     * 如果 node 是空节点，由于没有节点，返回 0。
     * 如果 node 没有右儿子，那么深度就是左子树的深度加一，即 dfs(node)=dfs(node.left)+1。
     * 如果 node 没有左儿子，那么深度就是右子树的深度加一，即 dfs(node)=dfs(node.right)+1。
     * 如果 node 左右儿子都有，那么分别递归计算左子树的深度，以及右子树的深度，二者取最小值再加一，即
     * dfs(node) = min(dfs(node.left), dfs(node.right)) + 1
     * 注意：并不需要特判 node 是叶子的情况，因为在没有右儿子的情况下，我们会递归 node.left，如果它是空节点，递归的返回值是 0，加一后得到 1，这正是叶子节点要返回的值。
     *
     * 答案：dfs(root)。
     * 代码实现时，可以直接递归调用 minDepth
     *
     * 复杂度分析
     * 时间复杂度：O(n)，其中 n 为二叉树的节点个数。
     * 空间复杂度：O(n)。最坏情况下，二叉树退化成一条链，递归需要 O(n) 的栈空间。
     */
    public int minDepth2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        if (root.right == null) {
            return minDepth2(root.left) + 1;
        }

        if (root.left == null) {
            return minDepth2(root.right) + 1;
        }

        return Math.min(minDepth2(root.left), minDepth2(root.right)) + 1;
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
