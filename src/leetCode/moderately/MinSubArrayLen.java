package leetCode.moderately;

/**
 * @author: gj
 * @description: 209. 长度最小的子数组
 */
public class MinSubArrayLen {

    public static void main(String[] args) {
        int target = 11;
        int[] nums = {1, 2, 3, 4, 5};
        System.out.println(new MinSubArrayLen().minSubArrayLen(target, nums));
    }

    /**
     * 滑动窗口：
     * 注意题意：1、要求是连续子数组，而非任意子数组；2、要求子数组和大于等于target
     */
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0, right = 0;
        int sum = 0;
        int res = Integer.MAX_VALUE;
        while (right < nums.length) {
            sum += nums[right];
            while (sum >= target) {
                res = Math.min(res, right - left + 1);
                sum -= nums[left];
                left++;
            }
            right++;
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }
}
