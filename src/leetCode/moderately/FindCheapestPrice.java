package leetCode.moderately;

import java.util.*;

/**
 * @description: 787. K 站中转内最便宜的航班
 */
public class FindCheapestPrice {

    public static void main(String[] args) {
        int n = 5;
        int[][] flights = {{4, 1, 1}, {1, 2, 3}, {0, 3, 2}, {0, 4, 10}, {3, 1, 1}, {1, 4, 3}};
        int src = 2;
        int dst = 1;
        int k = 1;
        System.out.println(new Solution1().findCheapestPrice(n, flights, src, dst, k));
    }

    /**
     * 深度优先(超时)
     * * 思路分析：
     * * 1. 题目要求从 src 到 dst，最多经过 k 个中转站，求最便宜的机票价格。
     * *    - 注意中转次数 k 表示 **最多经过 K 个中转城市**。
     * *    - 直接航班对应 0 次中转，中转一次对应 1 个中转城市，依此类推。
     * * 2. 解题方法：
     * *    - 使用 DFS 遍历所有可能的路径。
     * *    - 遍历过程中维护：
     * *        a. 当前城市 currentCity
     * *        b. 已用中转次数 currentK
     * *        c. 当前累积价格 currentPrice
     * *    - 每次 DFS 判断剪枝条件：
     * *        i. currentCity == dst → 更新最优价格 res
     * *       ii. currentK > k → 超过允许中转次数，剪枝
     * *      iii. currentPrice > res → 当前价格已经比最优解高，剪枝
     * *       iv. 当前城市没有出发航班 → 无法继续前进，剪枝
     * * 3. 邻接表构建：
     * *    - 使用 Map<Integer, List<int[]>> 存储航班信息
     * *      key: 出发城市
     * *      value: List<int[]>，每个 int[] 包含 [到达城市, 航班价格]
     * * 4. 特殊情况：
     * *    - 如果 src 没有出发航班，直接返回 -1
     * * 5. 时间复杂度：
     * *    - DFS 遍历所有路径，最坏情况下复杂度为 O(branch^K)，branch 为每个城市出发航班数。
     * *    - 题目数据规模有限或 K 较小，DFS 可行。
     * * 6. 核心点：
     * *    - 使用累积价格和中转次数剪枝，加速搜索。
     * *    - 处理没有出发航班的城市，保证 DFS 不报错。
     */
    static class Solution1 {
        int res = Integer.MAX_VALUE;

        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
            // Key为出发城市，Value为List<int[]>，int[0]为到达城市，int[1]为价格
            Map<Integer, List<int[]>> map = new HashMap<>();
            for (int[] flight : flights) {
                map.putIfAbsent(flight[0], new ArrayList<>());
                map.get(flight[0]).add(new int[]{flight[1], flight[2]});
            }
            if (!map.containsKey(src)) {
                return -1;
            }
            dfs(map, dst, k, src, 0, 0);
            return res == Integer.MAX_VALUE ? -1 : res;
        }

