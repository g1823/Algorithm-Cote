package leetCode.simple;

/**
 * @author: gj
 * @description: 724. 寻找数组的中心下标
 */
public class PivotIndex {

    /**
     * 前缀和：注意判断prefix[nums.length - 2] == 0需要放到最后，因为题目要求取最左侧的中心下标
     */
    public int pivotIndex(int[] nums) {
        int[] prefix = new int[nums.length];
        prefix[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            prefix[i] = prefix[i - 1] + nums[i];
        }
        int sum = prefix[nums.length - 1];
        if (sum - prefix[0] == 0) {
            return 0;
        }

        for (int i = 1; i < nums.length - 1; i++) {
            if (prefix[i - 1] == sum - prefix[i]) {
                return i;
            }
        }
        if (prefix[nums.length - 2] == 0) {
            return nums.length - 1;
        }
        return -1;
    }
}
