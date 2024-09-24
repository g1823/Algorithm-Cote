package leetCode.moderately;

/**
 * @author: gj
 * @description: 238. 除自身以外数组的乘积
 */
public class ProductExceptSelf {
    public int[] productExceptSelf(int[] nums) {
        int length = nums.length;
        int[] result = new int[length];
        result[0] = 1;
        for (int i = 1; i < length; i++) {
            result[0] *= nums[i];
            if (result[0] == 0) break;
        }
        for (int i = 1; i < length; i++) {
            if (nums[i] == 0) {
                int t = 1;
                for (int j = 0; j < length; j++) {
                    if (j == i) continue;
                    if (nums[j] == 0) {
                        t = 0;
                        break;
                    }
                    t *= nums[j];
                }
                result[i] = t;
            } else {
                result[i] = result[i - 1] * nums[i - 1] / nums[i];
            }
        }
        return result;
    }
}
