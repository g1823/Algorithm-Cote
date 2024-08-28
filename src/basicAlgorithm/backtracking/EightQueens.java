package basicAlgorithm.backtracking;

import java.util.Arrays;


/**
 * @author: gj
 * @description: 八皇后（二维数组）
 */
public class EightQueens {
    private static int resultNum = 0;

    public static void main(String[] args) {
        execute(4);
        if (resultNum == 0) System.out.println("无解");
    }

    public static void execute(int n) {
        int[][] data = new int[n][n];
        dfs(data, 0);
    }

    public static void dfs(int[][] data, int index) {
        // 最后一个皇后已经放入
        if (index == data.length) {
            printResult(data, ++resultNum);
            return;
        }
        for (int col = 0; col < data.length; col++) {
            // 剪枝:判断该位置是否可以放入，不可放入则直接终止
            if (canPlace(data, index, col)) {
                // 放入皇后
                data[index][col] = 1;
                // 放入下一个皇后
                dfs(data, index + 1);
                // 取出当前皇后(回溯)
                data[index][col] = 0;
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

    public static void printResult(int[][] data, int num) {
        System.out.println("第" + num + "组解：");
        for (int i = 0; i < data.length; i++) {
            System.out.println(Arrays.toString(data[i]));
        }
    }
}
