package leetCode.moderately;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 797. 所有可能的路径
 */
public class AllPathsSourceTarget {
    /**
     * 直接回溯遍历所有路径解决
     */
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(graph, 0, new ArrayList<Integer>(), result);
        return result;
    }

    private void dfs(int[][] graph, int i, ArrayList<Integer> path, List<List<Integer>> result) {
        int[] targets = graph[i];
        if (i == graph.length - 1) {
            path.add(i);
            result.add(new ArrayList<>(path));
            path.remove(path.size() - 1);
            return;
        }
        // 路径中加入当前节点
        path.add(i);
        for (int target : targets) {
            dfs(graph, target, path, result);
        }
        // 回溯，删除当前节点
        path.remove(path.size() - 1);
    }
}
