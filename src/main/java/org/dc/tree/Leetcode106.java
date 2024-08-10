package org.dc.tree;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 106. 从中序与后序遍历序列构造二叉树 【medium】
 *
 * 给定两个整数数组 inorder 和 postorder ，其中 inorder 是二叉树的中序遍历， postorder 是同一棵树的后序遍历，请你构造并返回这颗 二叉树 。
 *
 * 示例 1:
 * 输入：inorder = [9,3,15,20,7], postorder = [9,15,7,20,3]
 * 输出：[3,9,20,null,null,15,7]
 *
 * 示例 2:
 * 输入：inorder = [-1], postorder = [-1]
 * 输出：[-1]
 *
 * 提示:
 * 1 <= inorder.length <= 3000
 * postorder.length == inorder.length
 * -3000 <= inorder[i], postorder[i] <= 3000
 * inorder 和 postorder 都由 不同 的值组成
 * postorder 中每一个值都在 inorder 中
 * inorder 保证是树的中序遍历
 * postorder 保证是树的后序遍历
 */
public class Leetcode106 {
    /**
     * 解法一
     * 中序遍历和后续遍历的特性
     * 首先来看题目给出的两个已知条件 中序遍历序列 和 后序遍历序列 根据这两种遍历的特性我们可以得出两个结论：
     * 在后序遍历序列中,最后一个元素为树的根节点
     * 在中序遍历序列中,根节点的左边为左子树，根节点的右边为右子树
     *
     * 根据中序遍历和后续遍历的特性我们进行树的还原过程分析：
     * 首先在后序遍历序列中找到根节点(最后一个元素)
     * 根据根节点在中序遍历序列中找到根节点的位置
     * 根据根节点的位置将中序遍历序列分为左子树和右子树
     * 根据根节点的位置确定左子树和右子树在中序数组和后续数组中的左右边界位置
     * 递归构造左子树和右子树
     * 返回根节点结束
     *
     * 树的还原过程变量定义
     * 需要定义几个变量帮助我们进行树的还原：
     * HashMap memo 需要一个哈希表来保存中序遍历序列中,元素和索引的位置关系.因为从后序序列中拿到根节点后，要在中序序列中查找对应的位置,从而将数组分为左子树和右子树
     * int ri 根节点在中序遍历数组中的索引位置
     * 中序遍历数组的两个位置标记 [is, ie]，is 是起始位置，ie 是结束位置
     * 后序遍历数组的两个位置标记 [ps, pe] ps 是起始位置，pe 是结束位置
     *
     * 位置关系的计算
     * 在找到根节点位置以后，我们要确定下一轮中，左子树和右子树在中序数组和后续数组中的左右边界的位置。
     * 左子树-中序数组 is = is, ie = ri - 1
     * 左子树-后序数组 ps = ps, pe = ps + ri - is - 1 (pe计算过程解释，后续数组的起始位置加上左子树长度-1 就是后后序数组结束位置了，左子树的长度 = 根节点索引-左子树)
     * 右子树-中序数组 is = ri + 1, ie = ie
     * 右子树-后序数组 ps = ps + ri - is, pe - 1
     *
     * 参考： https://leetcode.cn/problems/construct-binary-tree-from-inorder-and-postorder-traversal/solutions/50561/tu-jie-gou-zao-er-cha-shu-wei-wan-dai-xu-by-user72/
     */
    Map<Integer, Integer> memo = new HashMap<>();
    int[] post;
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        for (int i = 0; i < inorder.length; i++) memo.put(inorder[i], i);

