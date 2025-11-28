package leetCode.moderately;

/**
 * @author: gj
 * @description: 36. 有效的数独
 */
public class IsValidSudoku {

    /**
     * 3个二维数组，10*10，分别记录：
     * 1、某一行某个数字是否出现过，比如 row[i][num] = true;表示第i行的num数字出现过
     * 2、某一列某个数字是否出现过。column[j][num] = true;表示第j列的num数字出现过
     * 3、某个3*3的块的某个数字是否出现过。转换规则：i / 3 * 3 + j / 3，行数决定大行块 → 乘 3；列数决定小块偏移 → 相加，得到唯一的宫格编号。
     * 遍历原数组过程中途出现重复即可直接返回。
     */
    public boolean isValidSudoku(char[][] board) {
        boolean[][] row = new boolean[9][10];
        boolean[][] column = new boolean[9][10];
        boolean[][] block = new boolean[9][10];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                if (c != '.') {
                    int num = c - '0';
                    if (row[i][num] || column[j][num] || block[i / 3 * 3 + j / 3][num]) {
                        return false;
                    }
                    row[i][num] = true;
                    column[j][num] = true;
                    block[i / 3 * 3 + j / 3][num] = true;
                }
            }
        }
        return true;
    }
}
