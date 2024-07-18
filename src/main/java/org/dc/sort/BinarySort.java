package org.dc.sort;

/**
 *
 * 有序数组中二分查找的四种类型（下面的转换仅适用于数组中都是整数），lower_bound返回下标
 * 第一个大于等于x的下标： low_bound(x)
 * 第一个大于x的下标：可以转换为第一个大于等于 x+1 的下标 ，low_bound(x+1)
 * 最后一个一个小于x的下标：可以转换为数组中第一个大于等于 x 的下标的左边位置, low_bound(x) - 1;
 * 最后一个小于等于x的下标：可以转换为数组中第一个大于等于 x+1 的下标的左边位置, low_bound(x+1) - 1;
 *
 * 红蓝染色法
 * left指针掌管左边红色区域，红色表示true即所求值及其左侧，right指针掌管右边蓝色区域，蓝色表示false即所求值右侧，两者互不冲突，通过不断向目标区域靠近，扩大两个指针的掌管区域，直到两者掌管的区域接壤
 *
 * 使用闭区间时，L-1必定是红色即false，R+1必定是蓝色即true，这就是循环不变量
 *
 * 关键不在于区间里的元素具有什么性质，而是区间外面的元素具有什么性质，即将区间外染成红色与蓝色。
 * 根据以上两点，在做题时关键即确定二分条件函数 isBlue()，判断是否满足条件，满足条件则right左移使右侧变蓝 ，不满足条件则left右移使左侧变红
 *
 * 代码规范
 * 注意代码区间开闭写法，之前提过的在LeetCode题解中大家看到的left与right取初值有时不一样，在二分时left指针有时等于mid+1有时等于mid，这是区间开闭的问题，不必纠结太多，三种都可以，我习惯于使用闭区间
 * 如果找不到比较元素，则可将数组最后一个元素单独提出作为比较元素，且right=n-2，因为若最后一个元素为所查找元素，二分查找时left也可以超出right=n-2的范围找到该元素；若不是所查找元素，单独提出也没有影响.
 * 根据上一点就明白为什么有时LeetCode题解在二分前就去掉了数组某个元素，这不会影响最终结果，可能是因为能判断这个元素不是所求结果，或者这个结果在最后一次循环时能够遍历到（闭区间时left能够遍历到数组长度+1）
 *
 * reference:
 * 1) https://blog.csdn.net/qq_45808700/article/details/129247507
 * 2) https://leetcode.cn/circle/discuss/SqopEo/
 */
public class BinarySort {
    /**
     *
     * 闭区间[L,R],M指向当前正在询问的数，红色染色表示false，本题即<8；蓝色染色表示true，即>=8；白色背景表示不确定。由于是闭区间，所以针对例题数组，L=0，R=n-1=9，m=L+(R-L)/2=0+(9-0)/2=4(向下取整)
     * 循环不变量：L-1始终是红色；R-1始终是蓝色。
     *
     */
    public int lowBound(int[] nums, int target) {
        // 闭区间 [left, right]
        int left = 0, right = nums.length - 1;
        // 区间不为空
        while (left <= right) {
            /**
             * 循环不变量：left - 1 与 right + 1
             * nums[left-1] < target
             * nums[right+1] >= target
             */
            int mid = (right - left) >>> 1 + left;
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }

    /**
     *
     * 半闭半开[)区间
     *
     */
    public int lowBound1(int[] nums, int target) {
        int left = 0, right = nums.length;

        while (left < right) {
            int mid = (right - left) >>> 1 + left;
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }

    /**
     *
     * 开区间()
     */
    public int lowBound2(int[] nums, int target) {
        int left = -1, right = nums.length;
        while (left + 1 < right) {
            int mid = (right - left) >>> 1 + left;
            if (nums[mid] < target) {
                left = mid;
            } else {
                right = mid;
            }
        }

        return right;
    }
}
