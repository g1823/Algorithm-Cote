package leetCode.moderately;

import java.util.HashSet;
import java.util.Set;

/**
 * @description: 2657. 找到两个数组的前缀公共数组
 */
public class FindThePrefixCommonArray {
    /**
     * 因为题目说明了A和B都是n的排排列，那意味着不会出现重复数字
     * 只需要是使用hash结构存储已经出现的数字即可。
     * ans[i] 基于 ans[i-1] 增量更新，而不是每次重新统计前缀交集。
     * 核心思路：
     * 每次下标 i 时：
     * A 新加入：a = A[i]
     * B 新加入：b = B[i]
     * 只有这两个新元素可能导致公共元素数量增加。
     * 分类讨论：
     * 1、a == b
     * 说明该数字第一次同时出现在两个前缀中：ans[i] = ans[i-1] + 1
     * 2、a != b
     * 分别判断：
     * -     a 是否之前出现在 B 前缀中
     * -     b 是否之前出现在 A 前缀中
     * 每满足一个条件：公共元素数量 +1，因此最多增加 2。
     * 数据结构：
     * 使用两个 HashSet（或 boolean[]）：
     * seenA：
     * 记录 A 前缀已经出现过的元素
     * seenB：
     * 记录 B 前缀已经出现过的元素
     * 从而 O(1) 判断某元素是否已经出现在另一数组前缀中。
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    public int[] findThePrefixCommonArray(int[] A, int[] B) {
        int[] res = new int[A.length];
        // 题目说明了n小于51
        boolean[] seenA = new boolean[51];
        boolean[] seenB = new boolean[51];
        for (int i = 0; i < A.length; i++) {
            int t = 0;
            if (A[i] == B[i]) {
               t++;
            }else{
                if(seenB[A[i]]){
                    t++;
                }
                if(seenA[B[i]]){
                    t++;
                }
            }
            seenA[A[i]] = true;
            seenB[B[i]] = true;
            res[i] = i == 0 ? t : res[i - 1] + t;
        }
        return res;
    }

    /**
     * 我们观察到题目给定的数组元素范围为：1≤A[i],B[i]≤n，且 n 的范围为 1≤n≤50，
     * 此时我们可以将一个集合压缩成一个数的二进制，第 i 为 1 表示存在数字 i，两个数的按位与就代表集合的交集，交集的大小就是二进制中 1 的个数。
     *
     */
    public int[] findThePrefixCommonArray2(int[] A, int[] B) {
        long p = 0, q = 0;
        for (int i = 0; i < A.length; i++) {
            p |= 1L << A[i];
            q |= 1L << B[i];
            A[i] = Long.bitCount(p & q);
        }
        return A;
    }
}
