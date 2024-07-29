package org.dc.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 给你一个整数 n ，请你生成并返回所有由 n 个节点组成且节点值从 1 到 n 互不相同的不同 二叉搜索树 。可以按 任意顺序 返回答案。
 *
 * 示例 1：
 * 输入：n = 3
 * 输出：[[1,null,2,null,3],[1,null,3,2],[2,1,3],[3,1,null,null,2],[3,2,null,1]]
 *
 * 示例 2：
 * 输入：n = 1
 * 输出：[[1]]
 *
 * 提示：
 * 1 <= n <= 8
 *
 */
public class Leetcode95 {
    /**
     * 回溯算法
     * 题目要我们求所有所能构造的二叉搜索树的具体方案，而给定 n 个节点所能构成的二叉搜索树的个数为 卡特兰数。
     * 其他方案数同为卡特兰数的还包括：凸多边形三角划分、n 对括号正确匹配数目 ...
     *
     * 回到本题，根据二叉搜索搜索的特性，若某个子树的根节点为 root，那么 root 的左子树任意节点值均比 root.val 要小，root 的右子树任意节点值均比 root.val 要大。
     * 因此，假设我们使用 [l,r] 连续段来构造二叉搜索树，并且选择了节点 t 作为二叉搜索树的根节点时，那么使用 [l,t−1] 构造出来的二叉搜索树均可作为根节点 t 的左子树；
     * 同理，使用 [t+1,r] 构造出来的二叉搜索树均可作为根节点 t 的右子树。
     *
     * 也就是说，我们可以设计递归函数 List<TreeNode> dfs(int l, int r)，含义为使用连续段 [l,r] 进行二叉搜索树构造，并返回相应集合。
     * 最终答案为 dfs(1, n)，起始我们可以枚举 [1,n] 范围内的的每个数 t 作为根节点，并递归 dfs(l, t - 1) 和 dfs(t + 1, r) 获取左右子树的集合 left 和 right，
     * 结合「乘法原理」，枚举任意左子树和任意右子树，即可得到 t 作为根节点的二叉搜索树方案集，枚举所有 t 后即可得到所有二叉搜索树的总集。
     *
     * 注意：当我们运用乘法原理，来构造以 t 为根节点的二叉搜索树时，其 left 或 right 某一边可能为空集，但此时我们仍要将非空的一边子树进行挂载。
     * 为了确保两层新循环的逻辑会被执行，对于空集我们不能使用 null 来代指，而要使用 [null] 来代指。
     *
     * 时间复杂度：卡特兰数
     * 空间复杂度：卡特兰数
     */
    public List<TreeNode> generateTrees(int n) {
        return dfs(1, n);
    }

    List<TreeNode> dfs(int l, int r) {
        if (l > r) {
            return new ArrayList<TreeNode>(){{add(null);}};
        }

        List<TreeNode> ans = new ArrayList<>();
        for (int i = l; i <= r; i++) {
            for (TreeNode x : dfs(l, i - 1)) {
                for (TreeNode y : dfs(i + 1, r)) {
                    TreeNode root = new TreeNode(i);
                    root.left = x;
                    root.right = y;
                    ans.add(root);
                }
            }
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
