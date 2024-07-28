package org.dc.array;

import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
 * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
 *
 * 示例 1：
 * 输入：nums = [100,4,200,1,3,2]
 * 输出：4
 * 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
 *
 * 示例 2：
 * 输入：nums = [0,3,7,2,5,8,4,6,0,1]
 * 输出：9
 *
 * 提示：
 * 0 <= nums.length <= 105
 * -109 <= nums[i] <= 109
 *
 */
public class Leetcode128 {
    /**
     * 对于数组中存在的连续序列，为了统计每个连续序列的长度，我们希望直接定位到每个连续序列的起点，从起点开始遍历每个连续序列，从而获得长度。
     * 那么如何获取到每个连续序列的起点呢，或者说什么样的数才是一个连续序列的起点？
     * 答案是这个数的前一个数不存在于数组中，因为我们需要能够快速判断当前数num的前一个数num - 1是否存在于数组中。
     *
     * 同时当我们定位到起点后，我们就要遍历这个连续序列，什么时候是终点呢？
     * 答案是当前数num的后一个数nunm + 1不存在于数组中，因此我们需要能够快速判断当前数num的后一个数num + 1是否存在于数组中。
     * 为了实现上述需求，我们使用哈希表来记录数组中的所有数，以实现对数值的快速查找。
     *
     */
    public int longestConsecutive(int[] nums) {
        int res = 0;
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }

        int seqLen;
        for (int num : nums) {
            // 该步骤用于判断是否 num 可以作为起点
            if (!numSet.contains(num - 1)) {
                seqLen = 1;
                while (numSet.contains(++num)) {
                    seqLen++;
                }
                res = Math.max(res, seqLen);
            }
        }
        return res;
    }
}
