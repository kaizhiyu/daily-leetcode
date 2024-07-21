package org.dc.tree;

/**
 *
 * 给你二叉树的根节点 root 和一个整数 limit ，请你同时删除树中所有 不足节点 ，并返回最终二叉树的根节点。
 * 假如通过节点 node 的每种可能的 “根-叶” 路径上值的总和全都小于给定的 limit，则该节点被称之为 不足节点 ，需要被删除。
 * 叶子节点，就是没有子节点的节点。
 *
 * 示例 1：
 * 输入：root = [1,2,3,4,-99,-99,7,8,9,-99,-99,12,13,-99,14], limit = 1
 * 输出：[1,2,3,4,null,null,7,8,9,null,14]
 *
 * 示例 2：
 * 输入：root = [5,4,8,11,null,17,4,7,1,null,null,5,3], limit = 22
 * 输出：[5,4,8,11,null,17,4,7,null,null,null,5]
 *
 * 示例 3：
 * 输入：root = [1,2,-3,-5,null,4,null], limit = -1
 * 输出：[1,null,-3,4]
 *
 *
 * 提示：
 * 树中节点数目在范围 [1, 5000] 内
 * -105 <= Node.val <= 105
 * -109 <= limit <= 109
 *
 */
public class Leetcode1080 {
    /**
     * 一、思考
     * 对于一个叶子节点，要想删除它，需要满足什么条件？
     * 对于一个非叶节点，如果它有一个儿子没被删除，那么它能被删除吗？如果它的儿子都被删除，意味着什么？
     *
     * 二、解惑
     * 对于一个叶子节点 leaf，由于根到 leaf 的路径仅有一条，所以如果这条路径的元素和小于 limit，就删除 leaf。
     * 对于一个非叶节点 node，如果 node 有一个儿子没被删除，那么 node 就不能被删除。这可以用反证法证明：假设可以把 node 删除，那么经过 node 的所有路径和都小于 limit，
     * 也就意味着经过 node 的儿子的路径和也小于 limit，说明 node 的儿子需要被删除，矛盾，所以 node 不能被删除。
     * 如果 node 的儿子都被删除，说明经过 node 的所有儿子的路径和都小于 limit，这等价于经过 node 的所有路径和都小于 limit，所以 node 需要被删除。
     * 因此，要删除非叶节点 node，当且仅当 node 的所有儿子都被删除。
     *
     * 三、算法
     * 一个直接的想法是，添加一个递归参数 sumPath，表示从根到当前节点的路径和。
     * 但为了能直接调用 sufficientSubset，还可以从 limit 中减去当前节点值。
     * 如果当前节点是叶子，且此时 limit>0，说明从根到这个叶子的路径和小于 limit，那么删除这个叶子。
     * 如果当前节点不是叶子，那么往下递归，更新它的左儿子为对左儿子调用 sufficientSubset 的结果，更新它的右儿子为对右儿子调用 sufficientSubset 的结果。
     * 如果左右儿子都为空，那么就删除当前节点，返回空；否则不删，返回当前节点。
     */
    public TreeNode sufficientSubset(TreeNode root, int limit) {
        limit -= root.val;
        if (root.left == root.right) { // root 是叶子
            // 如果 limit > 0 说明从根到叶子的路径和小于 limit，删除叶子，否则不删除
            return limit > 0 ? null : root;
        }

        if (root.left != null) {
            root.left = sufficientSubset(root.left, limit);
        }

        if (root.right != null) {
            root.right = sufficientSubset(root.right, limit);
        }

        // 如果儿子都被删除，就删 root，否则不删 root
        return root.left == null && root.right == null ? null : root;
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
