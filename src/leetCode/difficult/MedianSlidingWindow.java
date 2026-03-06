package leetCode.difficult;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author: gj
 * @description: 480. 滑动窗口中位数
 */
public class MedianSlidingWindow {
    /**
     * * 一、问题本质
     * * 维护一个固定大小为 k 的滑动窗口，
     * * 支持：
     * *     1. 插入一个元素
     * *     2. 删除一个元素
     * *     3. O(1) 查询中位数
     * * 要求整体复杂度 O(n log k)
     * *
     * * 二、为什么必须用“双堆”
     * * 我们希望：
     * *     左半部分 <= 右半部分
     * *     且两边大小平衡
     * * 设计：
     * *     maxHeap 维护较小的一半（大顶堆）
     * *     minHeap 维护较大的一半（小顶堆）
     * * 不变量：
     * *     1. maxSize >= minSize
     * *     2. maxSize - minSize <= 1
     * * 这样：
     * *     k 为奇数：
     * *         中位数 = maxHeap.peek()
     * *     k 为偶数：
     * *         中位数 = (maxHeap.peek() + minHeap.peek()) / 2
     * *
     * * 三、为什么必须用“延迟删除”
     * * Java PriorityQueue 不支持 O(log n) 删除任意元素。
     * * 滑动窗口每次都会删除窗口最左元素，
     * * 如果直接 remove(object)，复杂度 O(k)
     * * 为保证 O(n log k)，必须：
     * *     用 HashMap 记录待删除次数
     * *     当元素位于堆顶时才真正删除（prune）
     * * 这样：
     * *     删除操作 amortized O(1)
     * *     堆调整 O(log k)
     * *
     * * 四、为什么逻辑 size 必须单独维护
     * * 因为：
     * *     堆内实际元素数量 ≠ 有效元素数量
     * * 延迟删除会导致堆内“脏数据”存在。
     * * 所以我们维护：
     * *     maxSize = 有效左堆元素数
     * *     minSize = 有效右堆元素数
     * *
     * * rebalance 依据的是逻辑 size，
     * * 而不是 heap.size()
     * *
     * * 五、核心操作流程
     * * addNum(num):
     * *     1. 插入对应堆
     * *     2. 更新逻辑 size
     * *     3. rebalance()
     * * removeNum(num):
     * *     1. 在 map 中标记删除
     * *     2. 根据 num 与 maxHeap.peek() 判断属于哪个堆
     * *     3. 更新逻辑 size
     * *     4. prune 两个堆
     * *     5. rebalance()
     * * findMedian():
     * *     1. 先 prune
     * *     2. 再读取堆顶
     * *
     * * 六、为什么 rebalance 必须用 while
     * * 因为延迟删除可能导致：
     * *     逻辑 size 差值 > 1
     * * 如果只用 if，
     * * 可能无法一次恢复平衡。
     * * 必须：
     * *     while (maxSize > minSize + 1)
     * *     while (maxSize < minSize)
     * *
     * * 七、复杂度分析（严格证明）
     * * add:
     * *     O(log k)
     * * remove:
     * *     O(log k)
     * * prune:
     * *     每个元素最多被真正删除一次
     * *     整体 amortized O(n)
     * * 总复杂度：
     * *     O(n log k)
     * *
     * * 八、极值陷阱（本题隐藏大坑）
     * * 比较器不能写：
     * *     (a, b) -> b - a
     * * 因为：
     * *     当 a = -2147483648
     * *         b = 2147483647
     * *     b - a 会溢出
     * * 正确写法：
     * *
     * *     Integer.compare(b, a)
     * *     或 Comparator.reverseOrder()
     * *
     * * 九、为什么 median 必须 long 强转
     * * 如果 k 为偶数：
     * *     maxHeap.peek() + minHeap.peek()
     * * 可能溢出 int。
     * * 例如：
     * *     INT_MAX + INT_MAX
     * * 必须：
     * *     ((long)a + b) / 2.0
     * *
     * * 十、为什么不能用数组模拟完全平衡二叉树
     * * 虽然：
     * *     插入/删除 O(log k)
     * *     中位数 O(1)
     * * 但是：
     * *     删除任意元素需要定位
     * *     仍需辅助结构（HashMap + index）
     * * 实现复杂度远高于双堆。
     * *
     * * 十一、特殊情况 k = 1,2,3 是否要特判？
     * *    不需要，双堆结构本身已经天然支持，特判只会增加代码复杂度。
     * *
     * * 十二、最终结论
     * * 本题的难点不在：
     * *     堆
     * * 而在：
     * *     延迟删除 + 逻辑 size 维护 + rebalance 严格性
     * * 真正理解后，本题属于：
     * *     中等偏难，但结构非常优雅。
     */
    public double[] medianSlidingWindow(int[] nums, int k) {
        double[] res = new double[nums.length - k + 1];
        MedianFinderLimit medianFinderLimit = new MedianFinderLimit(k);

        for (int i = 0; i < nums.length; i++) {
            medianFinderLimit.addNum(nums[i]);

            if (i >= k) {
                medianFinderLimit.removeNum(nums[i - k]);
            }

            if (i >= k - 1) {
                res[i - k + 1] = medianFinderLimit.findMedian();
            }
        }

        return res;
    }
}

class MedianFinderLimit {

    // 左半部分（大顶堆） 注意这里使用 Integer.compare，不然对于2147483647,-2147483648这种极限数据会出错（INT_MAX - INT_MIN 会溢出）
    private PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));

    // 右半部分（小顶堆）
    private PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    int k;

    // 有效元素个数（逻辑 size）
    int maxSize = 0;
    int minSize = 0;

    Map<Integer, Integer> map = new HashMap<>();

    public MedianFinderLimit(int k) {
        this.k = k;
    }

    public void addNum(int num) {
        if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
            maxHeap.offer(num);
            maxSize++;
        } else {
            minHeap.offer(num);
            minSize++;
        }
        rebalance();
    }

    public void removeNum(int num) {
        map.put(num, map.getOrDefault(num, 0) + 1);

        if (num <= maxHeap.peek()) {
            maxSize--;
        } else {
            minSize--;
        }
        // 如果正好删的是堆顶，立刻 prune
        prune(maxHeap);
        prune(minHeap);

        rebalance();
    }

    private void rebalance() {
        // 保证：maxSize >= minSize 且差值 <= 1
        while (maxSize > minSize + 1) {
            minHeap.offer(maxHeap.poll());
            maxSize--;
            minSize++;
            prune(maxHeap);
        }

        while (maxSize < minSize) {
            maxHeap.offer(minHeap.poll());
            minSize--;
            maxSize++;
            prune(minHeap);
        }
    }

    private void prune(PriorityQueue<Integer> heap) {
        while (!heap.isEmpty()) {
            int num = heap.peek();
            if (!map.containsKey(num)) {
                break;
            }

            heap.poll();
            map.put(num, map.get(num) - 1);
            if (map.get(num) == 0) {
                map.remove(num);
            }
        }
    }

    public double findMedian() {
        if ((k & 1) == 1) {
            return maxHeap.peek();
        } else {
            return ((long) maxHeap.peek() + minHeap.peek()) / 2.0;
        }
    }
}