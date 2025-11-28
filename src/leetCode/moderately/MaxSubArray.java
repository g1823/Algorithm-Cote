package leetCode.moderately;

/**
 * @author: gj
 * @description: 53. 最大子数组和
 */
public class MaxSubArray {

    public static void main(String[] args) {
        int[] nums = new int[]{5, 4, -1, 7, 8};
        System.out.println(new MaxSubArray().maxSubArray(nums));
    }

    /**
     * 使用 Kadane 算法求解最大子数组和。
     * <p>
     * 思路：
     * 1. 特殊处理全为负数的情况：
     * - 若数组中不存在非负数，则最大子数组和即为数组中最大的那个负数。
     * - 因为 Kadane 算法在遇到负数时会将累计和置零，会导致结果错误。
     * <p>
     * 2. 对于包含非负数的场景，使用经典 Kadane 算法：
     * - 使用 thisResult 累计当前子数组和。
     * - 若 thisResult 在累加过程中变为负数，则说明此前形成的子数组无法贡献更大的和，直接重置为 0。
     * - 使用 maxResult 记录遍历过程中遇到的最大子数组和。
     * <p>
     * 整体时间复杂度为 O(n)，空间复杂度为 O(1)，是最大子数组和问题的最优解法。
     */
    public int maxSubArray(int[] nums) {
        // 判断数组是否存在非负数，同时记录全负情况下的最大值
        int maxTemp = Integer.MIN_VALUE;
        int flag = 0;
        for (int i = 0; i < nums.length; i++) {
            // 若出现非负数，则标记 flag 并停止判断全负逻辑
            if (nums[i] >= 0) {
                flag = 1;
                break;
            } else {
                // 记录全负情况下的最大元素
                maxTemp = Math.max(maxTemp, nums[i]);
            }
        }
        // 若数组全为负数，直接返回最大负数
        if (flag == 0) {
            return maxTemp;
        }
        // Kadane 算法：记录当前子数组和与最大子数组和
        int maxResult = Integer.MIN_VALUE;
        int thisResult = 0;
        for (int i = 0; i < nums.length; i++) {
            // 累加当前值
            thisResult += nums[i];
            // 记录当前最大子数组和
            maxResult = Math.max(maxResult, thisResult);
            // 若累计和变负，则重置，开始新的子数组
            if (thisResult < 0) {
                thisResult = 0;
            }
        }
        return maxResult;
    }
}
