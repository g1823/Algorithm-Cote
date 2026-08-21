package leetCode.moderately;

/**
 * @description: 1690. 石子游戏 VII
 *
 * 思路：零和博弈（博弈DP，区间型）。得分逻辑特殊：每轮从两端取一颗石头，
 * 本轮得分 = 取完后剩余所有石子的总和（而非取的那颗的值）。
 * 剩余局面永远是连续子区间 [l, r]，用区间 DP：dp[l][r] = 当前玩家在 [l,r] 上的净胜分。
 * 复杂度：时间 O(n²)，空间 O(n²)，n ≤ 1000。
 */
public class StoneGameVII {

    /**
     * 博弈DP：返回 Alice 相对 Bob 的最大净胜分（得分差值）
     *
     * 状态定义：dp[l][r] = 区间 [l, r] 剩余时，当前玩家相对对手的净胜分。
     * 边界：dp[i][i] = 0（只剩一堆，取走它后剩余总和为 0，双方净差 0）。
     * 转移：设 sum(l,r) 为区间总和（前缀和 O(1) 求），当前玩家从两端二选一：
     *   取左边 l：本轮得分 = sum(l+1,r)，对手在 [l+1,r] 的净胜 = dp[l+1][r]
     *            → 净胜 = sum(l+1,r) - dp[l+1][r]
     *   取右边 r：本轮得分 = sum(l,r-1)，净胜 = sum(l,r-1) - dp[l][r-1]
     *   dp[l][r] = max(两者)（当前玩家取对自己最有利的一边）
     * 填表：dp[l+1][r]、dp[l][r-1] 都是更短区间，按长度从小到大填。
     * 答案：dp[0][n-1]（Alice 先手，站在当前玩家视角即为 Alice 的净胜分）。
     *
     * @param stones 每颗石子的价值
     * @return Alice 相对 Bob 的最大得分差值
     */
    public int stoneGameVII(int[] stones) {
        int n = stones.length;
        // 前缀和-快速求区间和
        int[] prefixSum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + stones[i];
        }
        // 默认dp[i][i] = 0：单独1堆石子，那么当前决策者只能取这一堆，剩余0，总得分就是0，对手也没有得分，都是0，相对净优势就是0了
        int[][] dp = new int[n][n];
        // 按长度填表，确保小区间先填充（只需要填右上角半个区间，dp[i][j] 和 dp[j][i]互为镜像）
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int l = i, r = i + len - 1;
                dp[l][r] = Math.max(
                        prefixSum[r + 1] - prefixSum[l + 1] - dp[l + 1][r],
                        prefixSum[r] - prefixSum[l] - dp[l][r - 1]
                );
            }
        }
        return dp[0][n - 1];
    }
}
