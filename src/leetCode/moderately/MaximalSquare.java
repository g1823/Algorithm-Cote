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

    /**
     * 计算由 '1' 组成的最大正方形的面积。
     * 方法思路：
     * - 从每一行出发，依次尝试从当前位置向左连续的 '1' 数量构成的最大边长 size；
     * - 如果 size > 1，就进一步调用 verifySquare 方法验证该正方形是否有效；
     * - 若有效，更新 maxSize，否则回退起始位置；
     */
    public int maximalSquare(char[][] matrix) {
        // 当前发现的最大正方形边长
        int maxSize = 0;

        for (int i = 0; i < matrix.length; i++) {
            // 当前连续 '1' 的数量
            int size = 0;
            for (int j = 0; j < matrix[i].length; ) {
                if (matrix[i][j] == '1') {
                    // 至少能构成 1x1 正方形
                    maxSize = Math.max(maxSize, 1);
                    if (++size > 1 && size > maxSize) {
                        // 连续 '1' 增长后大于当前 maxSize，尝试验证更大正方形
                        // 从当前行 i，列 j - size + 1 开始验证 size * size 区域是否合法
                        if (verifySquare(matrix, size, i, j - size + 1)) {
                            // 验证通过则更新 maxSize
                            maxSize = Math.max(maxSize, size);
                            j++;
                        } else {
                            // 若验证失败，从下一个可能的起始位置重新开始
                            j = j == matrix[i].length - 1 ? j + 1 : j - size + 2;
                            // 重置连续 '1' 计数
                            size = 0;
                        }
                    } else {
                        // size <= 1 或未超过 maxSize，继续探索
                        j++;
                    }
                } else {
                    // 当前不是 '1'，跳过
                    j++;
                    size = 0;
                }
            }
        }
        return maxSize * maxSize;
    }

    /**
     * 验证以 matrix[row][col] 为左上角的 size x size 区域是否全为 '1'
     */
    public boolean verifySquare(char[][] matrix, int size, int row, int col) {
        if (row + size > matrix.length) {
            // 越界了，不可能构成正方形
            return false;
        }

        // 从 row+1 行开始，遍历 size 行 size 列区域
        for (int i = row + 1; i < row + size; i++) {
            for (int j = col; j < col + size; j++) {
                if (matrix[i][j] != '1') {
                    // 出现 '0'，验证失败
                    return false;
                }
            }
        }
        // 全部为 '1'，验证通过
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
