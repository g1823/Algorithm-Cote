package leetCode.moderately;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gj
 * @description: 77. 组合
 */
public class Combine {
    public static void main(String[] args) {
        List<List<Integer>> combine = new Combine().combine(4, 4);
        System.out.println(combine);
    }

    /**
     * 思路说明：
     * 使用 DFS + 剪枝 生成所有从 1~n 中选 k 个数字的组合。
     * 为了减少递归层数，在递归深度达到 k - 1 时，不继续深入 DFS，
     * 而是直接补齐最后一位可能的数字并加入结果列表，从而减少一层递归开销。
     * <p>
     * 此外，在普通 DFS 中，每次循环 i 都可以从 curIndex+1 一直走到 n。
     * 但这里进行了剪枝：当剩余可用的数字数量不足以凑满 k 个时，不再继续循环。
     * 剪枝条件：当前选择第 curLength 个数字，剩余必须还能选 (k - curLength) 个，
     * 因此 i 最大只能走到 n - (k - curLength) + 1。
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        // 使用 curIndex 表示当前层选取的数字的起点（上一层选取的数字）
        dfs(result, new ArrayList<>(), 0, n, k);
        return result;
    }

    /**
     * DFS 构造组合
     * cur：当前组合路径
     * curIndex：上一层选取的数字，用于确保组合递增且不重复
     */
    public void dfs(List<List<Integer>> result, List<Integer> cur, int curIndex, int n, int k) {
        int curLength = cur.size();

        // 如果已选了 k - 1 个数字，则最后一位可以直接补齐所有可能的数字
        // 这样能减少一层 DFS 的开销
        if (curLength == k - 1) {
            for (int i = curIndex + 1; i <= n; i++) {
                List<Integer> r = new ArrayList<>(cur);
                r.add(i);
                result.add(r);
            }
            return;
        }

        // 剪枝循环：确保剩余数字数量足够填满 k 个
        // i 最大为 n - (k - curLength) + 1
        for (int i = curIndex + 1; i <= n - (k - curLength) + 1; i++) {
            // 选择当前数字 i
            cur.add(i);

            // 递归构造下一层，下一层的起点变为 i
            dfs(result, cur, i, n, k);

            // 回溯，移除当前层加入的数字
            cur.remove(curLength);
        }
    }
}
