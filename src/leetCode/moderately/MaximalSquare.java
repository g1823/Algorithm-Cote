package leetCode.moderately;

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
        System.out.println(maximalSquare.maximalSquare(data));
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
}
