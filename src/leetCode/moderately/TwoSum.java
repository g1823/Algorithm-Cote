package leetCode.moderately;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: gj
 * @description: 167. 两数之和 II - 输入有序数组
 */
public class TwoSum {
    public static void main(String[] args) {
        int[] numbers = {2, 7, 11, 15};
        int target = 9;
        int[] result = new TwoSum().twoSum(numbers, target);
        System.out.println(result[0] + " " + result[1]);
    }

    /**
     * 用map记录数字出现的位置，然后依次遍历数组，直到找到目标和
     */
    public int[] twoSum(int[] numbers, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            if (map.containsKey(target - numbers[i])) {
                return new int[]{map.get(target - numbers[i]) + 1, i + 1};
            } else {
                map.put(numbers[i], i);
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * 双指针解法（适用于非递减有序数组）
     * 思路：
     *  - 设 left 指向数组最左，right 指向数组最右；
     *  - 计算 sum = numbers[left] + numbers[right]：
     *      * 若 sum == target：即找到目标，返回 (left+1, right+1)（题目要求 1-based 下标）
     *      * 若 sum < target：说明以当前 left 为首的任何候选都太小（见证明），因此 left++；
     *      * 若 sum > target：说明以当前 right 为尾的任何候选都太大（见证明），因此 right--；
     *  - 循环直到 left < right。题目保证有解，所以正常情况下会在循环中返回。
     */
    public int[] twoSum2(int[] numbers, int target) {
        int left = 0, right = numbers.length - 1;
        while (left < right) {
            long sum = (long) numbers[left] + numbers[right];
            if (sum == target) {
                // 返回 1-based 下标
                return new int[]{left + 1, right + 1};
            } else if (sum < target) {
                // 即使在 left 固定的情况下，将 right 从当前位置移动到更左侧，
                // numbers[left] + numbers[k] <= numbers[left] + numbers[right] < target
                // 因此不存在任何 k (left < k <= right) 使 numbers[left] + numbers[k] == target，
                // 所以安全地把 left 向右移动一个位置。
                left++;
            } else {
                // sum > target 的对称理由：
                // 即使把 left 向右移到更右的位置，numbers[k] + numbers[right] >= numbers[left] + numbers[right] > target
                // 因此不存在任何 k (left <= k < right) 使 numbers[k] + numbers[right] == target，
                // 所以安全地把 right 向左移动一个位置。
                right--;
            }
        }
        // 若题目保证有解，此处理论上不可达；若不保证，可抛出异常或返回特殊值
        return null;
    }

}
