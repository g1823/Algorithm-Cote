package leetCode.difficult;


import leetCode.help.DoubleLinkedNode;

import java.util.Arrays;

/**
 * @author: gj
 * @description: 312. 戳气球
 */
public class MaxCoins {

    public static void main(String[] args) {
        int[] data = new int[]{3, 1, 5, 8};
        System.out.println(new MaxCoins().maxCoins(data));
    }


    int maxCoins = 0;

    /**
     * 回溯法：（超时，时间复杂度为O(n!)）
     * - 逐个枚举当前可以戳掉的气球，尝试所有可能的戳法组合，取最大值。由于每次戳掉一个气球都会影响左右邻居，因此不能按固定顺序戳，必须枚举所有顺序。
     * 使用双向链表模拟删除操作：
     * - 每个气球封装为一个双向链表节点，便于在删除和恢复过程中操作前驱和后继。
     * - 每次递归选择一个节点，将其从链表中移除（断开与前后节点的连接），然后递归处理剩下的节点。
     * - 回溯结束后将节点重新插回原位，恢复现场，继续尝试其他路径。
     * 边界处理：
     * - 如果某个气球的左边或右边已经没有气球（即 null），按题目要求处理为值为 1。
     */
    public int maxCoins(int[] nums) {
        DoubleLinkedNode dummy = new DoubleLinkedNode(-1);
        DoubleLinkedNode prev = dummy;
        for (int num : nums) {
            DoubleLinkedNode node = new DoubleLinkedNode(num);
            prev.next = node;
            node.prev = prev;
            prev = node;
        }
        DoubleLinkedNode head = dummy.next;
        if (head != null) {
            head.prev = null;
        }
        backtrack(head, 0);
        return maxCoins;
    }

    private void backtrack(DoubleLinkedNode node, int coins) {
        if (node == null) {
            maxCoins = Math.max(maxCoins, coins);
            return;
        }

        DoubleLinkedNode t = node;
        while (t != null) {
            DoubleLinkedNode prev = t.prev;
            DoubleLinkedNode next = t.next;

            int prevVal = prev == null ? 1 : prev.val;
            int nextVal = next == null ? 1 : next.val;

            // 删除 t
            if (prev != null) {
                prev.next = next;
            }
            if (next != null) {
                next.prev = prev;
            }

            // 下一轮依然从链表的头节点开始遍历
            backtrack(node == t ? next : node, coins + prevVal * t.val * nextVal);

            // 恢复 t
            if (prev != null) {
                prev.next = t;
            }
            t.prev = prev;
            t.next = next;
            if (next != null) {
                next.prev = t;
            }

            t = t.next;
        }
    }


    /**
     * maxCoins优化：
     * - 正向模拟时，数据结构会动态变化（如数组元素增删、相邻关系变化）；
     * - 导致状态不好表示、难以递归/DP建模；
     * - 每个操作都会“影响后面的决策”；
     * - 并且你很难预知一个操作后留下的“状态”结构时；
     * 可以尝试逆向思考（倒推建模），若逆向思考：
     * - 结果构造更清晰：
     * -- 你能明确知道“最后一步”是什么；
     * -- 最后一项的得分容易计算（如：戳气球时，最后被戳的左右是确定的）；
     * - 状态划分更干净：
     * -- 倒着处理的过程中，原本动态变化的结构，变成了“稳定”的分段（如戳气球变成拆区间）；
     * -- 子问题可以不相互影响地独立处理，非常适合记忆化或DP；
     * - 最终目标可以由子目标组合而成：
     * -- 典型如：最后一步 = 子问题1 + 子问题2 + 当前操作得分；
     * 记忆化搜索：
     * 正向模拟气球戳破会导致数组内元素变化，不相邻元素变得相邻，需要改变数组元素或辅助数组标记。
     * 而逆向思考，找到某个区间内最后一个被戳破的气球，这个区间内没有数据，那么这个气球左右元素实际上是确认的。
     * 然后在使用数组记录会被重复计算的子问题区间即可。
     * 解法：
     * - dfs(left, right) 表示戳光区间 (left, right) 内所有气球（不包含两端）获得的最大得分
     * -- 如果当前 (left, right) 区间长度小于等于 1：没气球可戳，返回 0
     * -- 否则，我们尝试 枚举在这个区间内哪个气球是“最后被戳的”
     * - 枚举最后一个戳的气球 k：
     * -- 假设 k 是最后一个被戳的气球
     * -- 由于 left 和 right 还在（最后一个戳的），可得分：points[left] * points[k] * points[right]
     * - 而当前元素是区间内最后一个被戳破，在其被戳破前，该区间内的其他元素还未被戳破，因此递归处理：
     * -- 戳 (left, k) 中的所有气球：dfs(left, k)
     * -- 戳(k, right) 中的所有气球：dfs(k, right)
     * - 所以，当枚举完当前区间(left, right)内所有元素作为最后一个被戳破的，就可以得出当前区间内戳气球的最大得分了。
     * - 并且可以发现，递归调用中，子问题重复计算，因此需要使用记忆化搜索。
     * -- 比如计算下标(1,5)区间内的时候，计算2为最后一个被戳破的得分，会调用(1,2)、(2,3)、(3,4)、(4,5)区间内所有气球的得分，
     * -- 而计算3为最后一个被戳破的得分，会调用(1,3)、(2,3)、(3,4)、(4,5)区间内所有气球的得分，可见 (2,3)、(3,4)、(4,5)区间内所有气球的得分重复计算。
     */
    public int maxCoins2(int[] nums) {
        int n = nums.length;
        int[] points = new int[n + 2];
        points[0] = points[n + 1] = 1;
        System.arraycopy(nums, 0, points, 1, n);

        int[][] memo = new int[n + 2][n + 2];
        for (int[] ints : memo) {
            Arrays.fill(ints, -1);
        }

        return dfs(0, n + 1, points, memo);
    }

