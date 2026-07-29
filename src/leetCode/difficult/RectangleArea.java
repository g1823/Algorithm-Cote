package leetCode.difficult;

import java.util.*;

/**
 * @description: 850. 矩形面积 II
 */
public class RectangleArea {
    /**
     * 扫描线 + 线段树
     * 本题目垂直x轴扫描也可以，垂直于y轴扫描也可以，二者可以替换，这里采用垂直于x轴的扫描线
     */
    class solution{

        public int rectangleArea(int[][] rectangles) {
            // 统计x轴和y轴出现的坐标：将y轴离散化方便形成线段树，将x轴形成事件（y区间进入和离开事件）
            Set<Integer> ySet = new HashSet<>();
            List<Event> events = new ArrayList<>();
            for (int[] rectangle : rectangles) {
                int x1 = rectangle[0];
                int y1 = rectangle[1];
                int x2 = rectangle[2];
                int y2 = rectangle[3];
                ySet.add(y1);
                ySet.add(y2);
                events.add(new Event(x1, y1, y2, true));
                events.add(new Event(x2, y1, y2, false));
            }
            // 排序x事件
            events.sort(Comparator.comparingInt(a -> a.x));

            long res = 0;
            long mod = 1000000007;
            SegmentTree segmentTree = new SegmentTree(ySet);
            // 每次在进入新事件前计算当前区间的面积贡献
            for (int i = 0; i < events.size(); ) {
                Event event = events.get(i);
                int x = event.x;
                int preX = i == 0 ? 0 : events.get(i - 1).x;
                int width = i == 0 ? 0 : x - preX;
                res = (res + (long) width * segmentTree.nodes[1]) % mod;
                // 将当前x坐标所有事件全部处理完成
                while (i < events.size() && events.get(i).x == x) {
                    Event cur = events.get(i);
                    segmentTree.update(cur.y1, cur.y2, cur.isEnter);
                    i++;
                }
            }

            return (int) res;
        }

        class Event {
            int x;
            int y1;
            int y2;
            boolean isEnter;

            public Event(int x, int y1, int y2, boolean isEnter) {
                this.x = x;
                this.y1 = y1;
                this.y2 = y2;
                this.isEnter = isEnter;
            }
        }

        class SegmentTree {
            // 节点覆盖的有效 y 轴长度
            int[] nodes;
            // 节点被完整覆盖的次数
            int[] count;
            // n = ys.length - 1（区间个数），节点 [l, r] 覆盖区间 l~r，对应 y 范围 [ys[l], ys[r+1])
            int n;
            int[] ys;
            Map<Integer, Integer> yMap;

            public SegmentTree(Set<Integer> ySet) {
                Map<Integer, Integer> yMap = new HashMap<>();
                int[] ys = new int[ySet.size()];
                int index = 0;
                for (Integer y : ySet) {
                    ys[index++] = y;
                }
                Arrays.sort(ys);
                for (int i = 0; i < ys.length; i++) {
                    yMap.put(ys[i], i);
                }
                // n = ys.length - 1，将树构建在区间(interval)上而非点(point)上
                // 原因：相邻 y 之间形成的区间 [ys[i], ys[i+1]) 才有非零长度，
                //       原始写法 n = ys.length 导致叶子节点长度为 0，且 (mid, mid+1) 间区间被遗漏
                this.n = ys.length - 1;
                this.ys = ys;
                this.yMap = yMap;
                this.nodes = new int[n * 4];
                this.count = new int[n * 4];
            }

            public void update(int l, int r, boolean isAdd) {
                int lIndex = yMap.getOrDefault(l, -1);
                int rIndex = yMap.getOrDefault(r, n + 1);
                if (lIndex > rIndex || lIndex < 0 || rIndex > n) {
                    return;
                }
                // 改动：传入 rIndex - 1（将 y1/y2 索引转为区间索引）
                // 原因：y坐标索引 [lIndex, rIndex) 对应区间索引 [lIndex, rIndex-1]，
                //       原始写法将 rIndex 作为闭区间传入，在区间树中多覆盖了一个区间
                update(1, 0, n - 1, lIndex, rIndex - 1, isAdd);
            }

