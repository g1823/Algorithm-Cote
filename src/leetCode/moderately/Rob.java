package leetCode.moderately;

/**
 * @author: gj
 * @description: 198. 打家劫舍
 */
public class Rob {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 1};
        int[] nums2 = new int[]{2, 1, 1, 2};
        System.out.println(new Rob().rob(nums2));
    }

    /**
     * 动态规划
     *
     * @param nums nums
     * @return 最大值
     */
    public int rob(int[] nums) {
        int length = nums.length, max = 0;
        if (length <= 2) {
            return length == 2 ? Math.max(nums[0], nums[1]) : length == 1 ? nums[0] : 0;
        }
        int[] dp = new int[length];
        dp[0] = nums[0];
        // 第二个元素的最优解应该是Math.max(nums[0], nums[1])
        dp[1] = Math.max(nums[0], nums[1]);
        // 也可以使用max来标记当前下标前两个元素前的最大值，即当前下标为i，max的值为前i-2个元素的最大值
        max = nums[0];
        for (int i = 2; i < length; i++) {
            //dp[i] = Math.max(nums[i] + max, dp[i - 1]);
            dp[i] = Math.max(nums[i] + dp[i - 2], dp[i - 1]);
            max = Math.max(dp[i - 1], max);
        }
        return dp[length - 1];
    }
}
