package leetCode.moderately;

/**
 * @author: gj
 * @description: 55. 跳跃游戏
 */
public class CanJump {
    public static void main(String[] args) {
        int[] nums = new int[]{2, 3, 1, 1, 4};
        System.out.println(new CanJump().canJump2(nums));
    }

    // dfs
    public boolean canJump(int[] nums) {
        if (nums.length <= 1) return true;
        return dfs(nums[0], 0, nums);
    }

    public boolean dfs(int steps, int index, int[] nums) {
        if (index == nums.length - 1 || (index + steps) >= (nums.length - 1)) return true;
        if (steps == 0) return false;
        for (int i = 1; i <= steps; i++) {
            if (dfs(nums[i + index], i + index, nums)) {
                return true;
            }
        }
        return false;
    }

    // 贪心
    public boolean canJump2(int[] nums) {
        if (nums.length <= 1) return true;
        int maxIndex = 0;
        for (int i = 0; i <= maxIndex && i < nums.length; i++) {
            maxIndex = Math.max(i + nums[i], maxIndex);
        }
        return maxIndex >= nums.length - 1;
    }


}
