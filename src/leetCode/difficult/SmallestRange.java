package leetCode.difficult;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author: gj
 * @description: 632. 最小区间
 */
public class SmallestRange {

    /**
     * * 【问题理解】
     * * 给定 k 个升序数组，要求找一个区间 [a, b]，满足：
     * *   1. 每个数组中至少有一个元素落在 [a, b] 里
     * *   2. 区间 [a, b] 是最小区间：
     * *        - 先比较区间长度，长度更小的区间更优
     * *        - 如果长度相同，则左端点更小的区间更优
     * *
     * * 【最开始的尝试：收缩区间】
     * * - 初始想法：将每个数组的第一个元素和最后一个元素作为左右边界，记录全局最大值和最小值
     * * - 对每个数组维护当前“有效区间”，尝试依次收缩：
     * *      - 每次收缩选择所有右边界中的第二大、左边界中的第二小
     * *      - 更新每个数组的有效区间
     * * - 遇到问题：
     * *      1. 题目要求每个数组只需一个元素，而不是区间覆盖多个元素
     * *      2. 维护每个数组的有效区间导致复杂度高、逻辑混乱
     * *      3. 收缩左右边界时无法保证贪心正确性
     * *      4. 如果收缩右边界或左边界过度，可能导致覆盖数组的元素丢失，无法形成合法区间
     * *
     * * 【错误理解修正：只收缩左边界】
     * * - 想法修正为：只尝试收缩左端点
     * * - 原因：
     * *      1. 当前区间长度 = max - min
     * *      2. 只有提高最小值（左端点）才可能让区间变小
     * *      3. 移动最大值（右端点）只会让区间增大，不可能更优
     * * - 修正问题：
     * *      - 不能直接认为当前最小值所在数组已经到末尾时区间就是最优
     * *      - 正确做法：每次移动当前最小值，过程中不断更新区间最优值
     * *      - 当某数组耗尽（没有可选元素）时，循环结束，之前记录的历史最优区间即为答案
     * *
     * * 【贪心正确性证明】
     * * 1. 每次移动当前最小值所在数组的指针：
     * *      - 区间长度 = max - min
     * *      - 移动最小值可能缩小区间，移动最大值只会增大区间
     * * 2. 不会漏解：
     * *      - 假设丢弃的最小值 x，未来可以组成更优区间
     * *      - 由于数组升序，指针右移后，未来可选元素 ≥ 当前元素
     * *      - 当前最大值也不会下降
     * *      - 所以未来任何区间长度 ≥ 当前区间长度，不可能出现更优解
     * * 3. 核心单调性：
     * *      - 每个数组指针只向右移动（升序）
     * *      - 最大值只可能增大，最小值只会提高
     * *      - 丢弃的最小值不可能再被用作最优区间的左端点
     * *
     * * 【本质建模】
     * * - 将每个数组的当前指针元素看作 k 个选中元素
     * * - 当前区间 = [最小值, 当前最大值]
     * * - 相当于“多路归并 + 最小覆盖窗口”问题
     * * - 可以用最小堆维护当前最小值，同时记录当前最大值
     * * - 每次弹出最小值所在数组的下一个元素放入堆，更新最大值
     * * - 当某数组耗尽，循环结束
     * *
     * * 【解题思路总结】
     * * 1. 初始化：
     * *      - 每个数组的第一个元素入最小堆
     * *      - 更新当前最大值 currentMax
     * * 2. 循环：
     * *      a. 弹出堆顶最小值 currentMin
     * *      b. 更新区间 [currentMin, currentMax] 为历史最优区间（如果更优）
     * *      c. 将该最小值所在数组的下一个元素入堆
     * *         - 更新 currentMax = max(currentMax, nextValue)
     * *         - 如果该数组已经没有下一个元素，停止循环
     * * 3. 结束：
     * *      - 返回历史记录的最优区间
     * *
     * * 【时间复杂度】
     * * - 堆大小 = k
     * * - 总元素数 = N
     * * - 每个元素最多入堆一次，push/pop O(log k)
     * * - 总复杂度 O(N log k)，空间 O(k)
     * *
     * * 【核心总结】
     * * - 题目核心是“k 个有序数组的最小覆盖区间”
     * * - 核心贪心不变量：
     * *      1. 当前选中 k 个元素，堆顶为最小值
     * *      2. 当前最大值 currentMax
     * *      3. 每次移动最小值所在数组 → 区间可能缩小
     * *      4. 单调性保证丢弃的最小值不会产生更优解
     */
    public int[] smallestRange(List<List<Integer>> nums) {
        // 小根堆
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> nums.get(a[0]).get(a[1])));
        // 堆内最大值
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.size(); i++) {
            pq.offer(new int[]{i, 0});
            max = Math.max(max, nums.get(i).get(0));
        }
        // 结果区间
        int[] result = new int[]{nums.get(pq.peek()[0]).get(pq.peek()[1]), max};
        while (true) {
            int[] poll = pq.poll();
            // 达到了某个数组末尾，再往后该数组的任何元素将不再存在于结果区间
            if (poll[1] == nums.get(poll[0]).size() - 1) {
                break;
            }
            // 将当前最小值所在数组的下一个元素添加到堆中，更新最大值，更新结果区间
            max = Math.max(max, nums.get(poll[0]).get(poll[1] + 1));
            pq.offer(new int[]{poll[0], poll[1] + 1});
            if (max - nums.get(pq.peek()[0]).get(pq.peek()[1]) < result[1] - result[0]) {
                result[0] = nums.get(pq.peek()[0]).get(pq.peek()[1]);
                result[1] = max;
            }
        }
        return result;
    }

    /**
     * todo
     */
    public int[] smallestRange2(List<List<Integer>> nums) {
        return null;
    }
}
