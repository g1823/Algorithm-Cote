package leetCode.moderately;

import java.util.*;

/**
 * @author: gj
 * @description: 207. 课程表（问题转换为判断图中有没有环）
 */
public class CanFinishCourses {

    /**
     * 对于每个结点，都有三种状态：
     * 0：表示未访问过
     * 1：表示正在某一趟遍历中，如果本轮遍历再次遇到说明遇到了环
     * 2：表示该节点可以成功学完
     */
    int[] visited;

    boolean result = true;

    public static void main(String[] args) {
        //int[][] prerequisites = new int[][]{{1, 0}, {0, 1}};
        int[][] prerequisites = new int[][]{{1, 0}};
        //int[][] prerequisites = new int[][]{};
        //int[][] prerequisites = new int[][]{{0, 10}, {3, 18}, {5, 5}, {6, 11}, {11, 14}, {13, 1}, {15, 1}, {17, 4}};
        //int[][] prerequisites = new int[][]{{1, 0}, {1, 2}, {0, 1}};
        System.out.println(new CanFinishCourses().canFinish(2, prerequisites));
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if (prerequisites.length == 0) return true;
        visited = new int[numCourses];
        // 不能使用map，因为相同的key会覆盖,无法记录一个课程需要多个后续课程的情况
        // Map<Integer, Integer> nodes = new HashMap<>();
        List<List<Integer>> nodes = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            nodes.add(new ArrayList<>());
        }
        for (int i = 0; i < prerequisites.length; i++) {
            int[] prerequisite = prerequisites[i];
            nodes.get(prerequisite[0]).add(prerequisite[1]);
        }
        for (int i = 0; i < prerequisites.length; i++) {
            dfs(nodes, prerequisites[i][0]);
        }
        return result;
    }

    public void dfs(List<List<Integer>> nodes, int key) {
        if (!result) return;
        int t = visited[key];
        // 未访问
        if (t == 0) {
            visited[key] = 1;
            List<Integer> nextKeys = nodes.get(key);
            nextKeys.forEach(nextKey -> {
                if (nextKey != null) dfs(nodes, nextKey);
            });
        } else if (t == 1) {
            // 访问中
            result = false;
            return;
        }
        // 结束后，将当前结点置为2，表示当前结点可以访问完
        visited[key] = 2;
    }
}

