package org.dc.tree;

/**
 *
 * 给你一个二叉树的根节点 root ，树中每个节点都存放有一个 0 到 9 之间的数字。
 * 每条从根节点到叶节点的路径都代表一个数字：
 *
 * 例如，从根节点到叶节点的路径 1 -> 2 -> 3 表示数字 123 。
 * 计算从根节点到叶节点生成的 所有数字之和 。
 *
 * 叶节点 是指没有子节点的节点。
 *
 * 示例 1：
 * 输入：root = [1,2,3]
 * 输出：25
 * 解释：
 * 从根到叶子节点路径 1->2 代表数字 12
 * 从根到叶子节点路径 1->3 代表数字 13
 * 因此，数字总和 = 12 + 13 = 25
 *
 * 示例 2：
 * 输入：root = [4,9,0,5,1]
 * 输出：1026
 * 解释：
 * 从根到叶子节点路径 4->9->5 代表数字 495
 * 从根到叶子节点路径 4->9->1 代表数字 491
 * 从根到叶子节点路径 4->0 代表数字 40
 * 因此，数字总和 = 495 + 491 + 40 = 1026
 *
 *
 * 提示：
 * 树中节点的数目在范围 [1, 1000] 内
 * 0 <= Node.val <= 9
 * 树的深度不超过 10
 *
 */
public class Leetcode129 {
    /**
     * 对于路径 4→9→5，我们可以按照如下方式生成数字 495。
     *
     * 初始化 x=0。
     * 从 4 开始递归，更新 x=x⋅10+4=4。
     * 向下递归到 9，更新 x=x⋅10+9=49。
     * 向下递归到 5，更新 x=x⋅10+5=495。
     * 当我们递归到叶子节点时，把 x 加到答案中。
     *
     * 为了实现上述算法，把 x 作为 DFS 的参数，并在 DFS 中按照 x = x⋅10 + node.val 去更新 x。
     *
     * 复杂度分析
     * 时间复杂度：O(n)，其中 n 为二叉树的节点个数。
     * 空间复杂度：O(n)。最坏情况下，二叉树退化成一条链，递归需要 O(n) 的栈空间。
     */
    private int ans;

    private void dfs(TreeNode node, int x) {
        if (node == null) {
            return;
        }
        x = x * 10 + node.val;
        if (node.left == node.right) { // node 是叶子节点
            ans += x;
            return;
        }
        dfs(node.left, x);
        dfs(node.right, x);
    }

    public int sumNumbers(TreeNode root) {
        dfs(root, 0);
        return ans;
    }

    /**
     * 也可以把数字之和放到 DFS 的返回值中。对于部分语言，这样写可以直接递归调用 sumNumbers，更加简洁。
     */
    public int sumNumbers2(TreeNode root) {
        return dfs2(root, 0);
    }

    private int dfs2(TreeNode root, int x) {
        if (root == null) {
            return 0;
        }
        x = x * 10 + root.val;
        if (root.left == root.right) { // root 是叶子节点
            return x;
        }

        return dfs2(root.left, x) + dfs2(root.right, x);
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
