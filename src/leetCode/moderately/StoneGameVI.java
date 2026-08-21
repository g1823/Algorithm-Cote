package leetCode.moderately;

import java.util.Arrays;

/**
 * @description: 1686. 石子游戏 VI
 *
 * 思路：排序贪心（非博弈DP）。Alice 拿走石头 i 时，不仅得到 aliceValues[i]，
 * 还让 Bob 失去潜在的 bobValues[i]，所以一块石头的"战略价值" = aliceValues[i] + bobValues[i]。
 * 按 a+b 降序排列，Alice 先手拿偶数位，Bob 拿奇数位，最后比较得分。
 * 复杂度：排序 O(n·log n)，空间 O(n)。
 */
public class StoneGameVI {

    /**
     * 排序贪心：判断 Alice 与 Bob 谁得分高
     *
     * 算法：按 aliceValues[i]+bobValues[i] 降序排列下标，Alice 拿偶数位、Bob 拿奇数位，
     * 累加各自真实价值后比较返回。
     *
     * 踩坑记录：
     * 1. 排序键写错：曾用 a-b（相对收益）做排序键，正确应为 a+b（战略价值）。
     *    因为 Alice 拿走石头 i 的同时也让 Bob 失去 b[i]，交换论证可得排序键是"和"而非"差"。
     * 2. 排序后丢索引：若直接对 sum=a+b 数组排序，则累加时无法还原对应的 a 和 b。
     *    必须对下标数组排序，再用下标取回真实价值。
     * 3. 排序方向写反：comparingInt 默认升序，位置 0 是最小值；需要降序（位置 0 为最大值），
     *    否则 Alice 先手拿到的是战略价值最小的石头。
     * 4. 倒序遍历奇偶错位：把循环改成 i 从 n-1 递减后，若仍用 i%2 判断归属，
     *    当 n 为偶数时最大的石头会被判给 Bob。正序遍历（位置 0 最大）奇偶归属才正确。
     *
     * @param aliceValues Alice 对每块石头的主观价值
     * @param bobValues   Bob 对每块石头的主观价值
     * @return 1 表示 Alice 得分更高，-1 表示 Bob 得分更高，0 表示平局
     */
    public int stoneGameVI(int[] aliceValues, int[] bobValues) {
        int n = aliceValues.length;
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) {
            idx[i] = i;
        }
        // 按 a+b 降序排列：位置 0 是战略价值最大的石头，Alice 先手拿偶数位
        Arrays.sort(idx, (x, y) -> (aliceValues[y] + bobValues[y]) - (aliceValues[x] + bobValues[x]));
        int alice = 0, bob = 0;
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                alice += aliceValues[idx[i]];
            } else {
                bob += bobValues[idx[i]];
            }
        }
        if (alice > bob) {
            return 1;
        } else if (alice < bob) {
            return -1;
        }
        return 0;
    }
}
