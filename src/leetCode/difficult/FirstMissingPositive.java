package leetCode.difficult;

import leetCode.moderately.DetectCycle;
import leetCode.moderately.FindDuplicate;
import leetCode.simple.HasCycle;

/**
 * @author: gj
 * @description: 41. 缺失的第一个正数
 */
public class FirstMissingPositive {

    public static void main(String[] args) {
        int[] nums = {1, 1};
        System.out.println(new FirstMissingPositive().firstMissingPositive(nums));
    }

    /**
     * 1. 暴力解法：
     * - 最直观的方法是从 1 开始一个个查找是否出现在数组中，直到找到缺失的正整数。
     * - 每次查找都需要 O(n) 时间，总体为 O(n^2)，在大数组下性能差，不符合题目要求。
     * 2. 哈希集合解法：
     * - 将所有数加入一个 HashSet，然后从 1 开始往上查找最小缺失的数。
     * - 时间复杂度为 O(n)，但使用了 O(n) 空间，依然不符合空间要求。
     * 3. 排序解法：
     * - 先对数组排序，然后线性扫描查找缺失的正整数。
     * - 时间复杂度 O(n log n)，仍不符合题目要求。
     * 4. 原地交换法（本方法）：模仿环形链表，找到对应的环也就找到了重复的数：{@link HasCycle}{@link DetectCycle}{@link FindDuplicate#findDuplicate3(int[])}
     * - 观察发现，缺失的最小正整数一定在 [1, n+1] 范围内（n 为数组长度）。
     * - 我们希望将每个数 nums[i] 放到其对应的下标位置 nums[i] - 1 上。
     * - 比如，1 放到 index 0，2 放到 index 1，n 放到 index n - 1。
     * - 排除所有 <= 0 或 > n 的数，以及已经在正确位置或重复的数，避免死循环。
     * - 最终再从头到尾扫描，找到第一个 nums[i] != i + 1 的位置，返回 i + 1 即为答案。
     */
    public int firstMissingPositive(int[] nums) {
        // 先把所有数据放到应该在的地方
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            /** 条件说明：
             * nums[i] > 0 ：负数没有应该存在的位置，跳过
             * nums[i] <= n ：大于n的数，没有应该存在位置，跳过
             * nums[i] != nums[nums[i] - 1] ：当前位置的数，已经放到正确位置，避免无线循环，跳过
             */
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                // 把 nums[i] 放到正确位置上
                int temp = nums[i];
                nums[i] = nums[temp - 1];
                nums[temp - 1] = temp;
            }
        }
        // 所有的值都在自己应该在的位置了，从0（理论上1应该在的位置）开始向后遍历，找到第一个缺失的正数
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return n + 1;
    }
}
