package leetCode.moderately;

/**
 * @author: gj
 * @description: 45. 跳跃游戏 II
 */
public class Jump {
    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 1, 4};
        System.out.println(new Jump().jump(nums));
    }

    /**
     * 思路：
     * 本题要求从数组起点跳到终点的最少跳跃次数。
     * 我们可以用类似 BFS 层级扩展的方式解决：
     * 1. 用 dp[i] 记录到达下标 i 的最少跳跃次数。
     * 2. 遍历数组，每次从位置 i 出发，更新它能到达的区间 (i, i + nums[i]]。
     * 3. 为了避免重复更新，用 canArriveMax 记录“当前已经更新过的最远位置”。
     * - 对于新能到达的区间 [canArriveMax+1, i+nums[i]]，统一赋值为 dp[i] + 1。
     * - 因为 BFS 的特性，第一次被更新到的点一定是最少跳数。
     * 4. 不断扩张 canArriveMax，如果已经到达或超过末尾，可以提前结束。
     * 时间复杂度：O(n)，因为每个下标最多被写一次。
     * 空间复杂度：O(n)，因为需要 dp 数组保存每个位置的最少跳数。
     */
    public int jump(int[] nums) {
        // 记录当前已经能到达的最远位置
        int canArriveMax = 0;
        // dp[i] 表示到达下标 i 的最少跳跃次数
        int[] dp = new int[nums.length];
        // 遍历每个位置，尝试向前扩张可达区间
        for (int i = 0; i < nums.length; i++) {
            int t = nums[i];
            // 如果 i 能到的最远位置没有超过已经更新过的边界，则跳过
            if (i + t <= canArriveMax) {
                continue;
            }
            // 将 [canArriveMax+1, i+t] 区间的 dp 值统一更新为 dp[i]+1
            for (int j = canArriveMax + 1; j <= i + t && j < nums.length; j++) {
                dp[j] = dp[i] + 1;
            }
            // 更新已经能到达的最远边界
            canArriveMax = Math.max(canArriveMax, i + t);
            // 如果最远边界已经覆盖终点，可以提前结束
            if (canArriveMax >= nums.length - 1) {
                break;
            }
        }
        // 返回到达终点的最少跳数
        return dp[nums.length - 1];
    }


    /**
     * 解题思路（贪心法）：
     * 本题可以看作层级 BFS 的优化。
     * - 我们用 curEnd 表示当前这一跳的最远边界；
     * - 遍历数组时，不断更新 nextEnd = max(nextEnd, i + nums[i])，表示在当前层能达到的最远位置；
     * - 当到达 curEnd 时，说明这一跳走完了，必须跳一次，并更新 curEnd = nextEnd；
     * - 当 curEnd 覆盖到最后一个位置时，可以结束。
     * 差异对比：
     * - 你的写法：用 dp 数组记录到达每个位置的最少步数，相当于 BFS 显式扩展每一层，空间 O(n)；
     * - 贪心写法：不记录每个位置的步数，只关心“层数”，即总跳数，空间 O(1)。
     * 时间复杂度：O(n)，只需线性遍历一次；
     * 空间复杂度：O(1)，只用几个变量。
     */
    public int jump2(int[] nums) {
        // 记录跳跃次数
        int steps = 0;
        // 当前这一跳的最远边界
        int curEnd = 0;
        // 下一跳的最远边界
        int nextEnd = 0;
        // 遍历到倒数第二个元素即可，因为到最后一个位置就不用再跳了
        for (int i = 0; i < nums.length - 1; i++) {
            // 在当前范围内，不断更新下一跳的最远边界
            nextEnd = Math.max(nextEnd, i + nums[i]);
            // 如果走到了当前跳的边界，必须再跳一步
            if (i == curEnd) {
                steps++;
                // 更新当前边界为下一跳的最远边界
                curEnd = nextEnd;
                // 如果当前边界已经覆盖末尾，可以提前结束
                if (curEnd >= nums.length - 1) {
                    break;
                }
            }
        }
        // 返回最少跳数
        return steps;
    }
}
