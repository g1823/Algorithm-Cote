package leetCode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package leetCode
 * @Date 2024/9/8 19:15
 * @Author gaojie
 * @description: 1. 两数之和
 */
public class TwoSum {
    public static void main(String[] args) {
        int[] data = new int[]{-3, 4, 3, 90};
        System.out.println(Arrays.toString(new TwoSum().twoSum(data, 0)));
    }

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{i, map.get(target - nums[i])};
            } else {
                map.put(nums[i], i);
            }
        }
        return new int[]{-1, -1};
    }
}
