package org.dc.array;

/**
 * 152. 乘积最大子数组 【medium】
 *
 * 给你一个整数数组 nums ，请你找出数组中乘积最大的非空连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
 * 测试用例的答案是一个 32-位 整数。
 *
 * 示例 1:
 * 输入: nums = [2,3,-2,4]
 * 输出: 6
 * 解释: 子数组 [2,3] 有最大乘积 6。
 *
 * 示例 2:
 * 输入: nums = [-2,0,-1]
 * 输出: 0
 * 解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。
 *
 * 提示:
 * 1 <= nums.length <= 2 * 104
 * -10 <= nums[i] <= 10
 * nums 的任何前缀或后缀的乘积都 保证 是一个 32-位 整数
 *
 */
public class Leetcode152 {
    /**
     * 如果我们用 fmax(i) 开表示以第 i 个元素结尾的乘积最大子数组的乘积，a 表示输入参数 nums，那么根据「53. 最大子序和」的经验，我们很容易推导出这样的状态转移方程：
     * fmax(i)=max i=1..n{f(i−1)×ai,ai}
     * 它表示以第 i 个元素结尾的乘积最大子数组的乘积可以考虑 ai加入前面的 fmax(i−1) 对应的一段，或者单独成为一段，这里两种情况下取最大值。求出所有的 fmax(i) 之后选取最大的一个作为答案。
     *
     * 可是在这里，这样做是错误的。为什么呢？
     * 因为这里的定义并不满足「最优子结构」。具体地讲，如果 a={5,6,−3,4,−3}，那么此时 fmax对应的序列是 {5,30,−3,4,−3}，按照前面的算法我们可以得到答案为 30，即前两个数的乘积，而实际上答案应该是全体数字的乘积。
     * 我们来想一想问题出在哪里呢？问题出在最后一个 −3 所对应的 fmax 的值既不是 −3，也不是 4×−3，而是 5×30×(−3)×4×(−3)。所以我们得到了一个结论：当前位置的最优解未必是由前一个位置的最优解转移得到的。
     *
     * 考虑当前位置如果是一个负数的话，那么我们希望以它前一个位置结尾的某个段的积也是个负数，这样就可以负负得正，并且我们希望这个积尽可能「负得更多」，即尽可能小。如果当前位置是一个正数的话，我们更希望以它前一个位置结尾的某个段的积也是个正数，并且希望它尽可能地大。于是这里我们可以再维护一个 f
     * min(i)，它表示以第 i 个元素结尾的乘积最小子数组的乘积，那么我们可以得到这样的动态规划转移方程：
     *
     * fmax(i) = max(i=1..n){fmax(i−1)×ai,fmin(i−1)×ai,ai}
     * fmin(i) = min(i=1..n){fmax(i−1)×ai,fmin(i−1)×ai,ai}
     * 它代表第 i 个元素结尾的乘积最大子数组的乘积 fmax(i)，可以考虑把 ai加入第 i−1 个元素结尾的乘积最大或最小的子数组的乘积中，二者加上 ai，三者取大，就是第 i 个元素结尾的乘积最大子数组的乘积。
     * 第 i 个元素结尾的乘积最小子数组的乘积 fmin(i) 同理。
     */
    public int maxProduct(int[] nums) {
        int length = nums.length;
        long[] maxF = new long[length];
        long[] minF = new long[length];
        for (int i = 0; i < length; i++) {
            maxF[i] = nums[i];
            minF[i] = nums[i];
        }

        for (int i = 1; i < length; ++i) {
            maxF[i] = Math.max(maxF[i - 1] * nums[i], Math.max(nums[i], minF[i - 1] * nums[i]));
            minF[i] = Math.min(minF[i - 1] * nums[i], Math.min(nums[i], maxF[i - 1] * nums[i]));
            if (minF[i] < (-1 << 31)) {
                minF[i] = nums[i];
            }
        }

        int ans = (int) maxF[0];
        for (int i = 1; i < length; ++i) {
            ans = Math.max(ans, (int) maxF[i]);
        }
        return ans;
    }

    /**
     * 考虑优化空间。
     * 由于第 i 个状态只和第 i−1 个状态相关，根据「滚动数组」思想，我们可以只用两个变量来维护 i−1 时刻的状态，一个维护 fmax，一个维护 fmin。
     *
     * 复杂度分析
     * 记 nums 元素个数为 n。
     * 时间复杂度：程序一次循环遍历了 nums，故渐进时间复杂度为 O(n)。
     * 空间复杂度：优化后只使用常数个临时变量作为辅助空间，与 n 无关，故渐进空间复杂度为 O(1)。
     */
    public int maxProduct2(int[] nums) {
        long maxF = nums[0], minF = nums[0];
        int ans = nums[0];
        int length = nums.length;

        for (int i = 1; i < length; ++i) {
            long mx = maxF, mn = minF;
            maxF = Math.max(mx * nums[i], Math.max(nums[i], mn * nums[i]));
            minF = Math.min(mn * nums[i], Math.min(nums[i], mx * nums[i]));
            if (minF < -1<<31) {
                minF = nums[i];
            }
            ans = Math.max((int)maxF, ans);
        }
        return ans;
    }
}
