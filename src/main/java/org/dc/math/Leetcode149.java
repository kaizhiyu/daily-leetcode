package org.dc.math;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 给你一个数组 points ，其中 points[i] = [xi, yi] 表示 X-Y 平面上的一个点。求最多有多少个点在同一条直线上。
 *
 * 输入：points = [[1,1],[2,2],[3,3]]
 * 输出：3
 *
 * 输入：points = [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]]
 * 输出：4
 *
 * 提示：
 *
 * 1 <= points.length <= 300
 * points[i].length == 2
 * -104 <= xi, yi <= 104
 * points 中的所有点 互不相同
 *
 */
public class Leetcode149 {
    /**
     * 先确定一个点，再计算其余各点与这个点所连直线的斜率，如果斜率一样，说明大家在一条直线上，那么，我们只要拿一个Map来存放所有斜率对应的点数即可。
     * 我们可以考虑枚举所有的点，假设直线经过该点时，该直线所能经过的最多的点数。
     * 假设我们当前枚举到点 i，如果直线同时经过另外两个不同的点 j 和 k，那么可以发现点 i 和点 j 所连直线的斜率恰等于点 i 和点 k 所连直线的斜率。
     * 于是我们可以统计其他所有点与点 i 所连直线的斜率，出现次数最多的斜率即为经过点数最多的直线的斜率，其经过的点数为该斜率出现的次数加一（点 i 自身也要被统计）。
     * 如何记录斜率
     * 需要注意的是，浮点数类型可能因为精度不够而无法足够精确地表示每一个斜率，因此我们需要换一种方法来记录斜率。
     * 一般情况下，斜率可以表示为 slope=Δy/Δx的形式，因此我们可以用分子和分母组成的二元组来代表斜率。但注意到存在形如
     * 1 / 2 = 2 / 4 这样两个二元组不同，但实际上两分数的值相同的情况，所以我们需要将分数 Δy / Δx 化简为最简分数的形式。
     *
     * 将分子和分母同时除以二者绝对值的最大公约数，可得二元组 (
     * Δx / gcd(∣Δx∣,∣Δy∣), Δy / gcd(∣Δx∣,∣Δy∣))。令 mx = Δx / gcd(∣Δx∣,∣Δy∣)，my = Δy / gcd(∣Δx∣,∣Δy∣)，则上述化简后的二元组为 (mx,my)。
     * 此外，因为分子分母可能存在负数，为了防止出现形如 −1/ 2 = 1/−2 的情况，我们还需要规定分子为非负整数，如果 my 为负数，我们将二元组中两个数同时取相反数即可。
     * 特别地，考虑到 mx 和 my 两数其中有一个为 0 的情况（因为题目中不存在重复的点，因此不存在两数均为 0 的情况），此时两数不存在数学意义上的最大公约数，因此我们直接特判这两种情况。
     * 当 mx 为 0 时，我们令 my=1；当 my 为 0 时，我们令 mx=1 即可。
     * 经过上述操作之后，即可得到最终的二元组 (mx,my)。在本题中，因为点的横纵坐标取值范围均为 [−10^4,10^4]，所以斜率 slope= my / mx 中，mx 落在区间 [−2×10^4,2×10^4] 内，my 落在区间 [0,2×10^4] 内。
     * 注意到 32 位整数的范围远超这两个区间，因此我们可以用单个 32 位整型变量来表示这两个整数。具体地，我们令 val=my+(2×10^4+1)×mx 即可。
     *
     * 参考： https://leetcode.cn/problems/max-points-on-a-line/
     */
    public int maxPoints(int[][] points) {
        int n = points.length;
        if (n <= 2) {
            return n;
        }

        int max = 0;
        for (int i = 0; i < n - 1; i++) {
            if (max > n / 2 || max > n - i) {
                break;
            }

            // key是斜率，value是出现的次数
            Map<Integer, Integer> map = new HashMap<>();
            for (int j = i + 1; j < n; j++) {
                // 斜率可以表示为 slope= Δy/Δx 的形式
                int x = points[j][0] - points[i][0];
                int y = points[j][1] - points[i][1];

                if (x == 0) {
                    y = 1;
                } else if (y == 0) {
                    x = 1;
                } else {
                    // 因为分子分母可能存在负数，为了防止出现形如 2/−1 = −2/1 的情况，我们还需要规定分子为非负整数，如果 my 为负数，我们将二元组中两个数同时取相反数即可
                    if ( y < 0 ) {
                        x = -x;
                        y = -y;
                    }

                    // 计算x和y的最大公约数
                    // 两者都除以最大公约数，防止出现 2/1 4/2不一致的情况
                    int gcd = gcd(Math.abs(x), Math.abs(y));
                    x = x / gcd;
                    y = y / gcd;
                }

                // 把y/x转换成一个整数，防止使用double精度不准的问题
                int key = x + y * 20001;
                map.put(key, map.getOrDefault(key, 0) + 1);
            }

            // 计算最大值，这里注意加上当前的点
            for (Integer value : map.values()) {
                max = Math.max(max, value + 1);
            }
        }
        return max;
    }

    private int gcd(int x, int y) {
        return y == 0 ? x : gcd(y, x % y);
    }
}
