package leetCode.moderately;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: gj
 * @description: 51. N 皇后
 */
public class SolveNQueens {

    public static void main(String[] args) {
        int n = 4;
        List<List<String>> res = new SolveNQueens().solveNQueens(n);
        System.out.println(res);
    }

    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        int[][] data = new int[n][n];
        int[] col = new int[n];
        int[] left = new int[2 * n - 1];
        int[] right = new int[2 * n - 1];
        Arrays.fill(col, 0);
        Arrays.fill(left, 0);
        Arrays.fill(right, 0);
        backtrack(res, data, 0, n, col, left, right);
        return res;
    }

    public void backtrack(List<List<String>> res, int[][] data, int row, int n, int[] col, int[] left, int[] right) {
        if (row == n) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < n; j++) {
                    sb.append(data[i][j] == 1 ? 'Q' : '.');
                }
                list.add(sb.toString());
            }
            res.add(list);
            return;
        }
        for (int l = 0; l < n; l++) {
            if (canPlace(row, l, n, col, left, right)) {
                data[row][l] = 1;
                placeQueen(row, l, n, col, left, right);
                backtrack(res, data, row + 1, n, col, left, right);
                cancelPlaceQueen(row, l, n, col, left, right);
                data[row][l] = 0;
            }
        }
    }

    private boolean canPlace(int row, int col, int n, int[] colN, int[] left, int[] right) {
        return colN[col] == 0 && left[row - col + n - 1] == 0 && right[row + col] == 0;
    }

    private void placeQueen(int row, int col, int n, int[] colN, int[] left, int[] right) {
        colN[col] = 1;
        left[row - col + n - 1] = 1;
        right[row + col] = 1;
    }

    private void cancelPlaceQueen(int row, int col, int n, int[] colN, int[] left, int[] right) {
        colN[col] = 0;
        left[row - col + n - 1] = 0;
        right[row + col] = 0;
    }
}
