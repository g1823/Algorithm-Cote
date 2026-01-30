package leetCode.moderately;

import java.util.*;

/**
 * @author: gj
 * @description: 1466. 重新规划路线
 */
public class MinReorder {
    public static void main(String[] args) {
        int n = 6;
        int[][] connections = new int[][]{{0, 1}, {1, 3}, {2, 3}, {4, 0}, {4, 5}};
        System.out.println(new MinReorder().minReorder(n, connections));
    }

    int count = 0;

    /**
     * - 【问题抽象】
     * - - 给定 n 个城市和 n-1 条有向道路
     * - - 忽略方向后，所有城市连通，且边数 = n-1
     * - - 因此底层结构是一棵「无向树」，而不是一条链（这是一个重要前提）
     * - - 每条边仅存在唯一的一条路径连接到 0
     * - 【最初的错误理解】
     * - 1. 误以为 connections 是类似邻接矩阵或“i,j 是否连通”的结构，
     * -    从而尝试对 (i,j) 做全量扫描；
     * - 2. 又因为题目示例刚好是链形结构，进一步误判“所有节点最多连两个节点”；
     * - 3. 甚至担心 DFS 过程中会因为“存在多条路径”而导致误判是否需要反向。
     * - 【关键纠偏点】
     * - - connections 中的每一项 [from, to] 表示“一条真实存在的有向边”，而不是“互通关系”
     * - - 忽略方向后：
     * -      n 个节点 + n-1 条边 + 全连通  ⇒  必然是一棵树
     * - - 树的核心性质：
     * -      任意两个节点之间，路径唯一，不存在环
     * - 这一点直接否定了“DFS 时可能通过另一条路径绕回”的担心，
     * - 也保证了：一旦某条边在 DFS 树方向上是“指向外部的”，就没有替代路径可修正。
     * - 【核心建模方式】
     * - - 将每条有向边拆成两条“可遍历的边”：
     * -      原边 u -> v：
     * -        u -> v，标记为 1（原始方向，从当前节点指出去）
     * -        v -> u，标记为 0（原始方向，指向当前节点）
     * - - 这样可以：
     * -      1）按无向树进行 DFS / BFS
     * -      2）同时保留“当前遍历方向是否与原始方向一致”的信息
     * - 【为什么只从 0 做一次 DFS 就够】
     * - 1. 忽略方向后是树，因此所有节点都能且只能通过唯一一条路径到达 0
     * - 2. 从 0 出发 DFS，会形成一棵 DFS 树：
     * -      - 每条边只会以“父 -> 子”的形式被访问一次
     * -      - 不存在从子节点绕路回父节点的可能
     * - 3. 在 DFS 过程中：
     * -      - 若当前是从 u 走向 v
     * -      - 且原始方向也是 u -> v
     * -      - 则 v 无法沿这条唯一路径回到 u（最终回到 0）
     * -      - 该边必须反向，计数 +1
     * - 4. 由于路径唯一：
     * -      - 这个判断不会被后续遍历推翻
     * -      - 也不会被重复计算
     * - 【算法流程】
     * - 1. 构建邻接表（无向），每条边携带方向标记
     * - 2. 从节点 0 开始 DFS
     * - 3. 使用 visited 防止回溯
     * - 4. DFS 到邻居时：
     * -      - 若方向标记为 1，则计数
     * -      - 递归处理子节点
     * - 【本质总结】
     * - 本题并非一般有向图问题，而是：
     * -   「带方向状态的树遍历问题」
     * - 正确性完全依赖于“无向意义下是树、路径唯一”这一前提。
     */
    public int minReorder(int n, int[][] connections) {
        Set<Integer> visited = new HashSet<>();
        Map<Integer, List<int[]>> map = new HashMap<>();
        for (int[] connection : connections) {
            map.computeIfAbsent(connection[0], k -> new ArrayList<>()).add(new int[]{connection[1], 1});
            map.computeIfAbsent(connection[1], k -> new ArrayList<>()).add(new int[]{connection[0], 0});
        }
        dfs(map, visited, 0);
        return count;
    }

    public void dfs(Map<Integer, List<int[]>> map, Set<Integer> visited, int i) {
        if (visited.contains(i)) {
            return;
        }
        visited.add(i);
        if (map.containsKey(i)) {
            for (int[] connection : map.get(i)) {
                if (visited.contains(connection[0])) {
                    continue;
                }
                if (connection[1] == 1) {
                    count++;
                }
                dfs(map, visited, connection[0]);
            }
        }
    }
}
