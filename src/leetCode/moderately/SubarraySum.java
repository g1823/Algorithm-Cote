package leetCode.moderately;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author: gj
 * @description: 560. 和为 K 的子数组
 */
public class SubarraySum {
    public static void main(String[] args) {
        int[] nums = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        System.out.println(subarraySum2(nums, 0));
    }

    /**
     * 蛮力法
     */
    public static int subarraySum(int[] nums, int k) {
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum == k) {
                    result++;
                }
            }
        }
        return result;
    }

    /**
     * 前缀和 + 哈希表
     * 题目要求子数组，也就是下标必须连续，很容易联想到使用前缀和来减少重复计算。
     * 假设知道每个下标的前缀和，若 下标i的前缀和 - 下标j的前缀和 = k，则说明下标i到j的子数组的和为k。
     * 计算下标i为子数组最后一个下标，是否存在一个下标j（j<i），二者之间的子数组之和等于k，依旧需要从0到i-1的前缀和全部遍历一边，与蛮力法一样。
     * 但是，下标i的前缀和为 A，那么只需要找到前缀和为 k - A 的下标j即可，而不需要对每个下标和再计算。
     * 因此可以使用hash表，将已经出现过的前缀和缓存下来（从左到右），这样找指定值的前缀和下标时间复杂度遍降为了O(1)。
     * 思路：
     * 1. 创建一个hash表map，用于存储已经出现的前缀和，以及出现的次数,初始化哈希表 map，添加 (0,1)，表示前缀和为 0 出现一次（处理从头开始的子数组）
     * 2. 遍历数组，计算前缀和：
     * -- 若前缀和为k，则结果加1。
     * -- 若hash表中存在前缀和为k - A的前缀和，则这些前缀和均可以与当前下标组成和为k的子数组，那么结果加map[j]。
     * 3. 将当前前缀和加入hash表。
     */
    public static int subarraySum2(int[] nums, int k) {
        int result = 0;
        Map<Integer, Integer> map = new HashMap<>();
        // 需要初始化一个(0->1)，标识前缀和为0的次数为1
        map.put(0, 1);
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum == k) {
                result++;
            }
            if (map.containsKey(sum - k)) {
                result += map.get(sum - k);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return result;
    }
}
