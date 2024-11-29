package leetCode.moderately;

/**
 * @author: gj
 * @description: 322. 零钱兑换
 */
public class CoinChange {
    public static void main(String[] args) {
        int[] coins = new int[]{2};
        System.out.println(new CoinChange().coinChange(coins, 1));
    }

    /**
     * 动态规划
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        for (int i = 1; i < dp.length; i++) {
            int minCoin = Integer.MAX_VALUE;
            boolean b = false;
            for (int coin : coins) {
                if (i - coin >= 0 && dp[i - coin] != -1) {
                    minCoin = Math.min(minCoin, dp[i - coin] + 1);
                    b = true;
                }
            }
            dp[i] = b ? minCoin : -1;
        }
        return Math.max(dp[amount], -1);
    }
}
