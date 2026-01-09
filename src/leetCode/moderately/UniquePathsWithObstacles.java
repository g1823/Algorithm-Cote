package leetCode.moderately;

/**
 * @author: gj
 * @description: 63. 不同路径 II
 */
public class UniquePathsWithObstacles {

    /**
     * 思路：
     * 思路也是非常简单，因为题目限制，只能向右和向下走，那么意味着任意一个节点，有且只有两个方式可以到达该节点：
     * 1. 从上方到达该节点
     * 2. 从左方到达该节点
     * 那么，定义dp[i][j]表示到达节点(i, j)的总路径数，如果知道dp[i-1][j]和dp[i][j-1]，那么dp[i][j] = dp[i-1][j] + dp[i][j-1]
     * 其中
     * 当obstacleGrid[i][j] = 1 时，表示该节点不可到达，那么 dp[i][j] = 0
     * 即，动态转移方程为 dp[i][j] = obstacleGrid[i][j] = 1 ? 0 : dp[i-1][j] + dp[i][j-1]
     * 注意：
     * 注意第一行和第一列边界处理
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = obstacleGrid[0][0] == 1 ? 0 : 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    if (i > 0) {
                        // 上
                        dp[i][j] += dp[i - 1][j];
                        if (j > 0) {
                            // 左
                            dp[i][j] += dp[i][j - 1];
                        }
                    } else {
                        if (j > 0) {
                            // 左
                            dp[i][j] += dp[i][j - 1];
                        }
                    }
                }
            }
        }
        return dp[m - 1][n - 1];
    }
}
