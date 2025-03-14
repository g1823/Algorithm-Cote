package leetCode.moderately;

/**
 * @author: gj
 * @description: 53. 最大子数组和
 */
public class MaxSubArray {

    public static void main(String[] args) {
        int[] nums = new int[]{5,4,-1,7,8};
        System.out.println(new MaxSubArray().maxSubArray(nums));
    }

    public int maxSubArray(int[] nums) {
        int maxTemp = Integer.MIN_VALUE, flag = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= 0) {
                flag = 1;
                break;
            } else {
                maxTemp = Math.max(maxTemp, nums[i]);
            }
        }
        if (flag == 0) return maxTemp;
        int maxResult = Integer.MIN_VALUE, thisResult = 0;
        for (int i = 0; i < nums.length; i++) {
            thisResult += nums[i];
            maxResult = Math.max(thisResult, maxResult);
            if (thisResult < 0) thisResult = 0;
        }
        return maxResult;
    }
}
