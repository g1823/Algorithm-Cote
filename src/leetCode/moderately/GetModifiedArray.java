package leetCode.moderately;

/**
 * @author: gj
 * @description: 370. 区间加法
 */
public class GetModifiedArray {
    /**
     * 差分数组：
     * 可以老老实实对每次操作进行调整,但是复杂度非常高，因为每个操作都需要从0到n进行修改，最终复杂度为O(mn)了
     * 但是也可以采用差分的思想:
     * - 操作[l,r,d]可以理解为：对原数组l -> n做 +d 操作，对原数组 r->n 做-d操作。
     * - 这样可以一次把所有操作累积到一块，最后一次性修改原数组。
     * 由于原数组值都为0，操作被分解为从当前元素到数组末尾，因此每个操作的影响都需要向后扩展，那么最终累计结果就跟前缀和一样，ans[i] += ans[i-1]。
     */
    public int[] getModifiedArray(int length, int[][] updates) {
        int[] ans = new int[length];
        for (int[] update : updates) {
            int l = update[0];
            int r = update[1];
            int d = update[2];
            ans[l] += d;
            if (r + 1 < length) {
                ans[r + 1] -= d;
            }
        }
        for (int i = 1; i < length; i++) {
            ans[i] += ans[i - 1];
        }
        return ans;
    }

}
