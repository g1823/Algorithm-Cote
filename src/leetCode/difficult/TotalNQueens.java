package leetCode.difficult;

/**
 * @author: gj
 * @description: 52. N 皇后 II
 */
public class TotalNQueens {
    int result = 0;

    public int totalNQueens(int n) {
        // 皇后存在情况表
        int[][] data = new int[n][n];
        // 皇后存在列情况
        boolean[] existCol = new boolean[n];
        // 皇后存在对角线 \ 情况 (可以发现处于同一对角线的元素，行 - 列是同一个值，所以可以使用这个性质来存储对角线信息)
        boolean[] existLeftDiagonal = new boolean[2 * n - 1];
        // 皇后存在对角线 / 情况(可以发现处于同一对角线的元素，行+ 列是同一个值，所以可以使用这个性质来存储对角线信息)
        boolean[] existRightDiagonal = new boolean[2 * n - 1];
        dfs(data, n, 0, existCol, existLeftDiagonal, existRightDiagonal);
        return result;
    }

    private void dfs(int[][] data, int n, int row, boolean[] existCol, boolean[] existLeftDiagonal, boolean[] existRightDiagonal) {
        if (row == n) {
            result++;
        }
        for (int col = 0; col < n; col++) {
            // 剪枝:判断该位置是否可以放入，不可放入则直接终止
            if (canPlace(row, col, n, existCol, existLeftDiagonal, existRightDiagonal)) {
                // 放入皇后
                placeQueen(row, col, n, data, existCol, existLeftDiagonal, existRightDiagonal);
                // 放入下一个皇后
                dfs(data, n, row + 1, existCol, existLeftDiagonal, existRightDiagonal);
                // 取出皇后
                cancelPlaceQueen(row, col, n, data, existCol, existLeftDiagonal, existRightDiagonal);
            }
        }


    }

    public static boolean canPlace(int row, int col, int n, boolean[] existCol, boolean[] existLeftDiagonal, boolean[] existRightDiagonal) {
        return !(existCol[col] || existLeftDiagonal[row - col + n - 1] || existRightDiagonal[row + col]);
    }

    public static void placeQueen(int row, int col, int n, int[][] data, boolean[] existCol, boolean[] existLeftDiagonal, boolean[] existRightDiagonal) {
        data[row][col] = 1;
        existCol[col] = existLeftDiagonal[row - col + n - 1] = existRightDiagonal[row + col] = true;
    }

    public static void cancelPlaceQueen(int row, int col, int n, int[][] data, boolean[] existCol, boolean[] existLeftDiagonal, boolean[] existRightDiagonal) {
        data[row][col] = 0;
        existCol[col] = existLeftDiagonal[row - col + n - 1] = existRightDiagonal[row + col] = false;
    }
}
