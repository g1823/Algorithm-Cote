package leetCode.difficult;

import java.util.*;

/**
 * @description: 3620. 恢复网络路径
 */
public class FindMaxPathScore {

    /**
     * 二分答案 + Dijkstra：
     * 二分所有边，作为答案进行校验。
     * 再使用Dijkstra计算从源点到目标节点的最近距离，如果最近距离小于k，则说明指定边为最小边时，存在一条路径从源点到达最后节点，且长度小于k
     */
    class Solution1 {
        public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
            // 构造图,下标为出发点，value为目标点和成本。eg: nodes.get(i)[0] = {x,y}：表示i邻接x，距离为y
            List<List<int[]>> nodes = new ArrayList<>();
            for (int i = 0; i < online.length; i++) {
                nodes.add(new ArrayList<>());
            }
            // 记录所有的边枚举
            Set<Integer> costSet = new HashSet<>();
            for (int i = 0; i < edges.length; i++) {
                int cur = edges[i][0];
                int tar = edges[i][1];
                int cost = edges[i][2];
                costSet.add(cost);
                int[] prerequisite = new int[]{tar, cost};
                nodes.get(cur).add(prerequisite);
            }
            // 对每个节点的邻接节点进行排序，按照cost距离倒序排序
            // 这里用不上
            // for (int i = 0; i < online.length; i++) {
            //     nodes.get(i).sort(Comparator.comparingInt((int[] a) -> a[1]).reversed());
            // }
            List<Integer> costList = new ArrayList<>(costSet);
            Collections.sort(costList);

            // 二分答案
            int left = 0, right = costList.size() - 1, ans = -1;
            while (left <= right) {
                int mid = (left + right) >>> 1;
                if (check(nodes, costList.get(mid), k, online)) {
                    ans = costList.get(mid);
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            return ans;
        }
        private boolean check(List<List<int[]>> nodes, Integer minCost, long k, boolean[] online) {
            // 使用Dijkstra算法求解最短路径
            PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));
            pq.offer(new long[]{0, 0});
            long[] dist = new long[nodes.size()];
            Arrays.fill(dist, Long.MAX_VALUE);
            dist[0] = 0;
            while (!pq.isEmpty()) {
                long[] curr = pq.poll();
                int node = (int) curr[0];
                long cost = curr[1];
                // 当前成本不等于最短成本，结束(过时数据)
                if (cost != dist[node]) continue;
                // 当前成本高于最大成本，结束
                if (cost > k) continue;
                // 不在线，结束
                if (!online[node]) continue;
                // 到达最后一个节点
                if (node == nodes.size() - 1) {
                    return true;
                }
                for (int[] next : nodes.get(node)) {
                    int nextNode = next[0];
                    int nextCost = next[1];
                    // 当前边小于给定最小边，直接跳过
                    if (nextCost < minCost) continue;
                    if (dist[nextNode] > dist[node] + nextCost) {
                        dist[nextNode] = dist[node] + nextCost;
                        pq.offer(new long[]{nextNode, dist[nextNode]});
                    }
                }
            }
            return false;
        }
    }

    /**
     * 二分答案 + 拓扑排序 DP：
     * 利用 DAG 性质，每个节点在拓扑序中只被处理一次，无需优先队列。
     */
    class Solution2 {
        List<Integer> topo; // 拓扑序，只需计算一次

        public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
            int n = online.length;
            // 构造图，并统计入度
            List<List<int[]>> nodes = new ArrayList<>();
            int[] indegree = new int[n];
            for (int i = 0; i < n; i++) {
                nodes.add(new ArrayList<>());
            }
            Set<Integer> costSet = new HashSet<>();
            for (int[] e : edges) {
                int u = e[0], v = e[1], w = e[2];
                nodes.get(u).add(new int[]{v, w});
                indegree[v]++;
                costSet.add(w);
            }

            // ----- Kahn 算法求拓扑序（只需一次）-----
            topo = new ArrayList<>();
            Queue<Integer> q = new LinkedList<>();
            // 将所有入度为0的节点加入队列，即将所有起始节点加入队列，这里就是只有节点0
            for (int i = 0; i < n; i++) {
                if (indegree[i] == 0) q.offer(i);
            }
            while (!q.isEmpty()) {
                int u = q.poll();
                topo.add(u);
                for (int[] edge : nodes.get(u)) {
                    int v = edge[0];
                    indegree[v]--;
                    // 当节点都入度减为0，即其前面可以到达该节点的所有节点全部都已经处理完，当前节点入队
                    if (indegree[v] == 0) q.offer(v);
                }
            }

            // ----- 对所有边权进行二分 -----
            List<Integer> costList = new ArrayList<>(costSet);
            Collections.sort(costList);
            int left = 0, right = costList.size() - 1, ans = -1;
            while (left <= right) {
                int mid = (left + right) >>> 1;
                if (check(nodes, costList.get(mid), k, online)) {
                    ans = costList.get(mid);
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            return ans;
        }

        /**
         * 按拓扑序做 DP，检查是否存在一条 0→n-1 的路径：
         * - 每条边权 ≥ minCost
         * - 中间节点均在线
         * - 总成本 ≤ k
         */
        private boolean check(List<List<int[]>> nodes, int minCost, long k, boolean[] online) {
            int n = nodes.size();
            long[] dist = new long[n];
            Arrays.fill(dist, Long.MAX_VALUE);
            dist[0] = 0;

            for (int u : topo) {
                // 节点 u 不可达（没有从 0 出发的合法路径到达），跳过
                if (dist[u] == Long.MAX_VALUE) continue;
                // 到达终点，后面的节点不需要再处理
                if (u == n - 1) break;
                // 更新当前节点的邻接节点的值
                for (int[] edge : nodes.get(u)) {
                    int v = edge[0], w = edge[1];
                    // 边权小于阈值，跳过；目标节点不在线，跳过
                    if (w < minCost || !online[v]) continue;
                    long nd = dist[u] + w;
                    if (nd < dist[v]) {
                        dist[v] = nd;
                    }
                }
            }
            // 如果最后一个节点距离小于k，则说明存在一条路径从0到n-1，且距离小于k
            return dist[n - 1] <= k;
        }
    }

}
