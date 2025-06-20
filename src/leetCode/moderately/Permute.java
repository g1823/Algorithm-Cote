package leetCode.moderately;

import java.util.*;

/**
 * @author: gj
 * @description: 46. 全排列
 */
public class Permute {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        List<List<Integer>> result = permute(nums);
        System.out.println(result);
    }

    /**
     * 使用回溯算法 + LinkedHashSet 去重并保持排列顺序，依次尝试每个数字作为排列的起点，
     * 并递归构建剩余的排列，直到形成一个完整的长度为 nums.length 的排列。
     *
     * @param nums 要排列的整数数组，数组中不包含重复元素
     * @return 所有可能的排列列表，每个排列为一个整数列表
     */
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(result, new LinkedHashSet<Integer>(), nums);
        return result;
    }

    /**
     * 当前路径使用 LinkedHashSet 记录已选数字并保持顺序，
     * 在每一层尝试加入未使用的数字，并递归构造完整排列。
     * 当 set 中元素数量等于 nums.length 时，说明已经构成一个完整的排列。
     *
     * @param result 存放所有合法排列的结果列表
     * @param set    当前构造中的排列路径（LinkedHashSet 保证元素唯一且有序）
     * @param nums   可用的原始数组元素
     */
    public static void dfs(List<List<Integer>> result, LinkedHashSet<Integer> set, int[] nums) {
        if (set.size() == nums.length) {
            result.add(new ArrayList<>(set));
            return;
        }
        for (int num : nums) {
            if (set.contains(num)) {
                continue;
            }
            set.add(num);
            dfs(result, set, nums);
            set.remove(num);
        }
    }


    /**
     * 对于permute的优化：
     * 采用布尔数组代替set，采用 List<Integer> path存储路径，避免重复创建
     */
    public static List<List<Integer>> permute2(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        dfs2(nums, used, new ArrayList<>(), result);
        return result;
    }

    private static void dfs2(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> result) {
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }
            used[i] = true;
            path.add(nums[i]);
            dfs2(nums, used, path, result);
            path.remove(path.size() - 1);
            used[i] = false;
        }
    }
}
