package leetCode.moderately;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: gj
 * @description: 547. 省份数量
 */
public class FindCircleNum {

    /**
     * 直接深度优先遍历，使用visited记录已访问过的节点。
     * 外层遍历每个城市，即每个i,isConnected的每一层。
     * - 对于每一层的节点，遍历当前行，对于节点j，如果j没有被访问过，则进行深度优先遍历，并记录已访问的节点。
     * 外层每遍历一个节点，都会把当前节点连通的其他节点全部访问过。
     * 这样，外层遍历下一个节点时，如果不处于visited，则说明前面的节点并未访问到当前节点，可以作为新省份的起点继续dfs。
     * 最终所有节点访问完后返回已访问节点的个数
     */
    public int findCircleNum(int[][] isConnected) {
        Set<Integer> visited = new HashSet<>();
        int count = 0;
        for (int i = 0; i < isConnected.length; i++) {
            if (!visited.contains(i)) {
                dfs(isConnected, visited, i);
                count++;
            }
        }
        return count;
    }

    public void dfs(int[][] isConnected, Set<Integer> visited, int i) {
        if (visited.contains(i)) {
            return;
        }
        visited.add(i);
        for (int j = 0; j < isConnected.length; j++) {
            if (isConnected[i][j] == 1 && !visited.contains(j)) {
                dfs(isConnected, visited, j);
            }
        }
    }

    /**
     * 并查集
     */
    class Solution2 {
        public int findCircleNum(int[][] isConnected) {
            int n = isConnected.length;
            int[] parent = new int[n];
            int[] rank = new int[n];
            // 初始时，每个节点的父级都指向自己
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
            // 遍历下三角（或上三角）
            for (int i = n - 1; i >= 0; i--) {
                for (int j = 0; j < i; j++) {
                    // i可以到达j ，合并i和j
                    if (isConnected[i][j] == 1) {
                        union(parent, rank, i, j);
                    }
                }
            }
            int count = 0;
            for (int i = 0; i < n; i++) {
                if (i == parent[i]) {
                    count++;
                }
            }
            return count;
        }

        private void union(int[] parent, int[] rank, int i, int j) {
            int iRoot = find(parent, i), jRoot = find(parent, j);
            // i和j的根节点一致，无需处理
            if (iRoot == jRoot) {
                return;
            }
            // 合并，把矮树挂在高树下
            if (rank[iRoot] > rank[jRoot]) {
                parent[jRoot] = iRoot;
            } else if (rank[iRoot] < rank[jRoot]) {
                parent[iRoot] = jRoot;
            }
            // 相同深度则挂在i的根节点下，并更新深度
            else {
                parent[jRoot] = iRoot;
                rank[iRoot]++;
            }
        }

        private int find(int[] parent, int i) {
            if (i != parent[i]) {
                parent[i] = find(parent, parent[i]);
            }
            return parent[i];
        }
    }
}
