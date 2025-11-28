package leetCode.moderately;

/**
 * @author: gj
 * @description: 130. 被围绕的区域
 */
public class Solve130 {

    public static void main(String[] args) {
        char[][] board = {{'X', 'X', 'X', 'X'}, {'X', 'O', 'O', 'X'}, {'X', 'X', 'O', 'X'}, {'X', 'O', 'X', 'X'}};
        Solve130 solve130 = new Solve130();
        solve130.solve(board);
    }

    /**
     * 思路：
     * 根据题目要求，只有连接扩展到边界0的0，才能保留为0.
     * 因此，直接从边界0开始，依次将所有与其连接的0进行标记，标记为t.
     * 遍历整个矩阵，将所有t标记为0，将所有o标记为x.
     */
    public void solve(char[][] board) {
        int m = board.length, n = board[0].length;
        for (int i = 0; i < n; i++) {
            if (board[0][i] == o) {
                dfs(board, 0, i);
            }
            if (board[m - 1][i] == o) {
                dfs(board, m - 1, i);
            }
        }
        for (int i = 0; i < m; i++) {
            if (board[i][0] == o) {
                dfs(board, i, 0);
            }
            if (board[i][n - 1] == o) {
                dfs(board, i, n - 1);
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = board[i][j] == t ? o : x;
            }
        }
    }

    char x = 'X', o = 'O', t = 'T';

    public void dfs(char[][] board, int i, int j) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != o || board[i][j] == t) {
            return;
        }
        board[i][j] = t;
        dfs(board, i - 1, j);
        dfs(board, i + 1, j);
        dfs(board, i, j - 1);
        dfs(board, i, j + 1);
    }
}
