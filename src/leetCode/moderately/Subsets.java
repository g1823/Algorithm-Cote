package leetCode.moderately;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gj
 * @description: 78. 子集
 */
public class Subsets {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(nums, 0, null, result);
        return result;
    }

    public void dfs(int[] nums, int index, List<Integer> list, List<List<Integer>> result) {
        if (index == nums.length) {
            result.add(new ArrayList<>(list));
            return;
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(nums[index]);
        dfs(nums, index + 1, list, result);
        list.remove(list.size() - 1);
        dfs(nums, index + 1, list, result);
    }
}
