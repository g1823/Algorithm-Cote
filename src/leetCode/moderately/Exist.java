package leetCode.moderately;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: gj
 * @description: 79. 单词搜索
 */
public class Exist {
    public static void main(String[] args) {
        char[][] board = {{'C', 'A', 'A'}, {'A', 'A', 'A'}, {'B', 'C', 'D'}};
        String word = "AAB";
        System.out.println(new Exist().exist(board, word));
    }

    public boolean exist(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    Set<String> set = new HashSet<>();
                    set.add("" + i + "," + j);
                    if (dfs(board, word, i, j, 1, set)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean dfs(char[][] board, String word, int i, int j, int k, Set<String> set) {
        if (k == word.length()) {
            return true;
        }
        char c = word.charAt(k);
        if (i + 1 < board.length && board[i + 1][j] == c && !set.contains("" + (i + 1) + "," + j)) {
            // 下一行
            set.add("" + (i + 1) + "," + j);
            if (dfs(board, word, i + 1, j, k + 1, set)) {
                return true;
            }
            set.remove("" + (i + 1) + "," + j);
        }
        if (i - 1 >= 0 && board[i - 1][j] == c && !set.contains("" + (i - 1) + "," + j)) {
            // 上一行
            set.add("" + (i - 1) + "," + j);
            if (dfs(board, word, i - 1, j, k + 1, set)) {
                return true;
            }
            set.remove("" + (i - 1) + "," + j);
        }
        if (j + 1 < board[0].length && board[i][j + 1] == c && !set.contains("" + i + "," + (j + 1))) {
            // 右一列
            set.add("" + i + "," + (j + 1));
            if (dfs(board, word, i, j + 1, k + 1, set)) {
                return true;
            }
            set.remove("" + i + "," + (j + 1));
        }
        if (j - 1 >= 0 && board[i][j - 1] == c && !set.contains("" + i + "," + (j - 1))) {
            // 左一列
            set.add("" + i + "," + (j - 1));
            if (dfs(board, word, i, j - 1, k + 1, set)) {
                return true;
            }
            set.remove("" + i + "," + (j - 1));
        }
        return false;
    }


    /**
     * 方法一优化，
     * 1、将使用Set记录是否访问过改为使用将对应字符改为字符0，使用是否为0来判断
     * 2、将回溯逻辑改为当前方法内，而非每个if分支
     *
     * @param board
     * @param word
     * @return
     */
    public boolean exist2(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (dfs(board, word, i, j, 0)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public boolean dfs(char[][] board, String word, int i, int j, int k) {
        if (k == word.length()) {
            return true;
        }
        char c = word.charAt(k);
        // 边界值判断
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != c) {
            return false;
        }
        board[i][j] = '0';
        boolean result = dfs(board, word, i + 1, j, k + 1) ||
                dfs(board, word, i - 1, j, k + 1) ||
                dfs(board, word, i, j + 1, k + 1) ||
                dfs(board, word, i, j - 1, k + 1);
        board[i][j] = c;
        return result;
    }
}
