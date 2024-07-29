package org.dc.dp;

/**
 * 给定一个二叉树的 root ，返回 最长的路径的长度 ，这个路径中的 每个节点具有相同值 。 这条路径可以经过也可以不经过根节点。
 * 两个节点之间的路径长度 由它们之间的边数表示。
 *
 * 示例 1:
 * 输入：root = [5,4,5,1,1,5]
 * 输出：2
 *
 * 示例 2:
 * 输入：root = [1,4,5,4,4,5]
 * 输出：2
 *
 * 提示:
 * 树的节点数的范围是 [0, 104]
 * -1000 <= Node.val <= 1000
 * 树的深度将不超过 1000
 *
 */
public class Leetcode687 {
    /**
     * 还是取左右子树的最大深度，不过在使用前，需要额外判断，当前节点和左右子树节点值是否一致，一致的话，继续使用最大深度，否则直接为0。
     * 就在这么计算的过程中，一方面比较最长边数，同时返回最大深度。
     *
     */
    private int ans;
    public int longestUnivaluePath(TreeNode root) {
        dfs(root);
        return ans;
    }

    private int dfs(TreeNode node) {
        if (node == null) {
            return -1;
        }

        int l = dfs(node.left) + 1;
        int r = dfs(node.right) + 1;

        if (node.left != null && node.left.val != node.val) l = 0;
        if (node.right != null && node.right.val != node.val) r = 0;

        ans = Math.max(ans, r + l);
        return Math.max(l, r);
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
