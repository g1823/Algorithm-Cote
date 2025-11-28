package leetCode.moderately;

import java.util.Arrays;

/**
 * @author: gj
 * @description: 122. 买卖股票的最佳时机 II
 */
public class MaxProfit2 {

    int result = 0;

    /**
     * 暴力解法：
     * 使用深度优先搜索（DFS）暴力枚举的方式计算最大收益。
     * 思路：
     * 从第 0 天开始递归遍历到最后一天，每一天根据当前是否持有股票，
     * 可以做出“买入 / 卖出 / 不操作”的决策。
     * - 如果当前不持有股票，可以选择买入或继续不操作；
     * - 如果当前持有股票，可以选择卖出或继续持有；
     * - 当遍历到最后一天时，更新全局最大收益。
     * 复杂度：
     * 时间复杂度 O(2^n)，因为每一天最多有两个分支（买/卖 或 不操作），
     * 会枚举所有可能的交易序列；
     * 空间复杂度 O(n)，主要来自递归调用栈。
     * 注意：
     * - 这是一个正确但低效的暴力解法；
     * - 在 n 较大时不可用，可以通过记忆化搜索或动态规划优化。
     */
    public int maxProfit(int[] prices) {
        result = 0;
        dfs(0, prices, -1, 0);
        return result;
    }

    public void dfs(int index, int[] prices, int holdIndex, int currentProfit) {
        if (index == prices.length) {
            result = Math.max(currentProfit, result);
            return;
        }
        // 当前不持有股票，可以买入或直接不操作
        if (holdIndex == -1) {
            dfs(index + 1, prices, index, currentProfit);
            dfs(index + 1, prices, holdIndex, currentProfit);
        }
        // 当前持有股票，可以卖出或直接不操作
        if (holdIndex != -1) {
            dfs(index + 1, prices, -1, currentProfit + prices[index] - prices[holdIndex]);
            dfs(index + 1, prices, holdIndex, currentProfit);
        }
    }


    /**
     * 使用记忆化搜索（递归 + 备忘录）计算买卖股票的最大收益。
     * 思路来源：
     * 1. 暴力解法：
     * - 对于每一天，根据当前是否持有股票，可以选择「买入 / 卖出 / 不操作」；
     * - 递归遍历所有可能的交易序列，最终取最大收益；
     * - 该方法能保证正确性，但时间复杂度为 O(2^n)，在 n 较大时不可用。
     * 2. 问题分析：
     * - 在暴力递归中，许多子问题会被重复计算。例如「从第 j 天开始，手里持股」这个子问题可能从不同路径多次到达；
     * - 因此需要对相同子问题的结果进行缓存，避免重复计算。
     * 3. 记忆化搜索优化：
     * - 定义状态：
     * - - t[i][0] = 从第 i 天开始，手里不持股时的最大收益
     * - - t[i][1] = 从第 i 天开始，手里持股时的最大收益
     * - 转移方程：
     * - 不持股：max(不操作，今天买入)
     * - - t[i][0] = max(dfs(i+1, 0), dfs(i+1, 1) - prices[i])
     * - 持股：max(不操作，今天卖出)
     * - - t[i][1] = max(dfs(i+1, 1), dfs(i+1, 0) + prices[i])
     * - 递归过程中，如果某个状态 (i, hold) 已经计算过，则直接返回缓存结果。
     * 复杂度分析：
     * - 时间复杂度：O(n)，每个状态 (i, hold) 仅计算一次；
     * - 空间复杂度：O(n)，用于保存备忘录数组和递归调用栈。
     */
    public int maxProfit2(int[] prices) {
        // t[i][0]：标识第i天不持有股票，在i到最后一天期间的最大收益
        // t[i][1]：标识第i天持有股票，在i到最后一天期间的最大收益
        int[][] t = new int[prices.length][2];
        for (int[] ints : t) {
            Arrays.fill(ints, -1);
        }
        dfs2(0, prices, 0, t);
        return t[0][0];
    }

    public int dfs2(int index, int[] prices, int hold, int[][] t) {
        if (index == prices.length) {
            return 0;
        }
        // 如果第index天，持有\不持有
        if (t[index][hold] != -1) {
            return t[index][hold];
        }
        // 当前不持有股票，可以买入或直接不操作
        if (hold == 0) {
            t[index][hold] = Math.max(dfs2(index + 1, prices, 0, t), dfs2(index + 1, prices, 1, t) - prices[index]);
        } else if (hold == 1) {
            // 当前持有股票，可以卖出或直接不操作
            t[index][hold] = Math.max(dfs2(index + 1, prices, 1, t), dfs2(index + 1, prices, 0, t) + prices[index]);
        }
        return t[index][hold];
    }


