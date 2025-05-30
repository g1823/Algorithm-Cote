package leetCode.moderately;

/**
 * @author: gj
 * @description: 33. 搜索旋转排序数组
 */
public class Search {
    public static void main(String[] args) {
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
        System.out.println(search(nums, 0));
    }

    /**
     * 题目要求Log(n)时间复杂度解决，依旧思考二分查找：
     * 带查找值target，区间中间下标mid，区间左侧下标start，区间右侧下标end
     * 对比nums[mid] 和 nums[end]：
     * - nums[mid] < nums[end]，则说明右侧有序：
     * -- 1、若 target>nums[mid] && target<=nums[end]，则说明在右侧有序区间，则继续在右侧有序区间查找
     * -- 2、否则，则说明在左侧无序区间，在左侧区间内递归
     * - nums[mid] > nums[end]，则说明左侧有序：
     * -- 1、若 target>=nums[start] && target<nums[mid]，则说明在左侧有序区间，则继续在左侧有序区间查找
     * -- 2、否则，则说明在右侧无序区间，在右侧区间内递归
     */
    public static int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        return s(nums, target, 0, nums.length - 1);
    }

    public static int s(int[] nums, int target, int start, int end) {
        if (start > end) {
            return -1;
        }
        int mid = start + (end - start) / 2;
        if (nums[mid] == target) {
            return mid;
        }
        // 中间值小于右侧值，右边有序
        if (nums[mid] < nums[end]) {
            // 比中间值大，比右侧小，则说明在右侧
            if (target > nums[mid] && target <= nums[end]) {
                return s(nums, target, mid + 1, end);
            } else {
                return s(nums, target, start, mid - 1);
            }
        }
        // 中间值大于右侧值，左边有序
        else if (nums[mid] > nums[end]) {
            // 比中间值小，比左侧大，则说明在左侧
            if (target >= nums[start] && target < nums[mid]) {
                return s(nums, target, start, mid - 1);
            } else {
                return s(nums, target, mid + 1, end);
            }
        }
        return -1;
    }
}
