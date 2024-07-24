package org.dc.tree;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 给定一个二叉树的 根节点 root，请找出该二叉树的 最底层 最左边 节点的值。
 * 假设二叉树中至少有一个节点。
 *
 * 示例 1:
 * 输入: root = [2,1,3]
 * 输出: 1
 *
 * 示例 2:
 * 输入: [1,2,3,4,null,5,6,null,null,7]
 * 输出: 7
 *
 * 提示:
 * 二叉树的节点个数的范围是 [1,104]
 * -231 <= Node.val <= 231 - 1
 *
 */
public class Leetcode513 {
    /**
     *
     * BFS 这棵二叉树，先把右儿子入队，再把左儿子入队，这样最后一个出队的节点就是左下角的节点了
     *
     */
    public int findBottomLeftValue(TreeNode root) {
        TreeNode cur = root;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            cur = queue.poll();
            if (cur.right != null) queue.offer(cur.right);
            if (cur.left != null) queue.offer(cur.left);
        }
        return cur.val;
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