    public int dfs(int left, int right, int[] points, int[][] memo) {
        if (left + 1 == right) {
            return 0;
        }
        // 计算过就直接返回
        if (memo[left][right] != -1) {
            return memo[left][right];
        }

        int maxCoins = 0;
        for (int k = left + 1; k < right; k++) {
            int coins = points[left] * points[k] * points[right];
            coins += dfs(left, k, points, memo) + dfs(k, right, points, memo);
            maxCoins = Math.max(maxCoins, coins);
        }
        // 更新记忆数组
        memo[left][right] = maxCoins;
        return maxCoins;
    }


    /**
     * 动态规划：
     * 动态规划和记忆化搜索实际上是一样的，二者等价的背后原理：递归是需求驱动，动态规划是结果驱动。
     * 项目	                记忆化搜索	                    动态规划
     * 递推方向	            自顶向下	                        自底向上
     * 中间结果缓存	        用 memo 记录中间子问题	用 dp       数组直接保存
     * 调用顺序	            调用才计算	                    遍历顺序控制
     * 状态构建方式	        通过递归函数隐式定义状态	        显式遍历所有合法状态
     * 利用子问题	            等调用时才使用	                    明确保证使用时一定已经计算完
     * 状态定义：
     * - dp[i][j] 表示开区间 (i, j) 内所有气球被戳爆后能获得的最大得分（不包含 i 和 j）
     * 状态转移方程：
     * - dp[i][j] = max for all k in (i+1, j-1):
     * - dp[i][k] + dp[k][j] + points[i] * points[k] * points[j]
     * 递推顺序：
     * - 因为 dp[i][j] 依赖于 dp[i][k] 和 dp[k][j]
     * - 所以必须先计算所有长度较小的区间，再推较大的区间（从小区间往大区间）
     * - 当我们计算 dp[i][j] 时，所有依赖的子问题（更小的区间）已被提前填充完毕
     * 正确性说明：
     * - 举例来说，dp[i][j+1] 所依赖的 dp[i][k] 和 dp[k][j+1]（k ∈ (i+1, j)）在长度为 (j - i) 时就已计算完成
     * - 所以在从小区间向大区间递推的过程中，所有状态转移所需的子问题都已具备，无需重复计算
     *
     */
    public int maxCoins3(int[] nums) {
        int n = nums.length;
        int[] points = new int[n + 2];
        points[0] = points[n + 1] = 1;
        System.arraycopy(nums, 0, points, 1, n);

        int[][] dp = new int[n + 2][n + 2];
        /**
         * 区间左侧从n开始，因此从0开始无法保证小区间先被计算：
         * 在枚举区间 (i, j)，而状态转移依赖的是 dp[i][k] 和 dp[k][j]，当 i 递减时，意味着：
         * - 小区间 (i, k) 被优先计算；
         * - 大区间 (i, j) 可以放心使用这些子结果。
         */
        for (int i = n; i >= 0; i--) {
            // 区间右侧从左侧+2开始，保证区间内至少有一个元素
            for (int j = i + 2; j <= n + 1; j++) {
                for (int k = i + 1; k < j; k++) {
                    dp[i][j] = Math.max(dp[i][j],
                            dp[i][k] + dp[k][j] + points[i] * points[k] * points[j]);
                }
            }
        }
        return dp[0][n + 1];
    }
}
