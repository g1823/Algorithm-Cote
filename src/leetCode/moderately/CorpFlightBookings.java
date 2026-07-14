package leetCode.moderately;

/**
 * @description: 1109. 航班预订统计
 */
public class CorpFlightBookings {

    /**
     * 差分
     */
    public int[] corpFlightBookings(int[][] bookings, int n) {
        // 航班从1开始，1-n, 数组从0开始，0-n-1
        int[] res = new int[n];
        for (int[] booking : bookings) {
            int start = booking[0], end = booking[1], seats = booking[2];
            // 需要对坐标-1 才是正确的下标
            res[start - 1] += seats;
            // end不减1是因为预定时包含了end
            if (end < n) {
                res[end] -= seats;
            }
        }
        for (int i = 1; i < n; i++) {
            res[i] += res[i - 1];
        }
        return res;
    }

    /**
     * 线段树
     */
    public int[] corpFlightBookings2(int[][] bookings, int n) {
        SegmentTree segmentTree = new SegmentTree(n);
        for (int[] booking : bookings) {
            int start = booking[0], end = booking[1], seats = booking[2];
            // 线段树内部用 0-indexed，节点 i 代表第 i+1 号航班
            // 预定的 [start, end] 是 1-indexed 闭区间，因此两端各减 1 转为 0-indexed 闭区间即可
            segmentTree.update(start - 1, end - 1, seats);
        }
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = segmentTree.query(i, i);
        }
        return res;
    }

    static class SegmentTree {
        int[] tree;
        int[] lazy;
        int n;

        public SegmentTree(int n) {
            this.n = n;
            this.tree = new int[n * 4];
            this.lazy = new int[n * 4];
        }

        public int query(int l, int r) {
            if (l > r || r < 0 || l > n - 1) {
                return 0;
            }
            return query(1, 0, n - 1, l, r);
        }

        private int query(int node, int start, int end, int l, int r) {
            if (start > r || end < l) {
                return 0;
            }
            // 当前区间完全被查询区间包含
            if (start >= l && end <= r) {
                return tree[node];
            }
            // 部分包含
            pushDown(node);
            int left = query(node * 2, start, (start + end) / 2, l, r);
            int right = query(node * 2 + 1, (start + end) / 2 + 1, end, l, r);
            return left + right;
        }

        public void update(int l, int r, int val) {
            if (l > r || r < 0 || l > n - 1) {
                return;
            }
            update(1, 0, n - 1, l, r, val);
        }

        private void update(int node, int start, int end, int l, int r, int val) {
            // 不在当前区间
            if (start > r || end < l) {
                return;
            }
            // 当前区间完全被待更新区间包含
            if (start >= l && end <= r) {
                tree[node] += val;
                lazy[node] += val;
                return;
            }
            // 部分包含
            pushDown(node);
            update(node * 2, start, (start + end) / 2, l, r, val);
            update(node * 2 + 1, (start + end) / 2 + 1, end, l, r, val);
            tree[node] = tree[node * 2] + tree[node * 2 + 1];
        }

        private void pushDown(int node) {
            if (lazy[node] != 0) {
                tree[node * 2] += lazy[node];
                tree[node * 2 + 1] += lazy[node];
                lazy[node * 2] += lazy[node];
                lazy[node * 2 + 1] += lazy[node];
                lazy[node] = 0;
            }
        }
    }
}
