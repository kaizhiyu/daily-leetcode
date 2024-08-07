package org.dc.tree;

/**
 * 96. 不同的二叉搜索树
 *
 * 给你一个整数 n ，求恰由 n 个节点组成且节点值从 1 到 n 互不相同的 二叉搜索树 有多少种？返回满足题意的二叉搜索树的种数。
 *
 * 示例 1：
 * 输入：n = 3
 * 输出：5
 *
 * 示例 2：
 * 输入：n = 1
 * 输出：1
 *
 * 提示：
 * 1 <= n <= 19
 */
public class Leetcode96 {


    /**
     * 区间 DP（优化）
     * 求解完使用 [1,n] 共 n 个连续数所能构成的 BST 个数后，再来思考一个问题：使用 [L,R] 共 n=R−L+1 个连续数，所能构成的 BST 个数又是多少。
     *
     * 答案是一样的。
     * 由 n 个连续数构成的 BST 个数仅与数值个数有关系，与数值大小本身并无关系。
     * 由于可知，我们上述的「区间 DP」必然进行了大量重复计算，例如 f[1][3] 和 f[2][4] 同为大小为 3 的区间，却被计算了两次。
     * 调整我们的状态定义：定义 f[k] 为考虑连续数个数为 k 时，所能构成的 BST 的个数。
     * 不失一般性考虑 f[i] 如何计算，仍用 [1,i] 中哪个数值作为根节点进行考虑。假设使用数值 j 作为根节点，则有 f[j−1]×f[i−j] 个 BST 可贡献到 f[i]，而 j 共有 i 个取值（j∈[1,i]）。
     *
     * 即有：
     * f[i]=
     * j=1
     * ∑ (f[j−1] × f[i−j])
     * i
     * 同时有初始化 f[0]=1，含义为没有任何连续数时，只有“空树”一种合法方案。
     *
     * 参考：
     * （1）https://leetcode.cn/problems/unique-binary-search-trees/solutions/2438266/gong-shui-san-xie-cong-qu-jian-dp-dao-qi-fz5z/
     * （2）https://leetcode.cn/problems/unique-binary-search-trees/solutions/550154/96-bu-tong-de-er-cha-sou-suo-shu-dong-ta-vn6x/
     */
    public int numTrees(int n) {
        int[] f = new int[n + 10];
        f[0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                f[i] += f[j - 1] * f[i - j];
            }
        }
        return f[n];
    }

    /**
     * 用升序序列 1...n 构建一棵二叉搜索树，根节点任选一个，有 n 种选法。
     * 但是选定了一个数作为根节点 i(i∈[1,n])，那么由于二叉搜索树的特性，左子树只能用 1...i-1 构建，右子树只能用 i+1...n 构建。
     * 设用 1...i-1 构建左子树有x种方法，用 i+1...n 构建右子树有 y 种方法，那么以 i 为根的二叉搜索树共 x×y 种。
     * 把 i 依次取 1,2,3...n 的所得的结果累加起来便是最终结果。
     *
     * 递归求解
     * 有一点要明确的是，用元素个数相同的升序序列构造出的二叉搜索树的种类是相同的。比如用 1,2,3 构造出的二叉搜索树种数和用 4,5,6 构造出来的种数相同。
     * 可以看到，求解用 1...i-1 构建左子树的方法总数是原问题的一个子问题。由于(1)的结论，我们只需要关心这个序列有多少个元素即可，不需要知道它们的大小。
     * 递归出口：当 n=0 时只能构造出空树，空树认为是二叉搜索树；当 n=1 时，只有 1 个节点，也算二叉搜索树。
     *
     * 递归函数dp定义：给出一个 n ，返回以 1...n 构造出的二叉搜索树的方法种数。
     * 根据以上分析写出递归式
     *
     * dp(n)={
     * 1,  if n=0 or n=1
     * i=1
     * ∑  dp(i−1)×dp(n−i)， if n ≥2
     * n
     *
     * 参考：
     * （1）https://leetcode.cn/problems/unique-binary-search-trees/solutions/378347/bu-tong-de-er-cha-sou-suo-shu-cong-yuan-shi-de-di-/
     */
    public int numTrees2(int n) {
        int[] record = new int[n + 1];
        record[0] = 1;
        return helper(n, record);
    }

    public int helper(int n, int[] record) {
        if (n == 0 || n == 1) {     // 递归出口
            return 1;
        }
        if (record[n] > 0) {        // 如果计算过了
            return record[n];       // 提前返回结束递归求解，省时省空间
        }
        for (int i = 1; i <= n; i++) {
            record[n] += helper(i - 1, record) * helper(n - i, record);
        }
        return record[n];
    }

}
