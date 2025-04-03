package leetCode.moderately;

import java.util.Arrays;

/**
 * @author: gj
 * @description: 581. 最短无序连续子数组
 */
public class FindUnsortedSubarray {
    public static void main(String[] args) {
        int[] data = new int[]{1, 2, 3, 4};
        System.out.println(new FindUnsortedSubarray().findUnsortedSubarray1(data));
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
     * 基本思想：
     * - 将nums分为numsA,numsB,nums.c，对numsB排序，整个数组将变为有序。换而言之，当我们对整个序列进行排序，numsA和nums.c都不会改变。
     * - 假设numsB在nums中对应区间为[left,right]。
     * - 注意到numsB和numsC中任意一个数都大于等于numsA中任意一个数。因此有numsA中每一个数nums.i都满足：
     * - numA.i <= min(numsB和numsC)
     * - 我们可以从大到小枚举i，用一个变量minn记录min(nums.(i+1) - nums.(n-1))。
     * - 每次移动i，都可以O（1）地更新minn。这样最后一个使得不等式不成立的i即为 left。
     * - left 左侧即为 numsA能取得的最大范围。
     * @param nums 原始整数数组
     * @return 返回排序无序的部分数组的最短长度如果数组为空或长度不超过1，则返回0
     */
    public int findUnsortedSubarray1(int[] nums) {
        // 检查数组是否为空或只有一个元素，这种情况下不需要排序，返回0
        if (nums == null || nums.length <= 1) {
            return 0;
        }
        int n = nums.length;
        // 初始化左右边界及最小最大值变量
        int left = -1, right = -1, min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

        // 遍历数组，寻找需要排序的子数组的左右边界
        for (int i = 0; i < n; i++) {
            // 从右向左遍历，寻找左边界
            if (nums[n - 1 - i] > min) {
                left = n - 1 - i;
            } else {
                min = nums[n - 1 - i];
            }
            // 从左向右遍历，寻找右边界
            if (nums[i] < max) {
                right = i;
            } else {
                max = nums[i];
            }
        }
        // 如果right仍然是初始值-1，说明数组已经有序，返回0；否则返回需要排序的子数组长度
        return right == -1 ? 0 : right - left + 1;
    }
}
