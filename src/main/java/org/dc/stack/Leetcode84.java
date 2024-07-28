package org.dc.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
 * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
 *
 * 示例 1:
 * 输入：heights = [2,1,5,6,2,3]
 * 输出：10
 * 解释：最大的矩形为图中红色区域，面积为 10
 *
 * 示例 2：
 * 输入： heights = [2,4]
 * 输出： 4
 *
 * 提示：
 * 1 <= heights.length <=105
 * 0 <= heights[i] <= 104
 *
 */
public class Leetcode84 {
    /**
     * 首先，面积最大矩形的高度一定是 heights 中的元素。这可以用反证法证明：假如高度不在 heights 中，比如 4，那我们可以增加高度直到触及某根柱子的顶部，比如增加到 5，由于矩形底边长不变，高度增加，
     * 我们得到了面积更大的矩形，矛盾，所以面积最大矩形的高度一定是 heights 中的元素。
     *
     * 假设 h=heights[i] 是矩形的高度，那么矩形的宽度最大是多少？我们需要知道：
     * 在 i 左侧的小于 h 的最近元素的下标 left，如果不存在则为 −1。这样 left+1 就是在 i 左侧的大于等于 h 的最近元素的下。
     * 在 i 右侧的小于 h 的最近元素的下标 right，如果不存在则为 n。这样 right−1 就是在 i 右侧的大于等于 h 的最近元素的下标。
     *
     * 比如示例 1，选择 i=2 这个柱子作为矩形高，那么左边小于 heights[2]=5 的最近元素的下标为 left=1，右边小于 heights[2]=5 的最近元素的下标为 right=4。
     * 那么矩形的宽度就是 right−left−1=4−1−1=2，矩形面积为 h⋅(right−left−1)=5⋅2=10。
     * 枚举 i，计算对应的矩形面积，更新答案的最大值。
     * 如何快速计算 left 和 right？这可以用单调栈求出。
     *
     * 复杂度分析
     * 时间复杂度：O(n)，其中 n 为 heights 的长度。
     * 空间复杂度：O(n)。
     */
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;

        int[] left = new int[n];
        Deque<Integer> st = new ArrayDeque<>();
        for (int i = 0 ; i < n; i++) {
            int x = heights[i];
            while (!st.isEmpty() && x <= heights[st.peek()]) {
                st.pop();
            }

            left[i] = st.isEmpty() ? -1 : st.peek();
            st.push(i);
        }

        int[] right = new int[n];
        st.clear();
        for (int i = n - 1; i >= 0; i--) {
            int x = heights[i];
            while (!st.isEmpty() && x <= heights[st.peek()]) {
                st.pop();
            }
            right[i] = st.isEmpty() ? n : st.peek();
            st.push(i);
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans  = Math.max(ans, heights[i] * (right[i] - left[i] - 1));
        }
        return ans;
    }
}
