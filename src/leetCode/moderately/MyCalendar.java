package leetCode.moderately;

/**
 * @description: 729. 我的日程安排表 I (线段树)
 */
public class MyCalendar {
    SegmentTree segmentTree;

    public MyCalendar() {
        segmentTree = new SegmentTree(1000000000);
    }

    public boolean book(int startTime, int endTime) {
        // 查询左闭右开区间 [startTime, endTime) 是否重叠，对应点区间 [startTime, endTime - 1]
        if (segmentTree.query(startTime, endTime - 1)) {
            return false;
        }
        segmentTree.update(startTime, endTime - 1, true);
        return true;
    }

    static class SegmentTree {
        Node root;

        class Node {
            int l, r;
            boolean booked, lazy;
            Node left, right;

            public Node(int l, int r, boolean booked, Node left, Node right) {
                this.l = l;
                this.r = r;
                this.booked = booked;
                this.left = left;
                this.right = right;
                lazy = false;
            }
        }

        public SegmentTree(int n) {
            this.root = new Node(0, n - 1, false, null, null);
        }

        public boolean query(int l, int r) {
            if (l > r || r < 0 || l > root.r) {
                return false;
            }
            return query(root, l, r);
        }

        private boolean query(Node node, int l, int r) {
            // 无交集
            if (l > node.r || r < node.l) {
                return false;
            }
            // 全包含
            if (l <= node.l && node.r <= r) {
                return node.booked;
            }
            // 整个节点已预订，直接返回 true
            if (node.lazy) {
                return true;
            }
            // 没有子节点且无 lazy 标记，说明整个节点空闲
            if (node.left == null) {
                return false;
            }
            // 部分包含
            pushDown(node);
            int mid = node.l + (node.r - node.l) / 2;
            if (r <= mid) {
                return query(node.left, l, r);
            } else if (l > mid) {
                return query(node.right, l, r);
            } else {
                return query(node.left, l, mid) || query(node.right, mid + 1, r);
            }
        }

        public void update(int l, int r, boolean booked) {
            if (l > r || r < 0 || l > root.r) {
                return;
            }
            update(root, l, r, booked);
        }

        private void update(Node node, int l, int r, boolean booked) {
            // 查询区间和当前节点没有交集
            if (l > node.r || r < node.l) {
                return;
            }
            // 查询区间全包含当前节点区间
            if (l <= node.l && node.r <= r) {
                node.booked = booked;
                node.lazy = booked;
                return;
            }
            // 部分覆盖
            if (node.left == null) {
                node.left = new Node(node.l, (node.l + node.r) / 2, false, null, null);
                node.right = new Node((node.l + node.r) / 2 + 1, node.r, false, null, null);
            }
            pushDown(node);
            int mid = node.l + (node.r - node.l) / 2;
            if (r <= mid) {
                update(node.left, l, r, booked);
            } else if (l > mid) {
                update(node.right, l, r, booked);
            } else {
                update(node.left, l, mid, booked);
                update(node.right, mid + 1, r, booked);
            }
            node.booked = node.left.booked || node.right.booked;
        }

        private void pushDown(Node node) {
            if (node.lazy) {
                node.left.booked = node.lazy;
                node.right.booked = node.lazy;
                node.left.lazy = node.lazy;
                node.right.lazy = node.lazy;
                node.lazy = false;
            }
        }
    }
}
