package leetCode.moderately;

/**
 * @author: gj
 * @description: 309. 买卖股票的最佳时机含冷冻期
 */
public class MaxProfit {

    public static void main(String[] args) {
        int[] prices = new int[]{1, 2, 4};
        System.out.println(new MaxProfit().maxProfit(prices));
    }


    /**
     * 超时！！！
     * 深度优先，并对非正收益的情况进行剪枝。
     * 对于任意一个节点，其实只有三种情况：
     * ①未购入股票，可以选择购入或不购入当天股票
     * ②已经购入股票，可以选择在当天卖出或不卖出，此处进行剪枝，只有在当天股票价格大于买入价格时才会卖出。
     * ③昨天已经卖出股票，今天处于冷冻期，直接进入下一天。
     * 采用深度优先遍历所有情况即可
     *
     * @param prices 股票价格
     * @return 最大收益
     */
    public int maxProfit(int[] prices) {
        if (prices.length == 1) return result;
        dfs(0, prices, -1, 0);
        return result;
    }

    public int result = 0;

    public void dfs(int index, int[] prices, int price, int currentProfit) {
        if (index == prices.length - 1) {
            currentProfit += price == -1 || price == -2 ? 0 : Math.max(prices[prices.length - 1] - price, 0);
            result = Math.max(currentProfit, result);
            return;
        }
        if (price == -1) {
            dfs(index + 1, prices, prices[index], currentProfit);
            dfs(index + 1, prices, -1, currentProfit);
        } else if (price == -2) {
            dfs(index + 1, prices, -1, currentProfit);
        } else {
            if (prices[index] - price > 0) {
                currentProfit += prices[index] - price;
                dfs(index + 1, prices, -2, currentProfit);
                currentProfit -= prices[index] - price;
                dfs(index + 1, prices, price, currentProfit);
            } else {
                dfs(index + 1, prices, price, currentProfit);
            }
        }
    }


    /**
     * 动态规划：
     * 对于第i天的最大收益，其实可以由第i-1天的最大收益计算得来，分三种情况（处于冷冻期指的是当天卖出后第二天处于冷冻期，方便计算）：
     * 1、第i天持有股票的最大收益
     * - 第i-1天持有股票，第i天不卖出继续持有，那么第i天持有股票的最大收益 = 第i-1天持有股票的最大收益
     * - 第i-1天不处于冷冻期不持有股票，第i天买入当天股票，那么第i天持有股票的最大收益 = 第i-1天不处于冷冻期不持有股票的最大收益 - 第i天股票价格
     * 2、第i天处于冷冻期的最大收益
     * - 第i天处于冷冻期意味着第i天卖掉股票，也就是第i-1天必须持有股票，那么最大收益 = 第i-1天持有股票的最大收益 + 第i天股票的价格
     * 4、第i天不持有股票不处于冷冻期的最大收益
     * - 第i-1天不持有股票不处于冷冻期，第i天不做任何操作。那么最大收益 = 第i-1天不持有股票不处于冷冻期的最大收益
     * - 第 i-1天处于冷冻期，第i天不做任何操作。那么最大收益 = 第i-1天处于冷冻期的最大收益
     *
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
        int n = prices.length;
        if (n == 1) return 0;
        // 第i天持有股票最大收益为：result[0]; 处于冷冻期最大收益为lastDayProfit[1]; 不持有股票不处于冷冻期最大收益为lastDayProfit[2];
        // 第0天持有股票收益为买入第0天的股票；处于冷冻期收益为0，因为无法卖出；不持有股票不处于冷冻期为0
        int[] result = new int[]{-prices[0], 0, 0};
        for (int i = 1; i < n; i++) {
            int price = prices[i], lastDay0 = result[0], lastDay1 = result[1], lastDay2 = result[2];
            result[0] = Math.max(lastDay0, lastDay2 - price);
            result[1] = lastDay0 + price;
            result[2] = Math.max(lastDay1, lastDay2);
        }
        return Math.max(result[1], result[2]);
    }


}
