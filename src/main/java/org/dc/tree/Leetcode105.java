package org.dc.tree;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 105. 从前序与中序遍历序列构造二叉树 【medium】
 *
 * 给定两个整数数组 preorder 和 inorder ，其中 preorder 是二叉树的先序遍历， inorder 是同一棵树的中序遍历，请构造二叉树并返回其根节点。
 *
 * 示例 1:
 * 输入: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
 * 输出: [3,9,20,null,null,15,7]
 *
 * 示例 2:
 * 输入: preorder = [-1], inorder = [-1]
 * 输出: [-1]
 *
 * 提示:
 * 1 <= preorder.length <= 3000
 * inorder.length == preorder.length
 * -3000 <= preorder[i], inorder[i] <= 3000
 * preorder 和 inorder 均 无重复 元素
 * inorder 均出现在 preorder
 * preorder 保证 为二叉树的前序遍历序列
 * inorder 保证 为二叉树的中序遍历序列
 *
 */
public class Leetcode105 {
    /**
     * 解法一：
     * 前序遍历：按照「根-左子树-右子树」的顺序遍历二叉树。
     * 中序遍历：按照「左子树-根-右子树」的顺序遍历二叉树。
     *
     * 参考：https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/solutions/2646359/tu-jie-cong-on2-dao-onpythonjavacgojsrus-aob8/
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length;
        if (n == 0) {
            return null;
        }

        int leftSize = indexOf(inorder, preorder[0]);
        int[] pre1 = Arrays.copyOfRange(preorder, 1, leftSize + 1);
        int[] pre2 = Arrays.copyOfRange(preorder, leftSize + 1, n);
        int[] in1 = Arrays.copyOfRange(inorder, 0, leftSize);
        int[] in2 = Arrays.copyOfRange(inorder, leftSize + 1, n);

        TreeNode left = buildTree(pre1, in1);
        TreeNode right = buildTree(pre2, in2);
        return new TreeNode(preorder[0], left, right);
    }

    private int indexOf(int[] a, int x) {
        for (int i = 0; ;i++) {
            if (a[i] == x) {
                return i;
            }
        }
    }

    /**
     * 解法二：
     * 上面的写法有两个优化点：
     * 用一个哈希表（或者数组）预处理 inorder 每个元素的下标，这样就可以 O(1) 查到 preorder[0] 在 inorder 的位置，从而 O(1) 知道左子树的大小。
     * 把递归参数改成子数组下标区间（左闭右开区间）的左右端点，从而避免复制数组。
     *
     * 复杂度分析
     * 时间复杂度：O(n)，其中 n 为 preorder 的长度。递归 O(n) 次，每次只需要 O(1) 的时间。
     * 空间复杂度：O(n)。
     * 注：由于哈希表常数比数组大，实际运行效率可能不如写法一。
     */
    public TreeNode buildTree2(int[] preorder, int[] inorder) {
        int n = preorder.length;
        Map<Integer, Integer> index = new HashMap<>(n); // 预分配空间
        for (int i = 0; i < n; i++) {
            index.put(inorder[i], i);
        }

        return dfs(preorder, 0, n, inorder, 0, n, index); // 左闭右开区间
    }

    private TreeNode dfs(int[] preorder, int preL, int preR, int[] inorder, int inL, int inR, Map<Integer, Integer> index) {
        if (preL == preR) { // 空节点
            return null;
        }

        int leftSize = index.get(preorder[preL]) - inL; // 左子树的大小
        TreeNode left = dfs(preorder, preL + 1, preL + 1 + leftSize, inorder, inL, inL + leftSize, index);
        TreeNode right = dfs(preorder, preL + 1 + leftSize, preR, inorder, inL + 1 + leftSize, inR, index);
        return new TreeNode(preorder[preL], left, right);
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
