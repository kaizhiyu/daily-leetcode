package org.dc.tree;

/**
 *
 * 给定一个二叉树，判断它是否是
 * 平衡二叉树
 *
 * 示例 1：
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：true
 *
 * 示例 2：
 * 输入：root = [1,2,2,3,3,null,null,4,4]
 * 输出：false
 *
 * 示例 3：
 * 输入：root = []
 * 输出：true
 *
 * 提示：
 * 树中的节点数在范围 [0, 5000] 内
 * -104 <= Node.val <= 104
 *
 */
public class Leetcode110 {
    /**
     *
     * 获取高度的时候直接决定是否已经满足平衡树
     * 如果满足则返回高度值，不满足直接返回 -1
     *
     */
    public boolean isBalanced1(TreeNode root) {
        return getHeight(root) != -1;
    }

    private int getHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int leftH = getHeight(node.left);
        if (leftH == -1) {
            return -1; // 提前退出，不再递归
        }

        int rightH = getHeight(node.right);
        if (rightH == -1 || Math.abs(leftH - rightH) > 1) {
            return -1;
        }

        return Math.max(leftH, rightH) + 1;
    }

    /**
     *
     * 判断左子树是否为平衡树，右子树是否为平衡树，以及左子树和右子树的高度差的绝对值是否小于等于1
     *
     */
    public boolean isBalanced2(TreeNode root) {
        if (root == null) {
            return true;
        }

        return isBalanced2(root.left)
                && isBalanced2(root.right)
                && Math.abs(deep(root.left) - deep(root.right)) <= 1;
    }

    public int deep(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftH = deep(root.left);
        int rightH = deep(root.right);
        return Math.max(leftH, rightH) + 1;
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
