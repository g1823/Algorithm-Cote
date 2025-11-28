package leetCode.moderately;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author: gj
 * @description: 994. 腐烂的橘子
 */
public class OrangesRotting {
    public static void main(String[] args) {
        int[][] grid = {
                {2, 1, 1},
                {1, 1, 0},
                {0, 1, 1}};
        int result = new OrangesRotting().orangesRotting(grid);
        System.out.println(result);
    }

    /**
     * 模拟腐烂橘子的每日传播过程
     * 思路：
     * 1. 逐天模拟腐烂过程：
     * - 遍历整个网格，对每个新鲜橘子判断四周是否有腐烂橘子
     * - 如果有，先将其标记为临时状态(3)，避免同一天内重复腐烂传播
     * 2. 当一天的扫描结束后，将所有标记为3的橘子改成腐烂(2)
     * 3. 通过变量：
     * - curCount：记录当前腐烂 + 新鲜橘子数量
     * - newAdd：记录当天新增腐烂的数量
     * - block：记录空格(0)的数量
     * - count：记录上一轮的 curCount，用来判断是否有变化
     * 4. 终止条件：
     * - 全部为空格（block == m*n） → 返回天数
     * - 没有任何橘子（curCount == 0） → 返回 -1
     * - 所有非空格格子都是腐烂橘子（curCount == m*n - block）：
     * - 若 newAdd == 0 → 返回 day
     * - 否则 → 返回 day + 1
     * - 如果 curCount 与上一轮相同 → 没有新的腐烂发生 → 返回 -1
     * 5. 否则进入下一天，继续模拟
     * 时间复杂度：O((m*n)*天数)，空间复杂度：O(1)
     */
    public int orangesRotting(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int day = 0, count = 0, block = 0;

        while (true) {
            // 当前总橘子数（腐烂 + 新鲜）
            int curCount = 0;
            // 当天新腐烂数量
            int newAdd = 0;
            // 空格数量
            block = 0;

            // 遍历网格，标记当天将要腐烂的橘子
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    // 当前为新鲜橘子
                    if (grid[i][j] == 1) {
                        // 上
                        if (i > 0 && grid[i - 1][j] == 2) {
                            // 临时标记
                            grid[i][j] = 3;
                            curCount++;
                            newAdd++;
                            continue;
                        }
                        // 左
                        if (j > 0 && grid[i][j - 1] == 2) {
                            grid[i][j] = 3;
                            curCount++;
                            newAdd++;
                            continue;
                        }
                        // 下
                        if (i < m - 1 && grid[i + 1][j] == 2) {
                            grid[i][j] = 3;
                            curCount++;
                            newAdd++;
                            continue;
                        }
                        // 右
                        if (j < n - 1 && grid[i][j + 1] == 2) {
                            grid[i][j] = 3;
                            curCount++;
                            newAdd++;
                        }
                    }
                    // 当前为腐烂橘子
                    else if (grid[i][j] == 2) {
                        curCount++;
                    }
                    // 当前为空格
                    else {
                        block++;
                    }
                }
            }

