package leetCode.moderately;

import java.util.PriorityQueue;

/**
 * @author: gj
 * @description: 2462. 雇佣 K 位工人的总代价
 */
public class TotalCost {

    /**
     * 双指针+双堆
     * 思路：
     * 两个小根堆，一个维护当前前 candidates 个元素，一个存放后 candidates 个元素，注意重叠后置的元素不能重复加入
     * 每次从哪个堆取一个元素，则再加入一个，通过双指针left和right记录两个堆应该加入的下一个元素的下标
     */
    public long totalCost(int[] costs, int k, int candidates) {
        // 1、初始化两个小根堆，一个存放前 candidates 个元素，一个存放后 candidates 个元素，注意重叠后置的元素不能重复加入
        int n = costs.length;
        int left = 0, right = n - 1;
        PriorityQueue<Integer> leftPq = new PriorityQueue<>();
        PriorityQueue<Integer> rightPq = new PriorityQueue<>();

        // 初始化两个堆
        for (int i = 0; i < candidates; i++) {
            if (left <= right) {
                leftPq.add(costs[left++]);
            }
            if (left <= right) {
                rightPq.add(costs[right--]);
            }
        }

        long ans = 0;

        for (int i = 0; i < k; i++) {
            // 选择最小代价
            if (rightPq.isEmpty() || (!leftPq.isEmpty() && leftPq.peek() <= rightPq.peek())) {
                ans += leftPq.poll();
                if (left <= right) {
                    leftPq.add(costs[left++]);
                }
            } else {
                ans += rightPq.poll();
                if (left <= right) {
                    rightPq.add(costs[right--]);
                }
            }
        }

        return ans;
    }
}
