package leetCode.moderately;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gj
 * @description: 3453. 分割正方形 I
 */
public class SeparateSquares {
    public static void main(String[] args) {
        int[][] squares = {{0, 0, 1}, {2, 2, 1}};
        System.out.println(new SeparateSquares().separateSquares(squares));
    }

    /**
     * - 二分查找
     * - 问题抽象：
     * - 给定若干正方形，每个正方形由左下角坐标 (x, y) 和边长 l 描述。
     * - 目标是找到一条水平线 y，使得所有正方形在该线以下的面积等于总面积的一半，
     * - 并且要求 y 尽可能小。
     * -
     * - 思路演变：
     * - 1. 最初想法：将左下角坐标 + 边长转换成 y 坐标，并累计面积
     * -    - 对每个正方形，计算 y 坐标方向上的面积贡献：
     * -        y <= b        ：面积为 0
     * -        b < y < b + l ：面积为 l * (y - b)
     * -        y >= b + l    ：面积为 l * l
     * -    - 这样得到一个递增的面积函数 F(y)，F(y) 单调递增
     * - 2. 寻找中间值
     * -    - 目标是 F(y) = 总面积 / 2
     * -    - 直觉上可以用二分查找，但考虑到面积是连续的，坐标可能很大
     * - 3. 前缀/后缀面积优化不可行
     * -    - 尝试用前缀或后缀面积数组提前计算
     * -    - 因为每次二分 mid 的 y 可能截断正方形中间，无法直接用整数前缀求得截断面积
     * -    - 所以前缀/后缀优化无法直接应用
     * - 4. 排序优化也无意义
     * -    - 可以考虑将正方形按 y 排序，提前中止累加面积
     * -    - 但排序耗时 O(n log n)，二分每次仍需遍历数组，整体复杂度没有明显降低
     * - 5. 二分查找循环条件改造
     * -    - 初始循环思路：mid 对应的面积 = target 时直接返回 mid → 不对
     * -    - 正确做法：
     * -        - 左边界 l 保证 F(l) < target
     * -        - 右边界 r 保证 F(r) >= target
     * -        - 二分收敛条件 r - l < eps
     * -        - 收敛后返回 r，保证最小 y
     * - 6. 浮点精度问题
     * -    - 浮点累加和大坐标/大边长容易累积误差
     * -    - 修正方法：
     * -        1. 总面积累加使用 double 避免 int 溢出
     * -        2. 切割面积使用 len * Math.min(y - y1, len) 避免超出正方形面积
     * -        3. 调整二分精度 eps = 1e-5，保证收敛稳定
     * - 复杂度分析
     * -    - 二分次数 log(max_y / eps)
     * -    - 每次二分 O(n) 遍历所有正方形
     * -    - 总复杂度 O(n * log(max_y / eps))
     * - 关键实现点
     * -    - double 累加总面积，避免溢出
     * -    - getArea 使用 Math.min 防止截断面积超过边长
     * -    - 二分收敛条件使用 eps
     * -    - 返回 r 保证最小 y
     */
    public double separateSquares(int[][] squares) {
        double sum = 0;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int[] square : squares) {
            min = Math.min(min, square[1]);
            max = Math.max(max, square[1] + square[2]);
            sum += (double) square[2] * square[2];
        }

        double l = min, r = max, target = sum / 2.0;

        // 精度收敛条件
        while (r - l > 1e-5) {
            double mid = (l + r) / 2;
            double area = getArea(squares, mid);
            // mid 已经够了，缩小上界
            if (area >= target) {
                r = mid;
            } else {
                // mid 不够，下移下界
                l = mid;
            }
        }
        // 收敛后 r ≈ l，r 就是最小 y
        return r;
    }

    private double getArea(int[][] squares, double y) {
        double sum = 0;
        for (int[] square : squares) {
            double y1 = square[1];
            double len = square[2];
            // 正方形在上方
            if (y <= y1) {
            }
            // 正方形完全在下方
            else if (y >= y1 + len) {
                sum += len * len;
            }
            // 切割部分
            else {
                // 修正：使用 Math.min 避免浮点累积误差
                sum += len * Math.min(y - y1, len);
            }
        }
        return sum;
    }


    /**
     * 使用扫描线优化：{@link leetCode.difficult.SeparateSquares.Solution1}
     * 由于其可以重复计算重叠面积，那么就简单多了吧。
     * 计算宽度时，不需要使用集合记录x区间了，直接一个int记录即可，正方形进入则加其边长，离开则减其边长。
     * 这样O(n)时间复杂度计算出总面积以及每个事件的Y前缀和面积以及每个区间宽度。
     * 然后由于每个事件的Y前缀和是非递减的，可以二分查找取到中间区间，然后O(1)计算最终y，但是需要排序，因此复杂度应该是nLog(n)
     */
    public static class Solution {

        static class Event {
            int y;
            int delta;
            Event(int y, int delta) {
                this.y = y;
                this.delta = delta;
            }
        }

        public double separateSquares(int[][] squares) {
            int n = squares.length;
            // 1. 构造 y 事件
            List<Event> events = new ArrayList<>();
            for (int[] s : squares) {
                int y = s[1];
                int len = s[2];
                // 正方形进入和离开
                events.add(new Event(y, +len));
                events.add(new Event(y + len, -len));
            }
            // 2. 按 y 排序
            events.sort((a, b) -> Integer.compare(a.y, b.y));

            int m = events.size();
            double[] prefixArea = new double[m];
            int[] ys = new int[m];
            double curWidth = 0;
            double areaSum = 0;
            // 3. 扫描线计算前缀面积
            for (int i = 0; i < m; i++) {
                Event e = events.get(i);
                ys[i] = e.y;
                if (i > 0) {
                    double dy = ys[i] - ys[i - 1];
                    if (dy > 0 && curWidth > 0) {
                        areaSum += curWidth * dy;
                    }
                }
                prefixArea[i] = areaSum;
                curWidth += e.delta;
            }
            double half = areaSum / 2.0;
            // 4. 二分查找所在区间
            int l = 0, r = m - 1;
            while (l < r) {
                int mid = (l + r) >>> 1;
                if (prefixArea[mid] < half) {
                    l = mid + 1;
                } else {
                    r = mid;
                }
            }
            // 5. 在区间内 O(1) 回推 y
            // prefixArea[l - 1] < half <= prefixArea[l]
            if (l == 0) {
                return ys[0];
            }
            double prevArea = prefixArea[l - 1];
            double remain = half - prevArea;
            // 重新计算该区间的 width（事件处理顺序与扫描线一致）
            double width = 0;
            for (int i = 0; i < l; i++) {
                width += events.get(i).delta;
            }
            return ys[l - 1] + remain / width;
        }
    }
}
