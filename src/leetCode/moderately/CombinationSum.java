package leetCode.moderately;

import java.util.*;

/**
 * @author: gj
 * @description: 39. 组合总和
 */
public class CombinationSum {

    public static void main(String[] args) {
        int[] candidates = {2, 3, 6, 7};
        int target = 7;
        System.out.println(combinationSum(candidates, target));
    }

    /**
     * 采用动态规划(完全背包)解决组合总和问题
     * 使用set去重
     */
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<List<Integer>>[] dp = new ArrayList[target + 1];
        Set<String> set = new HashSet<>();
        for (int i = 0; i < dp.length; i++) {
            dp[i] = new ArrayList<>();
        }
        for (int candidate : candidates) {
            for (int i = candidate; i <= target; i++) {
                List<List<Integer>> t = dp[i - candidate];
                if (t != null && t.size() > 0) {
                    for (List<Integer> list : t) {
                        if (list != null && list.size() > 0) {
                            List<Integer> newList = new ArrayList<>(list);
                            newList.add(candidate);
                            dp[i].add(newList);
                        }
                    }
                }
                if (i == candidate) {
                    List<Integer> newList = new ArrayList<>();
                    newList.add(candidate);
                    dp[i].add(newList);
                }
                if (i == target && dp[i].size() > 0) {
                    List<List<Integer>> tr = dp[i];
                    for (List<Integer> list : tr) {
                        StringBuilder sb = new StringBuilder();
                        for (Integer integer : list) {
                            sb.append(integer).append("_");
                        }
                        if (set.contains(sb.toString())) {
                            continue;
                        } else {
                            set.add(sb.toString());
                        }
                        List<Integer> r1 = new ArrayList<>(list);
                        result.add(r1);
                    }
                }
            }
        }
        return result;
    }


    /**
     * 使用动态规划（完全背包思想）求解组合总和问题。
     * 对 combinationSum 的优化说明：
     * - 对 candidates 数组排序，以确保组合中元素按非递减排列，从而防止重复组合。
     * - 使用 dp[i] 表示和为 i 的所有组合，初始设置 dp[0] = [[]]，表示和为 0 的空组合。
     * - 对每个 candidate 遍历时，通过将其添加到 dp[i - candidate] 中已有组合来构造 dp[i]，只允许添加到非递减的组合末尾，避免重复。
     * - 不需要显式判断 i == candidate 或添加单元素组合，因为 dp[0] 已包含空集组合，自然会生成基本组合。
     * - 整个构造过程中只追加组合，无需删除或去重操作，最终直接返回 dp[target] 即为所有符合要求的组合。
     * 每个候选数字可以重复选择任意次，要求找出所有和为 target 的组合，组合内数字必须非递减排列。
     * <p>
     * 思路：
     * - 候选数组排序，确保组合有序以去重。
     * - dp[i] 表示组成和为 i 的所有组合（每个组合是一个 List<Integer>）。
     * - 对于每个数字 candidate：
     * - 从 candidate 到 target 遍历背包容量 i
     * - 将 dp[i - candidate] 中的每个组合加上 candidate 得到新的组合加入 dp[i]
     * - 为防止重复组合，只允许 candidate >= 当前组合最后一个数
     *
     * @param candidates 候选数字数组（正整数，无重复）
     * @param target     目标和
     * @return 所有和为 target 的非递减组合（数字可重复使用）
     */
    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        // 排序，确保后续组合中元素递增排列，避免重复组合
        Arrays.sort(candidates);

        // dp[i] 表示和为 i 的所有组合（每个组合是一个 List<Integer>）
        List<List<Integer>>[] dp = new ArrayList[target + 1];
        for (int i = 0; i <= target; i++) {
            dp[i] = new ArrayList<>();
        }

        // 初始状态：dp[0] 表示和为 0 的唯一组合 —— 空集合
        dp[0].add(new ArrayList<>());

        // 遍历每个候选数字，作为背包物品
        for (int candidate : candidates) {
            for (int i = candidate; i <= target; i++) {
                // 遍历 dp[i - candidate] 的每个组合，尝试添加当前数字
                for (List<Integer> prev : dp[i - candidate]) {
                    // 为避免重复组合，只有当当前数字 ≥ 组合最后一个数时才添加（保证递增）
                    if (prev.isEmpty() || candidate >= prev.get(prev.size() - 1)) {
                        List<Integer> newList = new ArrayList<>(prev);
                        // 添加当前数字
                        newList.add(candidate);
                        dp[i].add(newList);
                    }
                }
            }
        }

        // 返回所有组合和为 target 的组合
        return dp[target];
    }


    public static List<List<Integer>> combinationSum3(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(result, new ArrayList<>(), candidates, target, 0);
        return result;
    }

    /**
     * 深度优先搜索方法，构建所有合法的组合
     *
     * @param result     最终结果列表，保存所有符合条件的组合
     * @param list       当前组合
     * @param candidates 候选数组
     * @param target     当前剩余目标值
     * @param index      当前遍历到的候选数组下标
     */
    public static void dfs(List<List<Integer>> result, List<Integer> list, int[] candidates, int target, int index) {
        if (index == candidates.length) {
            return;
        }
        // 剪枝:当前组合已经等于目标和，之后再添加任意元素都会超出
        if (target == 0) {
            result.add(new ArrayList<>(list));
            return;
        }
        // 不放入当前元素(可能之前已经放入过了，本次不放入)，遍历下一个元素
        dfs(result, list, candidates, target, index + 1);
        // 放入当前元素，剪枝，放入后超过target，则直接剪枝
        if (target - candidates[index] >= 0) {
            list.add(candidates[index]);
            // 继续遍历当前元素，即index不变，因为每个元素可以添加多次
            dfs(result, list, candidates, target - candidates[index], index);
            list.remove(list.size() - 1);
        }
    }
}
