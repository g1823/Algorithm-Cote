package leetCode.moderately;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: gj
 * @description: 128. 最长连续序列
 */
public class LongestConsecutive {

    public static void main(String[] args) {
        //int[] nums = new int[]{100, 4, 200, 1, 3, 2};
        int[] nums = new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1};
        System.out.println(new LongestConsecutive().longestConsecutive2(nums));
    }

    /**
     * 暴力解决，遇到一个数字j就遍历整个数组，查找j+1是否存在时需要O(n)时间。
     * 通过将原数组放入Hash表，查找一个数是否存在从O(n)优化为O(1)
     * --------
     * 如果已经计算过 i,i+1,i+2,i+3,...i+k
     * 下次遇到i+1(或i+m其中m<k)，还需要向后全部计算一遍，造成重复计算
     * 通过转变策略，保证只计算一次：
     * 对于数i，如果i-1不存在，说明他一定是某个连续数列的起始位置，否则不是。
     * 对于i-1不存在的数据，进行向后判断，判断i+1,i+2,i+3....是否存在
     *
     * @param nums 数据数组
     * @return 最大连续数列
     */
    public int longestConsecutive(int[] nums) {
        int n = nums.length, result = 1;
        if (n == 0) return 0;
        Set<Integer> dataSet = Arrays.stream(nums)
                .boxed() // 将 int 转换为 Integer
                .collect(Collectors.toSet());
        for (int i = 0; i < n; i++) {
            int data = nums[i];
            if (!dataSet.contains(data - 1)) {
                int currentNum = 1;
                int t = data;
                while (dataSet.contains(t + 1)) {
                    currentNum++;
                    t++;
                }
                result = Math.max(currentNum, result);
            }
        }
        return result;
    }


    /**
     * 先排序、后直接遍历
     *
     * @param nums
     * @return
     */
    public int longestConsecutive2(int[] nums) {
        int n = nums.length, result = 1;
        if (n == 0) return 0;
        Arrays.sort(nums);
        int t = 1;
        for (int i = 1; i < n; i++) {
            int data = nums[i];
            if (data - 1 == nums[i - 1]) {
                t++;
            } else if (data == nums[i - 1]) {
            }else {
                result = Math.max(t, result);
                t = 1;
            }
        }
        result = Math.max(t, result);
        return result;
    }
}
