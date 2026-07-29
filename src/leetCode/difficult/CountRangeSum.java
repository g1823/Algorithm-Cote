package leetCode.difficult;

import java.util.*;

/**
 * @description: 327. 区间和的个数
 */
public class CountRangeSum {
    /**
     * 线段树 + 离散化
     */
    class solution {
        public int countRangeSum(int[] nums, int lower, int upper) {
            // 可以通过前缀和相减得到连续区间和
            // 离散化，计算所有可能的前缀和以及对于任意下标，以该下标为右端点，符合条件的区间最小值和最大值
            long[] preSum = new long[nums.length + 1];
            Set<Long> set = new HashSet<>();
            preSum[0] = 0;
            set.add(0L);
            set.add(0L - lower);
            set.add(0L - upper);
            for (int i = 0; i < nums.length; i++) {
                preSum[i + 1] = preSum[i] + nums[i];
                set.add(preSum[i + 1]);
                set.add(preSum[i + 1] - lower);
                set.add(preSum[i + 1] - upper);
            }
            long[] arr = new long[set.size()];
            int index = 0;
            for (Long integer : set) {
                arr[index++] = integer;
            }
            Arrays.sort(arr);
            // 离散化后的值到下标的映射
            Map<Long, Integer> map = new HashMap<>();
            for (int i = 0; i < arr.length; i++) {
                map.put(arr[i], i);
            }
            SegmentTree segmentTree = new SegmentTree(arr.length);
            int res = 0;
            // 遍历前缀和，计算以当前下标为右端点，符合条件的区间最小值和最大值，然后在线段树中查询区间和的个数
            for (long sum : preSum) {
                // 计算以当前节点为右端点，符合条件的区间最小值和最大值
                int left = map.get(sum - upper);
                int right = map.get(sum - lower);
                int count = segmentTree.query(left, right);
                res += count;
                // 将当前前缀和插入线段树中
                segmentTree.update(map.get(sum));
            }
            return res;
        }

        class SegmentTree {
            int[] nodes;
            int n;

            public SegmentTree(int n) {
                this.n = n;
                nodes = new int[n * 4];
            }

            public int query(int left, int right) {
                if (left > right || left < 0 || right >= n) {
                    return 0;
                }
                return query(1, 0, n - 1, left, right);
            }

            private int query(int node, int left, int right, int queryLeft, int queryRight) {
                // 查询区间覆盖当前区间
                if (left >= queryLeft && right <= queryRight) {
                    return nodes[node];
                }
                // 完全无重叠
                if (left > queryRight || right < queryLeft) {
                    return 0;
                }
                // 部分覆盖
                int mid = left + (right - left) / 2;
                // 左半部分有重叠
                if (queryRight <= mid) {
                    return query(node * 2, left, mid, queryLeft, queryRight);
                }
                // 右半部分有重叠
                else if (queryLeft > mid) {
                    return query(node * 2 + 1, mid + 1, right, queryLeft, queryRight);
                }
                // 左右半部分都有重叠
                else {
                    return query(node * 2, left, mid, queryLeft, mid) + query(node * 2 + 1, mid + 1, right, mid + 1, queryRight);
                }
            }

            /**
             * 这里更新就是更新具体叶子节点了，出现次数+1.叶子节点不代表区间了
             */
            public void update(int index) {
                if (index < 0 || index >= n) {
                    return;
                }
                update(1, 0, n - 1, index);
            }

            private void update(int node, int left, int right, int index) {
                // 具体叶子节点叶子节点
                if (left == right) {
                    if (left == index) {
                        nodes[node] += 1;
                    }
                    return;
                }
                // 不在当前范围
                if (index < left || index > right) {
                    return;
                }
                // 因为每次都是单点更新，且当前节点的值全都由叶子节点的值计算而来，因此不需要有下推或者lazy节点了
                int mid = left + (right - left) / 2;
                if (index <= mid) {
                    update(node * 2, left, mid, index);
                } else {
                    update(node * 2 + 1, mid + 1, right, index);
                }
                nodes[node] = nodes[node * 2] + nodes[node * 2 + 1];
            }
        }

    }