            // 将临时标记的橘子(3)变成腐烂(2)
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 3) {
                        grid[i][j] = 2;
                    }
                }
            }

            // 全部为空格
            if (block == m * n) {
                return day;
            }
            // 没有任何橘子
            if (curCount == 0) {
                return -1;
            }
            // 全部是腐烂橘子
            if (curCount == m * n - block) {
                if (newAdd == 0) {
                    return day;
                }
                return day + 1;
            }
            // 没有新的腐烂
            if (curCount == count) {
                return -1;
            } else {
                count = curCount;
            }

            // 进入下一天
            day++;
        }
    }

    /**
     * orangesRotting2 —— 模拟腐烂橘子的过程（精简优化版本）
     * 思路：
     * 1. 先统计新鲜橘子数量，如果为 0，直接返回 0。
     * 2. 每天找出所有会腐烂的新鲜橘子（通过遍历并检查四个方向是否有腐烂橘子）。
     * 3. 批量将这些橘子腐烂，并减少新鲜橘子计数 fresh。
     * 4. 如果某天没有橘子被腐烂（toRot 为空），说明剩余橘子无法被感染，直接返回 -1。
     * 5. 重复直到 fresh 为 0，返回所用的天数 day。
     * 相较于方法 1 的优化点：
     * 1. 移除多余状态变量
     * 方法 1 维护了 curCount、newAdd、block、count 等多个变量进行复杂判断，
     * 方法 2 只维护 `fresh`（新鲜橘子数）作为核心判断依据，减少了状态切换的复杂性。
     * 2. 一次遍历统计新鲜橘子
     * 方法 1 每天都会统计橘子数量，方法 2 在开头一次性统计初始 fresh，
     * 后续只在腐烂时递减，避免了重复 O(m*n) 的计数。
     * 3. 批量更新腐烂
     * 方法 1 使用临时标记 (3) 再二次遍历更新，方法 2 直接用 `toRot` 列表保存待腐烂位置，
     * 腐烂更新只需一次循环，减少不必要的网格二次扫描。
     * 4. 精简终止条件判断
     * 方法 1 有多层复杂的 if 判断组合终止条件，方法 2 直接依赖 `fresh == 0` 或 `toRot.isEmpty()` 作为结束条件，逻辑更清晰。
     */
    public int orangesRotting2(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int day = 0, fresh = 0;
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        List<int[]> toRot = new ArrayList<>();
        // 统计新鲜橘子数量
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    fresh++;
                }
            }
        }
        if (fresh == 0) {
            return 0;
        }

        while (fresh > 0) {
            toRot.clear();
            // 找到所有将被腐烂的新鲜橘子
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
                        for (int[] dir : dirs) {
                            int x = i + dir[0];
                            int y = j + dir[1];
                            if (x >= 0 && x < m && y >= 0 && y < n && grid[x][y] == 2) {
                                toRot.add(new int[]{i, j});
                                break;
                            }
                        }
                    }
                }
            }
            // 没有新鲜橘子可以腐烂，卡住了
            if (toRot.isEmpty()) {
                return -1;
            }

            // 批量更新腐烂
            for (int[] orange : toRot) {
                grid[orange[0]][orange[1]] = 2;
                fresh--;
            }
            // 一天结束
            day++;
        }

        return day;
    }

    /**
     * 方法思路：
     * 1. 多源 BFS：先将所有初始腐烂橘子入队，同时统计新鲜橘子数量。
     * 2. 每轮 BFS 代表一分钟的腐烂扩散：
     * - 遍历队列中的腐烂橘子，感染四周新鲜橘子。
     * - 新腐烂的橘子入队，等待下一轮扩散。
     * 3. 终止条件：
     * - BFS 完成后，如果还有新鲜橘子剩余，返回 -1。
     * - 否则返回所需分钟数。
     * 4. 优化点：
     * - 原地修改 grid，不使用额外 visited 数组。
     * - 使用队列进行层序遍历，自然计数分钟数。
     * - 每个橘子最多入队一次，减少冗余操作。
     */
    public int orangesRotting3(int[][] grid) {
        // 获取网格行数和列数
        int m = grid.length;
        int n = grid[0].length;

        // 队列保存腐烂橘子的位置，用于 BFS 扩散
        Queue<int[]> queue = new LinkedList<>();

        // 统计新鲜橘子数量
        int fresh = 0;

        // 遍历整个网格，初始化队列和新鲜橘子计数
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 如果是新鲜橘子，fresh 增加
                if (grid[i][j] == 1) {
                    fresh++;
                }
                // 如果是腐烂橘子，加入队列作为 BFS 起点
                else if (grid[i][j] == 2) {
                    queue.offer(new int[]{i, j});
                }
            }
        }
        // 如果没有新鲜橘子，直接返回 0
        if (fresh == 0) {
            return 0;
        }
        // 定义四个方向数组，上下左右
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        // 记录所需分钟数
        int minutes = 0;
        // BFS 循环，队列为空时停止
        while (!queue.isEmpty()) {
            // 当前队列大小表示本轮腐烂橘子的数量
            int size = queue.size();
            // 标记本轮是否有橘子被腐烂
            boolean infected = false;
            // 遍历当前层的所有腐烂橘子
            for (int i = 0; i < size; i++) {
                // 弹出队列头部橘子
                int[] cur = queue.poll();
                int x = cur[0];
                int y = cur[1];
                // 检查四个方向
                for (int[] dir : dirs) {
                    int nx = x + dir[0];
                    int ny = y + dir[1];
                    // 如果邻居在网格范围内且是新鲜橘子
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] == 1) {
                        // 将新鲜橘子腐烂
                        grid[nx][ny] = 2;
                        // 新腐烂橘子入队，等待下一轮扩散
                        queue.offer(new int[]{nx, ny});
                        // 新鲜橘子数量减少
                        fresh--;
                        // 标记本轮有感染发生
                        infected = true;
                    }
                }
            }
            // 如果本轮有橘子被腐烂，分钟数增加
            if (infected) {
                minutes++;
            }
        }
        // 如果还有新鲜橘子无法腐烂，返回 -1；否则返回分钟数
        return fresh == 0 ? minutes : -1;
    }

}
