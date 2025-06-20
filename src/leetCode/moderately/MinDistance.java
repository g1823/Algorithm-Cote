package leetCode.moderately;

/**
 * @author: gj
 * @description: 72. 编辑距离
 */
public class MinDistance {

    /**
     * 动态规划：
     * 1、给定两个字符串 word1 和 word2，要求将 word1 通过若干次操作转换成 word2，操作有三种：
     * -1、插入一个字符
     * -2、删除一个字符
     * -3、替换一个字符
     * 2、操作对称性分析
     * -1、给 word1 插入一个字符 等价于 删除 word2 的一个字符
     * -2、替换 word1 的字符 等价于 替换 word2 的字符
     * -3、删除 word1 的字符 等价于 给 word2 插入一个字符
     * 3、因此，我们只需以一个方向来思考（例如从 word1 转成 word2），可以统一归纳出三种有效操作：
     * -1、给 word1 插入一个字符（或等价地，删除 word2 的一个字符）
     * -2、替换 word1 的一个字符（或等价地，替换 word2 的一个字符）
     * -3、删除 word1 的一个字符（或等价地，给 word2 插入一个字符）
     * 分析可以发现，出现了子问题的结构是“求最优解 + 子问题包含结构”时，可以考虑最优子结构了：
     * 假设 dp[i][j] 表示 word1[0..i-1] 转换成 word2[0..j-1] 所需要的最小操作数
     * 子问题dp[i][j] 的转移来源	  对应的操作（使得 word1[0..i-1] → word2[0..j-1]）	代价
     * dp[i][j - 1]	              插入 word2[j-1] 到 word1 的末尾	                +1
     * dp[i - 1][j]	              删除 word1[i-1]	                            +1
     * dp[i - 1][j - 1]	          若 word1[i-1] == word2[j-1]：匹配；否则替换	    +0 / +1
     * 因此，状态转移公式就出来了：
     * dp[i][j] = min(dp[i-1][j-1] + (word1[i-1] != word2[j-1]), dp[i-1][j] + 1, dp[i][j-1] + 1)
     */
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        //dp[i][j] 表示将 word1 的前 i 个字符变成 word2 的前 j 个字符所需的最少操作数。
        //dp[0][0] = 0：两个空串之间不需要操作。
        int[][] dp = new int[m + 1][n + 1];
        // 初始化，当字符串 word2 为空时，word1转换为word2需要删除 word1 的字符操作数
        for (int i = 1; i <= m; i++) {
            dp[i][0] = i;
        }
        // 初始化，当字符串 word1 为空时，word1转换为word2时，需要插入word1的字符操作数
        for (int j = 1; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = Math.min(
                        dp[i - 1][j - 1] + (word1.charAt(i - 1) == word2.charAt(j - 1) ? 0 : 1),
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
            }
        }

        return dp[m][n];
    }

    /**
     * 优化minDistance，采用滚动数组
     */
    public int minDistance2(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        char[] chars1 = word1.toCharArray();
        char[] chars2 = word2.toCharArray();
        // dp[j] 表示 word1 的当前前缀 与 word2 的前 j 个字符 的最小编辑距离
        int[] dp = new int[n + 1];
        // 初始化第0行：当 word1 为空时，word1 → word2[0..j-1] 需要 j 次插入
        for (int j = 1; j <= n; j++) {
            dp[j] = j;
        }
        for (int i = 1; i <= m; i++) {
            // pre 保存的是 dp[j - 1] 在上一轮（i-1）时的值，即二维 dp[i-1][j-1]
            int pre = dp[0];
            // 当前 dp[0] 表示将 word1[0..i-1] 变为 ""，需要 i 次删除
            dp[0] = i;
            for (int j = 1; j <= n; j++) {
                // 记录当前 dp[j] 的旧值，用于下一轮 pre
                int temp = dp[j];

                if (chars1[i - 1] == chars2[j - 1]) {
                    // 字符相同，无需操作，继承 dp[i-1][j-1]
                    dp[j] = pre;
                } else {
                    // 三种操作取最小值：
                    // dp[j]：来自 dp[i-1][j]，删除
                    // dp[j - 1]：来自 dp[i][j-1]，插入
                    // pre：来自 dp[i-1][j-1]，替换
                    dp[j] = Math.min(dp[j] + 1, Math.min(dp[j - 1] + 1, pre + 1));
                }
                // 更新 pre 为旧 dp[j]，用于下一列
                pre = temp;
            }
        }
        return dp[n];
    }
}
