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
        System.out.println(new MaxProduct().maxProduct2(nums7));
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
                result = Math.max(result, temp);
                if (i + 1 < length) {
                    temp = nums[i + 1];
                    flag = 1;
                }
                continue;
            }
            if (t < 0) {
                if ((temp < 0 && negativeNumber[i] % 2 == 0) || (temp > 0 && negativeNumber[i] % 2 == 1)) {
                    if (flag == 1) {
                        flag = 0;
                    } else {
                        temp *= t;
                    }

                } else {
                    if (i + 1 < length) {
                        result = Math.max(result, temp);
                        temp = nums[++i];
                    }
                }
            } else {
                if (flag == 1) {
                    flag = 0;
                } else {
                    temp *= t;
                }
            }
            result = Math.max(result, temp);
        }


        return Math.max(result, temp);
    }


}
