package leetCode.moderately;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author: gj
 * @description: 56. 合并区间
 */
public class Merge {
    public static void main(String[] args) {
        int[][] data = new int[][]{{1, 4}, {0, 4}};
        System.out.println(new Merge().merge(data));
    }

    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        List<int[]> temp = new ArrayList<>();
        temp.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            int[] ints = temp.get(temp.size() - 1);
            if (intervals[i][0] <= ints[1]) {
                ints[1] = Math.max(intervals[i][1], ints[1]);
            } else {
                temp.add(intervals[i]);
            }
        }
        int[][] result = new int[temp.size()][];
        for (int i = 0; i < temp.size(); i++) {
            result[i] = temp.get(i);
        }
        return result;
    }
}
