package leetCode.moderately;

/**
 * @author gaojie
 * @date 2026/1/10 22:31
 * @description: 334. 递增的三元子序列
 */
public class IncreasingTriplet {
    /**
     * 两个数组，min和max：
     * - min[i] = nums[0..i-1] 中最小的数
     * - max[i] = nums[i+1..n-1] 中最大的数
     * 然后遍历nums数组，对于nums[i]，判断nums[i] > min[i-1] && nums[i] < max，如果满足则返回true
     * 优化：
     * 实际上，可以先优化一个min或max：
     * - 记录min数组，然后倒叙遍历，在遍历时维护一个从i到n的最大值即可。不需要完整记录max数组。
     */
    public boolean increasingTriplet(int[] nums) {
        int n = nums.length;
        int[] min = new int[n];
        min[0] = nums[0];
        for (int i = 1; i < n; i++) {
            min[i] = Math.min(min[i - 1], nums[i]);
        }
        int max = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] > min[i] && nums[i] < max) {
                return true;
            }
            max = Math.max(max, nums[i]);
        }
        return false;
    }

    /**
     * - 思路（贪心法）：
     * - 1. 维护两个变量 first 和 second：
     * -    - first：目前遇到的最小值
     * -    - second：目前遇到的、比 first 大的最小值
     * - 2. 遍历数组 nums：
     * -    - 如果 num <= first：
     * -        更新 first = num，因为我们希望 first 尽可能小，以便后续找到递增三元组
     * -    - 否则，如果 num <= second：
     * -        更新 second = num，因为 num 比 first 大，但比 second 小，更新 second 保持 second 尽可能小
     * -    - 否则：
     * -        num > second，说明存在 first < second < num，直接返回 true
     * - 3. 遍历结束仍未返回 true，说明不存在递增三元子序列，返回 false
     * - 贪心核心：
     * - - second 总是大于某个 first（可能是更新前或更新后的 first），保证递增序列合法性
     * - - 更新 first 或 second 时尽可能选择最小的值，为后续找到第三个数留空间
     */
    public boolean increasingTriplet2(int[] nums) {
        // 初始化最小值为无穷大
        // 初始化第二小值为无穷大
        int first = Integer.MAX_VALUE;
        int second = Integer.MAX_VALUE;

        for (int num : nums) {
            if (num <= first) {
                // 遇到更小的数，更新 first
                first = num;
            } else if (num <= second) {
                // num 比 first 大，但比 second 小，更新 second
                second = num;
            } else {
                // num > second，找到递增三元子序列
                return true;
            }
        }
        // 遍历完没有找到递增三元子序列
        return false;
    }
}
