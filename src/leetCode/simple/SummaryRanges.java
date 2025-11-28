package leetCode.simple;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gj
 * @description: 228. 汇总区间
 */
public class SummaryRanges {
    public static void main(String[] args) {
        int[] nums = {0, 1, 2, 4, 5, 7};
        List<String> res = summaryRanges(nums);
        System.out.println(res);
    }

    public static List<String> summaryRanges(int[] nums) {
        List<String> res = new ArrayList<>();
        int i = 0, n = nums.length;
        while (i < n) {
            int start = i;
            // 先+1
            i++;
            while (i < n && nums[i - 1] + 1 == nums[i]) {
                i++;
            }
            if (start == i - 1) {
                res.add(nums[start] + "");
            } else {
                res.add(nums[start] + "->" + nums[i - 1]);
            }
        }
        return res;
    }
}
