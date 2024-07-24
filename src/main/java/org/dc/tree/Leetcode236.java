package org.dc.tree;

/**
 *
 * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
 *
 * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个节点 p、q，最近公共祖先表示为一个节点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
 *
 * 示例 1：
 * 输入：root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
 * 输出：3
 * 解释：节点 5 和节点 1 的最近公共祖先是节点 3 。
 *
 * 示例 2：
 * 输入：root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
 * 输出：5
 * 解释：节点 5 和节点 4 的最近公共祖先是节点 5 。因为根据定义最近公共祖先节点可以为节点本身。
 *
 * 示例 3：
 *
 * 输入：root = [1,2], p = 1, q = 2
 * 输出：1
 *
 *
 * 提示：
 * 树中节点数目在范围 [2, 105] 内。
 * -109 <= Node.val <= 109
 * 所有 Node.val 互不相同 。
 * p != q
 * p 和 q 均存在于给定的二叉树中。
 *
 */
public class Leetcode236 {
    /**
     *
     * 问：lowestCommonAncestor 函数的返回值是什么意思？
     * 答：对于最外层的递归调用者来说，返回值是最近公共祖先的意思。但是，在递归过程中，递归函数的返回值有多个含义，
     * 可以是空节点、节点 p、节点 q 或最近公共祖先。代码根据节点是否为空来进入不同的分支，左右子树的返回值具体是哪个节点，并不影响进入的分支。
     *
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 当前节点为空节点，当前节点为p, 当前节点为q，直接返回当前节点
        if (root == null || root == p || root == q) {
            return root;
        }

        TreeNode l = lowestCommonAncestor(root.left, p, q);
        TreeNode r = lowestCommonAncestor(root.right, p, q);

        // 左右子树都找到的情况下，就是当前root节点
        if (l != null && r != null) {
            return root;
        }
        // 如果左子树不为空，右子树为空的情况下，就是左子树
        if (l != null) {
            return l;
        }
        // 要么右子树不为空，返回右子树，否则都为空返回空
        return r;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
}
