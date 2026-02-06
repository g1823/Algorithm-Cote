package leetCode.moderately;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author: gj
 * @description: 435. 无重叠区间
 */
public class EraseOverlapIntervals {

    /**
     * - 思路说明：
     * - 本题目标是删除尽可能少的区间，使剩余区间互不重叠。
     * - 核心贪心思想：
     * - 当多个区间发生重叠时，应优先保留“结束位置更靠左”的区间，
     * - 因为结束点越靠右，越容易与后续区间发生重叠，给后续选择留下的空间越小。
     * - 具体做法：
     * - 1. 先将区间按起始坐标升序排序；
     * - 2. 使用变量 end 表示当前被保留下来的区间集合中，最右侧的结束位置。
     * - 3. 依次遍历排序后的区间：
     * -    - 若当前区间的起点 >= end，说明不发生重叠，可以直接保留该区间，
     * -      并更新 end = 当前区间的结束位置。
     * -    - 若当前区间的起点 < end，说明发生重叠：
     * -        此时需要删除一个区间，为了尽量不影响后续区间的选择，
     * -        应删除两个区间中结束位置更靠右的那个，
     * -        等价于保留 end 和当前区间结束位置中的较小值。
     * -        删除次数 count + 1，同时更新 end = min(end, 当前区间结束位置)。
     * - 4. 遍历结束后，count 即为最少需要删除的区间数量。
     * - 本质理解：
     * - 该解法等价于经典的“按结束位置排序”的贪心解法，
     * - 只是将“选择结束最早的区间”这一贪心策略，
     * - 延迟到了区间发生冲突时再动态纠正。
     */
    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        int count = 0;
        int end = Integer.MIN_VALUE;
        for (int[] interval : intervals) {
            if (interval[0] >= end) {
                end = interval[1];
            } else {
                // 发生重叠，删掉 end 更大的那个
                count++;
                end = Math.min(end, interval[1]);
            }
        }
        return count;
    }


    /**
     * 题解思路（经典贪心解法）：
     * 本题等价于：在所有区间中，选出最多个互不重叠的区间，
     * 最终答案 = 区间总数 - 最多可保留的不重叠区间数。
     * 核心贪心策略：
     * 始终优先选择“结束位置最早”的区间。
     * 具体做法：
     * 1. 将所有区间按结束坐标升序排序；
     * -   若结束坐标相同，则按起始坐标升序排序（非必须，仅保证稳定性）。
     * 2. 使用变量 end 表示当前已选择区间的最后结束位置，
     * -   初始值为 Integer.MIN_VALUE。
     * 3. 依次遍历排序后的区间：
     * -   - 若当前区间的起点 >= end，说明与已选择区间不重叠，
     * -     可以保留该区间，并更新 end = 当前区间的结束位置。
     * -   - 否则发生重叠，直接跳过当前区间，相当于删除该区间。
     * 4. 最终删除的区间数 = 总区间数 - 成功选择的不重叠区间数。
     * 与「按起点排序 + 冲突时取最小 end」解法的关系：
     * 区别：
     * - 本解法通过“提前按 end 排序”，保证每次遍历到的区间，
     * -  都是当前能选择的结束最早的区间；
     * - 你的解法是先按 start 排序，在区间发生重叠时，
     * -  再通过 end = min(end, curEnd) 动态纠正选择。
     * 等价性：
     * - 两种解法都遵循同一个贪心不变量：
     * -  在任意发生重叠的区间集合中，始终保留结束位置最早的区间；
     * - 本解法是“事前保证 end 最小”，
     * -  你的解法是“事后在冲突时保证 end 最小”，
     * -  本质完全一致，最终保留下来的区间数量相同。
     */
    public int eraseOverlapIntervals2(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> {
            if (a[1] != b[1]) {
                return a[1] - b[1];
            }
            return a[0] - b[0];
        });
        int count = 0;
        int end = Integer.MIN_VALUE;
        for (int[] interval : intervals) {
            if (interval[0] >= end) {
                end = interval[1];
            } else {
                // 与已选择区间重叠，删除当前区间
                count++;
            }
        }
        return count;
    }

}
