package org.dc.tree;

/**
 *
 * 给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。
 *
 * 有效 二叉搜索树定义如下：
 * 节点的左
 * 子树
 * 只包含 小于 当前节点的数。
 * 节点的右子树只包含 大于 当前节点的数。
 * 所有左子树和右子树自身必须也是二叉搜索树。
 *
 * 示例 1：
 * 输入：root = [2,1,3]
 * 输出：true
 *
 * 示例 2：
 * 输入：root = [5,1,4,null,null,3,6]
 * 输出：false
 * 解释：根节点的值是 5 ，但是右子节点的值是 4 。
 *
 *
 * 提示：
 * 树中节点数目范围在[1, 104] 内
 * -231 <= Node.val <= 231 - 1
 *
 */
public class Leetcode98 {
    /**
     * 【总结】
     * 前序遍历在某些数据下不需要递归到叶子节点就能返回（比如根节点左儿子的值大于根节点的值，左儿子就不会继续往下递归了），而中序遍历和后序遍历至少要递归到一个叶子节点。从这个角度上来说，前序遍历是最快的。
     * 中序遍历很好地利用了二叉搜索树的性质，使用到的变量最少。
     * 后序遍历的思想是最通用的，即自底向上计算子问题的过程。想要学好动态规划的话，请务必掌握自底向上的思想。
     */

    /**
     * 前序遍历 根 - 左 - 右
     * 先判断根节点的值，然后再进行递归，不过递归的时候除了树节点，还有树节点的值的最大范围
     *
     * 复杂度分析
     * 时间复杂度：O(n)，其中 n 为二叉搜索树的节点个数。
     * 空间复杂度：O(n)。最坏情况下，二叉搜索树退化成一条链（注意题目没有保证它是平衡树），因此递归需要 O(n) 的栈空间
     */
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean isValidBST(TreeNode root, long left, long right) {
        if (root == null) {
            return true;
        }

        long x = root.val;
        return x > left && x < right
                && isValidBST(root.left, left, x)
                && isValidBST(root.right, x, right);
    }

    /**
     * 中序遍历 左 - 根 - 右
     *
     * 复杂度分析
     * 时间复杂度：O(n)，其中 n 为二叉搜索树的节点个数。
     * 空间复杂度：O(n)。最坏情况下，二叉搜索树退化成一条链（注意题目没有保证它是平衡树），因此递归需要 O(n) 的栈空间
     */
    private long pre = Long.MIN_VALUE;
    public boolean isValidBST2(TreeNode root) {
        if (root == null) {
            return true;
        }

        if (!isValidBST2(root.left)) {
            return false;
        }

        long x = root.val;
        if (x < pre) {
            return false;
        }

        pre = x;
        return isValidBST2(root.right);
    }

    /**
     * 后序遍历 左 - 右 - 根
     * 从下往上返回目前最大的值范围，使用数组表示，数组纬度为2，[0]表示最小值 [1]表示最大值
     *
     * 复杂度分析
     * 时间复杂度：O(n)，其中 n 为二叉搜索树的节点个数。
     * 空间复杂度：O(n)。最坏情况下，二叉搜索树退化成一条链（注意题目没有保证它是平衡树），因此递归需要 O(n) 的栈空间。
     */
    public boolean isValidBST3(TreeNode root) {
        return dfs3(root)[1] != Long.MAX_VALUE;
    }

    private long[] dfs3(TreeNode node) {
        if (node == null) {
            // 正常情况，就需要返回 [正无穷, 负无穷] 主要是为了方便后面的对比
            return new long[]{Long.MAX_VALUE, Long.MIN_VALUE};
        }

        long[] l = dfs3(node.left);
        long[] r = dfs3(node.right);

        long val = node.val;
        if (val <= l[1] || val >= r[0]) {
            // 不符合条件的树，这么返回的话，归场景下，上面也会判断出不符合
            return new long[]{Long.MIN_VALUE, Long.MAX_VALUE};
        }

        long nMin = Math.min(val, l[0]);
        long nMax = Math.max(val, r[1]);
        return new long[]{nMin, nMax};
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
