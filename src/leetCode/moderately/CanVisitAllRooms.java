package leetCode.moderately;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: gj
 * @description: 841. 钥匙和房间
 */
public class CanVisitAllRooms {
    /**
     * 直接深度优先遍历，使用visited记录已访问过的房间，最终所有节点访问完后判断已访问房间的个数是否等于房间的个数
     */
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        Set<Integer> visited = new HashSet<>();
        dfs(rooms, 0, visited);
        return visited.size() == rooms.size();
    }

    private void dfs(List<List<Integer>> rooms, int index, Set<Integer> visited) {
        if (visited.contains(index)) {
            return;
        }
        visited.add(index);
        for (int i : rooms.get(index)) {
            dfs(rooms, i, visited);
        }
    }
}
