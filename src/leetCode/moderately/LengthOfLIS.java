package leetCode.moderately;

/**
 * @author: gj
 * @description: 300. 最长递增子序列
 */
public class LengthOfLIS {
    public static void main(String[] args) {
        int[] nums = new int[]{10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println(new LengthOfLIS().lengthOfLIS3(nums));
    }

    /**
     * 给原数组排序，并记录连续递增的个数
     * 错误：排序时需要获取到比起小元素中的最大连续个数，排序变得无意了
     *
     * @param nums 原数组
     * @return 最大递增子序列长度
     */
    public int lengthOfLIS(int[] nums) {
        int[][] orderNums = new int[nums.length][2];
        orderNums[0][0] = nums[0];
        orderNums[0][1] = 1;
        int result = 1;
        for (int i = 1; i < nums.length; i++) {
            int num = nums[i], thisCount = 1;
            int index = getIndex(orderNums, 0, i - 1, num);
            for (int j = i; j > index; j--) {
                orderNums[j][0] = orderNums[j - 1][0];
                orderNums[j][1] = orderNums[j - 1][1];
            }
            if (index > 0) {
                thisCount = orderNums[index - 1][1] + 1;
            }
            result = Math.max(thisCount, result);
            orderNums[index][0] = num;
            orderNums[index][1] = thisCount;
        }
        return result;
    }

    public int getIndex(int[][] orderNums, int l, int r, int key) {
        int left = l, right = r;
        while (left <= right) {
            int midIndex = left + (right - left) / 2;
            int v = orderNums[midIndex][0];
            if (v == key) return midIndex + 1;
            if (v < key) {
                left = midIndex + 1;
            } else {
                right = midIndex - 1;
            }
        }
        return left;
    }


    /**
     * 动态规划，对于任意一个元素，其最大连续序列数量 = 其左侧比其小的元素中连续序列最大的数+1
     *
     * @param nums
     * @return
     */
    public int lengthOfLIS2(int[] nums) {
        int result = 1;
        int[] data = new int[nums.length];
        data[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            int d = 1, t = nums[i];
            for (int j = i - 1; j >= 0; j--) {
                if (nums[j] < t) d = Math.max(data[j] + 1, d);
            }
            data[i] = d;
            result = Math.max(result, d);
        }
        return result;
    }

    /**
     * 要使上升子序列尽可能的长，则我们需要让序列上升得尽可能慢，因此我们希望每次在上升子序列最后加上的那个数尽可能的小。
     * 数组d[i]的值 = 长度为i+1的连续上升子序列最小值
     * 比如：5 1 3 2 ...
     * 遍历 5，最大长度 1 ，即d[0] = 5 ，长度为0+1=1时，最小值为5.
     * 遍历 1，1比5小，到1最大长度也为1，长度同为1，1比5小，那么把5换成1.
     * 遍历 3，3比1大，那么最大长度为1+1=2，即d[1]=3。
     * 遍历 2，2比3小，比1大，那么2的最大长度为1+1=2，最大长度同为2，2比3小，为了后续更长，替换3为2，即d[1]=2。
     *
     * @param nums
     * @return
     */
    public int lengthOfLIS3(int[] nums) {
        int len = 0;
        int[] d = new int[nums.length];
        d[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            // 如果当前元素比已遍历元素中最后一个大，那么直接长度+1
            if (d[len] < nums[i]) {
                d[len + 1] = nums[i];
                len++;
            } else {
                // 因为d单调递增，因此采用二分查找获取当前元素应该在的位置
                int l = 0, r = len, k = nums[i];
                while (l <= r) {
                    int mid = l + (r - l) / 2;
                    if (d[mid] == k) {
                        l = mid;
                        break;
                    } else if (d[mid] > k) {
                        r = mid - 1;
                    } else if (d[mid] < k) {
                        l = mid + 1;
                    }
                }
                if (d[l] > k) d[l] = k;
            }
        }
        return len + 1;
    }
}
