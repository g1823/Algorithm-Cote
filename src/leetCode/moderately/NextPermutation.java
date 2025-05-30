package leetCode.moderately;

import java.util.Arrays;

/**
 * @author: gj
 * @description: 31. 下一个排列
 */
public class NextPermutation {
    public static void main(String[] args) {
        int[] nums = {1, 3, 2};
        nextPermutation(nums);
        System.out.println();
    }

    /**
     * 思路：
     * 希望找到当前排列的“下一个更大的排列”，即所有可能排列中刚好比当前大的那个。
     * 对于一个数，越靠左的数，其代表的值越大，例如123,1代表1*100，2代表2*10，3代表3*1，因此需要尽可能改变 当前数靠右位置的数
     * 前提：
     * - 若一个数从右到左一直递增，则说明针对这组递增的数，已经是最大值了，现在需要找这些数的下一个排列，就需要从这个数的最左侧的往左下一个数开始改变了
     * 步骤：
     * 1、找到第一个从右往左第一个非递增的数，设其下标为i，则从i+1 -> nums.length-1,已经是最大排列了
     * - 需要找到比当前数大的最小排列，那么仅改变i+1->nums.length-1的已经不可能了，因为已经是最大排列，
     * - 因此只能改动i位置了，如何找到下一个最小数。
     * 3、需要从i+1->nums.length-1中从右往左找第一个比nums[i]大的数，假设这个数的下标为j，
     * 则：
     * - 因为这些数是从右往左递增:  nums[j-1] > nums[j] ;
     * - 因为nums[j]是从右往左找到的第一个比nums[i]的的数，所以： nums[j]> nums[i]; nums[i] >nums[i+1]
     * - 因此：nums[j-1] > nums[i] > nums[j+1]
     * - 即：将nums[i]替换至nums[j]的位置后，从i+1到nums.length-1的从右到左递增性质不变
     * 5、此时，i位置取了比原排列的最小值了，需要保证i+1到nums.length-1组成的数也要最小
     * - 考虑到3中，从i+1到nums.length-1从右往左递增，因此要i+1到nums.length-1组成的数最小只需要将其逆序即可
     * 6、将i+1 -> nums.length-1逆序
     *
     * @param nums
     */
    public static void nextPermutation(int[] nums) {
        if (nums.length <= 1) {
            return;
        }
        // 拿到第一个从右往左的非递增的数
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        // 从右往左找到第一个比nums[i]大的数nums[j]
        // 此时，nums[j-1] > nums[j] > nums[i] > nums[j+1],即i和j替换后不破换原来的从右往左递增性质
        if (i >= 0) {
            for (int j = nums.length - 1; j > i; j--) {
                if (nums[j] > nums[i]) {
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                    break;
                }
            }
        }
        // 左右交换，从递增改为递减
        int left = i + 1, right = nums.length - 1;
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }
}
