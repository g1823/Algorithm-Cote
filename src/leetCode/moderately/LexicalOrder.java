package leetCode.moderately;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gj
 * @description: 386. 字典序排数
 */
public class LexicalOrder {

    /**
     * 思路：直接深度优先，从1开始，每次将当前数 *10 然后再次从0-9遍历：
     * 举例：
     * 假设n = 50;
     * 遍历顺序：
     * - 1（加入）
     * - - 1 * 10 + 0 = 10 (加入)
     * - - - 10 * 10 + 0 = 100 (结束)
     * - - 1 * 10 + 1 = 11 (加入)
     * ...
     * - - 1 * 10 + 9 = 19 (加入)
     * - - - 19 * 10 + 0 = 190 (结束)
     * - 2 (加入)
     * - - 2 * 10 + 0 = 20 (加入)
     * - - - 20 * 10 + 0 = 200 (结束)
     */
    public List<Integer> lexicalOrder(int n) {
        int k = Math.min(9, n);
        List<Integer> res = new ArrayList<>(n);
        for (int i = 1; i <= k; i++) {
            dfs(n, i, res);
        }
        return res;
    }

    public void dfs(int n, int k, List<Integer> res) {
        if (k > n) {
            return;
        }
        res.add(k);
        for (int i = 0; i < 10; i++) {
            dfs(n, k * 10 + i, res);
        }
    }

    /**
     * lexicalOrder()递归的优化，改为迭代实现
     * 题目要求空间复杂度O(1)，递归因为栈空间开辟方法栈，空间复杂度是O(lgn)
     * 采用迭代实现，空间复杂度O(1)
     */
    public List<Integer> lexicalOrder2(int n) {
        List<Integer> ret = new ArrayList<>(n);
        // 从 1 开始
        int number = 1;
        // 一共要添加 n 个数
        for (int i = 0; i < n; i++) {
            // 添加当前节点
            ret.add(number);
            if (number * 10 <= n) {
                // 向下走：进入子节点
                number *= 10;
            } else {
                // 无法向下走时，需要向上回溯，找到下一个兄弟节点
                // 当个位是 9 或者 当前节点已经是最右端（加 1 会超过 n）时，需要一直回溯
                while (number % 10 == 9 || number + 1 > n) {
                    // 回溯到父节点
                    number /= 10;
                }
                // 然后移动到父节点的下一个兄弟节点
                number++;
            }
        }
        return ret;
    }

}
