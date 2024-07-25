package org.dc.dp;

/**
 * 二叉树中的 路径 被定义为一条节点序列，序列中每对相邻节点之间都存在一条边。同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不一定经过根节点。
 * 路径和 是路径中各节点值的总和。
 * 给你一个二叉树的根节点 root ，返回其 最大路径和 。
 *
 * 示例 1：
 * 输入：root = [1,2,3]
 * 输出：6
 * 解释：最优路径是 2 -> 1 -> 3 ，路径和为 2 + 1 + 3 = 6
 *
 * 示例 2：
 * 输入：root = [-10,9,20,null,null,15,7]
 * 输出：42
 * 解释：最优路径是 15 -> 20 -> 7 ，路径和为 15 + 20 + 7 = 42
 *
 * 提示：
 * 树中节点数目范围是 [1, 3 * 104]
 * -1000 <= Node.val <= 1000
 *
 */
public class Leetcode124 {
    /**
     * 遍历二叉树，在计算最大链和的同时，顺便更新答案的最大值
     * 在当前节点拐弯的最大路径和 = 左子树最大链和 + 右子树最大链和 + 当前节点值
     * 返给父节点的是 = max (左子树最大链和, 右子树最大链和) + 当前节点值，如果这个值是负值，则返回0
     *
     * 本题有两个关键概念：
     * 链：从叶子到当前节点的路径。其节点值之和是 dfs 的返回值。
     * 直径：等价于由两条（或者一条）链拼成的路径。我们枚举每个 node，假设直径在这里「拐弯」，也就是计算由左右两条从叶子到 node 的链的节点值之和，去更新答案的最大值。
     * ⚠注意：dfs 返回的是链的节点值之和，不是直径的节点值之和。
     *
     * 复杂度分析
     * 时间复杂度：O(n)，其中 n 为二叉树的节点个数。
     * 空间复杂度：O(n)。最坏情况下，二叉树退化成一条链，递归需要 O(n) 的栈空间。
     */
    int ans = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }

        dfs(root);
        return ans;
    }

    private int dfs(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int l = dfs(node.left);
        int r = dfs(node.right);

        ans = Math.max(ans, node.val + l + r);
        return Math.max(Math.max(node.val + l, node.val + r), 0);
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