    /**
     * 树状数组(BIT)
     */
    class solution2 {
        public int countRangeSum(int[] nums, int lower, int upper) {
            // 可以通过前缀和相减得到连续区间和
            // 离散化，计算所有可能的前缀和以及对于任意下标，以该下标为右端点，符合条件的区间最小值和最大值
            long[] preSum = new long[nums.length + 1];
            Set<Long> set = new HashSet<>();
            preSum[0] = 0;
            set.add(0L);
            set.add(0L - lower);
            set.add(0L - upper);
            for (int i = 0; i < nums.length; i++) {
                preSum[i + 1] = preSum[i] + nums[i];
                set.add(preSum[i + 1]);
                set.add(preSum[i + 1] - lower);
                set.add(preSum[i + 1] - upper);
            }
            long[] arr = new long[set.size()];
            int index = 0;
            for (Long integer : set) {
                arr[index++] = integer;
            }
            Arrays.sort(arr);
            // 离散化后的值到下标的映射
            Map<Long, Integer> map = new HashMap<>();
            for (int i = 0; i < arr.length; i++) {
                map.put(arr[i], i);
            }
            /**
             * tree[i]:表示区间[i - lowbit(i) + 1 ,i]内的元素出现个数
             */
            int[] tree = new int[arr.length + 1];
            int res = 0;
            for (long sum : preSum) {
                /**
                 * bit的下标从1开始，而map中映射的下标从0开始，需要+1
                 */
                int left = map.get(sum - upper) + 1;
                int right = map.get(sum - lower) + 1;
                // 通过计算出小于右边界的前缀和总个数 - 小于左边界的前缀和总个数 得到目标前缀和的个数
                res += query(tree, right) - query(tree, left - 1);
                update(tree, map.get(sum) + 1);
            }
            return res;
        }

        private int lowbit(int x) {
            return x & -x;
        }

        private void update(int[] tree, int index) {
            while (index < tree.length) {
                tree[index]++;
                index += lowbit(index);
            }
        }

        private int query(int[] tree, int index) {
            int res = 0;
            while (index > 0) {
                res += tree[index];
                index -= lowbit(index);
            }
            return res;
        }

    }

    /**
     * 归并排序：
     * 先统计好前缀和之后，我们希望的只是前缀和排序前下标（i>j），prefix[i] - prefix[j]在目标区间的值。
     * 每次递归返回当前区间内的所有符合条件的数量。
     * 而合并两个子区间时，需要考虑跨区间的符合条件的区间数量，由于右区间的原始下标一定大于左区间，因此只需要考虑值即可。
     * 左区间L，右区间R，P为前缀和数组
     * - L 和 R 都已升序排列。对右半的每个 j，本质是找左半落在 [Pj - upper, Pj - lower] 范围内的 i。
     * - 定义两个指针，都只在 L 上移动：
     * - i1 = 第一个满足 L[i] ≥ P[j] - upper 的 i    (有效范围的左边界)
     * - i2 = 第一个满足 L[i] > P[j] - lower 的 i    (有效范围的右边界的下一个)
     * - 对当前 j，有效个数 = i2 - i1
     * - 左半升序 L:   [ ... | i1 |  有效区域  | i2-1 | ... ]
     * -                         └── i2 - i1 个 ──┘
     * - 随 j 右移，Pj 增大：
     * - - P[j] - upper ↑ → i1 只右移（或不动）
     * - - P[j] - lower ↑ → i2 只右移（或不动）
     * - 双指针单调，总 O(n)。
     */
    class solution3 {
        public int countRangeSum(int[] nums, int lower, int upper) {
            // 可以通过前缀和相减得到连续区间和，第一个元素为0，方便计算
            long[] preSum = new long[nums.length + 1];
            for (int i = 0; i < nums.length; i++) {
                preSum[i + 1] = preSum[i] + nums[i];
            }
            return mergeSort(preSum, 0, preSum.length - 1, lower, upper);
        }

        private int mergeSort(long[] preSum, int left, int right, int lower, int upper) {
            // 单前缀和，直接返回（前缀和第一个元素为0，合并时可以计算到从0开始的区间）
            if (right <= left) {
                return 0;
            }
            int mid = left + (right - left) / 2;
            // 先计算两个子区间的符合条件的区间数
            int count = mergeSort(preSum, left, mid, lower, upper) + mergeSort(preSum, mid + 1, right, lower, upper);
            // 再累加跨区间的区间数量
            int i1 = left, i2 = left;
            // 固定右区间，计算符合要求的左区间数量
            for (int j = mid + 1; j <= right; j++) {
                while (i1 <= mid && preSum[i1] < preSum[j] - upper) {
                    i1++;
                }
                while (i2 <= mid && preSum[i2] <= preSum[j] - lower) {
                    i2++;
                }
                count += i2 - i1;
            }
            // 合并
            long[] temp = new long[right - left + 1];
            int i = left, j = mid + 1, k = 0;
            while (i <= mid && j <= right) {
                if (preSum[i] < preSum[j]) {
                    temp[k++] = preSum[i++];
                } else {
                    temp[k++] = preSum[j++];
                }
            }
            while (i <= mid) {
                temp[k++] = preSum[i++];
            }
            while (j <= right) {
                temp[k++] = preSum[j++];
            }
            System.arraycopy(temp, 0, preSum, left, right - left + 1);
            return count;
        }

    }
}
