package leetCode.difficult;

import java.util.*;

/**
 * @description: 699. 掉落的方块
 */
public class FallingSquares {

    /**
     * 线段树：
     * 1、利用完全二叉树性质，使用数组表示线段树
     * 2、将x端点离散化，映射为下标
     */
    static class solution {
        public List<Integer> fallingSquares(int[][] positions) {
            // 1、统计所有端点，因为预先知道所有方块数据，因此可以直接得到每个x区间（端点），从而精确设置线段树的区间
            Set<Integer> set = new HashSet<>();
            for (int[] position : positions) {
                set.add(position[0]);
                set.add(position[0] + position[1]);
            }
            // 对落点排序，得到有序的x端点
            int[] arr = set.stream().mapToInt(Integer::intValue).toArray();
            Arrays.sort(arr);
            // 设置端点和下标映射关系（因为后续使用线段树存储下标，下标更稳定（一定小于等于2*positions.length），且连续）
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < arr.length; i++) {
                map.put(arr[i], i);
            }
            // 初始化线段树
            SegmentTree seg = new SegmentTree(arr.length);
            List<Integer> res = new ArrayList<>();
            for (int[] position : positions) {
                int x = position[0];
                int y = position[1];
                int l = map.get(x);
                int r = map.get(x + y) - 1;
                seg.update(l, r, y);
                res.add(seg.getMax());
            }
            return res;
        }

        static class SegmentTree {
            // 因为真实的X端点直接映射成了连续的0-N下标，利用完全二叉树性质，直接使用数组表示树即可。
            int[] tree;
            // 是否懒加载，-1表示未加载，非-1表示懒加载的值
            int[] lazy;
            // 元素个数
            int n;
            // 当前所有区间的最大值
            int max;

            public SegmentTree(int n) {
                this.n = n;
                this.tree = new int[n * 4];
                this.lazy = new int[n * 4];
                Arrays.fill(lazy, -1);
                this.max = 0;
            }

            /**
             * 获取所有区间内的最大值（高度）
             */
            public int getMax() {
                // 在update时会维护max
                return max;
            }

            /**
             * 查询当前区间最大值
             */
            public int query(int l, int r) {
                if (l > r || r < 0 || l > n - 1) {
                    return 0;
                }
                // 根节点为1
                return query(1, 0, n - 1, l, r);
            }

            private int query(int node, int start, int end, int l, int r) {
                // 不在当前区间内
                if (start > r || end < l) {
                    return 0;
                }
                // 当前区间完全被覆盖在查询区间内
                if (start >= l && end <= r) {
                    return tree[node];
                }
                // 部分覆盖的情况，返回两个子区间中更大的
                pushDown(node);
                int curMax = Math.max(query(node * 2, start, (start + end) / 2, l, r),
                        query(node * 2 + 1, (start + end) / 2 + 1, end, l, r));
                return curMax;
            }

            /**
             * 更新区间[l, r]的值,新放入边长为val的方块
             * l 和 r 时下标索引而非端点
             */
            public void update(int l, int r, int val) {
                if (l > r || r < 0 || l > n - 1) {
                    return;
                }
                // 查询当前区间最大值
                int curMax = query(l, r);
                int newMax = curMax + val;
                max = Math.max(max, newMax);
                // 这里不能累加，应该方块可能并非完全对齐，不会下落，y轴方向两个方块之间可能有空隙，因此需要直接设置为新的最大值
                update(1, 0, n - 1, l, r, newMax);
            }

            private void update(int node, int start, int end, int l, int r, int newMax) {
                // 不在当前区间内
                if (start > r || end < l) {
                    return;
                }
                // 当前区间被包含在待更新区间内
                if (start >= l && end <= r) {
                    tree[node] = newMax;
                    lazy[node] = newMax;
                    return;
                }
                // 待更新区间和当前区间部分重叠,需要把lazy下放
                pushDown(node);
                // 更新左子节点
                update(node * 2, start, (start + end) / 2, l, r, newMax);
                // 更新右子节点
                update(node * 2 + 1, (start + end) / 2 + 1, end, l, r, newMax);
                // 使用子节点更新更新当前节点
                tree[node] = Math.max(tree[node * 2], tree[node * 2 + 1]);
            }

            private void pushDown(int node) {
                if (lazy[node] != -1) {
                    int v = lazy[node];
                    tree[node * 2] = v;
                    lazy[node * 2] = v;
                    tree[node * 2 + 1] = v;
                    lazy[node * 2 + 1] = v;
                    lazy[node] = -1;
                }
            }
        }
    }

}
