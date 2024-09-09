package leetCode.simple;

/**
 * @author: gj
 * @description: 283. 移动零
 */
public class MoveZeroes {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 0};
        MoveZeroes moveZeroes = new MoveZeroes();
        //moveZeroes.moveZeroes(nums);
        moveZeroes.moveZeroes2(nums);
    }

    /**
     * 参考插入排序，从后向前遍历，将0当作最大值向后移动
     *
     * @param nums
     */
    public void moveZeroes(int[] nums) {
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] == 0) {
                for (int j = i; j < nums.length; j++) {
                    if (j == nums.length - 1 || nums[j + 1] == 0) {
                        nums[j] = 0;
                        break;
                    }
                    nums[j] = nums[j + 1];
                }
            }
        }
    }


    /**
     * 快慢指针：
     * 模仿快排，慢指针指向0区域初始位置，快指针指向待遍历位置
     *
     * @param nums
     */
    public void moveZeroes2(int[] nums) {
        int slow = 0, fast = 0;
        for (; fast < nums.length; fast++) {
            if (nums[fast] != 0) {
                int t = nums[slow];
                nums[slow++] = nums[fast];
                nums[fast] = t;
            }
        }
    }
}
