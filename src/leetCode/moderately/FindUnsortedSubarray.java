package leetCode.moderately;

import java.util.Arrays;

/**
 * @author: gj
 * @description: 581. 最短无序连续子数组
 */
public class FindUnsortedSubarray {
    public static void main(String[] args) {
        int[] data = new int[]{2, 6, 4, 8, 10, 9, 15};
        System.out.println(new FindUnsortedSubarray().findUnsortedSubarray(data));
    }

    /**
     * 排序，克隆一份数据进行排序，然后跟原数组对比，找到最长匹配前缀和后缀，就得到最短无序了
     *
     * @param nums 原数组
     * @return 最短无序长度
     */
    public int findUnsortedSubarray(int[] nums) {
        int[] clone = nums.clone();
        Arrays.sort(clone);
        int l = 0, r = nums.length - 1;
        for (l = 0; l < nums.length; l++) {
            if (nums[l] != clone[l]) {
                break;
            }
        }
        if (l >= nums.length - 1) {
            return 0;
        }
        while (r >= 0 && nums[r] == clone[r]) {
            r--;
        }
        return r - l + 1;
    }

    /**
     * 排序，克隆一份数据进行排序，然后跟原数组对比，找到最长匹配前缀和后缀，就得到最短无序了
     *
     * @param nums 原数组
     * @return 最短无序长度
     */
    public int findUnsortedSubarray1(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }
        int left = 0, right = nums.length - 1, disorderMin = Integer.MAX_VALUE, disorderMax = Integer.MIN_VALUE;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) {
                left = i - 1;
            }
        }
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] > nums[i + 1]) {
                right = i + 1;
            }
        }
        for (int i = left; i <= right; i++) {
            disorderMin = Math.min(disorderMin, nums[i]);
            disorderMax = Math.max(disorderMax, nums[i]);
        }
        while (left > 0 && nums[left - 1] > disorderMin) {
            left--;
        }
        while (right < nums.length - 1 && nums[right + 1] < disorderMax) {
            right++;
        }
        return right - left + 1;
    }
}
