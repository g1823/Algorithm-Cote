package leetCode.difficult;

/**
 * @description: 1406. 石子游戏 III
 */
public class StoneGameIII {
    public String stoneGameIII(int[] stoneValue) {
        int[] dp = new int[stoneValue.length + 1];
        for (int i = stoneValue.length - 1; i >= 0; i--) {
            int max = Integer.MIN_VALUE;
            int sum = 0;
            for (int j = i; j < i + 3 && j < stoneValue.length; j++) {
                sum += stoneValue[j];
                max = Math.max(max, sum - dp[j + 1]);
            }
            dp[i] = max;
        }
        return dp[0] > 0 ? "Alice" : dp[0] < 0 ? "Bob" : "Tie";
    }
}
