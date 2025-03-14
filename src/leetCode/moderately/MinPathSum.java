package leetCode.moderately;

/**
 * @author: gj
 * @description: 64. 最小路径和
 */
public class MinPathSum {
    public static void main(String[] args) {
        int[][] data = new int[][]{{1, 2, 3}, {4, 5, 6}};
        System.out.println(new MinPathSum().minPathSum(data));
    }

    public int minPathSum(int[][] grid) {
        int row = grid.length, col =  grid[0].length;
        int[] dp = new int[col];
        dp[0] = grid[0][0];
        for (int i = 1; i < col; i++) {
            dp[i] = dp[i - 1] + grid[0][i];
        }
        for (int i = 1; i < row; i++) {
            for (int j = 0; j < col; j++) {
                dp[j] = j == 0 ? dp[j] + grid[i][j] : grid[i][j] + Math.min(dp[j-1], dp[j]);
            }
        }
        return dp[col - 1];
    }
}

