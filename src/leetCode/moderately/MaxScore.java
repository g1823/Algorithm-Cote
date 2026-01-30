package leetCode.moderately;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author: gj
 * @description: 2542. 最大子序列的分数
 */
public class MaxScore {
    public static void main(String[] args) {
        int[] nums1 = new int[]{1, 3, 3, 2};
        int[] nums2 = new int[]{2, 1, 3, 4};
        System.out.println(new MaxScore().maxScore(nums1, nums2, 2));
    }

    /**
     * - 转换问题（转换思考过程、逆向思考）+ 最小堆
     * - 思路梳理：
     * - 1. 初始暴力思路：
     * -    - 最直观的做法是枚举 nums1 中所有可能的 k 个下标组合
     * -    - 对应选出的子序列得分 = sum(nums1[i1..ik]) * min(nums2[i1..ik])
     * -    - 组合数量为 C(n, k)，当 n 较大时不可行
     * - 2. 固定 nums2 的最小值尝试：
     * -    - 注意得分公式中 min(nums2) 决定了整体的瓶颈
     * -    - 尝试枚举 nums2 的某个值作为最小值
     * -    - 然后在对应可选下标集合中，选择 nums1 最大的 k 个值
     * - 3. nums1 全局排序 + 前缀和优化（错误尝试）：
     * -    - 曾尝试对 nums1 全局排序，然后利用前缀和求最大和
     * -    - 错误原因：
     * -        1) 可选下标受限于当前 nums2 最小值
     * -        2) 全局排序前 k 个元素可能包含不合法下标
     * -        3) 这种优化不能保证每个 nums2 最小值的候选集合正确
     * - 4. 最终解法：
     * -    - 对 nums2 按值降序排序（保留原下标）
     * -    - 初始化：
     * -        a) 将前 k 个下标对应的 nums1 元素加入小根堆
     * -        b) sum = 前 k 个 nums1 之和
     * -        c) ans = sum * 当前最小 nums2（即 nums2[index[k-1]]）
     * -    - 遍历排序后的剩余下标：
     * -        a) 每加入一个新的 nums1 元素，先加到堆中 sum += nums1[idx]
     * -        b) 因为堆大小 > k，弹出堆中最小元素，sum -= poll()
     * -        c) 更新 ans = max(ans, sum * 当前 nums2 值)
     * -    - 关键逻辑：
     * -        1) 堆中始终保存当前可选集合中最大的 k 个 nums1
     * -        2) 当 nums2 最小值为当前下标时，最优解一定在堆中被正确计算到
     * -        3) 不需要显式保证包含最小值的下标，算法自然覆盖所有可能情况
     * -    - 时间复杂度 O(n log n)（排序 + 堆操作）
     * -    - 空间复杂度 O(k)（小根堆）
     * - 总结：
     * - - 算法核心是“枚举 nums2 从大到小作为最小值，维护当前 top-k nums1”
     * - - 初始化前 k 个元素只是方便第一步计算 ans，后续逻辑和核心思想一致
     */
    public long maxScore(int[] nums1, int[] nums2, int k) {
        // 1、nums2按照值对下标降序排序
        int n = nums1.length;
        Integer[] index = new Integer[n];
        for (int i = 0; i < n; i++) {
            index[i] = i;
        }
        Arrays.sort(index, (i, j) -> Integer.compare(nums2[j], nums2[i]));

        // 2、初始化，将前k个元素对应的nums1中的值初始化到小根堆中，并维护小根堆中k个元素的和
        long sum = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < k; i++) {
            sum += nums1[index[i]];
            pq.add(nums1[index[i]]);
        }
        long ans = sum * nums2[index[k - 1]];
        for (int i = k; i < n; i++) {
            sum -= pq.poll();
            sum += nums1[index[i]];
            pq.add(nums1[index[i]]);
            ans = Math.max(ans, sum * nums2[index[i]]);
        }
        return ans;
    }

    /**
     * 与maxScore一致，只是去除了初始化操作，代码更简洁
     */
    public long maxScore2(int[] nums1, int[] nums2, int k) {
        int n = nums1.length;
        Integer[] index = new Integer[n];
        for (int i = 0; i < n; i++) {
            index[i] = i;
        }
        // 按 nums2 值降序排列下标
        Arrays.sort(index, (i, j) -> Integer.compare(nums2[j], nums2[i]));
        // 小根堆维护当前最大 k 个 nums1
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        long sum = 0, ans = 0;
        for (int i = 0; i < n; i++) {
            int idx = index[i];
            pq.add(nums1[idx]);
            sum += nums1[idx];
            // 堆大小超过 k，移除最小元素
            if (pq.size() > k) {
                sum -= pq.poll();
            }
            // 当堆恰好有 k 个元素时，计算当前得分
            if (pq.size() == k) {
                ans = Math.max(ans, sum * (long) nums2[idx]);
            }
        }
        return ans;
    }

}
