package leetCode.simple;

/**
 * @author: gj
 * @description: 26. 删除有序数组中的重复项
 */
public class RemoveDuplicates {
    public int removeDuplicates(int[] nums) {
        int l = 0, r = 0;
        while (r < nums.length) {
            if (nums[l] != nums[r]) {
                l++;
                nums[l] = nums[r];
            } else {
                r++;
            }
        }
        return l + 1;
    }
}
