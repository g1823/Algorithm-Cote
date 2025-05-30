package leetCode.moderately;

/**
 * @author: gj
 * @description: 34. 在排序数组中查找元素的第一个和最后一个位置
 */
public class SearchRange {

    public static void main(String[] args) {
        int[] nums = {5, 7, 7, 8, 8, 10};
        System.out.println(searchRange2(nums, 8));
    }

    /**
     * 先用二分查找找到目标元素，然后在左右边界上进行搜索相等的元素
     * 这样虽然找到指定元素为O(long N)，但是向左右两侧搜索时，在有大量相同元素的情况下会退化为O(N)
     */
    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[]{-1, -1};
        }
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                int l = mid, r = mid;
                while (l >= 0 && nums[l] == target) {
                    l--;
                }
                while (r < nums.length && nums[r] == target) {
                    r++;
                }
                return new int[]{l + 1, r - 1};
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * 与传统的二分查找不同：
     * - 传统二分：一旦找到目标元素就返回
     * - 本题：需要找到目标元素的左右边界，因此即使找到目标也不返回，而是继续缩小搜索范围
     * 左边界查找策略：
     * - 如果 nums[mid] >= target，说明 mid 有可能是左边界，向左收缩（right = mid - 1）
     * 右边界查找策略：
     * - 如果 nums[mid] <= target，说明 mid 有可能是右边界，向右收缩（left = mid + 1）
     */
    public static int[] searchRange2(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[]{-1, -1};
        }
        int left = getLeft(nums, target);
        int right = getRight(nums, target);
        return new int[]{left, right};
    }

    public static int getLeft(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        int res = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
            if (nums[mid] == target) {
                res = mid;
            }
        }
        return res;
    }

    public static int getRight(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        int res = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
            if (nums[mid] == target) {
                res = mid;
            }
        }
        return res;
    }
}
