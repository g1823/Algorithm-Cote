package leetCode.moderately;

import java.util.LinkedList;
import java.util.Queue;

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
        char[][] grid3 = new char[][]{
                {'1', '1', '1', '1', '0'},
                {'1', '1', '0', '1', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '0', '0', '0'}};
        System.out.println(new NumIslands().numIslands4(grid3));
    }

    /**
     * 尝试使用动态规划，从上到下，从左到右，每个位置仅考虑上方和左侧是否为1，无法考虑到如下情况：
     * {'1', '1', '1'},
     * {'0', '1', '0'},
     * {'1', '1', '1'}
     *
     * @param grid 数据
     * @return 独立岛屿个数
     */
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


    /**
     * 使用深度优先策略遍历，遇到0停止，遇到1就将1改为0（因为这个1一定和之前的1连在一起的）
     *
     * @param grid
     * @return
     */
    public int numIslands2(char[][] grid) {
        int result = 0, row = grid.length, col = grid[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == '1') {
                    result++;
                    dfs(grid, i, j);
                }
            }
        }
        return result;
    }

    public void dfs(char[][] grid, int i, int j) {
        int row = grid.length, col = grid[0].length;
        if (i < 0 || i >= row || j < 0 || j >= col) return;
        if (grid[i][j] == '0') return;
        grid[i][j] = '0';
        dfs(grid, i - 1, j);
        dfs(grid, i + 1, j);
        dfs(grid, i, j - 1);
        dfs(grid, i, j + 1);
    }


    /**
     * 采用广度优先遍历，思路与深度优先一致
     *
     * @param grid
     * @return
     */
    public int numIslands3(char[][] grid) {
        int result = 0, row = grid.length, col = grid[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == '1') {
                    result++;
                    grid[i][j] = '0';
                    Queue<Integer> queue = new LinkedList<>();
                    queue.add(i * col + j);
                    while (!queue.isEmpty()) {
                        Integer integer = queue.poll();
                        int i1 = integer / col;
                        int j1 = integer % col;
                        if (i1 - 1 >= 0 && grid[i1 - 1][j1] == '1') {
                            grid[i1 - 1][j1] = '0';
                            queue.add((i1 - 1) * col + j1);
                        }
                        if (i1 + 1 < row && grid[i1 + 1][j1] == '1') {
                            grid[i1 + 1][j1] = '0';
                            queue.add((i1 + 1) * col + j1);
                        }
                        if (j1 - 1 >= 0 && grid[i1][j1 - 1] == '1') {
                            grid[i1][j1 - 1] = '0';
                            queue.add(i1 * col + j1 - 1);
                        }
                        if (j1 + 1 < col && grid[i1][j1 + 1] == '1') {
                            grid[i1][j1 + 1] = '0';
                            queue.add(i1 * col + j1 + 1);
                        }
                    }
                }
            }
        }
        return result;
    }


    /**
     * TODO:并查集
     *
     * @param grid 原始数据
     * @return
     */
    public int numIslands4(char[][] grid) {
        int row = grid.length, col = grid[0].length;
        UnionFind unionFind = new UnionFind(grid);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == '1') {
                    // 将当前结点置为0，因为与其邻接的上下左右结点若也为1，在当前结点就会合并当前结点与其邻接结点
                    // 当遍历到为1的结点后，因为已经提前将结点置为0，则不会再次重复合并
                    grid[i][j] = '0';
                    // 上
                    if (i > 0 && grid[i - 1][j] == '1') {
                        unionFind.union(i * col + j, (i - 1) * col + j);
                    }
                    // 下
                    if (i < row - 1 && grid[i + 1][j] == '1') {
                        unionFind.union(i * col + j, (i + 1) * col + j);
                    }
                    // 左
                    if (j > 0 && grid[i][j - 1] == '1') {
                        unionFind.union(i * col + j, i * col + j - 1);
                    }
                    // 右
                    if (j < col - 1 && grid[i][j + 1] == '1') {
                        unionFind.union(i * col + j, i * col + j + 1);
                    }
                }
            }
        }
        return unionFind.getCount();
    }

    class UnionFind {
        int[] parents;
        int[] rank;
        int count = 0;

        public UnionFind(char[][] grid) {
            int row = grid.length;
            int col = grid[0].length;
            parents = new int[row * col];
            // 默认所有的结点的秩初始为0
            rank = new int[row * col];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (grid[i][j] == '1') {
                        // 初始时每个1结点都为单独岛屿，且其根结点都为自身
                        parents[i * col + j] = i * col + j;
                        // 所有的1都作为一个独立岛屿，所以数量++
                        count++;
                    }
                }
            }
        }

        public int find(int index) {
            if (parents[index] != index) return find(parents[index]);
            return index;
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            // 该合并的两个结点根节点不一样，进行合并并数量--
            if (rootX != rootY) {
                if (rank[rootX] > rank[rootY]) {
                    parents[rootY] = rootX;
                } else if (rank[rootX] < rank[rootY]) {
                    parents[rootX] = rootY;
                } else {
                    parents[rootY] = rootX;
                    rank[rootX] += 1;
                }
                count--;
            }
        }

        public int getCount() {
            return count;
        }

    }
}
