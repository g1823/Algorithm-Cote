package leetCode.moderately;

/**
 * @author: gj
 * @description: 75. 颜色分类
 */
public class SortColors {
    /**
     * 记录每个颜色的个数，但是会遍历两次
     * @param nums
     */
    public void sortColors(int[] nums) {
        int zeroNum = 0, oneNum = 0, twoNum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                zeroNum++;
            } else if (nums[i] == 1) {
                oneNum++;
            } else {
                twoNum++;
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (zeroNum > 0) {
                nums[i] = 0;
                zeroNum--;
            } else if (oneNum > 0) {
                nums[i] = 1;
                oneNum--;
            } else {
                nums[i] = 2;
                twoNum--;
            }
        }
    }

    // TODO
    public void sortColors2(int[] nums) {

    }

}
