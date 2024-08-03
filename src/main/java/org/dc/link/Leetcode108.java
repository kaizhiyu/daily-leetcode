package org.dc.link;

/**
 * 给你一个整数数组 nums ，其中元素已经按 升序 排列，请你将其转换为一棵平衡二叉搜索树。
 *
 * 示例 1：
 * 输入：nums = [-10,-3,0,5,9]
 * 输出：[0,-3,9,-10,null,5]
 * 解释：[0,-10,5,null,-3,null,9] 也将被视为正确答案：
 *
 * 示例 2：
 * 输入：nums = [1,3]
 * 输出：[3,1]
 * 解释：[1,null,3] 和 [3,1] 都是高度平衡二叉搜索树。
 *
 * 提示：
 * 1 <= nums.length <= 104
 * -104 <= nums[i] <= 104
 * nums 按 严格递增 顺序排列
 *
 */
public class Leetcode108 {
    /**
     * 如果做了 98 题 和 99 题，那么又看到这里的升序数组，然后应该会想到一个点，二叉搜索树的中序遍历刚好可以输出一个升序数组。
     * 所以题目给出的升序数组就是二叉搜索树的中序遍历。
     * 根据中序遍历还原一颗树，又想到了 105 题 和 106 题，通过中序遍历加前序遍历或者中序遍历加后序遍历来还原一棵树。前序（后序）遍历的作用呢？提供根节点！然后根据根节点，就可以递归的生成左右子树。
     * 这里的话怎么知道根节点呢？平衡二叉树，既然要做到平衡，我们只要把根节点选为数组的中点即可。
     * 综上，和之前一样，找到了根节点，然后把数组一分为二，进入递归即可。注意这里的边界情况，包括左边界，不包括右边界。
     *
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayToBST(nums, 0, nums.length);
    }

    private TreeNode sortedArrayToBST(int[] nums, int start, int end) {
        if (start == end) {
            return null;
        }

        int mid = (start + end) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = sortedArrayToBST(nums, start, mid);
        root.right = sortedArrayToBST(nums, mid + 1, end);
        return root;
    }

    // 其他解法请参考： https://leetcode.cn/problems/convert-sorted-array-to-binary-search-tree/solutions/15864/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by-24/

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
