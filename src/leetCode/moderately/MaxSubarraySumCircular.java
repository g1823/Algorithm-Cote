package leetCode.moderately;

/**
 * @author: gj
 * @description: 918. 环形子数组的最大和
 */
public class MaxSubarraySumCircular {
    public static void main(String[] args) {
        int[] nums = new int[]{9, -4, -7, 9};
        System.out.println(new MaxSubarraySumCircular().maxSubarraySumCircular(nums));
    }


    /**
     * 最大子数组只有两种情况：
     * - 不跨越头尾节点：那么最大子数组就是53. 最大子数组和
     * - 跨越头尾节点：逆向思考，先求出最小子数组和，然后用总和 - 最小子数组和就可以了
     */
    public int maxSubarraySumCircular(int[] nums) {
        // 判断数组是否存在非负数，同时记录全负情况下的最大值
        int maxTemp = Integer.MIN_VALUE;
        int flag = 0;
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
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
        int maxResult = Integer.MIN_VALUE;
        int thisMaxResult = 0;
        for (int i = 0; i < nums.length; i++) {
            // 累加当前值
            thisMaxResult += nums[i];
            // 记录当前最大子数组和
            maxResult = Math.max(maxResult, thisMaxResult);
            // 若累计和变负，则重置，开始新的子数组
            if (thisMaxResult < 0) {
                thisMaxResult = 0;
            }
        }
        int minResult = Integer.MAX_VALUE;
        int thisMinResult = 0;
        for (int i = 0; i < nums.length; i++) {
            thisMinResult += nums[i];
            minResult = Math.min(minResult, thisMinResult);
            if (thisMinResult > 0) {
                thisMinResult = 0;
            }
        }
        return Math.max(maxResult, sum - minResult);
    }
}
