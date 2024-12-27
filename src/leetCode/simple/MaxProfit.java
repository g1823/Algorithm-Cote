package leetCode.simple;

/**
 * @author: gj
 * @description: 121. 买卖股票的最佳时机
 */
public class MaxProfit {
    public int maxProfit(int[] prices) {
        int result = 0, temMax = 0;
        for (int i = prices.length - 2; i >= 0; i--) {
            temMax = Math.max(temMax + prices[i + 1] - prices[i], prices[i + 1] - prices[i]);
            result = Math.max(temMax, result);
        }
        return result;
    }
}
