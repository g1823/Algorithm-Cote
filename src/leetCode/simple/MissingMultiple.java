package leetCode.simple;

import java.util.HashSet;
import java.util.Set;

/**
 * @description: 3718. 缺失的最小倍数
 */
public class MissingMultiple {
    public int missingMultiple(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int t = k;
        while (true) {
            if (!set.contains(t)) {
                return t;
            }
            t = t + k;
        }
    }
}
