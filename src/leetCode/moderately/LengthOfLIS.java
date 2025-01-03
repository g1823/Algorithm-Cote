package leetCode.moderately;

/**
 * @author: gj
 * @description: 300. 最长递增子序列
 */
public class LengthOfLIS {
    public static void main(String[] args) {
        int[] nums = new int[]{10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println(new LengthOfLIS().lengthOfLIS(nums));
    }

    /**
     * 给原数组排序，并记录连续递增的个数
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
}
