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

    /**
     * 题目：79. 单词搜索
     *
     * 思路说明：
     * 这是一个典型的 DFS（深度优先搜索） + 回溯问题。
     * 给定一个二维字符网格 board 和一个单词 word，要求判断是否存在一条路径，
     * 能够通过上下左右连续相邻的格子依次匹配整个单词。
     *
     * 解法一：
     * 1. 遍历整个网格，找到首字母相同的起点。
     * 2. 从起点开始使用 DFS 搜索下一个字母。
     * 3. 为防止重复使用同一个格子，通过 Set 记录访问过的位置。
     * 4. 若搜索到最后一个字符则返回 true，否则在递归返回后回溯（移除访问标记）。
     *
     * 时间复杂度：O(M * N * 4^L)，M、N为网格大小，L为单词长度。
     * 空间复杂度：O(L)，递归深度及路径记录。
     */
    public boolean exist(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                // 找到首字母匹配的位置作为DFS起点
                if (board[i][j] == word.charAt(0)) {
                    Set<String> set = new HashSet<>();
                    // 记录当前已访问位置
                    set.add(i + "," + j);
                    // 若从该起点开始能匹配成功则返回 true
                    if (dfs(board, word, i, j, 1, set)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 深度优先搜索 + 回溯
     * @param board 字符矩阵
     * @param word  目标单词
     * @param i     当前行索引
     * @param j     当前列索引
     * @param k     当前匹配到 word 的第 k 个字符
     * @param set   记录访问路径的集合
     */
    public boolean dfs(char[][] board, String word, int i, int j, int k, Set<String> set) {
        // 若所有字符已匹配完毕，说明找到完整路径
        if (k == word.length()) {
            return true;
        }
        char c = word.charAt(k);
        // 向下
        if (i + 1 < board.length && board[i + 1][j] == c && !set.contains((i + 1) + "," + j)) {
            set.add((i + 1) + "," + j);
            if (dfs(board, word, i + 1, j, k + 1, set)) {
                return true;
            }
            // 回溯
            set.remove((i + 1) + "," + j);
        }
        // 向上
        if (i - 1 >= 0 && board[i - 1][j] == c && !set.contains((i - 1) + "," + j)) {
            set.add((i - 1) + "," + j);
            if (dfs(board, word, i - 1, j, k + 1, set)) {
                return true;
            }
            set.remove((i - 1) + "," + j);
        }
        // 向右
        if (j + 1 < board[0].length && board[i][j + 1] == c && !set.contains(i + "," + (j + 1))) {
            set.add(i + "," + (j + 1));
            if (dfs(board, word, i, j + 1, k + 1, set)) {
                return true;
            }
            set.remove(i + "," + (j + 1));
        }
        // 向左
        if (j - 1 >= 0 && board[i][j - 1] == c && !set.contains(i + "," + (j - 1))) {
            set.add(i + "," + (j - 1));
            if (dfs(board, word, i, j - 1, k + 1, set)) {
                return true;
            }
            set.remove(i + "," + (j - 1));
        }
        return false;
    }



    /**
     * 优化版（方法二）：
     * 优化思路：
     * 1. 用字符修改代替 Set 记录访问过的格子：访问过的格子暂时改为字符 '0'。
     *    这样节省了字符串拼接和 HashSet 存储的开销。
     * 2. 将四个方向的递归统一写在一个语句中，而不是分支重复代码。
     * 3. 回溯时再将字符恢复原状，保证其他搜索路径不受影响。
     * 优点：性能更好，代码更简洁。
     * 时间复杂度：O(M * N * 4^L)
     * 空间复杂度：O(L)
     */
    public boolean exist2(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                // 以每个匹配首字母的格子为起点开始搜索
                if (board[i][j] == word.charAt(0)) {
                    if (dfs(board, word, i, j, 0)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 深度优先搜索 + 原地修改标记法
     * @param board 字符矩阵
     * @param word  目标单词
     * @param i     当前行索引
     * @param j     当前列索引
     * @param k     当前匹配到 word 的第 k 个字符
     */
    public boolean dfs(char[][] board, String word, int i, int j, int k) {
        // 若所有字符均匹配成功，返回 true
        if (k == word.length()) {
            return true;
        }
        // 边界条件与当前字符不匹配的剪枝
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != word.charAt(k)) {
            return false;
        }
        // 暂存当前字符，并标记为访问过
        char c = board[i][j];
        board[i][j] = '0';
        // 向四个方向递归搜索
        boolean result = dfs(board, word, i + 1, j, k + 1) ||
                dfs(board, word, i - 1, j, k + 1) ||
                dfs(board, word, i, j + 1, k + 1) ||
                dfs(board, word, i, j - 1, k + 1);
        // 回溯：恢复现场
        board[i][j] = c;
        return result;
    }

}
