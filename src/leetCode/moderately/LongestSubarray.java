package leetCode.moderately;

import java.util.Arrays;

/**
 * @author: gj
 * @description: 2419. 按位与最大的最长子数组
 */
public class LongestSubarray {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 3, 2, 2};
        System.out.println(new LongestSubarray().longestSubarray(nums));
    }

    /**
     * 1. 按位与操作的性质：
     * - 与运算的结果不会大于任意一个操作数，即 a & b <= a 且 <= b。
     * - 所以，所有子数组中按位与的最大可能值就是数组中的最大元素 maxVal。
     * 2. 要使得某个子数组的按位与结果为 maxVal，子数组中的所有元素必须都是 maxVal。
     * - 因为只要某一位上存在 0，按位与结果对应位就一定是 0，不可能保留 maxVal 原本为 1 的位。
     * - 因此，只需要寻找最长的连续的元素全为 maxVal 的子数组。
     * 3. 算法实现：
     * - 第一次遍历数组，找出最大值 maxVal。
     * - 第二次遍历数组，统计连续出现 maxVal 的最大长度，即为答案。
     */
    public int longestSubarray(int[] nums) {
        int max = 0;
        for (int num : nums) {
            max = Math.max(max, num);
        }
        int ans = 0, count = 0;
        for (int num : nums) {
            if (num == max) {
                count++;
                ans = Math.max(ans, count);
            } else {
                count = 0;
            }
        }
        return ans;
    }


    /**
     * 对于longestSubarray的优化：
     * 改为1次遍历，在寻找最大值的同时，实时统计连续最大值的长度
     */
    public int longestSubarray2(int[] nums) {
        int max = 0, ans = 0, count = 0;
        for (int num : nums) {
            if (num > max) {
                ans = 1;
                count = 1;
                max = num;
            } else if (num == max) {
                count++;
                ans = Math.max(ans, count);
            }else{
                count = 0;
            }
        }
        return ans;
    }
}
