package leetCode.moderately;

/**
 * @author: gj
 * @description: 494. 目标和
 */
public class FindTargetSumWays {
    public static void main(String[] args) {
        //int[] nums = new int[]{1, 1, 1, 1, 1};
        //int[] nums = new int[]{1000};
        //int[] nums = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 1};
        int[] nums = new int[]{100, 100};
        //System.out.println(new FindTargetSumWays().findTargetSumWays2(nums, -1000));
        System.out.println(new FindTargetSumWays().findTargetSumWays2(nums, -400));
    }


    /**
     * 深度优先
     *
     * @param nums   nums
     * @param target target
     * @return
     */
    public int findTargetSumWays(int[] nums, int target) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) {
            if (nums[0] == target || nums[0] == -target) return 1;
            else return 0;
        }
        dfs(0, 0, nums, target);
        return result;
    }

    // 这里不能使用静态的，因为LeetCode是多线程，使用static类变量，会因为多线程导致数量异常
    int result = 0;

    /**
     * @param i          当前坐标
     * @param currentSum 当前合
     * @param nums       nums
     * @param target     target
     * @return
     */
    public int dfs(int i, int currentSum, int[] nums, int target) {
        if (i == nums.length - 1) {
            // +-0 值不变，因此需要分开考虑
            if (currentSum + nums[i] == target) result++;
            if (currentSum - nums[i] == target) result++;
            return 0;
        }
        dfs(i + 1, currentSum + nums[i], nums, target);
        dfs(i + 1, currentSum - nums[i], nums, target);
        return 0;
    }


    /**
     * 题目要求实际上可以转化为：
     * 将集合Nums中的数据分成两部分，一部分取正值、一部分取负值。两部分相加即为target。
     * 设正值集合为add，负值集合为reduce。则：
     * sum(add)+sum(reduce)=sum(nums)
     * sum(add)-sum(reduce)=target
     * 左右相加去掉reduce:
     * 2*sum(add) = sum(nums) + target
     * sum(add) = (sum(nums) + target)/2
     * 而sum(nums) + target是已知的，就转换为了背包问题
     *
     * @param nums
     * @param target
     * @return
     */
    public int findTargetSumWays2(int[] nums, int target) {
        int sumNums = 0;
        for (int num : nums) {
            sumNums += num;
        }
        if ((target + sumNums) < 0 || (target + sumNums) % 2 != 0) {
            return 0;
        }
        int packCapacity = (target + sumNums) / 2;
        int[][] dp = new int[nums.length + 1][Math.abs(packCapacity) + 1];
        dp[0][0] = 1;
        for (int i = 1; i < dp.length; i++) {
            int currentNum = nums[i - 1];
            for (int j = 0; j < dp[i].length; j++) {
                if (currentNum > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j - currentNum] + dp[i - 1][j];
                }
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }

    /**
     * 对于2的优化：
     * 由于只需要用到上一行的数据，可以不使用二维数组，才用一维滚动即可。
     * 但是为了保留上一轮的值，需要倒序遍历
     *
     * @param nums
     * @param target
     * @return
     */
    public int findTargetSumWays3(int[] nums, int target) {
        int sumNums = 0;
        for (int num : nums) {
            sumNums += num;
        }
        if ((target + sumNums) < 0 || (target + sumNums) % 2 != 0) {
            return 0;
        }
        int packCapacity = (target + sumNums) / 2;
        int[] dp = new int[packCapacity + 1];
        dp[0] = 1;
        for (int num : nums) {
            // 如果j比num小，则说明放不下，则直接取上一行的值，也就是不变
            // 对于第一行来说也就是取0
            for (int j = dp.length - 1; j >= num; j--) {
                dp[j] += dp[j - num];
            }
        }
        return dp[packCapacity];
    }

    public int findTargetSumWays4(int[] nums, int target) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        int diff = sum - target;
        if (diff < 0 || diff % 2 != 0) {
            return 0;
        }
        int n = nums.length, neg = diff / 2;
        int[][] dp = new int[n + 1][neg + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            int num = nums[i - 1];
            for (int j = 0; j <= neg; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= num) {
                    dp[i][j] += dp[i - 1][j - num];
                }
            }
        }
        return dp[n][neg];
    }
}
