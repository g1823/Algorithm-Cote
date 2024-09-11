package leetCode.moderately;

import java.util.List;

/**
 * @Package leetCode.moderately
 * @Date 2024/9/10 21:46
 * @Author gaojie
 * @description: 221. 最大正方形
 */
public class MaximalSquare {
    public static void main(String[] args) {
        char[][] data = new char[][]{{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {'1', '0', '0', '1', '0'}};
        char[][] data2 = new char[][]{{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {'1', '0', '0', '1', '0'}};
        char[][] data3 = new char[][]{{'0', '1'}};
        char[][] data4 = new char[][]{{'1', '1'}, {'1', '1'}};
        MaximalSquare maximalSquare = new MaximalSquare();
        System.out.println(maximalSquare.maximalSquare2(data3));
    }

    public int maximalSquare(char[][] matrix) {
        int maxSize = 0;
        for (int i = 0; i < matrix.length; i++) {
            int size = 0;
            for (int j = 0; j < matrix[i].length; ) {
                if (matrix[i][j] == '1') {
                    maxSize = Math.max(maxSize, 1);
                    if (++size > 1 && size > maxSize) {
                        if (verifySquare(matrix, size, i, j - size + 1)) {
                            maxSize = Math.max(maxSize, size);
                            j++;
                        } else {
                            j = j == matrix[i].length - 1 ? j + 1 : j - size + 2;
                            size = 0;
                        }
                    } else {
                        j++;
                    }
                } else {
                    j++;
                    size = 0;
                }
            }
        }
        return maxSize * maxSize;
    }

    public boolean verifySquare(char[][] matrix, int size, int row, int col) {
        if (row + size > matrix.length) {
            return false;
        }
        for (int i = row + 1; i < row + size; i++) {
            for (int j = col; j < col + size; j++) {
                if (matrix[i][j] != '1') return false;
            }
        }
        return true;
    }


    /**
     * 动态规划：
     * 上述蛮力法解决问题时可以发现一些结点不断重复遍历
     * 按照 左->右，上->下 顺序遍历，可以记录每个结点作为正方形右下角，所代表的最大正方形
     * （按照 右->左，下->上 顺序遍历，可以记录每个结点作为正方形左上角，所代表的最大正方形）
     * 遍历到新结点时，可以利用上述的数据，比如：
     * 当前结点左上角结点的值代表了左上角x*x的区域全是1
     * 当前结点左边结点的值代表了左边x的区域全是1
     * 当前结点上边结点的值代表了上边x的区域全是1
     * 取其中最小值+1就是当前结点作为正方形右下角的最大正方形
     *
     * @param matrix 数据
     * @return 最大面积
     */
    public int maximalSquare2(char[][] matrix) {
        int maxSize = 0;
        int[][] dp = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix[0].length; i++) {
            maxSize = Math.max(dp[0][i] = matrix[0][i] == '1' ? 1 : 0, maxSize);
        }
        for (int i = 0; i < matrix.length; i++) {
            maxSize = Math.max(dp[i][0] = matrix[i][0] == '1' ? 1 : 0, maxSize);
        }
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                dp[i][j] = matrix[i][j] == '1' ? Math.min(dp[i][j - 1], Math.min(dp[i - 1][j - 1], dp[i - 1][j])) + 1 : 0;
                maxSize = Math.max(dp[i][j], maxSize);
            }
        }
        return maxSize * maxSize;
    }

    /**
     * 官方DP
     *
     * @param matrix
     * @return
     */
    public int maximalSquare3(char[][] matrix) {
        int maxSide = 0;
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return maxSide;
        }
        int rows = matrix.length, columns = matrix[0].length;
        int[][] dp = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j] == '1') {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                    }
                    maxSide = Math.max(maxSide, dp[i][j]);
                }
            }
        }
        int maxSquare = maxSide * maxSide;
        return maxSquare;
    }

}
