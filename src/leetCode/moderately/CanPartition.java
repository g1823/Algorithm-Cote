package leetCode.moderately;

/**
 * @author: gj
 * @description: 416. 分割等和子集
 */
public class CanPartition {

    public static void main(String[] args) {
        //int[] nums = new int[]{1, 5, 11, 5};
        int[] nums = new int[]{1, 5, 10, 6};
        System.out.println(new CanPartition().canPartition(nums));
    }

    /**
     * TODO:记录
     * 结合494. 目标和{@link FindTargetSumWays#findTargetSumWays2(int[], int)}
     * 可知动态规划背包思想可以解决求解一组数据是否可以组合出指定解或组合出指定解的组合数量
     * @param nums
     * @return
     */
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 2 != 0) return false;
        int target = sum >> 1;
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 1; i < nums.length + 1; i++) {
            int num = nums[i - 1];
            for (int j = target; j >= 1; j--) {
                dp[j] = j >= num ? num == j ? 1 : Math.max(dp[j], dp[j - num]) : dp[j];
            }
        }
        return dp[target] == 1;
    }

//    public boolean canPartition(int[] nums) {
//        int sum = 0;
//        for (int num : nums) {
//            sum += num;
//        }
//        if (sum % 2 != 0) return false;
//        int target = sum >> 1;
//        int[][] dp = new int[nums.length + 1][target + 1];
//        dp[0][0] = 1;
//        for (int i = 1; i < nums.length + 1; i++) {
//            int num = nums[i - 1];
//            for (int j = 1; j < target + 1; j++) {
//                dp[i][j] = num <= j ? num == j ? 1 : Math.max(dp[i - 1][j - num], dp[i - 1][j]) : dp[i - 1][j];
//            }
//        }
//        return dp[nums.length][target] == 1;
//    }
}
