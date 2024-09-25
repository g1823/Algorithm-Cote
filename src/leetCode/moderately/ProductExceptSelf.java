package leetCode.moderately;

/**
 * @author: gj
 * @description: 238. 除自身以外数组的乘积
 */
public class ProductExceptSelf {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4};
        new ProductExceptSelf().productExceptSelf2(nums);
    }

    /**
     * 先计算除下标0以外的所有元素的乘积，然后向后遍历：
     * 除元素i以外元素的乘积 = 除元素(i-1)以外元素的乘积 * 元素(i-1) / 元素i
     * 但是需要对 元素i =0 的情况单独处理
     *
     * @param nums nums
     * @return 结果
     */
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


    /**
     * 可以整两个长度为n的数组，分别存储左前缀乘积和右前缀乘积
     * 但是为了减少空间复杂度，使用结果数组存储左前缀合，然后从数组末尾往前读取，并设置临时遍量r记录右前缀合，减少一个数组的长度
     *
     * @param nums
     * @return
     */
    public int[] productExceptSelf2(int[] nums) {
        int length = nums.length;
        int[] result = new int[length];
        result[0] = nums[0];
        for (int i = 1; i < length; i++) {
            result[i] = nums[i] * result[i - 1];
            if (result[i] == 0) break;
        }
        int r = 1;
        for (int i = length - 1; i > 0; i--) {
            result[i] = result[i - 1] * r;
            r *= nums[i];
        }
        result[0] = r;
        return result;
    }
}
