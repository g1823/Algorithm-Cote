package leetCode.moderately;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: gj
 * @description: 57. 插入区间
 */
public class Insert {
    public static void main(String[] args) {
        int[][] intervals = {{1, 3}, {6, 9}};
        int[] newInterval = {2, 5};
        System.out.println(Arrays.deepToString(insert(intervals, newInterval)));
    }

    /**
     * 【思路说明】
     * 给定一组按起始位置升序且不重叠的区间 intervals，以及一个新区间 newInterval，
     * 需要将新区间插入 intervals 中，并合并所有可能重叠的区间。
     * 整体分为三步：
     * 1️⃣ 先加入所有在 newInterval 左侧且完全不重叠的区间（即 interval[i][1] < newInterval[0]）
     * 2️⃣ 合并所有与 newInterval 重叠的区间（即 interval[i][0] <= newInterval[1]）
     * -    在合并过程中，不断更新合并区间的左右边界：
     * -      left = min(left, interval[i][0])
     * -      right = max(right, interval[i][1])
     * 3️⃣ 再加入所有在 newInterval 右侧的区间（即 interval[i][0] > newInterval[1]）
     * 最终返回合并后的区间列表。
     * 时间复杂度：O(n)，只需遍历一次
     * 空间复杂度：O(n)，用于存储结果列表
     */
    public static int[][] insert(int[][] intervals, int[] newInterval) {
        // 若原区间列表为空，直接返回 newInterval
        if (intervals.length == 0) {
            return new int[][]{newInterval};
        }
        int left = newInterval[0], right = newInterval[1];
        int n = intervals.length, i = 0;
        List<int[]> res = new ArrayList<>();
        // 1️⃣ 处理左侧区间：完全位于 newInterval 左边的区间，直接加入结果
        while (i < n && intervals[i][1] < left) {
            res.add(intervals[i]);
            i++;
        }
        // 2️⃣ 合并重叠区间
        // 若当前区间的起点 <= newInterval 的终点，说明存在重叠
        while (i < n && intervals[i][0] <= right) {
            // 更新合并区间的左右边界
            left = Math.min(left, intervals[i][0]);
            right = Math.max(right, intervals[i][1]);
            i++;
        }
        // 将合并后的区间加入结果
        res.add(new int[]{left, right});
        // 3️⃣ 处理右侧区间：完全位于 newInterval 右边的区间，直接加入结果
        while (i < n) {
            res.add(intervals[i]);
            i++;
        }
        // 转换为二维数组输出
        return res.toArray(new int[res.size()][]);
    }
}
