package leetCode.moderately;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author: gj
 * @description: 909. 蛇梯棋
 */
public class SnakesAndLadders {
    public static void main(String[] args) {
        int[][] ints = {
                {-1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1},
                {-1, 35, -1, -1, 13, -1},
                {-1, -1, -1, -1, -1, -1},
                {-1, 15, -1, -1, -1, -1}};
        int[][] ints1 = {
                {1, 1, -1},
                {1, 1, 1},
                {-1, 1, 1}};
        int[][] ints2 = {
                {-1, -1, -1, 46, 47, -1, -1, -1},
                {51, -1, -1, 63, -1, 31, 21, -1},
                {-1, -1, 26, -1, -1, 38, -1, -1},
                {-1, -1, 11, -1, 14, 23, 56, 57},
                {11, -1, -1, -1, 49, 36, -1, 48},
                {-1, -1, -1, 33, 56, -1, 57, 21},
                {-1, -1, -1, -1, -1, -1, 2, -1},
                {-1, -1, -1, 8, 3, -1, 6, 56}
        };
        System.out.println(new SnakesAndLadders().snakesAndLadders(ints2));
    }

    /**
     * 首先考虑dfs，每层递归尝试移动1-6的格子，每次递归次数+1.当达到终点时更新最少次数。
     * 问题：路径指数爆炸，因为需要所有路径都访问、且难以通过visited进行剪枝，因为有蛇或者桥，可能会回退
     * 思路：
     * 采用bfs，因为起点是固定的，那么每一层都是定的（1-6），然后一层一层访问，第一次找到终点时一定是最少次数，直接返回即可。
     * 步骤：
     * 1. 创建队列，将起点1加入队列，创建visited数组，记录访问过的节点，避免重复访问
     * 2. 每次遍历当前层的节点，对每个节点进行移动1-6格，并记录访问过的节点（因为是一层一层访问，下次再次到达该节点，层数一定加1，会大于当前层，可以直接剪枝）
     * 3. 遍历到终点时，返回当前步数
     * 注意：
     * 需要进行下标和横纵坐标的转换：next为按照题目规则，从左下角排序的下标。
     * // 走了几行
     * int r = (next - 1) / n;
     * // 在当前行的偏移量（从左往右）
     * int c = (next - 1) % n;
     * // 实际的横坐标
     * int row = m - 1 - r;
     * // 实际的纵坐标（当走了奇数行则从右往左，偶数行从左往右）
     * int col = r % 2 == 0 ? c : n - 1 - c;
     */
    public int snakesAndLadders(int[][] board) {
        int m = board.length, n = board[0].length;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        int step = 0;
        boolean[] visited = new boolean[m * n + 1];
        visited[1] = true;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int cur = queue.poll();
                for (int j = 1; j <= 6; j++) {
                    int next = cur + j;
                    if (next > m * n || visited[next]) {
                        continue;
                    }
                    // 提前将当前节点标记为已访问，因为后续next值会改变为蛇梯棋的终点，因此需要提前标记为已访问
                    visited[next] = true;
                    // 走了几行
                    int r = (next - 1) / n;
                    // 在当前行的偏移量（从左往右）
                    int c = (next - 1) % n;
                    // 实际的横坐标
                    int row = m - 1 - r;
                    // 实际的纵坐标（当走了奇数行则从右往左，偶数行从左往右）
                    int col = r % 2 == 0 ? c : n - 1 - c;
                    if (board[row][col] != -1) {
                        next = board[row][col];
                    }
                    if (next == m * n) {
                        return step + 1;
                    }
                    queue.offer(next);
                }
            }
            step++;
        }
        return -1;
    }
}
