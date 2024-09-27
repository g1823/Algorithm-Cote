package leetCode.moderately;

/**
 * @author: gj
 * @description: 152. 乘积最大子数组
 */
public class MaxProduct {

    public static void main(String[] args) {
        int[] nums = new int[]{-3, 0, 1, -2};
        int[] nums2 = new int[]{2, 3, -2, 4};
        int[] nums3 = new int[]{-4, -3};
        int[] nums4 = new int[]{-3, -1, -1};
        int[] nums5 = new int[]{-2, 0, -1};
        int[] nums6 = new int[]{0, 2};
        int[] nums7 = new int[]{1, 0, -1, 2, 3, -5, -2};
        int[] nums8 = new int[]{2, -5, -2, -4, 3};
        int[] nums9 = new int[]{2, -1, 1, 1};
        System.out.println(new MaxProduct().maxProduct3(nums9));
    }

    /**
     * 蛮力法，暴力破解
     *
     * @param nums
     * @return
     */
    public int maxProduct(int[] nums) {
        int result = Integer.MIN_VALUE;
        if (nums.length == 1) return nums[0];
        for (int i = 0; i < nums.length; i++) {
            int t = nums[i];
            for (int j = i + 1; j < nums.length && t != 0; j++) {
                result = Math.max(t, result);
                if (nums[j] == 0) break;
                t *= nums[j];
            }
            result = Math.max(t, result);
        }
        return result;
    }

    /**
     * 和求连续子数组和区别为：
     * 连续子数组和只要当前累加值小于0，之后无论加什么都会变小。
     * 而连续乘积不行，当前值小于0，后面可能还有负数，乘积会变大。
     * 这里尝试记录每个下标后续负数的个数（直到遇到0），然后根据后续负数个数判断是否还能乘积为正数
     * 但是无法考虑到如下情况：1, 0, -1, 2, 3, -5, -2 ：无法跳过-1去计算 2*3*（-5）*（-2）
     *
     * @param nums nums
     * @return int
     */
    public int maxProduct2(int[] nums) {
        int result = Integer.MIN_VALUE, length = nums.length, negativeCount = 0;
        if (length == 1) return nums[0];
        int[] negativeNumber = new int[length];
        for (int i = length - 1; i >= 0; i--) {
            negativeNumber[i] = negativeCount;
            int t = nums[i];
            // 遇到0重置负数个数
            if (t == 0) negativeCount = 0;
            if (t < 0) negativeCount++;
        }
        Integer temp = null, flag = 0;
        for (int i = 0; i < length; i++) {
            int t = nums[i];
            if (temp == null) {
                temp = t;
                flag = 1;
            }
            if (t == 0) {
                result = Math.max(result, Math.max(temp, t));
                if (i + 1 < length) {
                    temp = nums[i + 1];
                    flag = 1;
                }
                continue;
            }
            if (flag == 1) {
                flag = 0;
            } else {
                temp *= t;
            }
            if (temp < 0) {
                // 当前乘积大于0，后续还有负数，可以继续计算,否则temp重新复制
                if (negativeNumber[i] < 1) {
                    if (i + 1 < length) {
                        temp = nums[i + 1];
                        flag = 1;
                    }
                }
            }
            result = Math.max(result, Math.max(temp, t));

        }
        return Math.max(result, temp);
    }


    /**
     * 官方题解:动态规划
     * 对于下标为n的数：
     * 如果当前数为正数，那么只需要直到前n-1个数（必须连续乘到n-1，即包含n-1）中连续乘积最大的数，其与当前下标为n的数相乘即为最大值。
     * 如果当前数为负数，那么只需要直到前n-1个数（必须连续乘到n-1，即包含n-1）中连续乘积最小的数，其与当前下标为n的数相乘即为最大值。
     * 所以只需要记录前i-1个数连续乘积的最大值和最小值即可，一次遍历就可以结束
     * @param nums nums
     * @return int
     */
    public int maxProduct3(int[] nums) {
        int length = nums.length;
        if (length == 1) return nums[0];
        int max = nums[0], min = nums[0], result = nums[0];
        for (int i = 1; i < length; i++) {
            int t = nums[i], maxTemp = max, minTemp = min;
            max = Math.max(t, Math.max(maxTemp * t, minTemp * t));
            min = Math.min(t, Math.min(maxTemp * t, minTemp * t));
            result = Math.max(max, result);
        }
        return result;
    }
}
