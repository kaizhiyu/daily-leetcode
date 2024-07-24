package org.dc.tree;

/**
 *
 * 给你二叉树的根节点 root 和一个表示目标和的整数 targetSum 。判断该树中是否存在 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和 targetSum 。如果存在，返回 true ；否则，返回 false 。
 * 叶子节点 是指没有子节点的节点。
 *
 * 示例 1：
 * 输入：root = [5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum = 22
 * 输出：true
 * 解释：等于目标和的根节点到叶节点路径如上图所示。
 *
 * 示例 2：
 * 输入：root = [1,2,3], targetSum = 5
 * 输出：false
 * 解释：树中存在两条根节点到叶子节点的路径：
 * (1 --> 2): 和为 3
 * (1 --> 3): 和为 4
 * 不存在 sum = 5 的根节点到叶子节点的路径。
 *
 * 示例 3：
 * 输入：root = [], targetSum = 0
 * 输出：false
 * 解释：由于树是空的，所以不存在根节点到叶子节点的路径。
 *
 *
 * 提示：
 * 树中节点的数目在范围 [0, 5000] 内
 * -1000 <= Node.val <= 1000
 * -1000 <= targetSum <= 1000
 *
 */
public class Leetcode112 {
    /**
     * 看示例 1，我们可以从 targetSum=22 开始，不断地减去路径上的节点值，如果走到叶子节点发现 targetSum=0，就说明我们找到了一条符合题目要求的路径。具体来说：
     * 递归前，targetSum=22。
     * 从根节点 5 开始递归，把 targetSum 减少 5，现在 targetSum=17。
     * 向下递归到 4，把 targetSum 减少 4，现在 targetSum=13。
     * 向下递归到 11，把 targetSum 减少 11，现在 targetSum=2。
     * 向下递归到 2，把 targetSum 减少 2，现在 targetSum=0。
     *
     * 这样「倒着减小」可以让我们直接递归调用 hasPathSum：
     * 如果 root 是空，返回 false。
     * 把 targetSum 减少 root.val。
     * 如果 root 是叶子节点：如果 targetSum=0，返回 true，否则返回 false。
     * 递归左子树，如果左子树返回 true，那么当前子树就返回 true，否则返回递归右子树的结果。
     *
     * 复杂度分析
     * 时间复杂度：O(n)，其中 n 为二叉树的节点个数。
     * 空间复杂度：O(n)。最坏情况下，二叉树退化成一条链，递归需要 O(n) 的栈空间。
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }

        targetSum = targetSum - root.val;
        if (root.left == root.right) {
            return targetSum == 0;
        }

        return hasPathSum(root.left, targetSum) || hasPathSum(root.right, targetSum);
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
