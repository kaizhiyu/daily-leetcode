package org.dc.dp;

/**
 * level: medium
 * 打家劫舍 III
 *
 * 小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为 root 。
 * 除了 root 之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。 如果 两个直接相连的房子在同一天晚上被打劫 ，房屋将自动报警。
 * 给定二叉树的 root 。返回 在不触动警报的情况下 ，小偷能够盗取的最高金额 。
 *
 * 示例 1:
 * 输入: root = [3,2,3,null,3,null,1]
 * 输出: 7
 * 解释: 小偷一晚能够盗取的最高金额 3 + 3 + 1 = 7
 *
 * 示例 2:
 * 输入: root = [3,4,5,1,3,null,1]
 * 输出: 9
 * 解释: 小偷一晚能够盗取的最高金额 4 + 5 = 9
 *
 * 提示：
 * 树的节点数在 [1, 104] 范围内
 * 0 <= Node.val <= 104
 *
 */
public class Leetcode337 {
    /**
     * 选或者不选： 1）选当前节点，左右子树都不能选；2）不选当前节点，左右子树都能选
     * 提炼状态： 1）选当前节点，以当前节点为根的子树的最大点权和；2）不选当前节点，以当前节点为根的子树最大点权和
     * 转移方程：1）选 = 左不选 + 右不选 + 当前节点值；2）max（左选，左不选） + max（右选，右不选）。 最终答案：max（根选，根不选）
     */
    public int rob(TreeNode root) {
        int[] res = dfs(root);
        return Math.max(res[0], res[1]);
    }

    private int[] dfs(TreeNode node) {
        if (node == null) {
            return new int[]{0, 0}; // 没有节点，怎么选都是0
        }

        int[] left = dfs(node.left);
        int[] right = dfs(node.right);

        int rob = left[1] + right[1] + node.val; // 选当前节点，则不选左右子树
        int notRob = Math.max(left[0], left[1]) + Math.max(right[0], right[1]); // 不选当前节点，则左子树选或者不选的最大值+右子树选或者不选的最大值
        return new int[]{rob, notRob};
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