        post = postorder;
        return buildTree(0, inorder.length - 1, 0, post.length - 1);
    }

    public TreeNode buildTree(int is, int ie, int ps, int pe) {
        if (ie < is || pe < ps) return null;

        int root = post[pe];
        int ri = memo.get(root);

        TreeNode node = new TreeNode(root);
        node.left = buildTree(is, ri - 1, ps, ps + ri - is - 1);
        node.right = buildTree(ri + 1, ie, ps + ri - is, pe - 1);
        return node;
    }

    /**
     * 解法二：
     * 中序遍历：按照「左子树-根-右子树」的顺序遍历二叉树。
     * 后序遍历：按照「左子树-右子树-根」的顺序遍历二叉树。
     * 递归边界：如果 postorder 的长度是 0（此时 inorder 的长度也是 0），对应着空节点，返回空。
     *
     * 复杂度分析
     * 时间复杂度：O(n^2)，其中 n 为 postorder 的长度。最坏情况下二叉树是一条链，我们需要递归 O(n) 次，每次都需要 O(n) 的时间查找 postorder[n−1] 和复制数组。
     * 空间复杂度：O(n^2)。
     *
     * 参考： https://leetcode.cn/problems/construct-binary-tree-from-inorder-and-postorder-traversal/solutions/2647794/tu-jie-cong-on2-dao-onpythonjavacgojsrus-w8ny/
     */
    public TreeNode buildTree2(int[] inorder, int[] postorder) {
        int n = postorder.length;
        if (n == 0) { // 空节点
            return null;
        }

        int leftSize = indexOf(inorder, postorder[n - 1]); // 左子树的大小
        int[] in1 = Arrays.copyOfRange(inorder, 0, leftSize);
        int[] in2 = Arrays.copyOfRange(inorder, leftSize + 1, n);
        int[] post1 = Arrays.copyOfRange(postorder, 0, leftSize);
        int[] post2 = Arrays.copyOfRange(postorder, leftSize, n - 1);

        TreeNode left = buildTree2(in1, post1);
        TreeNode right = buildTree2(in2, post2);
        return new TreeNode(postorder[n - 1], left, right);
    }

    // 返回 x 在 a 中的下标，保证 x 一定在 a 中
    private int indexOf(int[] a, int x) {
        for (int i = 0; ; i++) {
            if (a[i] == x) {
                return i;
            }
        }
    }

    /**
     * 解法三：
     *
     * 上面的写法有两个优化点：
     * 用一个哈希表（或者数组）预处理 inorder 每个元素的下标，这样就可以 O(1) 查到 postorder[n−1] 在 inorder 的位置，从而 O(1) 知道左子树的大小。
     * 把递归参数改成子数组下标区间（左闭右开区间）的左右端点，从而避免复制数组。
     *
     * 参考： https://leetcode.cn/problems/construct-binary-tree-from-inorder-and-postorder-traversal/solutions/2647794/tu-jie-cong-on2-dao-onpythonjavacgojsrus-w8ny/
     *
     * 复杂度分析
     * 时间复杂度：O(n)，其中 n 为 inorder 的长度。递归 O(n) 次，每次只需要 O(1) 的时间。
     * 空间复杂度：O(n)。
     * 注：由于哈希表常数比数组大，实际运行效率可能不如写法一。
     */
    public TreeNode buildTree3(int[] inorder, int[] postorder) {
        int n = inorder.length;
        Map<Integer, Integer> index = new HashMap<>(n); // 预分配空间
        for (int i = 0; i < n; i++) {
            index.put(inorder[i], i);
        }
        return dfs(inorder, 0, n, postorder, 0, n, index); // 左闭右开区间
    }

    private TreeNode dfs(int[] inorder, int inL, int inR, int[] postorder, int postL, int postR, Map<Integer, Integer> index) {
        if (postL == postR) { // 空节点
            return null;
        }

        int leftSize = index.get(postorder[postR - 1]) - inL; // 左子树的大小
        TreeNode left = dfs(inorder, inL, inL + leftSize, postorder, postL, postL + leftSize, index);
        TreeNode right = dfs(inorder, inL + leftSize + 1, inR, postorder, postL + leftSize, postR - 1, index);
        return new TreeNode(postorder[postR - 1], left, right);
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