            private void update(int node, int left, int right, int l, int r, boolean isAdd) {
                if (left > r || right < l) {
                    return;
                }
                if (left >= l && right <= r) {
                    if (isAdd) {
                        count[node]++;
                        // 改动：用 ys[right + 1] - ys[left] 代替 ys[right] - ys[left]
                        // 原因：节点 [left, right] 覆盖区间 left~right，对应 y 范围 [ys[left], ys[right+1])
                        nodes[node] = ys[right + 1] - ys[left];
                    } else {
                        if (count[node] > 1) {
                            count[node]--;
                        } else if (count[node] == 1) {
                            count[node] = 0;
                            // 叶子节点无子节点，直接置0；否则从子节点聚和
                            if (left == right) {
                                nodes[node] = 0;
                            } else {
                                nodes[node] = nodes[node * 2] + nodes[node * 2 + 1];
                            }
                        } else {
                            if (left == right) {
                                nodes[node] = 0;
                            } else {
                                int mid = (left + right) / 2;
                                update(node * 2, left, mid, l, r, isAdd);
                                update(node * 2 + 1, mid + 1, right, l, r, isAdd);
                                nodes[node] = nodes[node * 2] + nodes[node * 2 + 1];
                            }
                        }
                    }
                    return;
                }
                int mid = (left + right) / 2;
                update(node * 2, left, mid, l, r, isAdd);
                update(node * 2 + 1, mid + 1, right, l, r, isAdd);
                if (count[node] == 0) {
                    nodes[node] = nodes[node * 2] + nodes[node * 2 + 1];
                } else {
                    // 改动：用 ys[right + 1] - ys[left] 代替 ys[right] - ys[left]
                    nodes[node] = ys[right + 1] - ys[left];
                }
            }
        }
    }

    class solution2 {

        public int rectangleArea(int[][] rectangles) {
            Set<Integer> ySet = new HashSet<>();
            List<int[]> events = new ArrayList<>();
            for (int[] r : rectangles) {
                ySet.add(r[1]);
                ySet.add(r[3]);
                events.add(new int[]{r[0], r[1], r[3], 1});
                events.add(new int[]{r[2], r[1], r[3], -1});
            }
            events.sort(Comparator.comparingInt(a -> a[0]));

            int[] ys = ySet.stream().sorted().mapToInt(Integer::intValue).toArray();
            SegTree seg = new SegTree(ys);

            long ans = 0;
            int prevX = events.get(0)[0];
            int MOD = 1_000_000_007;

            for (int[] e : events) {
                int x = e[0], y1 = e[1], y2 = e[2], type = e[3];
                ans = (ans + (long) (x - prevX) * seg.len[1]) % MOD;
                seg.update(y1, y2, type);
                prevX = x;
            }
            return (int) ans;
        }

        class SegTree {
            int[] cover;
            int[] len;
            int[] ys;
            int n;

            SegTree(int[] ys) {
                this.ys = ys;
                this.n = ys.length - 1;
                cover = new int[n * 4];
                len = new int[n * 4];
            }

            void update(int y1, int y2, int val) {
                int l = Arrays.binarySearch(ys, y1);
                int r = Arrays.binarySearch(ys, y2);
                if (l < 0 || r < 0 || l >= r) return;
                update(1, 0, n - 1, l, r - 1, val);
            }

            private void update(int node, int l, int r, int ql, int qr, int val) {
                if (ql > r || qr < l) return;
                if (ql <= l && r <= qr) {
                    cover[node] += val;
                } else {
                    int mid = (l + r) / 2;
                    update(node * 2, l, mid, ql, qr, val);
                    update(node * 2 + 1, mid + 1, r, ql, qr, val);
                }
                if (cover[node] > 0) {
                    len[node] = ys[r + 1] - ys[l];
                } else if (l == r) {
                    len[node] = 0;
                } else {
                    len[node] = len[node * 2] + len[node * 2 + 1];
                }
            }
        }
    }
}
