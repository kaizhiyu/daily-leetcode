package org.dc.dp;

import java.util.Arrays;

/**
 *
 * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
 *
 * 示例 1：
 * 输入：[1,2,3,1]
 * 输出：4
 * 解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
 *      偷窃到的最高金额 = 1 + 3 = 4 。
 *
 * 示例 2：
 * 输入：[2,7,9,3,1]
 * 输出：12
 * 解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
 *      偷窃到的最高金额 = 2 + 9 + 1 = 12 。
 *
 * 提示：
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 400
 *
 */
public class Leetcode198 {
    /**
     * 回溯（自顶往下算-记忆化搜索）
     * 当前操作？枚举第i个房子选还是不选？
     * 子问题？从前i个房子中获得的最大金额和
     * 下一个子问题？分类讨论：
     *  不选：从前i-1个房子获得的最大金额和
     *   选：从前i-2个房子获得的最大金额和
     *
     *   dfs(i) = max(dfs(i-1), dfs(i-2) + nums[i])
     *   dfs(i) 的含义就是从前i个房子中获得的最大金额和
     *
     * 但是回溯的时间复杂度都是指数级别的，这个时候可以考虑是cache数组或者哈希表缓存计算结果
     *
     * 复杂度分析
     * 时间复杂度：O(n)，其中 n 为 nums 的长度。  状态个数 * 单个状态所需要的计算时间 本题就是 O(n) * O(1)
     * 空间复杂度：O(n)。
     */
    private int[] cache;

    public int rob(int[] nums) {
        int n = nums.length;
        cache = new int[n];
        Arrays.fill(cache, -1); // -1 表示没有计算过
        return dfs(nums, n - 1); // 从最后一个房子开始思考
    }

    // dfs(i) 表示从 nums[0] ... nums[i] 最多能偷多少
    private int dfs(int[] nums, int i) {
        if (i < 0) { // 递归边界，没有房子
            return 0;
        }

        if (cache[i] != -1) { // 之前计算过
            return cache[i];
        }

        int res = Math.max(dfs(nums, i - 1), dfs(nums, i - 2) + nums[i]); // 计算的max是在调用递归结束之后，也就是在递归中的归的过程中才发生的实际的计算，
        cache[i] = res; // 记忆化：保存计算结果
        return res;
    }

    /**
     *
     * 自底往上算-递推
     * 1:1翻译成递推
     *  dfs->f数组          dfs(i) = max(dfs(i-1), dfs(i-2)+nums[i]) -> f[i] = max(f[i-1], f[i-2] + nums[i]) 这样的化，需要对i=0|1做特殊处理，因为会产生负数下标，
     *  递归->循环
     *  递归边界->数组初始化   f[i+2] = max(f[i+1], f[i] + nums[i])
     *  问：为什么只需要把 f 的下标 +2，nums 的下标不需要 +2？
     *  答：把 nums 的下标 +2 是错误的。第一，nums[0] 和 nums[1] 要怎么算进答案中呢？第二，当 i=n−1 时，i+2=n+1，这会导致 nums 数组越界。
     *
     * 复杂度分析
     * 时间复杂度：O(n)。其中 n 为 nums 的长度。
     * 空间复杂度：O(n)。
     */
    public int rob2(int[] nums) {
        int n = nums.length;
        int[] f = new int[n + 2];
        for (int i = 0; i < n; i++) {
            f[i + 2] = Math.max(f[i + 1], f[i] + nums[i]);
        }
        return f[n + 1];
    }

    /**
     *
     * 当前=max（上一个，上上一个+nums[i]）
     * f0 上一个 ; f1 上上一个
     * newF = max(f0, f1+nums[i])
     * f0 = f1
     * f1 = newF
     *
     * 复杂度分析
     * 时间复杂度：O(n)。其中 n 为 nums 的长度。
     * 空间复杂度：O(1)。仅用到若干额外变量。
     */
    public int rob3(int[] nums) {
        int f0 = 0;
        int f1 = 0;
        for (int x : nums) {
            int newF = Math.max(f1, f0 + x);
            f0 = f1;
            f1 = newF;
        }
        return f1;
    }
}
