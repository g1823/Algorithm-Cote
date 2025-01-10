package leetCode.moderately;

/**
 * @author: gj
 * @description: 279. 完全平方数
 */
public class NumSquares {
    public static void main(String[] args) {
        System.out.println(new NumSquares().numSquares(1));
    }

    /**
     * 动态规划：
     * 求出所有小于n的完全平方数，接下来就跟硬币求和一样了{@link CoinChange}
     *
     * @param n
     * @return
     */
    public int numSquares(int n) {
        int[] nums = new int[n];
        int length = 0;
        for (int i = 1; i <= n; i++) {
            if (i * i <= n) nums[length++] = i * i;
            else break;
        }
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int minCount = Integer.MAX_VALUE;
            boolean b = false;
            for (int j = 0; j < length; j++) {
                int num = nums[j];
                if (i - num >= 0 && dp[i - num] != -1) {
                    minCount = Math.min(minCount, dp[i - num] + 1);
                    b = true;
                }
            }
            dp[i] = b ? minCount : -1;
        }
        return dp[n];
    }

    /**
     * 官方题解动态规划：不需要先计算一边小于n的完全平方数
     * 因为结果一定在1-√n 之间，因此直接遍历即可
     *
     * @param n
     * @return
     */
    public int numSquares2(int n) {
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int minCount = Integer.MAX_VALUE;
            for (int j = 1; j * j <= i; j++) {
                minCount = Math.min(minCount, dp[i - j * j]);
            }
            dp[i] = minCount + 1;
        }
        return dp[n];
    }

    /**
     * 官方题解：
     * 数学：四平方和定理
     * 四平方和定理证明了任意一个正整数都可以被表示为至多四个正整数的平方和。这给出了本题的答案的上界。
     * 同时四平方和定理包含了一个更强的结论：当且仅当 n 不等于 4^k * (8m + 7) 时，n 可以被表示为至多三个正整数的平方和。因此，
     * 当 n = 4^k * (8m + 7)时，n 只能被表示为四个正整数的平方和。此时我们可以直接返回 4。
     * 当 n 不等于 4^k * (8m + 7) 时，我们需要判断到底多少个完全平方数能够表示 n，我们知道答案只会是 1,2,3 中的一个：
     * 答案为 1 时，则必有 n 为完全平方数，这很好判断；
     * 答案为 2 时，则有 n = a^2 + b^2，我们只需要枚举所有的 a(1≤a≤√n),判断 n−a^2是否为完全平方数即可。
     * 答案为 3 时，我们很难在一个优秀的时间复杂度内解决它，但我们只需要检查答案为 1 或 2 的两种情况，即可利用排除法确定答案。
     *
     * @param n
     * @return
     */
    public int numSquares3(int n) {
        if (isPerfectSquare(n)) {
            return 1;
        }
        if (checkAnswer4(n)) {
            return 4;
        }
        for (int i = 1; i * i <= n; i++) {
            int j = n - i * i;
            if (isPerfectSquare(j)) {
                return 2;
            }
        }
        return 3;
    }

    // 判断是否为完全平方数
    public boolean isPerfectSquare(int x) {
        int y = (int) Math.sqrt(x);
        return y * y == x;
    }

    // 判断是否能表示为 4^k*(8m+7)
    public boolean checkAnswer4(int x) {
        while (x % 4 == 0) {
            x /= 4;
        }
        return x % 8 == 7;
    }
}
