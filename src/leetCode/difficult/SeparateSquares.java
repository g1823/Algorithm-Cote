package leetCode.difficult;

import java.util.*;

/**
 * @author: gj
 * @description: 3454. 分割正方形 II TODO
 */
public class SeparateSquares {
    public static void main(String[] args) {
        int[][] squares = {{3, 16, 5}, {21, 26, 3}, {21, 24, 3}, {0, 1, 4}};
        System.out.println(new Solution1().separateSquares(squares));
    }

    public double separateSquares(int[][] squares) {

        return 0;
    }

    /**
     * 扫描线
     * - 一、问题对比与整体可行性分析
     * - 本题是 3453. 分割正方形 I 的进阶版本。
     * - 在上一题中可以发现：
     * - 定义
     * -     F(y) = y 轴以下的总面积
     * -     F(y) 随 y 单调不减，因此可以通过二分或精确计算找到使
     * -     F(y) = totalArea / 2 的 y
     * - 在本题中，虽然 多个正方形的重叠面积只能计算一次，但 F(y) 的单调性依然成立，因此问题的关键变为：
     * -     如何 精确、高效地计算 F(y)（去重后的面积）
     * -
     * - 二、最直接的暴力思路及其问题
     * - 最初可以尝试直接暴力计算 F(y)：
     * -     对正方形按 y 排序
     * -     计算某个 y 时，对每一个正方形：
     * -         向前遍历所有正方形
     * -         若 当前 y < 之前正方形的 y + 边长，说明存在重叠
     * -         将当前正方形的重叠部分剔除
     * - 这样在计算一次 F(y) 时，就已经需要 O(n²)，
     * - 而如果再对 y 做二分或枚举，整体复杂度会退化为 O(n³)，不可行。
     * -
     * - 三、尝试按 y 轴“单位 1”离散的思路及其问题
     * - 注意到题目中：
     * -     所有坐标和边长均为 整数
     * - 于是可以尝试：
     * -     按 y 轴方向，以单位 1 进行离散
     * -     对每一个 y 单位区间：
     * -         收集该 y 层中所有正方形贡献的 x 区间
     * -         合并 x 区间，得到宽度
     * -         面积 = 1 × 合并后的 x 区间宽度
     * - 但该方法的问题是：
     * -     y 的取值范围可能是 [0, Integer.MAX_VALUE)
     * -     即使每次只算 1 的高度，也会导致 离散规模极其庞大
     * - 因此，按单位 1 离散 y 轴是不可行的。
     * -
     * - 四、关键转折：从“单位离散”到“事件离散”
     * - 进一步观察正方形的性质：
     * -     对于一个正方形，左下角为 (x, y)，边长为 l
     * -     在 y ∈ [y, y + l) 的整个区间内：
     * -         该正方形对 x 轴的贡献区间始终是 [x, x + l),不会发生变化
     * - 这意味着：
     * -     x 区间的变化只可能发生在正方形“进入”和“离开”的 y 坐标处
     * - 因此，可以将：
     * -     原本按单位 1 离散的 y 轴
     * -     转换为 按正方形进入 / 离开的事件进行离散
     * - 具体做法：
     * -     对每个正方形 (x, y, l)：
     * -         在 y 处生成一个 进入事件
     * -         在 y + l 处生成一个 离开事件
     * -     总事件数为 2n
     * -
     * - 五、扫描线（按 y 轴）的核心过程
     * - 1.按 y 坐标对所有事件排序
     * - 2.维护一个“当前有效的 x 区间集合”，表示：
     * -     当前 y 区间内，所有仍然覆盖该 y 的正方形在 x 轴上的贡献
     * - 3.从前一个事件到当前事件：
     * -     高度 Δy = curY - prevY
     * -     若 Δy > 0：
     * -         计算当前 x 区间集合的并集宽度 W
     * -         面积贡献为 Δy × W
     * - 4.处理当前事件：
     * -     若是进入事件：将该正方形的 x 区间加入集合
     * -     若是离开事件：从集合中移除该正方形的 x 区间
     * - 分析：
     * -     在两个相邻 y 事件之间，没有正方形进入或离开，因此 x 区间集合在该区间内是 完全不变的
     * -
     * - 六、x 区间合集的计算方式（当前版本）
     * - 在当前实现中，x 区间合集采用暴力方式计算：
     * -     将当前所有有效的 x 区间放入一个列表
     * -     将区间表示为 [l, r)
     * -     按 l 排序
     * -     顺序合并：
     * -         若当前区间与前缀区间重叠，仅补充新增部分
     * -         否则直接累加整个区间长度
     * - 这种方式下：
     * -     每次计算宽度是 O(k log k)，k 为当前区间数
     * -     在最坏情况下，整体复杂度为 O(n²)
     * -
     * - 七、利用前缀面积直接定位答案区间
     * - 在扫描 y 事件、计算总面积的过程中，可以同步记录：
     * -     areaMap[i]：从最小 y 到第 i 个事件为止的前缀面积
     * -     widthMap[i]：区间 [event[i].y, event[i+1].y) 内的 x 区间并集宽度
     * - 由于 F(y) 单调：
     * -     总面积的一半 totalArea / 2，一定落在某一个事件区间 [event[i], event[i+1]) 内
     * - 找到该区间后（循环或二分）：
     * -     新目标面积：remain = totalArea / 2 - areaMap[i]
     * -     当前区间高度：h = remain / widthMap[i]
     * - 最终答案：y = event[i].y + h，无需再次扫描或二分。
     * -
     * - 八、复杂度总结
     * - 当前实现：
     * -     时间复杂度：O(n²)（瓶颈在 x 区间并集的重复计算）
     * -     空间复杂度：O(n)
     * - 若进一步优化 x 区间维护（如差分 / TreeMap / 线段树）：可将时间复杂度降为 O(n log n)
     */
    public static class Solution1 {
        /**
         * 区间线段类（x轴）
         */
        static class Interval {
            // l: 左端点， r: 右端点
            public int l, r;

