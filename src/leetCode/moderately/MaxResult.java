package leetCode.moderately;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;

/**
 * @description: 1696. 跳跃游戏 VI (动态规划 + 区间查询优化)
 * @author: gj
 */
public class MaxResult {

    /**
     * 方案一：动态规划 + 单调队列（dp 数组 + 队列只存下标）
     *
     * 状态定义：dp[i] 表示从 0 跳到 i 的最大得分
     * 递推关系：dp[i] = nums[i] + max(dp[i-k], ..., dp[i-1])
     *
     * 单调递减队列（队头到队尾 dp 值递减，只存下标）三步走：
     *   1. 淘汰过期：弹出队头所有下标 < i-k 的元素（窗口为 [i-k, i-1]）
     *   2. 取最大值：队头即窗口内最大 dp 值，dp[i] = nums[i] + dp[队头]
     *   3. 维护单调：从队尾弹出所有 dp 值 <= dp[i] 的元素（新下标存活更久，旧元素永不会被选）
     *
     * 时间 O(n)，空间 O(n)（dp 数组）
     */
    public int maxResult(int[] nums, int k) {
        int n = nums.length;
        int[] dp = new int[n];
        Deque<Integer> deque = new ArrayDeque<>();

        dp[0] = nums[0];
        deque.addLast(0);

        for (int i = 1; i < n; i++) {
            // 第一步：淘汰过期元素。窗口为 [i-k, i-1]，下标 < i-k 的元素不可能再被使用
            while (!deque.isEmpty() && deque.peekFirst() < i - k) {
                deque.pollFirst();
            }
            // 第二步：取队头（窗口最大值）计算 dp[i]，此时队头一定非空且合法
            dp[i] = nums[i] + dp[deque.peekFirst()];
            // 第三步：维护单调性。队尾 dp 值 <= 当前 dp[i] 的直接淘汰
            while (!deque.isEmpty() && dp[deque.peekLast()] <= dp[i]) {
                deque.pollLast();
            }
            deque.addLast(i);
        }
        return dp[n - 1];
    }

    /**
     * 方案二：单调队列存二元组 {下标, dp 值}，省去 dp 数组
     * 空间降为 O(k)，但需要维护二元组，代码略繁琐
     */
    public int maxResultWithPair(int[] nums, int k) {
        int n = nums.length;
        Deque<int[]> deque = new ArrayDeque<>();
        deque.addLast(new int[]{0, nums[0]});  // {下标, dp 值}

        int cur = nums[0];
        for (int i = 1; i < n; i++) {
            // 淘汰过期：队头下标 < i-k 则弹出
            while (!deque.isEmpty() && deque.peekFirst()[0] < i - k) {
                deque.pollFirst();
            }
            // 取队头 dp 值计算当前结果
            cur = nums[i] + deque.peekFirst()[1];
            // 维护单调：弹出队尾 dp 值 <= cur 的元素
            while (!deque.isEmpty() && deque.peekLast()[1] <= cur) {
                deque.pollLast();
            }
            deque.addLast(new int[]{i, cur});
        }
        return cur;
    }

    /**
     * 方案三：大根堆 + 延迟删除
     * 堆存 {dp 值, 下标}，按 dp 值降序。过期的元素不立即删除，
     * 而是"延迟"到它成为堆顶时才弹出（堆顶必须是合法窗口内的元素）
     * 时间 O(n log n)，空间 O(n)
     */
    public int maxResultWithHeap(int[] nums, int k) {
        int n = nums.length;
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        heap.offer(new int[]{nums[0], 0});

        int cur = nums[0];
        for (int i = 1; i < n; i++) {
            // 延迟删除：堆顶过期（下标 < i-k）就弹出，直到堆顶合法
            while (heap.peek()[1] < i - k) {
                heap.poll();
            }
            cur = nums[i] + heap.peek()[0];
            heap.offer(new int[]{cur, i});
        }
        return cur;
    }

    public static void main(String[] args) {
        MaxResult solver = new MaxResult();
        int[][] numsList = {
                {1, -1, -2, 4, -7, 3},
                {10, -5, -2, 4, 0, 3},
                {1, -5, -20, 4, -1, 3, -6, -3}
        };
        int[] kList = {2, 3, 2};

        for (int t = 0; t < numsList.length; t++) {
            int a = solver.maxResult(numsList[t], kList[t]);
            int b = solver.maxResultWithPair(numsList[t], kList[t]);
            int c = solver.maxResultWithHeap(numsList[t], kList[t]);
            System.out.println("用例" + t + ": dp数组=" + a + ", 二元组=" + b + ", 大根堆=" + c
                    + (a == b && b == c ? "  [一致]" : "  [不一致!]"));
        }
    }
}
