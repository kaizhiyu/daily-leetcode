package org.dc.tree;

import javafx.util.Pair;

/**
 *
 * 给你一个有根节点 root 的二叉树，返回它 最深的叶节点的最近公共祖先 。
 *
 * 回想一下：
 * 叶节点 是二叉树中没有子节点的节点
 * 树的根节点的 深度 为 0，如果某一节点的深度为 d，那它的子节点的深度就是 d+1
 * 如果我们假定 A 是一组节点 S 的 最近公共祖先，S 中的每个节点都在以 A 为根节点的子树中，且 A 的深度达到此条件下可能的最大值。
 *
 *
 * 示例 1：
 * 输入：root = [3,5,1,6,2,0,8,null,null,7,4]
 * 输出：[2,7,4]
 * 解释：我们返回值为 2 的节点，在图中用黄色标记。
 * 在图中用蓝色标记的是树的最深的节点。
 * 注意，节点 6、0 和 8 也是叶节点，但是它们的深度是 2 ，而节点 7 和 4 的深度是 3 。
 *
 * 示例 2：
 * 输入：root = [1]
 * 输出：[1]
 * 解释：根节点是树中最深的节点，它是它本身的最近公共祖先。
 *
 * 示例 3：
 * 输入：root = [0,1,3,null,2]
 * 输出：[2]
 * 解释：树中最深的叶节点是 2 ，最近公共祖先是它自己。
 *
 *
 * 提示：
 * 树中的节点数将在 [1, 1000] 的范围内。
 * 0 <= Node.val <= 1000
 * 每个节点的值都是 独一无二 的。
 *
 */
public class Leetcode1123 {
    /**
     * 方法一：递归递归，有递有归
     * 上面视频中提到，如果我们要找的节点只在左子树中，那么最近公共祖先也必然只在左子树中。对于本题，如果左子树的最大深度比右子树的大，那么最深叶结点就只在左子树中，所以最近公共祖先也只在左子树中。
     *
     * 如果左右子树的最大深度一样呢？当前节点一定是最近公共祖先吗？
     *
     * 不一定。比如节点 1 的左右子树最深叶节点 0,  8 的深度都是 2，但该深度并不是全局最大深度，所以节点 1 并不能是答案。
     *
     * 根据以上讨论，正确做法如下：
     * 递归这棵二叉树，同时维护全局最大深度 maxDepth。
     * 在「递」的时候往下传 depth，用来表示当前节点的深度。
     * 在「归」的时候往上传当前子树最深叶节点的深度。
     * 设左子树最深叶节点的深度为 leftMaxDepth，右子树最深叶节点的深度为 rightMaxDepth。如果 leftMaxDepth=rightMaxDepth=maxDepth，
     * 那么更新答案为当前节点。注意这并不代表我们找到了答案，如果后面发现了更深的叶节点，那么答案还会更新。
     *
     * 复杂度分析
     * 时间复杂度：O(n)。每个节点都会恰好访问一次。
     * 空间复杂度：O(n)。最坏情况下，二叉树是一条链，递归需要 O(n) 的栈空间。
     */
    private TreeNode ans;
    private int maxDepth = -1; // 全局最大深度

    public TreeNode lcaDeepestLeaves(TreeNode root) {
        dfs(root, 0);
        return ans;
    }

    private int dfs(TreeNode node, int depth) {
        if (node == null) {
            maxDepth = Math.max(maxDepth, depth); // 维护全局最大深度
            return depth;
        }
        int leftMaxDepth = dfs(node.left, depth + 1); // 获取左子树最深叶节点的深度
        int rightMaxDepth = dfs(node.right, depth + 1); // 获取右子树最深叶节点的深度
        if (leftMaxDepth == rightMaxDepth && leftMaxDepth == maxDepth)
            ans = node;
        return Math.max(leftMaxDepth, rightMaxDepth); // 当前子树最深叶节点的深度
    }

    /**
     * 方法二：自底向上
     * 也可以不用全局变量，而是把每棵子树都看成是一个「子问题」，
     * 即对于每棵子树，我们需要知道：
     * 这棵子树最深叶结点的深度。这里是指叶子在这棵子树内的深度，而不是在整棵二叉树的视角下的深度。相当于这棵子树的高度。
     * 这棵子树的最深叶结点的最近公共祖先 lca。
     *
     * 分类讨论：
     * 设子树的根节点为 node，node 的左子树的高度为 leftHeight，node 的右子树的高度为 rightHeight。
     * 如果 leftHeight>rightHeight，那么子树的高度为 leftHeight+1，lca 是左子树的 lca。
     * 如果 leftHeight<rightHeight，那么子树的高度为 rightHeight+1，lca 是右子树的 lca。
     * 如果 leftHeight=rightHeight，那么子树的高度为 leftHeight+1，lca 就是 node。反证法：如果 lca 在左子树中，那么 lca 不是右子树的最深叶结点的祖先，这不对；
     * 如果 lca 在右子树中，那么 lca 不是左子树的最深叶结点的祖先，这也不对；如果 lca 在 node 的上面，那就不符合「最近」的要求。所以 lca 只能是 node。
     *
     * 复杂度分析
     * 时间复杂度：O(n)。每个节点都会恰好访问一次。
     * 空间复杂度：O(n)。最坏情况下，二叉树是一条链，递归需要 O(n) 的栈空间。
     */
    public TreeNode lcaDeepestLeaves2(TreeNode root) {
        return dfs(root).getValue();
    }

    private Pair<Integer, TreeNode> dfs(TreeNode node) {
        if (node == null)
            return new Pair<>(0, null);

        Pair<Integer, TreeNode> left = dfs(node.left);
        Pair<Integer, TreeNode> right = dfs(node.right);

        // 左子树更高
        if (left.getKey() > right.getKey())
            return new Pair<>(left.getKey() + 1, left.getValue());
        // 右子树更高
        if (left.getKey() < right.getKey())
            return new Pair<>(right.getKey() + 1, right.getValue());
        // 一样高
        return new Pair<>(left.getKey() + 1, node);
    }

    public static void main(String[] args) {
        // root = [3,5,1,6,2,0,8,null,null,7,4]

        TreeNode t6 = new TreeNode(6);

        TreeNode t7 = new TreeNode(7);
        TreeNode t4 = new TreeNode(4);
        TreeNode t2 = new TreeNode(2, t7, t4);
        TreeNode t5 = new TreeNode(5, t6, t2);

        TreeNode t0 = new TreeNode(0);
        TreeNode t8 = new TreeNode(8);
        TreeNode t1 = new TreeNode(1, t0, t8);
        TreeNode root = new TreeNode(3, t5, t1);

        Leetcode1123 leetcode1123 = new Leetcode1123();
        TreeNode ans = leetcode1123.lcaDeepestLeaves(root);
        System.out.println(ans.val);
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
