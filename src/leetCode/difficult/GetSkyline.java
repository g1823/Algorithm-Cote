package leetCode.difficult;

import java.util.*;

/**
 * @description: 218. 天际线问题
 */
public class GetSkyline {
    /**
     * 线段树+dfs遍历到(value[i] = -1 作为标记，表示两个子区间值不相等)+dfs结果合并
     */
    class Solution {
        /**
         * 三元组 buildings[i] = [lefti, righti, heighti] 表示：
         * lefti 是第 i 座建筑物左边缘的 x 坐标。
         * righti 是第 i 座建筑物右边缘的 x 坐标。
         * heighti 是第 i 座建筑物的高度。
         */
        public List<List<Integer>> getSkyline(int[][] buildings) {
            // 把原问题离散化
            Set<Integer> set = new HashSet<>();
            for (int[] building : buildings) {
                int left = building[0];
                int right = building[1];
                set.add(left);
                set.add(right);
            }
            int[] points = new int[set.size()];
            int index = 0;
            for (int point : set) {
                points[index++] = point;
            }
            Arrays.sort(points);
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < points.length; i++) {
                map.put(points[i], i);
            }
            SegmentTree segmentTree = new SegmentTree(points.length);
            for (int[] building : buildings) {
                int left = building[0];
                int right = building[1];
                int height = building[2];
                segmentTree.update(map.get(left), map.get(right) - 1, height);
            }
            List<int[]> raw = new ArrayList<>();
            segmentTree.dfs(1, 0, points.length - 1, points, raw);

            List<List<Integer>> res = new ArrayList<>();
            // 合并相等区间
            for (int[] p : raw) {
                if (res.isEmpty() || !res.get(res.size() - 1).get(1).equals(p[1])) {
                    res.add(Arrays.asList(p[0], p[1]));
                }
            }
            return res;
        }


        class SegmentTree {
            int[] value;
            int[] lazy;
            int n;

            public SegmentTree(int n) {
                value = new int[n * 4];
                lazy = new int[n * 4];
                this.n = n;
            }


            public void update(int left, int right, int val) {
                if (left > right || left < 0 || right > n - 1) {
                    return;
                }
                update(1, 0, n - 1, left, right, val);
            }

            private void update(int node, int left, int right, int updateLeft, int updateRight, int val) {
                if (left > right || left > updateRight || right < updateLeft) {
                    return;
                }
                int curVal = value[node];
                // 只有大于当前值才更新
                if (val <= curVal) {
                    return;
                }
                // 全包含（只有节点为统一值时才能直接覆盖，否则子节点可能有更高的值）
                if (left >= updateLeft && right <= updateRight && value[node] != -1) {
                    value[node] = val;
                    lazy[node] = val;
                    return;
                }
                // 部分包含
                pushDown(node);
                int mid = (left + right) / 2;
                update(node * 2, left, mid, updateLeft, updateRight, val);
                update(node * 2 + 1, mid + 1, right, updateLeft, updateRight, val);
                // 由于部分包含的情况下，左右区间值不一致时，当前区间就无法合并了，不存在意义，因此直接给当前区间赋值-1
                if (value[node * 2] != value[node * 2 + 1]) {
                    value[node] = -1;
                } else {
                    value[node] = value[node * 2];
                }
            }

            private void pushDown(int node) {
                if (lazy[node] != 0) {
                    int left = node * 2;
                    int right = node * 2 + 1;
                    if (left < value.length) {
                        lazy[left] = lazy[node];
                        value[left] = lazy[node];
                    }
                    if (right < value.length) {
                        lazy[right] = lazy[node];
                        value[right] = lazy[node];
                    }
                    lazy[node] = 0;
                }
            }

            public void dfs(int node, int left, int right, int[] points, List<int[]> out) {
                // 不为-1时，表示子区间值一样，自动合并，直接返回即可。
                if (value[node] != -1) {
                    out.add(new int[]{points[left], value[node]});
                    return;
                }
                if (left == right) {
                    out.add(new int[]{points[left], value[node]});
                    return;
                }
                pushDown(node);
                int mid = (left + right) / 2;
                dfs(node * 2, left, mid, points, out);
                dfs(node * 2 + 1, mid + 1, right, points, out);
            }
        }
    }

    /**
     * 扫描线+优先队列
     */
    class Solution1 {

    }
}
