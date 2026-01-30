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
}
