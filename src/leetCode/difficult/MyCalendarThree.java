package leetCode.difficult;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;

/**
 * @description: 732. 我的日程安排表 III
 */
public class MyCalendarThree {
    /**
     * 差分 + TreeMap 稀疏存储
     */
    class solution1 {

        TreeMap<Integer, Integer> treeMap;

        public solution1() {
            treeMap = new TreeMap<>();
        }

        public int book(int startTime, int endTime) {
            // 记录差分值
            treeMap.put(startTime, treeMap.getOrDefault(startTime, 0) + 1);
            treeMap.put(endTime, treeMap.getOrDefault(endTime, 0) - 1);
            // 计算(下标0为当前值，1为最大值)
            final int[] num = {0, 0};
            treeMap.forEach(new BiConsumer<Integer, Integer>() {
                @Override
                public void accept(Integer integer, Integer integer2) {
                    num[0] += integer2;
                    num[1] = Math.max(num[0], num[1]);
                }
            });
            return num[1];
        }
    }

    /**
     * 差分 + 线段树
     */
    class solution2 {
        SegmentTree seg;

        public solution2() {
            seg = new SegmentTree();
        }

        public int book(int startTime, int endTime) {
            return seg.update(startTime, endTime);
        }

        class SegmentTree {
            final int ROOT_L = 0;
            final int ROOT_R = 1_000_000_000;

            class Node {
                /**
                 * l表示当前节点左边界（包含）
                 * r表示当前节点右边界（不包含）
                 * count表示当前区间内出现的最大重叠次数
                 * lazy表示当前区间完整覆盖的重复次数，暂不下传给子节点
                 */
                int l, r, count, lazy;
                Node left, right;

                Node(int l, int r) {
                    this.l = l;
                    this.r = r;
                }
            }

            Node root;

            public SegmentTree() {
                root = new Node(ROOT_L, ROOT_R);
            }

            /**
             * 更新区间 [start, end)，返回全局最大重叠次数
             */
            public int update(int start, int end) {
                update(root, start, end);
                return root.count;
            }

            /**
             * 递归更新，区间为左闭右开 [node.l, node.r)
             * 更新范围为左闭右开 [start, end)
             */
            private void update(Node node, int start, int end) {
                // 无交集判断：半开区间 [l,r) 与 [start,end) 不重叠 ⇔ start>=r || end<=l
                // ⚠ 原代码用 start>r || end<l，当边界重合时（如 end==node.l）会漏判，导致无限递归创建子节点
                if (start >= node.r || end <= node.l) {
                    return;
                }
                // 完全覆盖
                if (start <= node.l && node.r <= end) {
                    node.lazy++;
                    updateCount(node);
                    return;
                }
                // 部分覆盖，先确保子节点存在并下发 lazy
                pushDown(node);
                update(node.left, start, end);
                update(node.right, start, end);
                updateCount(node);
            }

            private void updateCount(Node node) {
                int childMax = 0;
                if (node.left != null) {
                    childMax = Math.max(childMax, node.left.count);
                }
                if (node.right != null) {
                    childMax = Math.max(childMax, node.right.count);
                }
                node.count = childMax + node.lazy;
            }

            private void pushDown(Node node) {
                int mid = node.l + (node.r - node.l) / 2;
                if (node.left == null) {
                    node.left = new Node(node.l, mid);
                }
                if (node.right == null) {
                    node.right = new Node(mid, node.r);
                }
                // lazy == 0 时仍要确保子节点已创建，但不下发
                if (node.lazy == 0) return;
                node.left.lazy += node.lazy;
                node.left.count += node.lazy;
                node.right.lazy += node.lazy;
                node.right.count += node.lazy;
                node.lazy = 0;
            }
        }

    }

    /**
     * 线段树标准实现（与 solution2 对比注释）
     */
    class solution3 {

        class Node {
            Node left, right;
            int count;   // 当前区间最大重叠次数
            int lazy;     // 当前区间完整覆盖次数（待下发给子节点）
        }

        final int MAX = 1_000_000_000;
        Node root;

        public solution3() {
            root = new Node();
        }

        public int book(int start, int end) {
            update(root, 0, MAX, start, end);
            return root.count;
        }

