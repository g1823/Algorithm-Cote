package leetCode.difficult;

/**
 * @description: 1510. 石子游戏 IV
 * <p>有一堆石子，共 n 颗。两个玩家轮流取走【恰好】一个完全平方数（1, 4, 9, 16...）颗石子，
 * 无法行动者（石子不够取或已取完）输。判断先手是否必胜。</p>
 * <p>博弈DP（剩余石子数维度）：
 * 状态 dp[i] = 剩余 i 颗石子时，当前玩家是否必胜；
 * 必胜判定：存在一个平方数 k²≤i，使得 dp[i-k²] == false（把必败态留给对手）则 dp[i]=true。
 * 复杂度：O(n·√n)，n<10^5 时约 3.16×10^7 次，可行。</p>
 */
public class WinnerSquareGame {

    /**
     * 零和博弈（动态规划-博弈DP）：判断先手在 n 颗石子的游戏中是否必胜
     */
    public boolean winnerSquareGame(int n) {
        // n < 10^5，最多一次取316²=99856颗，超过316²则超过n（317²>10^5）
        // dp[i]表示剩余i个石子时，当前决策者是否必胜
        boolean[] dp = new boolean[n + 1];
        // 没有石子的时候当前用户必输
        dp[0] = false;
        for (int i = 1; i <= n; i++) {
            // 枚举所有平方数取法：k² ≤ i（不能超过剩余石子数）
            for (int j = 1; j * j <= i; j++) {
                // 只要存在一种取法让对手面对必败态，当前玩家就必胜
                if (!dp[i - j * j]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];
    }
}
