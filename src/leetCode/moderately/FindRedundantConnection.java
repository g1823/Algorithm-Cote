package leetCode.moderately;

/**
 * @description: 684. 冗余连接
 */
public class FindRedundantConnection {

    /**
     * - 并查集
     * - 核心思路：
     * - 给定一棵树加一条多余的边，要求找出那条在输入数组中最后出现的、使得图形成环的边。
     * - 1. 初始想法：环上的任意一条边都可以删除，都能恢复成一棵树。
     * -    可以先找到环，然后删除任意一条边，但题目要求返回“最后出现”的那条边，因此需要精确定位。
     * - 2. 并查集的工作机制：
     * -    - 并查集本质是维护一个“帮派系统”，每个节点都有一个“根节点”（代表元）。
     * -    - find(x) 查找 x 的根节点，同时进行路径压缩，将沿途节点直接挂到根上。
     * -    - union(u, v) 尝试将 u 和 v 所在的集合合并。
     * - 3. 连通性判断的逻辑（关键所在）：
     * -    - 初始化时，每个节点的父节点是自己（parent[i] = i），即每个节点自成一棵树。
     * -    - 当处理一条边 [u, v] 时：
     * -      a. 调用 find(u) 和 find(v) 分别找到 u 和 v 的根节点。
     * -      b. 若根节点不同，说明 u 和 v 目前不在同一个连通分量中，合并它们，这条边是安全的。
     * -      c. 若根节点相同，说明 u 和 v 已经连通（即之前已经存在一条从 u 到 v 的路径），
     * -         此时再添加 [u, v] 必然形成一个环。
     * - 4. 为什么第一次发现“已连通”的边就是答案？（核心证明）
     * -    - 题目保证输入图只有一个环。
     * -    - 在遍历到当前这条边之前，所有已处理的边构成的图一定是森林（无环）。
     * -        - 因为如果之前已经成环，那么整个图就不止一个环了，与题意矛盾。
     * -    - 当遇到一条边 [u, v] 且 find(u) == find(v) 时：
     * -        - 在之前的边中，u 和 v 已经存在一条唯一路径（因为之前无环）。
     * -        - 当前这条边恰好闭合了这个路径，形成了唯一的环。
     * -        - 这个环上的所有其他边都出现在当前边之前，因此当前边是环上“最后出现”的边。
     * -        - 直接返回当前边，即是题目要求的答案。
     * - 5. 数学上的反证：
     * -    - 如果加入一条边之前，find(u) != find(v)，说明 u 和 v 分属两棵不同的树，
     * -      连接它们不会产生环，因为两棵树之间原本没有路径。
     * -    - 如果加入一条边之后，发现 find(u) == find(v)，则说明这条边连接了原本已连通的
     * -      两个节点，必然产生环。这是图论的基本定理。
     * - 白话说：当一个节点的父节点还未出现时，该节点就不会连接到整棵树的根节点上，因为中间节点父节点不存在。根据这一点，不断的使用并查集合并两个元素，一旦出现两个元素的parent一致（不一定是原始的根节点），说明出现环了。如果没有环的话，两个节点的父节点，再新加入时不可能一致。
     */
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length + 1;
        int[] parent = new int[n];
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            if (find(parent, u) == find(parent, v)) {
                return edge;
            }
            union(parent, rank, u, v);
        }
        return null;
    }

    private void union(int[] parent, int[] rank, int u, int v) {
        int uRoot = find(parent, u);
        int vRoot = find(parent, v);
        if (rank[uRoot] > rank[vRoot]) {
            parent[vRoot] = uRoot;
        } else if (rank[uRoot] < rank[vRoot]) {
            parent[uRoot] = vRoot;
        } else {
            parent[vRoot] = uRoot;
            rank[uRoot]++;
        }
    }

    private int find(int[] parent, int u) {
        if (u != parent[u]) {
            parent[u] = find(parent, parent[u]);
        }
        return parent[u];
    }
}
