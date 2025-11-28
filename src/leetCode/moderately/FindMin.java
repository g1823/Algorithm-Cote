package leetCode.moderately;

/**
 * @author: gj
 * @description: 153. 寻找旋转排序数组中的最小值
 */
public class FindMin {
    public static void main(String[] args) {
        int[] nums = new int[]{2, 1};
        System.out.println(new FindMin().findMin(nums));
    }

    /**
     * 使用二分查找在旋转排序数组中寻找最小值。
     * 思路：
     * 1. 如果 nums[r] >= nums[l]，说明区间 [l..r] 已经完全有序，
     * -此时最小值就是 nums[l]，直接返回。
     * 2. 否则说明区间中存在旋转点，取中点 mid：
     * - 若 nums[mid] >= nums[l]：
     * --说明左半区间 [l..mid] 有序，最小值一定不在这部分，
     * --因此可以直接丢弃，更新 l = mid + 1。
     * - 若 nums[mid] < nums[l]：
     * --说明旋转点在左半区间 [l..mid] 内，
     * --且 mid 本身可能就是最小值，所以更新 r = mid。
     * 3. 循环执行上述过程，直到 l == r 收敛，此时指针指向的元素就是最小值。
     * 关键点：
     * - l = mid + 1：排除掉有序的左半部分，最小值一定在右边。
     * - r = mid：保留 mid，因为 mid 可能是最小值。
     * 时间复杂度：O(log n)，每次排除一半区间。
     * 空间复杂度：O(1)，仅用常数额外空间。
     */
    public int findMin(int[] nums) {
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int mid = l + (r - l) / 2;
            // 如果右端 >= 左端，说明区间已经有序，直接返回
            if (nums[r] >= nums[l]) {
                return nums[l];
            }
            // 区间存在旋转
            if (nums[mid] >= nums[l]) {
                // 最小值一定在右半边
                l = mid + 1;
            } else {
                // 最小值一定在左半边（含 mid）
                r = mid;
            }
        }
        return nums[l];
    }

    /**
     * 在旋转排序数组中寻找最小值（标准二分法）。
     * 核心思路：
     * 1. 比较中点 mid 与右端点 r：
     * - 如果 nums[mid] > nums[r]：
     * --说明最小值在右半区间 [mid+1..r]，更新 l = mid + 1。
     * - 否则（nums[mid] <= nums[r]）：
     * --说明最小值在左半区间 [l..mid]，更新 r = mid。
     * 2. 循环直到 l == r，指向最小值。
     * 时间复杂度：O(log n)
     * 空间复杂度：O(1)
     */
    public int findMin2(int[] nums) {
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] > nums[r]) {
                // 最小值在右半区间
                l = mid + 1;
            } else {
                // 最小值在左半区间（含 mid）
                r = mid;
            }
        }
        return nums[l];
    }
}
