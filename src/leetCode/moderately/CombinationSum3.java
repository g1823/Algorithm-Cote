package leetCode.moderately;

import java.util.*;

/**
 * @author: gj
 * @description: 216. 组合总和 III
 */
public class CombinationSum3 {

    public static void main(String[] args) {
        List<List<Integer>> lists = new CombinationSum3().combinationSum3(3, 9);
        System.out.println(lists);
    }

    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        Set<Integer> usedNumber = new HashSet<>();
        Set<String> usedPath = new HashSet<>();
        dfs(result, new ArrayList<>(), k, n, 0, usedNumber, usedPath);
        return result;
    }

    private void dfs(List<List<Integer>> result, List<Integer> path, int k, int n, int sum, Set<Integer> usedNumber, Set<String> usedPath) {
        if (path.size() == k - 1) {
            int rest = n - sum;
            if (usedNumber.contains(rest) || rest < 1 || rest > 9) {
                return;
            }
            path.add(rest);
            List<Integer> copyPath = new ArrayList<>(path);
            copyPath.sort(Comparator.comparingInt(o -> o));
            String p = copyPath.toString();
            if (!usedPath.contains(p)) {
                result.add(copyPath);
                usedPath.add(p);
            }
            path.remove(path.size() - 1);
            return;
        }
        for (int i = 1; i < 9; i++) {
            if (usedNumber.contains(i)) {
                continue;
            }
            if (sum + i > n) {
                continue;
            }
            path.add(i);
            usedNumber.add(i);
            dfs(result, path, k, n, sum + i, usedNumber, usedPath);
            usedNumber.remove(i);
            if (path.size() > 0) {
                path.remove(path.size() - 1);
            }
        }
    }

    /**
     * - 解题思路（优化后）：
     * - 一、问题本质分析
     * - --------------------------------------------------
     * - 题目要求：
     * - 1）从 1 ~ 9 中选 k 个数
     * - 2）每个数字只能使用一次
     * - 3）数字之和等于 n
     * - 4）结果是“组合”，顺序不重要
     * - 关键结论：
     * - - 这是一个【组合型回溯】问题，而不是排列问题
     * - - 只要在搜索阶段保证“数字递增”，就天然不会产生重复解
     * - 二、递归模型设计
     * - --------------------------------------------------
     * - dfs 参数含义：
     * - - path：当前已选择的数字集合
     * - - sum：path 中数字的当前和
     * - - start：下一层递归可以选择的最小数字
     * - 不变量：
     * - - path 中的数字严格递增
     * - - 每个数字最多使用一次
     * - 因此：
     * - - 不需要 usedNumber（start 已经隐含“不能重复使用”）
     * - - 不需要 usedPath（递增搜索不会产生重复组合）
     * - 三、终止条件设计
     * - --------------------------------------------------
     * - 1）如果 path.size() == k：
     * -    - 若 sum == n，说明找到一个合法组合
     * -    - 否则直接返回
     * - 2）剪枝：
     * -    - sum > n：当前路径不可能再合法，直接返回
     * -    - path.size() > k：选数超出限制，直接返回
     * - 四、与原解法的核心优化点对比
     * - --------------------------------------------------
     * - 原解法中的做法：
     * - 1）使用 usedNumber Set 记录是否使用过数字
     * - 2）使用 usedPath Set + 排序 + toString 去重结果
     * - 3）在 path.size() == k - 1 时，特殊处理“补最后一个数”
     * - 优化后的改进：
     * - 1）通过 start 控制搜索顺序，天然避免数字重复 → 删除 usedNumber
     * - 2）通过“递增组合搜索”，从源头杜绝重复路径 → 删除 usedPath + 排序
     * - 3）统一递归终止条件，不再区分“补最后一个数”的特殊逻辑
     * - 本质变化：
     * - - 原解法是「先生成，再去重」
     * - - 优化解法是「从搜索模型上就不生成重复解」
     * - 五、总结
     * - --------------------------------------------------
     * - 本题的优化关键不在于剪枝技巧，
     * - 而在于正确识别“组合问题”这一抽象模型，
     * - 并用搜索顺序（start）替代所有显式的去重结构。
     */
    class solution2 {
        public List<List<Integer>> combinationSum3(int k, int n) {
            List<List<Integer>> result = new ArrayList<>();
            dfs(result, new ArrayList<>(), k, n, 1, 0);
            return result;
        }

        private void dfs(List<List<Integer>> result,
                         List<Integer> path,
                         int k,
                         int n,
                         int start,
                         int sum) {

            // 剪枝
            if (sum > n) {
                return;
            }
            if (path.size() > k) {
                return;
            }

            // 成功条件
            if (path.size() == k) {
                if (sum == n) {
                    result.add(new ArrayList<>(path));
                }
                return;
            }

            // 从 start 开始，保证组合不重复
            for (int i = start; i <= 9; i++) {
                path.add(i);
                dfs(result, path, k, n, i + 1, sum + i);
                path.remove(path.size() - 1);
            }
        }
    }
}
