package basic_algorithm.backtracking.combination;

import java.util.Arrays;

/**
 * @author: gj
 * @description: N皇后蛮力法
 */
public class EightQueensForce {
    public static int num = 0;

    public static void main(String[] args) {
        execute(9);
    }

    public static void execute(int n) {
        int[][] data = new int[n][n];
        nQueens(data, 0);
    }

    public static void nQueens(int[][] data, int row) {
        int n = data.length;
        // 已经到达最后一行，n个皇后已经放置完毕
        if (row == n) {
            // 校验皇后拜访位置是否合理
            for (int row1 = 0; row1 < n; row1++) {
                for (int col = 0; col < n; col++) {
                    if (data[row1][col] == 1) {
                        // 判断每一行中皇后位置是否合理
                        if (!canPlace(data, row1, col)) {
                            // 不合理直接返回
                            return;
                        } else {
                            // 到达最后一行输出结果
                            if (row1 == n - 1) {
                                System.out.println("第" + ++num + "组解：");
                                for (int[] d : data) {
                                    System.out.println(Arrays.toString(d));
                                }
                            }
                        }
                    }
                }
            }
        }else{
            // 未到达最后一行，遍历
            for (int col = 0; col < n; col++) {
                data[row][col] = 1;
                nQueens(data, row + 1);
                data[row][col] = 0;
            }
        }
    }

    public static boolean canPlace(int[][] data, int row, int col) {
        // 检查同一列是否有皇后
        for (int i = 0; i < row; i++) {
            if (data[i][col] == 1) return false;
        }
        // 检查 \ 对角线是否存在皇后
        for (int i = 1; row - i >= 0 && col - i >= 0; i++) {
            if (data[row - i][col - i] == 1) return false;
        }
        // 检查 / 对角线是否存在皇后
        for (int i = 1; row - i >= 0 && col + i < data.length; i++) {
            if (data[row - i][col + i] == 1) return false;
        }
        return true;
    }
}
