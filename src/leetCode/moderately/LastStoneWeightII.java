package leetCode.moderately;

/**
 * @description: 1049. 最后一块石头的重量 II
 */
public class LastStoneWeightII {

    /**
     * 动态规划（背包dp）
     */
    public int lastStoneWeightII(int[] stones) {
        int sum = 0;
        for (int stone : stones) {
            sum += stone;
        }
        boolean[] dp = new boolean[sum / 2 + 1];
        // dp[0] = true，保证只取当前石头时为true
        dp[0] = true;
        // 到当前石头的dp状态
        for (int stone : stones) {
            for (int i = dp.length - 1; i >= stone; i--) {
                dp[i] = dp[i] || dp[i - stone];
            }
        }
        for (int i = dp.length - 1; i > 0; i--) {
            if(dp[i]){
                return sum - 2 * i;
            }
        }
        // 只有一个石头的时候返回全部
        return sum;
    }
}
