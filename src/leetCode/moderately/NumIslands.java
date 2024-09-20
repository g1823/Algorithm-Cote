package leetCode.moderately;

/**
 * @author: gj
 * @description: 200. 岛屿数量
 */
public class NumIslands {
    public static void main(String[] args) {
        char[][] grid = new char[][]{
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}};
        char[][] grid2 = new char[][]{
                {'1', '1', '1'},
                {'0', '1', '0'},
                {'1', '1', '1'}};
        System.out.println(new NumIslands().numIslands(grid2));
    }

    public int numIslands(char[][] grid) {
        int result = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int up = i - 1 >= 0 ? grid[i - 1][j] : '0';
                int left = j - 1 >= 0 ? grid[i][j - 1] : '0';
                if (up == '0' && left == '0' && grid[i][j] == '1') result++;
            }
        }
        return result;
    }
}