    /**
     * 方法说明（核心思路）：
     * 前提：
     * - 题目允许当天买入再卖出，这个条件保证了连续上涨区间的收益不会丢失。
     * 1) 记忆化递归的出发点（自顶向下）：
     * - 定义状态：dfs(i, hold) 表示「从第 i 天开始、处于 hold 状态（0=不持股，1=持股）时」到数组末尾能取得的最大收益。
     * - 递归关系（自顶向下）：
     * - - dfs(i,0) = max( dfs(i+1,0), dfs(i+1,1) - prices[i] ) // 不持股：要么跳过，要么今天买
     * - - dfs(i,1) = max( dfs(i+1,1), dfs(i+1,0) + prices[i] ) // 持股：要么继续持有，要么今天卖
     * - 终止条件：i == n 时返回 0。使用 memo[i][hold] 缓存 dfs 的结果避免重复计算。
     * 2) 从记忆化到动态规划（自底向上）的关键观察：
     * - dfs(i,hold) 只依赖于 i+1 的状态（局部依赖），不依赖于更远的历史细节；
     * - 因此可以把 memo 表看成一个完整的 DP 表：dp[i][hold] = dfs(i,hold)（这里 dp 表定义为「从 i 到末尾的最优值」）；
     * - 采用自底向上的方式，用循环把 dp 的值从 i = n-1 填到 i = 0，最终得到 dp[0][0] 即答案。
     * 3) 常见的迭代（等价）写法 — “以天为前缀” 的定义（更常用，直观）：
     * - 定义：dp[i][0] 表示第 i 天结束时 不持股 的最大收益；dp[i][1] 表示第 i 天结束时 持股 的最大收益。
     * - 初始化：
     * - - dp[0][0] = 0;           // 第0天不持股，收益为0
     * - - dp[0][1] = -prices[0];  // 第0天买入，收益为 -prices[0]
     * - 递推（从前往后）：
     * - - dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i]); // 不持股：昨天不持股 / 昨天持股今天卖出
     * - - dp[i][1] = max(dp[i-1][1], dp[i-1][0] - prices[i]); // 持股：昨天持股 / 昨天不持股今天买入
     * - 最终答案：dp[n-1][0]（最后一天不持股时的最大收益）。
     * 4) 为什么不需要记录“具体是哪天买入”：
     * - dp[i][1] 存储的是「持股时的最大净资产（已把买入成本计入）」，历史上的任何一次买入
     * - - 在递推中都会通过“取最大值”被保留成 dp[i][1] 的值；
     * - 当在很久以前买入并在今天卖出时，其收益会通过 dp[i-1][1] + prices[i] 一次性体现出来。
     * 小结：
     * - 记忆化递归帮助我们发现了“状态（i, hold）”和“状态间局部依赖”；
     * - 动态规划通过把递归的子问题展开成表格，改为自底向上迭代，去掉递归开销并更容易做空间压缩。
     */
    public int maxProfit3(int[] prices) {
        // dp[i][0\1]记录当前的最大收益
        int n = prices.length;
        int[][] dp = new int[n][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < n; i++) {
            // 第i天不持有股票的最大收益 = max((第i-1天持有股票最大价值，今天买出)，（第i-1天不持有股票最大价值，今天不操作））
            dp[i][0] = Math.max(dp[i - 1][1] + prices[i], dp[i - 1][0]);
            // 第i天持有股票的最大收益 = max((第i-1天不持有股票最大价值，今天买入)，（第i-1天持有股票最大价值，今天不操作））
            dp[i][1] = Math.max(dp[i - 1][0] - prices[i], dp[i - 1][1]);
        }
        return dp[n - 1][0];
    }

    /**
     * 贪心解法：
     * 从第二天开始，只要今天价格比昨天高，就相当于昨天买入、今天卖出，
     * 把这部分正收益累加即可。
     * <p>
     * 关键点：
     * - 因为题目允许“当天买入再卖出”，所以不会出现冲突：
     * 例如 prices[1] < prices[2] < prices[3] 时，
     * 我们可以看作 (第1天买入→第2天卖出) + (第2天买入→第3天卖出)，
     * 这与 (第1天买入→第3天卖出) 的收益相同。
     * <p>
     * - 因此不需要担心“多次卖出会影响后续买入”，
     * 直接把所有上升区间的收益累加就是最大收益。
     */
    public int maxProfit4(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }
        return profit;
    }

}
