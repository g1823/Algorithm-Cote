package leetCode.moderately;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @description: 785. 判断二分图
 */
public class IsBipartite {
    /**
     * * 判断二分图（785. Is Bipartite Graph）
     * *
     * * 一、原始想法（最初的困惑）
     * * ----------
     * * - 最开始可能想法是“枚举所有可能的集合划分”，然后判断每条边是否符合要求。
     * *   这是不可行的，复杂度指数级，效率太低。
     * *
     * * 二、核心转化：染色法
     * * ----------
     * * - 观察到题目要求：“相邻节点不能在同一个集合”。
     * * - 这可以转化为图的二染色问题（2-coloring）：
     * *      - 每个节点染红或蓝
     * *      - 相邻节点颜色必须不同
     * * - 由此提出 DFS/BFS 的思路：
     * *      1. 从某节点开始，染红
     * *      2. 递归或广度扩散染相邻节点为蓝，再染红的邻居为红，依次类推
     * *      3. 遇到已经染色的节点时：
     * *           - 如果颜色冲突 → 非二分图
     * *           - 否则继续
     * *
     * * 三、连通/不连通图理解
     * * ----------
     * * - 图可能由多个连通分量组成：
     * *      - 如果节点i与节点j连通 → 从任意一个节点出发都能把整个分量染色完成
     * *      - 如果节点i与节点j不连通 → 属于不同分量，互不影响
     * * - 结论：
     * *      1. 对每个节点 i，如果未染色，则说明它属于一个新的连通分量
     * *      2. 对这个分量任选初始颜色即可
     * *      3. 共用同一个染色数组 color[] 作为全局状态
     * *
     * * 四、初始颜色选择的理解
     * * ----------
     * * - 对于一个连通分量：
     * *      - 起始节点染红或染蓝都行（整体颜色翻转是等价的）
     * *      - 不会影响二分性判断
     * * - 对于不同连通分量：
     * *      - 互不影响，独立染色即可
     * *
     * * 五、奇环的本质
     * * ----------
     * * - 二分图存在等价判定：图是二分图 ⇔ 图中不存在奇数长度环
     * * - 原理：
     * *      - 偶数环：可以交替染色 → 不冲突
     * *      - 奇数环：交替染色必出现颜色冲突 → 非二分图
     * * - 在 DFS/BFS 中，颜色冲突正是检测奇数环的体现
     * *
     * * 六、算法流程总结
     * * ----------
     * * 1. 初始化颜色数组 color[]，长度为节点数，0表示未染色
     * * 2. 遍历每个节点：
     * *      - 如果未染色，说明是新的连通分量，从该节点出发 DFS/BFS 染色
     * *      - 遇到冲突立即返回 false
     * * 3. 遍历完成且未出现冲突 → 返回 true
     * *
     * * 七、复杂度分析
     * * ----------
     * * - 时间复杂度：O(V + E)，每个节点和边只遍历一次
     * * - 空间复杂度：O(V)，存储颜色数组和 DFS/BFS 栈/队列
     * *
     * * 八、核心面试点总结
     * * ----------
     * * 1. 二分图的核心就是“相邻节点颜色不同” → 染色法
     * * 2. 全局 color[] 数组必须共享，保证连通分量间状态一致
     * * 3. 对每个连通分量，只需任选起点初始颜色
     * * 4. 冲突检测即是奇环检测
     * * 5. DFS/BFS 都可实现，DFS用递归，BFS用队列
     */
    public boolean isBipartite(int[][] graph) {
        // 0表示未染色，1表示红色，-1表示蓝色
        int[] colors = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            // 当前节点未被染色，说明与之前的节点不连通，即当前节点所处的图和之前的是两个孤立的图，那么初始染色红色开始判断即可
            if (colors[i] == 0 && !dfs(graph, i, 1, colors)) {
                return false;
            }
        }
        return true;
    }

    private boolean dfs(int[][] graph, int i, int color, int[] colors) {
        // 当前节点已染色，且颜色和目标颜色不同，结束
        if (colors[i] != 0 && colors[i] != color) {
            return false;
        }
        // 已染色且未冲突，可以直接返回
        if (colors[i] != 0) {
            return true;
        }
        // 给当前节点染色
        colors[i] = color;
        // 遍历当前节点的所有邻接节点
        for (int target : graph[i]) {
            if (!dfs(graph, target, -color, colors)) {
                return false;
            }
        }
        return true;
    }


    public boolean isBipartite2(int[][] graph) {
        // 0表示未染色，1表示红色，-1表示蓝色
        int[] colors = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            // 当前节点未被染色，说明是一个新的连通分量，从该节点开始进行BFS染色
            if (colors[i] == 0 && !bfs(graph, i, colors)) {
                return false;
            }
        }
        return true;
    }

    private boolean bfs(int[][] graph, int start, int[] colors) {
        Queue<Integer> queue = new LinkedList<>();
        // 初始节点染成红色
        colors[start] = 1;
        queue.offer(start);

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            // 遍历当前节点的所有邻接节点
            for (int next : graph[cur]) {
                // 邻接节点未染色，染成相反颜色并加入队列
                if (colors[next] == 0) {
                    colors[next] = -colors[cur];
                    queue.offer(next);
                } else {
                    // 已染色，判断是否冲突
                    if (colors[next] == colors[cur]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
