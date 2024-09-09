package leetCode.simple;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gj
 * @description: 448. 找到所有数组中消失的数字
 */
public class FindDisappearedNumbers {
    public static void main(String[] args) {

    }

    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> result = new ArrayList<>();
        int[] temp = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            temp[nums[i]] += 1;
        }
        for (int i = 1; i < temp.length; i++) {
            if (temp[i] == 0) result.add(i);
        }
        return result;
    }

    public List<Integer> findDisappearedNumbers2(int[] nums) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int t = Math.abs(nums[i]);
            if (nums[t - 1] > 0) nums[t - 1] = -nums[t - 1];
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) result.add(i + 1);
        }
        return result;
    }
}
