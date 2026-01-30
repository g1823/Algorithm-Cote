package leetCode.moderately;


import java.util.LinkedList;
import java.util.Queue;

/**
 * @author: gj
 * @description: 1926. 迷宫中离入口最近的出口
 */
public class NearestExit {

    public static void main(String[] args) {
        char[][] maze = new char[][]{{'+', '+', '+'}, {'.', '.', '.'}, {'+', '+', '+'}};
        System.out.println(new NearestExit().nearestExit(maze, new int[]{1, 0}));
    }

    /**
     * BFS 广度优先遍历
     * 解题思路：
     * 1. 使用 BFS 逐层搜索，因为 BFS 能保证找到的出口是最短的（步数最少）。
     * 2. 从入口开始，将四个方向（上、下、左、右）可通行的相邻格子加入队列。
     * 3. 可通行的条件是：在迷宫范围内、不是墙（'+'）、且未被访问过。
     * 4. 当找到一个格子满足以下条件时即为出口：
     * - 位于迷宫边界（x==0 或 x==m-1 或 y==0 或 y==n-1）
     * - 不是入口本身
     * 5. 找到第一个满足条件的出口时，当前的步数就是最短路径步数，直接返回。
     * 原始代码问题分析：
     * 1. 找到出口时使用 break：break 只跳出内层循环，外层循环继续执行，导致：
     * - 步数继续增加
     * - 可能找到的不是最短路径
     * - 最终返回错误的步数
     * 2. 访问标记时机错误：在检查是否为出口后才标记访问，导致：
     * - 可能重复访问同一节点
     * - 进入死循环风险
     * 3. 步数计数逻辑错误：
     * - step 初始化为 0，但第一轮循环处理起点时 step 就会自增
     * - 返回 step 而不是实际到达出口的步数
     * 4. 未标记起点已访问：起点入队后未标记 visited，可能被重复访问
     */
    public int nearestExit(char[][] maze, int[] entrance) {
        int m = maze.length, n = maze[0].length;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(entrance);
        visited[entrance[0]][entrance[1]] = true;

        int step = 0;
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty()) {
            int size = queue.size();
            // 处理当前层的所有节点
            for (int i = 0; i < size; i++) {
                int[] cur = queue.poll();
                int x = cur[0], y = cur[1];

                // 检查是否为出口（需排除起点）
                if ((x == 0 || x == m - 1 || y == 0 || y == n - 1) &&
                        (x != entrance[0] || y != entrance[1])) {
                    // 直接返回步数，确保最短路径
                    return step;
                }

                // 探索四个方向
                for (int[] dir : dirs) {
                    int nx = x + dir[0];
                    int ny = y + dir[1];
                    // 验证新位置是否有效
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n &&
                            maze[nx][ny] == '.' && !visited[nx][ny]) {
                        visited[nx][ny] = true;
                        queue.offer(new int[]{nx, ny});
                    }
                }
            }
            // 完成一层探索，步数加一
            step++;
        }
        // BFS 结束仍未找到出口
        return -1;
    }
}
