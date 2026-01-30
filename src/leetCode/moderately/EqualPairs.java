package leetCode.moderately;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: gj
 * @description: 2352. 相等行列对
 */
public class EqualPairs {
    public int equalPairs(int[][] grid) {
        Map<String, Integer> rowMap = new HashMap<>();
        Map<String, Integer> colMap = new HashMap<>();
        for (int i = 0; i < grid.length; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < grid[i].length; j++) {
                row.append(grid[i][j]).append("-");
            }
            rowMap.put(row.toString(), rowMap.getOrDefault(row.toString(), 0) + 1);
        }
        for (int i = 0; i < grid.length; i++) {
            StringBuilder col = new StringBuilder();
            for (int j = 0; j < grid.length; j++) {
                col.append(grid[j][i]).append("-");
            }
            colMap.put(col.toString(), colMap.getOrDefault(col.toString(), 0) + 1);
        }
        int count = 0;
        for (String key : rowMap.keySet()) {
            if (colMap.containsKey(key)) {
                count += rowMap.get(key) * colMap.get(key);
            }
        }
        return count;
    }
}
