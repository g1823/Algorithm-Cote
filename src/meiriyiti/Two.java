package meiriyiti;

/**
 * @Author:gj
 * @DateTime:2023/10/17 20:36
 * @description: 给你一个正整数 n ，请你计算在 [1，n] 范围内能被 3、5、7 整除的所有整数之和。
 * 返回一个整数，用于表示给定范围内所有满足约束条件的数字之和。
 **/
public class Two {
    public static void main(String[] args) {
        new Two().sumOfMultiples(7);
    }

    /**
     * 遍历相加
     * @param n
     * @return
     */
    public int sumOfMultiples(int n) {
        int result = 0;
        for (int i = 1; i <= n; i++) {
            result += (i % 3 == 0 || i % 5 == 0 || i % 7 == 0) ? i : 0;
        }
        return result;
    }

    /**
     * 找规律，从1到n能整除x的数字，一定是一个等差数列，可以使用等差数列求出这些数字的和。
     * 那么从1到n能被3、5、7整除的数的和 = 被3整除数之和 + 被5整除数之和 + 被7整除数之和
     * - 这些数字直接重合的数
     * @param n
     * @return
     */
    public int sumOfMultiples2(int n) {
        //根据容斥原理可知：
        //那么1-n范围内能被3、5、7任意一个整除的数之和 = f(n,3) + f(n,5) + f(n,7) - f(n,3*5) - f(n,3*7) - f(n,5*7) + f(n,3*5*7)
        return f(n, 3) + f(n, 5) + f(n, 7) - f(n, 3 * 5) - f(n, 3 * 7) - f(n, 5 * 7) + f(n, 3 * 5 * 7);
    }

    // 求1-m之间，能被n整除的数之和
    private int f(int n, int m) {
        /**
         * 考虑在区间 [1,n]内能被数 mmm 整除的整数，从小到大排序后成为一个等差数列，和为：
         *  使用等差数列求和公式
         */
        return (m + n / m * m) * (n / m) / 2;
    }
}