        /**
         * @param map          城市与城市的航班价格
         * @param dst          目标城市
         * @param k            中转次数
         * @param currentCity  当前城市
         * @param currentK     当前中转次数
         * @param currentPrice 当前价格
         */
        private void dfs(Map<Integer, List<int[]>> map, int dst, int k, int currentCity, int currentK, int currentPrice) {
            // 如果当前城市是目标城市，则更新最小价格
            if (currentCity == dst) {
                res = Math.min(res, currentPrice);
                return;
            }
            // 如果当前中转次数大于k，则返回
            if (currentK > k) {
                return;
            }
            // 如果当前价格大于最小价格，则返回
            if (currentPrice > res) {
                return;
            }
            // 当前城市没有飞往其他城市的航班
            if (!map.containsKey(currentCity)) {
                return;
            }
            for (int[] nextCity : map.get(currentCity)) {
                dfs(map, dst, k, nextCity[0], currentK + 1, currentPrice + nextCity[1]);
            }
        }
    }

    /**
     * 广度优先层序遍历(超时)
     * 除了dfs中的剪枝策略以外，添加cities记录到达每个节点时的最小价格，一旦再次到达该节点价格超过最小价格，则剪枝
     */
    static class Solution2 {

        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
            // Key为出发城市，Value为List<int[]>，int[0]为到达城市，int[1]为价格
            Map<Integer, List<int[]>> map = new HashMap<>();
            for (int[] flight : flights) {
                map.putIfAbsent(flight[0], new ArrayList<>());
                map.get(flight[0]).add(new int[]{flight[1], flight[2]});
            }
            Queue<int[]> queue = new LinkedList<>();
            // 初始化队列，[出发城市，中转次数，价格]
            queue.offer(new int[]{src, 0, 0});
            // 记录到达每个城市的最小价格
            int[] cities = new int[n];
            Arrays.fill(cities, Integer.MAX_VALUE);
            cities[src] = 0;
            int res = Integer.MAX_VALUE;
            while (!queue.isEmpty()) {
                int[] poll = queue.poll();
                int city = poll[0];
                int cK = poll[1];
                int cP = poll[2];
                // 到达目标城市，结束
                if (city == dst) {
                    res = Math.min(res, cP);
                    continue;
                }
                // 中转次数超过k，结束
                if (cK > k) {
                    continue;
                }
                // 当前价格大于最小价格，结束
                if (cP > res) {
                    continue;
                }
                // 当前价格大于到达该城市的最小价格，结束
                if (cP > cities[city]) {
                    continue;
                }
                // 当前城市没有飞往其他城市的航班，结束
                if (!map.containsKey(city)) {
                    continue;
                }
                for (int[] nextCity : map.get(city)) {
                    queue.offer(new int[]{nextCity[0], cK + 1, cP + nextCity[1]});
                }
            }
            return res == Integer.MAX_VALUE ? -1 : res;
        }
    }

    /**
     * Solution2的优化版本
     * 解法：BFS（广度优先搜索）+ 状态剪枝（带中转次数维度）
     * *
     * * 一、原始思路（你的做法）：
     * * 1. 使用 BFS 层序遍历，每一层代表一次中转。
     * * 2. 队列中存储：[当前城市, 当前中转次数, 当前价格]
     * * 3. 使用 cities[city] 记录到达某个城市的最小价格，尝试剪枝：
     * *      如果当前价格 > 已记录最小价格，则剪枝
     * *
     * * 二、原解法存在的问题：
     * *   剪枝维度不足（核心问题）
     * *    - cities[city] 只记录“到达该城市的最小价格”
     * *    - 但没有考虑“中转次数”
     * *
     * *   举例：
     * *    路径A：价格100，中转1次
     * *    路径B：价格80，中转2次
     * *
     * *    如果记录 cities[city] = 80，则路径A（100）会被剪掉，
     * *    但路径A中转次数更少，可能更有机会在 K 限制内到达终点
     * *
     * *   本质问题：
     * *    这是一个“带路径长度限制的最短路径问题”，
     * *    状态必须包含：
     * *        (城市, 已用中转次数)
     * *
     * * 三、优化思路（本解法）：
     * *   使用二维数组 minPrice[city][steps]
     * *    - 表示：到达 city，使用 steps 条边（中转次数）的最小价格
     * *
     * *   剪枝条件升级：
     * *    - 如果当前价格 >= minPrice[city][steps] → 剪枝
     * *
     * *   优化效果：
     * *    - 避免错误剪枝（不会错过更优路径）
     * *    - 避免重复搜索（减少状态爆炸）
     * *
     * * 四、复杂度分析：
     * * 时间复杂度：O(K * E)
     * * 空间复杂度：O(K * V)
     * *
     * * 五、核心总结：
     * * 本题关键在于：
     * *   不能只用“城市”作为状态
     * *   必须用“城市 + 中转次数”作为状态
     */
    static class Solution3 {
        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
            // 构建邻接表：出发城市 -> List< [到达城市, 价格] >
            Map<Integer, List<int[]>> map = new HashMap<>();
            for (int[] flight : flights) {
                map.putIfAbsent(flight[0], new ArrayList<>());
                map.get(flight[0]).add(new int[]{flight[1], flight[2]});
            }

            // BFS 队列：[当前城市, 已用边数(中转次数), 当前价格]
            Queue<int[]> queue = new LinkedList<>();
            queue.offer(new int[]{src, 0, 0});

            // 二维剪枝数组：
            // minPrice[city][steps] = 到达 city 使用 steps 条边的最小价格
            int[][] minPrice = new int[n][k + 2];
            for (int i = 0; i < n; i++) {
                Arrays.fill(minPrice[i], Integer.MAX_VALUE);
            }
            minPrice[src][0] = 0;

            int res = Integer.MAX_VALUE;

            while (!queue.isEmpty()) {
                int[] curr = queue.poll();
                int city = curr[0];
                int steps = curr[1];
                int price = curr[2];
                // 到达目标城市，更新最优解（注意不能直接 return，因为可能有更便宜路径）
                if (city == dst) {
                    res = Math.min(res, price);
                    continue;
                }
                // 剪枝1：超过最大中转次数（最多 k 次中转，对应最多 k+1 条边）
                if (steps > k) continue;
                // 剪枝2：当前价格已经超过全局最优解
                if (price > res) continue;
                // 没有出发航班
                if (!map.containsKey(city)) continue;
                // 遍历所有邻接节点
                for (int[] next : map.get(city)) {
                    int nextCity = next[0];
                    int nextPrice = price + next[1];
                    int nextSteps = steps + 1;
                    // 剪枝3（核心）：
                    // 如果以相同步数到达该城市已经有更优价格，则无需继续
                    if (nextPrice >= minPrice[nextCity][nextSteps]) continue;
                    // 更新最优状态
                    minPrice[nextCity][nextSteps] = nextPrice;
                    // 入队继续 BFS
                    queue.offer(new int[]{nextCity, nextSteps, nextPrice});
                }
            }
            return res == Integer.MAX_VALUE ? -1 : res;
        }
    }

    /**
     * Bellman-Ford 变形（基于边松弛）
     * 解法思路：
     * 1. 题目本质是：求从 src 到 dst 的最短路径，但限制路径最多 K+1 条边（最多 K 次中转）。
     * 2. Bellman-Ford 核心思想：第 i 轮松弛后，得到“最多 i 条边”的最短路径。
     * 3. 因此，本题可以做 K+1 轮松弛，每轮基于上一轮结果更新价格，保证每轮最多增加一条边。
     * 4. 优化点：
     * - 相比 DFS/BFS，本算法无需显式存储队列或记录访问状态，复杂度稳定 O(K * E)
     * - 避免 DFS/BFS 状态爆炸，且天然支持步数限制
     */
    static class Solution4 {

        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
            // 构建邻接表：出发城市 -> List< [到达城市, 价格] >
            Map<Integer, List<int[]>> map = new HashMap<>();
            for (int[] flight : flights) {
                map.putIfAbsent(flight[0], new ArrayList<>());
                map.get(flight[0]).add(new int[]{flight[1], flight[2]});
            }
            // 记录从 src 到每个城市的最小价格，初始时 src->src 为 0，其他为无穷大
            int[] dist = new int[n];
            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[src] = 0;
            // 最多 K 次中转 → 最多 K+1 条边 → 做 K+1 轮松弛
            for (int i = 0; i <= k; i++) {
                // 克隆上一轮结果，避免本轮更新本轮再使用（防止走多条边）
                int[] last = dist.clone();

                // 遍历所有有出发航班的城市
                for (int city : map.keySet()) {
                    // 如果上一轮到 city 无法到达，则跳过
                    if (last[city] == Integer.MAX_VALUE) continue;

                    // 遍历 city 的所有航班，尝试松弛下一城市价格
                    for (int[] next : map.get(city)) {
                        int nextCity = next[0];
                        int price = next[1];
                        // 松弛操作：用上一轮结果更新本轮价格
                        dist[nextCity] = Math.min(dist[nextCity], last[city] + price);
                    }
                }
            }

            // 如果 dst 仍然是无穷大，说明无法到达，返回 -1
            return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
        }
    }

    /**
     * 动态规划
     * 解法思路：
     * 1. 定义二维 dp 数组：
     * -     dp[i][j] 表示从 src 出发，经过恰好 j 条边（航班）到达城市 i 的最小价格。
     * 2. 初始化：
     * -     dp[src][0] = 0，表示从 src 出发，不经过任何边价格为 0。
     * -     其他 dp[i][j] = Integer.MAX_VALUE，表示尚不可达。
     * 3. 状态转移：
     * -     对于每条航班 flight = [source, target, price]，如果 dp[source][i-1] 可达（不为 MAX_VALUE）：
     * -         dp[target][i] = min(dp[target][i], dp[source][i-1] + price)
     * -     注意：
     * -         a) 不能写成 dp[target][i] = min(dp[target][i-1], dp[source][i-1]+price)
     * -            - dp[target][i-1] 是上一轮（少一条边）到达 target 的价格
     * -            - 第 i 轮表示恰好 i 条边到达 target，如果用 dp[target][i-1] 比较，会混淆边数，导致无法正确更新多条城市同时飞向 target 的最优价格
     * -         b) 不需要克隆 dp 数组
     * -            - 二维数组天然保证第 i 轮只用上一轮 dp[..][i-1] 的数据
     * -            - 本轮更新 dp[..][i] 不会影响其他 source 的更新
     * 4. 最终结果：
     * -     - 题目要求“最多 K 次中转”，即最多 K+1 条边
     * -     - 最便宜的航班可能使用少于 K+1 条边
     * -     - 因此需要在 dp[dst][1..k+1] 中取最小值
     * -     - 不能直接返回 dp[dst][k+1]
     * 5. 与 Bellman-Ford 变形对比：
     * -     - 本算法每轮更新所有边，只依赖上一轮状态
     * -     - 与 Bellman-Ford 每轮松弛所有边、使用上一轮 dist 数组的原理一致
     * -     - 理论上是等价的，只是 Bellman-Ford 通常使用一维数组表示当前最短距离
     */
    static class Solution5 {
        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
            // dp[i][j]表示：从src到i，最多j次中转，最小价格
            // dp[i][0]表示，从src到i，不中转，最小价格，则默认最大值
            // 因为最多可以中转k次，也就是有k+1个节点，因此需要k+2个节点
            int[][] dp = new int[n][k + 2];
            for (int i = 0; i < n; i++) {
                Arrays.fill(dp[i], Integer.MAX_VALUE);
            }
            dp[src][0] = 0;
            for (int i = 1; i <= k + 1; i++) {
                for (int[] flight : flights) {
                    int source = flight[0];
                    int target = flight[1];
                    int price = flight[2];
                    // 如果上一轮 source 无法到达，则跳过
                    if (dp[source][i - 1] == Integer.MAX_VALUE) continue;
                    // 经历i次中转，从src到达target的最小价格 =
                    // min(i-1次到达target, i-1次到达source + source到target的价格)
                    dp[target][i] = Math.min(dp[target][i], dp[source][i - 1] + price);
                }
            }
            int res = Integer.MAX_VALUE;
            // 经历任意次中转，从src到达dst的最小价格
            for (int i = 1; i <= k + 1; i++) {
                res = Math.min(res, dp[dst][i]);
            }
            return res == Integer.MAX_VALUE ? -1 : res;
        }
    }

    /**
     * Dijkstra + 优先队列（带中转次数限制）
     * 解法思路：
     * 1. 经典 Dijkstra 只能处理“无边数限制”的最短路径，本题需要限制最多 K 次中转，
     * 因此必须把“中转次数”作为状态的一部分。
     * 2. 状态定义：
     * (city, steps, price)
     * - city：当前城市
     * - steps：已经使用的边数（航班数）
     * - price：当前累计价格
     * 3. 核心思想：
     * - 使用优先队列（最小堆），按 price 从小到大排序
     * - 每次优先扩展价格最小的路径（Dijkstra 思想）
     * 4. 剪枝优化：
     * - 使用 dist[city][steps] 记录到达某城市、使用 steps 条边的最小价格
     * - 如果当前路径价格 >= 已记录值，则剪枝
     * 5. 为什么可以提前返回？
     * - 优先队列保证每次取出的都是当前最小价格路径
     * - 第一次到达 dst 的路径一定是满足限制条件的最优解
     * 6. 与 BFS / Bellman-Ford 的关系：
     * - BFS：按“步数”优先
     * - Bellman-Ford：按“轮数（步数）”推进
     * - Dijkstra：按“价格”优先（更快找到最优解）
     */
    static class Solution6 {

        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
            // 构建邻接表：出发城市 -> List< [到达城市, 价格] >
            Map<Integer, List<int[]>> map = new HashMap<>();
            for (int[] flight : flights) {
                map.putIfAbsent(flight[0], new ArrayList<>());
                map.get(flight[0]).add(new int[]{flight[1], flight[2]});
            }

            // 优先队列：[当前价格, 当前城市, 已用边数]
            PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
            pq.offer(new int[]{0, src, 0});

            // dist[city][steps]：到达 city 使用 steps 条边的最小价格
            int[][] dist = new int[n][k + 2];
            for (int i = 0; i < n; i++) {
                Arrays.fill(dist[i], Integer.MAX_VALUE);
            }
            dist[src][0] = 0;

            while (!pq.isEmpty()) {
                int[] curr = pq.poll();
                int price = curr[0];
                int city = curr[1];
                int steps = curr[2];
                // 如果到达终点，直接返回（Dijkstra 保证最优）
                if (city == dst) {
                    return price;
                }
                // 超过最多允许的边数（k+1 条边）
                if (steps > k) continue;
                // 剪枝：当前状态不是最优
                if (price > dist[city][steps]) continue;
                // 没有出发航班
                if (!map.containsKey(city)) continue;
                // 扩展邻居
                for (int[] next : map.get(city)) {
                    int nextCity = next[0];
                    int nextPrice = price + next[1];
                    int nextSteps = steps + 1;
                    // 剪枝：如果更优才更新
                    if (nextPrice >= dist[nextCity][nextSteps]) continue;
                    dist[nextCity][nextSteps] = nextPrice;
                    pq.offer(new int[]{nextPrice, nextCity, nextSteps});
                }
            }
            return -1;
        }
    }

}
