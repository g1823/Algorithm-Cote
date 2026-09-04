package leetCode.moderately;

import java.util.Arrays;

/**
 * @description: 518. 零钱兑换 II
 * 本道题核心点：遍历顺序决定动态规划求的是组合还是排列。 可以形成详细笔记
 */
public class Change {
    public static void main(String[] args) {
        int amount = 5;
        int[] coins = new int[]{1, 2, 5};
        System.out.println(new Change().change(amount, coins));
    }

    /**
     * 错误：当前解法会求成排列，而当前题目要求的是组合，不看顺序
     * 假设有硬币1和2，求3
     * 1,1,1
     * 1,2
     * 2,1
     */
    public int change(int amount, int[] coins) {
        // 对硬币面额进行排序，后续可提前结束
        Arrays.sort(coins);
        /**
         * dp[i] 表示凑出金额 i 的最多方法
         */
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int i = 1; i < dp.length; i++) {
            int count = 0;
            for (int coin : coins) {
                // 硬币从小到大排序，当前硬币已经比硬币更大，直接结束
                if (i < coin) {
                    break;
                }
                count += dp[i - coin];
            }
            dp[i] = count;
        }
        return dp[amount];
    }

    /**
     * 动态规划(完全背包)，这次只计算组合
     * 因为外层固定了硬币顺序。当处理到 coin = 2 时，dp[i] 里只包含了已经处理过的硬币（即 ≤ 2 的硬币）的组合。
     * - 计算 dp[3] 时，只有在 coin=1 时算了一次 [1,1,1]，在 coin=2 时只允许在 dp[1]（由硬币1组成）的基础上加一个 2，得到了 [1,2]。
     * - 它绝对不会在 coin=2 的时候去尝试组合出 [2,1]，因为当处理 coin=2 时，后面还没轮到 coin=1（或者说不允许再回头用更小的硬币去填补顺序）。
     * 这样强行规定了组合中硬币的非递减顺序（比如 1 必须在 2 前面），从而去重。
     */
    public int change2(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        // 凑成0元，只有一种方法：什么都不拿
        dp[0] = 1;

        // 外层遍历硬币（固定硬币种类）
        for (int coin : coins) {
            // 内层正序遍历金额（完全背包：允许重复使用当前硬币）
            for (int i = coin; i <= amount; i++) {
                dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }
}
