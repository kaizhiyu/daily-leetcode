package org.dc.tree;

/**
 *
 * 给你一个二叉树的根节点 root ， 检查它是否轴对称。
 * 示例 1：
 * 输入：root = [1,2,2,3,4,4,3]
 * 输出：true
 *
 * 示例 2：
 * 输入：root = [1,2,2,null,3,null,3]
 * 输出：false
 *
 *
 * 提示：
 * 树中节点数目在范围 [1, 1000] 内
 * -100 <= Node.val <= 100
 *
 */
public class Leetcode101 {
    public boolean isSymmetric(TreeNode root) {
        return isSameTree(root.left, root.right);
    }

    // 在【100. 相同的树】的基础上稍加改动
    private boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null || q == null)
            return p == q;
        return p.val == q.val
                && isSameTree(p.left, q.right)
                && isSameTree(p.right, q.left);
    }

    public class TreeNode {
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
