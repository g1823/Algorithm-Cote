package leetCode.difficult;

import javafx.util.Pair;

import java.util.PriorityQueue;

/**
 * @author: gj
 * @description: 295. 数据流的中位数
 * MedianFinder — 数据流中位数维护
 * <p>
 * 思考分析及演进过程：
 * <p>
 * 1. 最初想法：用数组或堆排序维护全局有序
 * - 可直接取中位数 O(1)
 * - 问题：每次插入需要移动或重建堆，插入 O(n) 或 O(n log n)，
 * 数据流动态插入时性能无法接受
 * <p>
 * 2. 进一步尝试：只记录中间有限元素
 * - 思路是维护左右计数和几个中间值
 * - 问题：新元素可能丢失，无法动态更新中位数
 * <p>
 * 3. 最优方案：双堆（大顶堆 + 小顶堆）
 * - 左半部分最大堆 maxHeap 保存较小一半
 * - 右半部分最小堆 minHeap 保存较大一半
 * - 不变量 1：maxHeap.size() >= minHeap.size() 或两者差 ≤ 1
 * - 不变量 2：maxHeap 所有元素 ≤ minHeap 所有元素
 * <p>
 * 4. 插入逻辑：
 * ① 新数先放入 maxHeap
 * ② 将 maxHeap 堆顶移到 minHeap，保证顺序
 * ③ 如 minHeap 数量超过 maxHeap，再移回一个，保证数量差 ≤ 1
 * <p>
 * 5. 取中位数逻辑：
 * - 奇数个元素：多的那堆堆顶
 * - 偶数个元素：两堆堆顶平均
 * <p>
 * 这种方法实现：
 * - addNum: O(log n)
 * - findMedian: O(1)
 */
class MedianFinder {

    // 左半部分大顶堆：保存较小的一半元素
    private PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);

    // 右半部分小顶堆：保存较大的一半元素
    private PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    public MedianFinder() {
    }

    /**
     * 向数据流中添加一个数字
     * 插入步骤：
     * 1. 先放入 maxHeap（左半部分）
     * 2. 将左半最大值移动到 minHeap，保证顺序关系
     * 3. 如右半多于左半，再移回一个，保证数量平衡
     */
    public void addNum(int num) {
        // ① 插入左半部分（大顶堆）
        maxHeap.offer(num);

        // ② 保证顺序不变量：左半最大 ≤ 右半最小
        minHeap.offer(maxHeap.poll());

        // ③ 保证数量平衡不变量：两堆差 ≤ 1
        if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    /**
     * 获取当前数据流的中位数
     * - 奇数个元素：返回元素多的那堆堆顶
     * - 偶数个元素：返回两堆堆顶平均值
     */
    public double findMedian() {
        if (maxHeap.size() > minHeap.size()) {
            return maxHeap.peek();
        }
        return (maxHeap.peek() + minHeap.peek()) / 2.0;
    }
}

