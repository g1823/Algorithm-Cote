package leetCode.moderately;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: gj
 * @description: 1679. K 和数对的最大数目
 */
public class MaxOperations {
    public int maxOperations(int[] nums, int k) {
        int count = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            if (map.getOrDefault(k - num, 0) > 0) {
                count++;
                map.put(k - num, map.get(k - num) - 1);
            } else {
                map.put(num, map.getOrDefault(num, 0) + 1);
            }
        }
        return count;
    }
}
