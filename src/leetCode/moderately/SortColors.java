package leetCode.moderately;

/**
 * @author: gj
 * @description: 75. 颜色分类
 */
public class SortColors {
    /**
     * 解法：
     * 直接记录每个颜色的个数，然后依次赋值即可。
     * 缺点是需要遍历两次
     */
    public void sortColors(int[] nums) {
        int zeroNum = 0, oneNum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                zeroNum++;
            } else if (nums[i] == 1) {
                oneNum++;
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (zeroNum > 0) {
                nums[i] = 0;
                zeroNum--;
            } else if (oneNum > 0) {
                nums[i] = 1;
                oneNum--;
            } else {
                nums[i] = 2;
            }
        }
    }

    /**
     * 思路：
     * 使用两个指针，分别记录0和1区间的末尾位置：
     * 遇到0，0和1都向后扩张
     * 遇到1，向后扩张1
     * 遇到2，从末尾向前扩张
     */
    public void sortColors2(int[] nums) {
        int low = 0, mid = 0, high = nums.length - 1;

        while (mid <= high) {
            if (nums[mid] == 0) {
                swap(nums, low++, mid++);
            } else if (nums[mid] == 1) {
                mid++;
            } else {
                swap(nums, mid, high--);
            }
        }
    }

    public void swap(int[] arr, int l, int r) {
        int temp = arr[l];
        arr[l] = arr[r];
        arr[r] = temp;
    }

}
