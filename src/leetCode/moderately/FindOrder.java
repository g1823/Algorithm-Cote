package leetCode.moderately;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gj
 * @description: 210. 课程表 II
 */
public class FindOrder {

    /**
     * 题目：210. 课程表 II
     * 【思路说明】
     * 本题要求在给定课程数量和先修关系下，返回一个可以完成所有课程的学习顺序。
     * 其本质是对课程依赖关系构成的「有向图」进行拓扑排序。
     * 1. 图构建：
     * -   - 将课程视为图的节点（编号 0 ~ numCourses - 1）。
     * -   - prerequisites[i] = [a, b] 表示课程 a 依赖课程 b，因此在图中存在一条边 a -> b。
     * -   - 通过邻接表（List<List<Integer>>）存储这种依赖关系。
     * 2. DFS 拓扑排序：
     * -   - 使用一个状态数组 nodeStatus 记录每个节点的访问状态：
     * -       0 = 未访问
     * -       1 = 当前路径中（正在访问）
     * -       2 = 已完成（已处理完所有依赖）
     * -   - 从每个未访问的节点开始 DFS：
     * -       - 若遇到正在访问的节点（状态为 1），说明出现环，课程无法完成；
     * -       - 若成功完成所有依赖节点，则将该课程加入结果序列。
     * -   - 为避免重复搜索和判断环，在 DFS 过程中使用一个全局 flag 标记是否存在环。
     * 3. 结果处理：
     * -   - DFS 每次完成一个节点时将其加入 result，顺序为「后序遍历」；
     * -   - 因为先修课程会在依赖它的课程前被访问完，最终 result 中的顺序自然符合学习顺序；
     * -   - 若存在环，返回空数组。
     * 时间复杂度：O(V + E)，其中 V 为课程数量，E 为先修关系数量。
     * 空间复杂度：O(V + E)，主要用于邻接表和递归调用栈。
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // 若仅有一门课程，直接返回
        if (numCourses == 1) {
            return new int[]{0};
        }
        // ------------------------ 1. 构建图 ------------------------
        // 每门课程作为一个节点，nodes[i] 记录「课程 i 依赖的课程列表」
        List<List<Integer>> nodes = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            nodes.add(new ArrayList<>());
        }
        // prerequisites[i] = [a, b] 表示 a 依赖 b，因此添加边 a -> b
        for (int i = 0; i < prerequisites.length; i++) {
            int[] prerequisite = prerequisites[i];
            nodes.get(prerequisite[0]).add(prerequisite[1]);
        }
        // ------------------------ 2. 初始化状态 ------------------------
        // 0=未访问，1=正在访问，2=已访问
        int[] nodeStatus = new int[numCourses];
        List<Integer> result = new ArrayList<>();
        // ------------------------ 3. DFS 遍历每个节点 ------------------------
        for (int i = 0; i < numCourses; i++) {
            if (nodeStatus[i] == 0) {
                dfs(nodes, i, nodeStatus, result);
            }
        }
        // 若存在环，返回空数组
        if (flag) {
            return new int[]{};
        }
        // ------------------------ 4. 结果转换 ------------------------
        // 将结果列表转换为数组输出
        int[] res = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            res[i] = result.get(i);
        }
        return res;
    }

    // 全局属性，标记是否存在环，一旦为 true 则无需继续搜索
    boolean flag = false;

    /**
     * 对当前课程进行 DFS 搜索
     *
     * @param nodes      邻接表，记录依赖关系
     * @param key        当前课程编号
     * @param nodeStatus 节点状态数组
     * @param result     拓扑序结果列表
     */
    public void dfs(List<List<Integer>> nodes, int key, int[] nodeStatus, List<Integer> result) {
        // 若已经检测到环，直接终止递归
        if (flag) {
            return;
        }
        int status = nodeStatus[key];
        if (status == 0) {
            // 标记当前节点为“正在访问”
            nodeStatus[key] = 1;
            // 遍历所有前置课程
            List<Integer> children = nodes.get(key);
            for (int i = 0; i < children.size(); i++) {
                int child = children.get(i);
                dfs(nodes, child, nodeStatus, result);
            }
            // 当前节点所有依赖已访问完，加入结果序列
            result.add(key);
            // 标记为“已访问”
            nodeStatus[key] = 2;

        } else if (status == 1) {
            // 若再次访问到“正在访问”的节点，说明存在环
            flag = true;
        } else {
            // status == 2，节点已访问，无需处理
        }
    }
}
