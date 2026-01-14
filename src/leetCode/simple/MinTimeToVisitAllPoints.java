package leetCode.simple;

/**
 * @author: gj
 * @description: 1266. 访问所有点的最小时间
 */
public class MinTimeToVisitAllPoints {

    /**
     * 因为要求按顺序访问，那么只能贪心着计算，即：每次只需要考虑当前点到下一个点最短时间即可。
     * 如何计算两个点之间的最短时间：
     * - 当两个点x相同或y相同时，即在同一条线上，那么走直线最快，即(x2-x1)或(y2-y1)
     * - 如果不在一条线上，那么应该尽可能走斜线，那么就尝试先走直线，到达斜率等于1，且能经过下一个点的直线对应的当前点横坐标上，然后走斜线
     * 换句话说：
     * 因为可以对角移动（算 1 单位时间），所以从 (x1, y1) 到 (x2, y2) 的最短时间，不是曼哈顿距离，而是 切比雪夫距离（Chebyshev distance） 在二维连续格点移动下的特殊形式。
     * - 水平差 dx = abs(x2 - x1)
     * - 垂直差 dy = abs(y2 - y1)
     * 最少时间 = max(dx, dy)
     * 因为：
     * - 对角移动能同时减少 x 差和 y 差，所以最小步数等于 max(dx, dy)。
     * 假设 dx > dy：
     * - 我们可以先走 dy 步对角（同时减少 x 和 y），这样就消除了 y 方向的差距，x 方向还剩下 dx - dy。
     * - 然后走 dx - dy 步水平。
     * - 总步数 = dy + (dx - dy) = dx = max(dx, dy)。
     * - 如果 dy > dx 同理，总步数 = dy = max(dx, dy)。
     * - 如果 dx == dy，那就是走 dx 步对角。
     * 所以结论就是：两点间的最短时间 = max(|x2 - x1|, |y2 - y1|)。
     */
    public int minTimeToVisitAllPoints(int[][] points) {
        int result = 0;
        for (int i = 1; i < points.length; i++) {
            int x = Math.abs(points[i][0] - points[i - 1][0]);
            int y = Math.abs(points[i][1] - points[i - 1][1]);
            result += Math.max(x, y);
        }
        return result;
    }
}
