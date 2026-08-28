package leetCode.difficult;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @description: 1425. 带限制的子序列和
 */
public class ConstrainedSubsetSum {
    /**
     * 动态规划 + 区间查询优化(单调队列)
     */
    public int constrainedSubsetSum(int[] nums, int k) {
        int n = nums.length;
        // dp[i] = 以i结尾的最大序列和
        int[] dp = new int[n];
        Deque<Integer> deque = new ArrayDeque<>();
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            // 去除队首的无效元素（超出k范围限制）
            while (!deque.isEmpty() && deque.peekFirst() < i - k) {
                deque.pollFirst();
            }
            // 计算dp[i]
            int curMax = deque.isEmpty() ? 0 : Math.max(0, dp[deque.peekFirst()]);
            dp[i] = curMax + nums[i];
            // 维护单调性，去除队尾更小的元素
            while (!deque.isEmpty() && dp[deque.peekLast()] < dp[i]){
                deque.pollLast();
            }
            // 维护全局最大值
            max = Math.max(max, dp[i]);
            deque.addLast(i);
        }
        return max;
    }
}