        /**
         * 递归更新 [ql, qr) 区间，每个节点 +1
         *
         * @param node 当前节点（保证非 null）
         * @param l    当前节点覆盖的左边界（包含）
         * @param r    当前节点覆盖的右边界（不包含）
         * @param ql   更新区间左边界（包含）
         * @param qr   更新区间右边界（不包含）
         */
        private void update(Node node, int l, int r, int ql, int qr) {

            // ---------- 区别1：边界判断 ----------
            // solution2：start>=node.r || end<=node.l，使用节点存储的边界
            // 这里：ql>=r || qr<=l，使用方法参数传入的边界，不依赖节点存储
            if (ql >= r || qr <= l) {
                return;
            }

            // ---------- 区别2：完全覆盖 ----------
            // solution2：用 updateCount(node) 单独方法更新 count
            // 这里：直接 node.count++ + node.lazy++ 后 return，不拆方法
            if (ql <= l && r <= qr) {
                node.count++;
                node.lazy++;
                return;
            }

            // ---------- 区别3：子节点创建 ----------
            // solution2：在 pushDown(node) 中统一创建子节点 + 下发 lazy
            // 这里：在 pushDown 之前先确保子节点存在，pushDown 只负责下发
            int mid = l + (r - l) / 2;
            if (node.left == null) node.left = new Node();
            if (node.right == null) node.right = new Node();

            // ---------- 区别4：pushDown 时机 ----------
            // solution2：pushDown 内统一处理子节点创建 + lazy 下发
            // 这里：子节点已创建，pushDown 只负责下发 lazy
            pushDown(node);

            // ---------- 区别5：递归后更新 count ----------
            // solution2：用 updateCount(node) 读取子节点 count + 自己的 lazy
            // 这里：直接 Math.max + lazy，无单独方法，更紧凑
            update(node.left, l, mid, ql, qr);
            update(node.right, mid, r, ql, qr);
            node.count = Math.max(node.left.count, node.right.count) + node.lazy;
        }

        /**
         * 下发懒标记：将当前节点的 lazy 加到子节点上
         * <p>
         * 与 solution2 的 pushDown 区别：
         * - solution2：在 pushDown 内创建子节点（若不存在）
         * - 这里：调用 pushDown 前已确保子节点存在，职责更单一
         */
        private void pushDown(Node node) {
            if (node.lazy == 0) return;
            node.left.count += node.lazy;
            node.left.lazy += node.lazy;
            node.right.count += node.lazy;
            node.right.lazy += node.lazy;
            node.lazy = 0;
        }
    }

    /**
     * 官方标答：HashMap 模拟完全二叉树下标（与 solution2 对比注释）
     * 核心思路：
     * 利用完全二叉树的编号规则（root=1, left=2*idx, right=2*idx+1），
     * 但不预先建数组，而是用两个 HashMap 只存被访问到的节点。
     * 兼具"数组下标快速定位"和"动态开点节省空间"的优点。
     */
    class solution4 {

        // tree[idx] = 节点 idx 覆盖区间的最大重叠次数
        // lazy[idx] = 节点 idx 完整覆盖次数（待下发给子节点）
        Map<Integer, Integer> tree;
        Map<Integer, Integer> lazy;

        public solution4() {
            tree = new HashMap<>();
            lazy = new HashMap<>();
        }

        /**
         * 注意点（与 solution2 对比）：
         * <p>
         * 1) 区间为闭区间 [l, r] 而非 solution2 的半开区间 [l, r)
         * - 传入 endTime 后转为闭区间：end - 1
         * - 划分：左 [l, mid]  右 [mid+1, r]
         * <p>
         * 2) 无 Node 类，改用 HashMap<Integer, Integer> 存数据
         * - 节点用整数 idx 索引（完全二叉树编号），不存 l/r
         * - 边界通过递归参数传递（同 solution3）
         * <p>
         * 3) 无独立 pushDown 方法
         * - solution2：pushDown 将 lazy 物理下发给子节点
         * - 这里：直接在父节点 put 时从 lazy Map 读取当前 lazy，
         * 子节点只负责自身区间的计数，不继承父节点的 lazy
         * <p>
         * 4) 递归基的判断条件用 r<start || end<l（闭区间）
         * - solution2 用 start>=r || end<=l（半开区间）
         */
        public int book(int start, int end) {
            update(start, end - 1, 0, 1_000_000_000, 1);
            return tree.getOrDefault(1, 0);
        }

        /**
         * 递归更新闭区间 [l, r]
         *
         * @param idx 当前节点在完全二叉树中的编号（root = 1）
         */
        private void update(int start, int end, int l, int r, int idx) {
            // 无交集：闭区间不重叠 ⇔ r < start || end < l
            if (r < start || end < l) {
                return;
            }
            // 完全覆盖
            if (start <= l && r <= end) {
                tree.put(idx, tree.getOrDefault(idx, 0) + 1);
                lazy.put(idx, lazy.getOrDefault(idx, 0) + 1);
                return;
            }
            // 部分覆盖，递归子节点
            int mid = (l + r) >> 1;
            update(start, end, l, mid, 2 * idx);
            update(start, end, mid + 1, r, 2 * idx + 1);
            // 回溯时合并：当前 lazy（完整覆盖次数）+ 子节点最大值
            tree.put(idx,
                    lazy.getOrDefault(idx, 0)
                            + Math.max(tree.getOrDefault(2 * idx, 0),
                            tree.getOrDefault(2 * idx + 1, 0)));
        }
    }

}
