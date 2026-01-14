package leetCode.simple;

/**
 * @author: gj
 * @description: 643. 子数组最大平均数 I
 */
public class FindMaxAverage {
    public double findMaxAverage(int[] nums, int k) {
        if (nums.length < k) {
            return 0;
        }
        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        int max = sum;
        for (int i = k; i < nums.length; i++) {
            sum = sum - nums[i - k] + nums[i];
            max = Math.max(max, sum);
        }
        return (double) max / k;
    }
}
