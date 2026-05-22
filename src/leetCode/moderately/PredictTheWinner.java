package leetCode.moderately;

/**
 * @description: 486. 预测赢家
 */
public class PredictTheWinner {
    /**
     * - 区间 DP（博弈 DP）
     * - dp[l][r]：
     * - 表示在区间 [l, r] 内，当前玩家相对于对手，最终最多能领先多少分。
     * - 由于是零和博弈：
     * - 当前玩家获得的优势 = 当前拿到的分数 - 对手后续能获得的优势
     * -
     * - 状态转移：
     * - 1、选择左边 nums[l]
     * -    当前玩家获得 nums[l]
     * -    剩余区间 [l+1, r] 交给对手
     * -    dp[l+1][r] 表示：
     * -      “对手”在该区间相对于当前玩家能领先多少
     * -    因此当前玩家最终领先：
     * -      nums[l] - dp[l+1][r]
     * - 2、选择右边 nums[r]
     * -      nums[r] - dp[l][r-1]
     * - 3、当前玩家一定会选择让自己领先更多的方案：
     * -      dp[l][r] =
     * -          max(
     * -              nums[l] - dp[l+1][r],
     * -              nums[r] - dp[l][r-1]
     * -          )
     * - 初始化：
     * - 当 l == r 时：
     * - 当前玩家直接拿走唯一数字：
     * -      dp[i][i] = nums[i]
     * -
     * - 遍历顺序：
     * - 当前状态依赖：
     * -      dp[l+1][r]
     * -      dp[l][r-1]
     * -
     * - 都是更短区间，因此需要：
     * - 1、先枚举区间长度
     * - 2、再枚举左边界
     * - 即按照二维数组“斜线方向”进行填表。
     * -
     * - 最终：
     * - dp[0][n-1] >= 0
     * - 表示先手最终至少不输（赢或平）
     * -
     * - 时间复杂度：O(n^2)
     * - 空间复杂度：O(n^2)
     */
    public boolean PredictTheWinner(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n][n];
        // 长度为1的区间
        for (int i = 0; i < n; i++) {
            dp[i][i] = nums[i];
        }
        // len 表示区间长度差：r - l
        for (int len = 1; len < n; len++) {
            // 枚举左边界
            for (int l = 0; l + len < n; l++) {
                int r = l + len;
                dp[l][r] = Math.max(
                        nums[l] - dp[l + 1][r],
                        nums[r] - dp[l][r - 1]
                );
            }
        }
        return dp[0][n - 1] >= 0;
    }
}

