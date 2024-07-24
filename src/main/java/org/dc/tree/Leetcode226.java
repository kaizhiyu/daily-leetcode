package org.dc.tree;

/**
 * 给你一棵二叉树的根节点 root ，翻转这棵二叉树，并返回其根节点。
 *
 * 示例 1：
 * 输入：root = [4,2,7,1,3,6,9]
 * 输出：[4,7,2,9,6,3,1]
 *
 * 示例 2：
 * 输入：root = [2,1,3]
 * 输出：[2,3,1]
 *
 * 示例 3：
 * 输入：root = []
 * 输出：[]
 *
 * 提示：
 *
 * 树中节点数目范围在 [0, 100] 内
 * -100 <= Node.val <= 100
 */
public class Leetcode226 {
    /**
     * 要点
     * 对于根节点，它的左右儿子必须交换，即左儿子变成右儿子，右儿子变成左儿子。
     * 对于根节点的左右子树，也需要翻转其内部节点。这是一个和原问题相似的子问题，看完视频后，你知道，这可以用递归解决。
     *
     * 算法
     * 递归调用 invertTree(root.left)，获取到左子树翻转后的结果 left。
     * 递归调用 invertTree(root.right)，获取到右子树翻转后的结果 right。
     * 交换左右儿子，即更新 root.left 为 right，更新 root.right 为 left。
     * 返回 root。
     * 递归边界：如果 root 是空节点，返回空。
     *
     * 复杂度分析
     * 时间复杂度：O(n)，其中 n 为二叉树的节点个数。
     * 空间复杂度：O(n)。最坏情况下，二叉树退化成一条链，递归需要 O(n) 的栈空间。
     */

    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
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
