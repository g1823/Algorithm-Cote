package leetCode.moderately;

/**
 * @author: gj
 * @description: 289. 生命游戏
 */
public class GameOfLife {
    public static void main(String[] args) {
        int[][] board = {{0, 0, 0, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 0, 0, 0}};
        new GameOfLife().gameOfLife(board);
        System.out.println(board);
    }

    /**
     * 289. 生命游戏
     * 思路与过程：
     * 1. 遍历每个格子，统计它周围八个邻居的存活数量（需要考虑边界）。
     * - 注意：由于更新是同步进行的，因此我们不能直接覆盖原有状态。
     * - 用中间状态表示变化过程：
     * - - 2 表示原来是活的，但下一轮变为死；
     * - - 3 表示原来是死的，但下一轮变为活。
     * 2. 根据生命游戏规则更新状态：
     * - 活细胞：<2 或 >3 个邻居活细胞 → 变死（标记为 2）；
     * - 死细胞：恰好 3 个邻居活细胞 → 变活（标记为 3）。
     * 3. 最后再遍历一遍，把所有 2 改为 0，3 改为 1，即完成新一轮状态更新。
     */
    public void gameOfLife(int[][] board) {
        int m = board.length, n = board[0].length;

        // 第一次遍历：计算每个细胞的下一轮状态
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 周围活细胞计数
                int count = 0;

                // 上面三格
                if (i - 1 >= 0) {
                    // 正上
                    count = board[i - 1][j] == 1 || board[i - 1][j] == 2 ? count + 1 : count;
                    // 左上
                    count = j - 1 >= 0 && (board[i - 1][j - 1] == 1 || board[i - 1][j - 1] == 2) ? count + 1 : count;
                    // 右上
                    count = j + 1 < n && (board[i - 1][j + 1] == 1 || board[i - 1][j + 1] == 2) ? count + 1 : count;
                }

                // 下面三格
                if (i + 1 < m) {
                    // 正下
                    count = board[i + 1][j] == 1 || board[i + 1][j] == 2 ? count + 1 : count;
                    // 左下
                    count = j - 1 >= 0 && (board[i + 1][j - 1] == 1 || board[i + 1][j - 1] == 2) ? count + 1 : count;
                    // 右下
                    count = j + 1 < n && (board[i + 1][j + 1] == 1 || board[i + 1][j + 1] == 2) ? count + 1 : count;
                }

                // 左边一个
                if (j - 1 >= 0) {
                    count = board[i][j - 1] == 1 || board[i][j - 1] == 2 ? count + 1 : count;
                }

                // 右边一个
                if (j + 1 < n) {
                    count = board[i][j + 1] == 1 || board[i][j + 1] == 2 ? count + 1 : count;
                }

                // 按规则更新状态
                if (board[i][j] == 1) {
                    // 当前是活细胞
                    if (count < 2 || count > 3) {
                        // 活 → 死
                        board[i][j] = 2;
                    }
                } else {
                    // 当前是死细胞
                    if (count == 3) {
                        // 死 → 活
                        board[i][j] = 3;
                    }
                }
            }
        }

        // 第二次遍历：还原最终状态
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 2) {
                    board[i][j] = 0;
                }
                if (board[i][j] == 3) {
                    board[i][j] = 1;
                }
            }
        }
    }

}
