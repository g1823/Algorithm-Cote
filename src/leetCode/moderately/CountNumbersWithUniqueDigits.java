package leetCode.moderately;

import java.util.Arrays;

/**
 * @description: 357. 统计各位数字都不同的数字个数
 */
public class CountNumbersWithUniqueDigits {
    public static void main(String[] args) {
    }

    /**
     * 数位DP
     * 错误较多，ai改正，需要后续再思考
     */
    class solution {
        /**
         * cache[i][j]= k ,表示第i位，当前已经出现的数字状态为j时，后面总共有k个有效数字个数
         */
        int[][] cache;

        /**
         * 原始数字的每一位
         */
        int[] numbers;

        public int countNumbersWithUniqueDigits(int n) {
            // 初始题意理解错误，题目说的就是n位数字，理解成了n
            if (n == 0) return 1;

            numbers = new int[n];
            Arrays.fill(numbers, 9);

            // 在 Java 中，^ 是按位异或，所以：2 ^ 10 == 8。应该用：1 << 10
            cache = new int[n][1 << 10];
            for (int i = 0; i < n; i++) {
                Arrays.fill(cache[i], -1);
            }
            return dfs(0, true, true, 0);
        }

        public int dfs(int cur, boolean limit, boolean leadZero, int state) {
            // 到达结尾
            if (cur == numbers.length) {
                // 全0只会到达1次，也应该计数1
                return 1;
            }
            // 查缓存
            if (!limit && !leadZero && cache[cur][state] != -1) {
                return cache[cur][state];
            }

            int up = limit ? numbers[cur] : 9;
            int res = 0;
            for (int i = 0; i <= up; i++) {
                // 如果不是前导零，并且 i 已经用过，则跳过
                if (!leadZero && (state & (1 << i)) != 0) {
                    continue;
                }
                // 只有当前位不是前导零时，才把 i 加入状态
                int newState = state;
                if (!(leadZero && i == 0)) {
                    newState = state | (1 << i);
                }
                res += dfs(cur + 1, limit && i == up, leadZero && i == 0, newState);
            }

            if (!limit && !leadZero) {
                cache[cur][state] = res;
            }
            return res;
        }

    }


    /**
     * 排列组合：由于题目要求是n位，那意味着每一位都可以取0~9
     * 首先考虑两种边界情况：
     * - 当 n=0 时，0≤x<1，x 只有 1 种选择，即 0。
     * - 当 n=1 时，0≤x<10，x 有 10 种选择，即 0∼9。
     * 当 n=2 时，0≤x<100，x 的选择可以由两部分构成：只有一位数的 x 和有两位数的 x。
     * - 只有一位数的 x 可以由上述的边界情况计算。
     * - 有两位数的 x 可以由组合数学进行计算：第一位的选择有 9 种，即 1∼9，第二位的选择也有 9 种，即 0∼9 中除去第一位的选择。
     * 更一般地，含有 d （2≤d≤10）位数的各位数字都不同的数字 x 的个数可以由公式 9×A上（d-1）下9计算。
     * 再加上含有小于 d 位数的各位数字都不同的数字 x 的个数，即可得到答案。
     */
    class solution2 {
        public int countNumbersWithUniqueDigits(int n) {
            // 0位和1位特殊处理
            if (n == 0) {
                return 1;
            }
            if (n == 1) {
                return 10;
            }
            int res = 10, cur = 9;
            for (int i = 0; i < n - 1; i++) {
                cur *= 9 - i;
                res += cur;
            }
            return res;
        }
    }
}
