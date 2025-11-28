package leetCode.difficult;

import java.util.*;

/**
 * @author: gj
 * @description: 502. IPO
 */
public class FindMaximizedCapital {

    /**
     * 思路说明：
     * 本题初看类似 0/1 背包：从多个项目中选取 k 个使收益最大，但与背包不同的是，
     * 项目一旦完成会增加资本 W，从而动态解锁更多项目；资本不是静态容量，
     * 因此无法使用 DP 或一次性确定选择集合。尝试采用 DFS 暴力枚举所有选择顺序，
     * 但选择顺序影响能否解锁后续项目，导致排列级别的复杂度，不可行。
     * <p>
     * 随后考虑每次在“当前能做的项目”中选择利润最大的项目，这符合贪心思想，
     * 但若仅按成本排序并线性扫描，会出现指针回退的问题：当做完某项目后资本不足以继续做成本更高的项目，
     * 下标需要向前回退查找其他可做项目，导致整体复杂度退化到 O(n^2)。
     * <p>
     * 为避免指针回退，需要将“解锁项目”与“选择最大利润项目”的职责分离：
     * 1. 先按成本升序排序所有项目，使用一个指针 i 仅负责将满足 cost <= 当前资本 W 的项目依次加入候选。
     * 由于资本只会增加，指针只会向前移动，每个项目最多加入一次，不会重复扫描。
     * 2. 对已解锁的候选项目使用大根堆（按利润排序），每次从堆中取出利润最大者完成，
     * 使资本增长最快，从而解锁更多项目。
     * <p>
     * 最终整体流程为：排序 + 大根堆。每次循环将所有已解锁项目加入堆，再从堆中取最大利润项目完成。
     * 时间复杂度为 O(n log n + k log n)，满足大规模数据要求。
     */

    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        // 按成本排序
        List<Integer> orderIndex = new ArrayList<>();
        for (int i = 0; i < capital.length; i++) {
            orderIndex.add(i);
        }
        orderIndex.sort(Comparator.comparingInt(i -> capital[i]));

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        int i = 0;
        for (int t = 0; t < k; t++) {
            // 将所有当前可做项目加入堆
            while (i < orderIndex.size() && capital[orderIndex.get(i)] <= w) {
                maxHeap.offer(profits[orderIndex.get(i)]);
                i++;
            }
            // 没有可做项目，提前退出
            if (maxHeap.isEmpty()) {
                break;
            }
            // 选利润最大项目
            int cur = maxHeap.poll();
            w += cur;
        }
        return w;
    }
}
