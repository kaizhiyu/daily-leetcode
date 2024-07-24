package org.dc.tree;

import java.util.*;

/**
 * 给你二叉树的根节点 root ，返回其节点值的 锯齿形层序遍历 。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
 *
 * 示例 1：
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：[[3],[20,9],[15,7]]
 *
 * 示例 2：
 * 输入：root = [1]
 * 输出：[[1]]
 *
 * 示例 3：
 * 输入：root = []
 * 输出：[]
 *
 * 提示：
 * 树中节点数目在范围 [0, 2000] 内
 * -100 <= Node.val <= 100
 */
public class Leetcode103 {
    /**
     * 复杂度分析
     * 时间复杂度：O(n)，其中 n 为二叉树的节点个数。
     * 空间复杂度：O(n)。满二叉树（每一层都填满）最后一层有大约 n/2 个节点，因此队列中最多有 O(n) 个元素，所以空间复杂度是 O(n) 的。
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> path = new ArrayList<>();
            for (int i = 1; i <= size; i++) {
                TreeNode cur = queue.poll();
                path.add(cur.val);

                if (cur.left != null) {
                    queue.offer(cur.left);
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                }
            }
            if (ans.size() % 2 == 1) {
                Collections.reverse(path);
            }
            ans.add(path);
        }

        return ans;
    }

    /**
     * 两个数组
     */
    public List<List<Integer>> zigzagLevelOrder2(TreeNode root) {
        if (root == null) return new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        List<TreeNode> cur = new ArrayList<>();
        cur.add(root);
        while (!cur.isEmpty()) {
            List<TreeNode> nxt = new ArrayList<>();
            List<Integer> vals = new ArrayList<>(cur.size()); // 容量已知
            for (TreeNode node : cur) {
                vals.add(node.val);
                if (node.left != null)  nxt.add(node.left);
                if (node.right != null) nxt.add(node.right);
            }
            cur = nxt;
            if (ans.size() % 2 > 0) Collections.reverse(vals);
            ans.add(vals);
        }
        return ans;
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
