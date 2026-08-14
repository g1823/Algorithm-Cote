package leetCode.moderately;

/**
 * @description: 1140. 石子游戏 II
 *
 * 【题目】若干堆石子排成一行，Alice 先手，两人轮流从前端取 1~2M 堆，取后 M = max(M, 本次取堆数)，初始 M=1。
 *         取完全部堆后，总石子数多者胜。求 Alice 最多能拿到多少颗石子。
 *
 * 【思路】零和博弈（博弈DP）
 *  1. 前缀取 → 剩余永远是后缀，状态用"当前起始下标 i"即可描述位置；
 *     且 M 会随每次取值动态增大，因此状态为二维 (i, M)。
 *  2. dp[i][M] 定义：从第 i 堆开始、当前参数为 M 时，当前玩家相对对手的净优势（己方得分 - 对方得分）。
 *  3. 转移：当前取 X 堆（1 ≤ X ≤ min(2M, n-i)），本轮收益 = suffixSum[i] - suffixSum[i+X]，
 *     对手在剩余后缀 [i+X, n) 上净优势 = dp[i+X][max(M,X)]，
 *     故 dp[i][M] = max over X ( 本轮收益 - 对手净优势 )。
 *  4. 边界：i == n 时无石子可取，dp[n][*] = 0（数组默认值）。
 *  5. 填表方向：dp[i][M] 依赖 i+X > i 的子问题，故 i 从大到小倒序填；
 *     同一行内不同 M 互不依赖，顺序无关。初始 M=1，净优势即 dp[0][1]。
 *
 * 【返回值换算】dp 存的是净优势，题目要的是 Alice 得分。
 *     Alice + Bob = suffixSum[0]，Alice - Bob = dp[0][1]，
 *     联立得 Alice = (suffixSum[0] + dp[0][1]) / 2。
 *
 * 【复杂度】时间 O(n³)，空间 O(n²)
 */
public class stoneGameII {
    /**
     * 零和博弈（动态规划-博弈DP）
     */
    public int stoneGameII(int[] piles) {
        int n = piles.length;
        // 后缀和：suffixSum[i] = piles[i..n-1] 的总和，suffixSum[n] = 0，O(1) 求任意区间和
        int[] suffixSum = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            suffixSum[i] = suffixSum[i + 1] + piles[i];
        }
        // dp[i][M]：从 i 开始、当前参数 M 下当前玩家的净优势
        int[][] dp = new int[n + 1][n + 1];
        // 填表（i 从大到小，保证依赖的 dp[i+X][...] 已算好）
        for (int i = n - 1; i >= 0; i--) {
            // 把 M 从 1 到 n 全部填一遍，这样未来任何 max(M,X) 都能直接查表
            for (int M = 1; M <= n; M++) {
                int best = Integer.MIN_VALUE;
                // X 从 1 取到 min(2M, n-i)：不能超过剩余堆数
                for (int X = 1; X <= 2 * M && i + X <= n; X++) {
                    // 本轮收益 - 对手在剩余后缀上的净优势
                    int cur = (suffixSum[i] - suffixSum[i + X]) - dp[i + X][Math.max(M, X)];
                    best = Math.max(cur, best);
                }
                dp[i][M] = best;
            }
        }
        // 净优势转 Alice 得分：Alice = (总和 + 净优势) / 2
        return (suffixSum[0] + dp[0][1]) / 2;
    }
}