            Interval(int l, int r) {
                this.l = l;
                this.r = r;
            }

            @Override
            public String toString() {
                return "l=" + l + ", r=" + r;
            }
        }

        /**
         * 事件类
         */
        static class Event {
            // 事件发生的坐标
            public int y;
            // 事件区间，即当前正方形的x轴线段区间
            public Interval interval;
            // true：正方形进入，false：正方形离开
            public boolean isStart;

            Event(int y, Interval interval, boolean isStart) {
                this.y = y;
                this.interval = interval;
                this.isStart = isStart;
            }

            @Override
            public String toString() {
                return "y=" + y + ", interval={" + interval + "}, isStart=" + isStart;
            }
        }

        public double separateSquares(int[][] squares) {
            // 生成事件
            List<Event> events = new ArrayList<>();
            for (int[] square : squares) {
                int x1 = square[0];
                int y1 = square[1];
                int len = square[2];
                events.add(new Event(y1, new Interval(x1, x1 + len), true));
                events.add(new Event(y1 + len, new Interval(x1, x1 + len), false));
            }
            // 事件按y轴排序
            events.sort(Comparator.comparingInt(a -> a.y));
            // key为事件下标，value为从0到当前事件的前缀面积和
            Map<Integer, Double> areaMap = new HashMap<>();
            // key为事件下标，value为当前事件的宽度
            Map<Integer, Double> widthMap = new HashMap<>();
            double totalArea = getTotalArea(events, areaMap, widthMap);
            // 现在由于面积需要从0开始累加，因此无法通过像分割正方形 I那样，将l和r进行不断收缩，直到r - l < 1e-5
            // 那么可以在计算总面积时，记录每个事件的前缀和面积，然后遍历数据得到最接近总面积一半的区间然后再收缩区间直到得到答案。
            int i = 0;
            double target = totalArea / 2;
            while (i < events.size() - 1) {
                double area = areaMap.get(i);
                double nextArea = areaMap.get(i + 1);
                if (Math.abs(area - target) < 1e-5) {
                    return events.get(i).y;
                }
                if (target >= area && target < nextArea) {
                    break;
                }
                i++;
            }
            // 新的目标面积
            target = target - areaMap.get(i);
            double width = widthMap.get(i);
            double height = (target) / width;
            return height + events.get(i).y;
        }

        private double getTotalArea(List<Event> events, Map<Integer, Double> areaMap, Map<Integer, Double> widthMap) {
            List<Interval> curIntervals = new ArrayList<>();
            double totalArea = 0;
            // 初始化
            curIntervals.add(events.get(0).interval);
            areaMap.put(0, 0d);
            for (int i = 1; i < events.size(); i++) {
                Event event = events.get(i);
                int height = event.y - events.get(i - 1).y;
                // 只在 height > 0 时计算 width，并且记在 i-1 上
                if (height > 0) {
                    double width = getWidth(curIntervals);
                    // width 对应区间 [events[i-1].y, events[i].y)
                    widthMap.put(i - 1, width);
                    totalArea += width * height;
                }
                // 更新区间集合（事件驱动）
                if (event.isStart) {
                    curIntervals.add(event.interval);
                } else {
                    Iterator<Interval> iterator = curIntervals.iterator();
                    while (iterator.hasNext()) {
                        Interval interval = iterator.next();
                        if (interval.l == event.interval.l &&
                                interval.r == event.interval.r) {
                            iterator.remove();
                            break;
                        }
                    }
                }
                areaMap.put(i, totalArea);
            }
            return totalArea;
        }

        private double getWidth(List<Interval> curIntervals) {
            if (curIntervals == null || curIntervals.isEmpty()) {
                return 0;
            }
            curIntervals.sort(Comparator.comparingInt(a -> a.l));
            double width = curIntervals.get(0).r - curIntervals.get(0).l;
            int r = curIntervals.get(0).r;
            for (int i = 1; i < curIntervals.size(); i++) {
                Interval interval = curIntervals.get(i);
                if (interval.l <= r) {
                    width += Math.max(interval.r - r, 0);
                    r = Math.max(r, interval.r);
                } else {
                    width += interval.r - interval.l;
                    r = interval.r;
                }
            }
            return width;
        }
    }


    public static class Solution2 {}
}
