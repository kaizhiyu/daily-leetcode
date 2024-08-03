package org.dc.math;

/**
 * 最大公约数 Greatest Common Divisor (GCD)：  整数 a 和 b 的 GCD 是指能同时整除 a 和 b 的最大整数，记为 gcd(a,b)。
 *
 * 由于 -a 的因子和 a 的因子相同，因此 gcd(a, b) = gcd(|a|, |b|)。编码时只关注正整数的最大公约数。
 * 性质：
 * （1）gcd(a, b) = gcd(a, a+b) = gcd(a, k*a+b)
 * （2）gcd(ka, kb) = k*gcd(a, b)
 * （3）定义多个整数的最大公约数：gcd(a, b, c)=gcd(gcd(a, b), c)
 * （4）若 gcd(a, b)=d，则 gcd(a/d, b/d)=1，即 a/d 与 b/d 互素。这个定理很重要
 * （5）gcd(a+cb, b)=gcd(a, b)
 *
 * 参考：
 * （1）https://blog.csdn.net/Ljnoit/article/details/104730787/
 * （2）https://blog.csdn.net/m0_52711790/article/details/128904796
 * （3）https://zhuanlan.zhihu.com/p/97106642
 */
public class Gcd {
    /**
     * 辗转相除法
     *
     */
    public static int gcd(int a, int b) {
        if(b == 0) return a;
        return gcd(b, a % b);
    }

    public static void main(String[] args) {
        System.out.println(Gcd.gcd(1, 2));
        System.out.println(Gcd.gcd(4, 2));
    }
}
