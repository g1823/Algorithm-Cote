package leetCode.moderately;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gj
 * @description: 763. 划分字母区间
 */
public class PartitionLabels {
    public static void main(String[] args) {
        String s = "vhaagbqkaq";
        System.out.println(new PartitionLabels().partitionLabels(s));
    }

    /**
     * 解题思路：
     * 题目要求将字符串划分成尽可能多的片段，保证同一字母只会出现在一个片段中。
     * 思考过程：
     * 1. 关键点是：每个字母必须完全落在某个片段内，因此需要知道每个字母最后出现的位置。
     * 2. 第一步先预处理字符串，记录每个字母的最后一次出现下标 last[]。
     * 3. 然后从左到右遍历字符串：
     * - 对于当前字符，查找它的最后出现位置；
     * - 更新当前区间的最远边界 minScope；
     * - 如果当前下标 i 到达了 minScope，说明该区间内所有字母的最后位置都已覆盖，可以完成一个切分。
     * 4. 遍历过程中先记录每个区间的右边界位置；
     * 最后再通过一次遍历，将这些边界转化为具体的区间长度（即相邻边界的差值）。
     * 时间复杂度：O(n)，其中 n 为字符串长度。
     * 空间复杂度：O(1)，仅使用一个长度为 26 的数组存储字母最后出现位置。
     */
    public List<Integer> partitionLabels(String s) {
        // 边界情况：空字符串直接返回空结果
        if (s == null || s.length() == 0) {
            return new ArrayList<>();
        }
        // 记录每个字符最后一次出现的下标
        int[] last = new int[26];
        for (int i = 0; i < s.length(); i++) {
            last[s.charAt(i) - 'a'] = i;
        }
        // 当前区间的最远边界，初始化为第一个字符的最后出现位置
        int minScope = last[s.charAt(0) - 'a'];
        // 用于存储每个区间的结束位置（后续会转成长度）
        List<Integer> res = new ArrayList<>();
        // 遍历字符串，动态更新区间的最远边界
        for (int i = 0; i < s.length(); i++) {
            // 当前字符的最后出现位置
            int thisScope = last[s.charAt(i) - 'a'];
            // 更新当前区间的最远边界
            minScope = Math.max(minScope, thisScope);
            // 如果当前位置到达区间边界，说明可以切分出一个区间
            if (i >= minScope) {
                // 暂存区间的右边界索引
                res.add(i);
            }
        }
        // 将区间右边界转化为区间长度
        for (int i = res.size() - 1; i >= 0; i--) {
            // 计算当前区间的左边界（前一个区间右边界 + 1）
            int lastIndex = i == 0 ? -1 : res.get(i - 1);
            // 用右边界减去左边界，得到区间长度
            res.set(i, res.get(i) - lastIndex);
        }
        return res;
    }

    /**
     * 解题思路（优化版）：
     * 原始解法的核心是：
     * 1. 先记录每个字符的最后出现位置；
     * 2. 遍历字符串时，找到每个区间的结束位置并存储；
     * 3. 最后再通过一次遍历，把这些结束位置转换为区间长度。
     * 优化点：
     * - 在原始解法中，区间长度的计算是分成两步完成的：
     * a) 第一次遍历只保存右边界索引；
     * b) 第二次遍历再把索引转成区间长度。
     * - 实际上，我们完全可以在第一次遍历过程中，直接算出区间长度并存储，这样就不需要第二次循环了，代码更简洁，执行效率也略高。
     * 优化后的做法：
     * 1. 和原始解法一样，先记录每个字符最后出现位置。
     * 2. 遍历字符串，用变量 end 维护当前区间的最远边界；
     * 3. 当遍历下标 i == end 时，说明找到了一个完整区间：
     * - 区间长度 = end - start + 1；
     * - 将区间长度加入结果；
     * - 更新 start = i + 1，为下一个区间做准备。
     * 时间复杂度：O(n)，只需要遍历字符串两次（一次建表，一次切分）。
     * 空间复杂度：O(1)，只使用一个 26 大小的数组。
     */
    public List<Integer> partitionLabels2(String s) {
        // 记录每个字符最后一次出现的位置
        int[] last = new int[26];
        for (int i = 0; i < s.length(); i++) {
            last[s.charAt(i) - 'a'] = i;
        }
        List<Integer> res = new ArrayList<>();
        // start 表示当前区间的起始位置
        int start = 0;
        // end 表示当前区间的最远边界
        int end = 0;
        // 遍历字符串
        for (int i = 0; i < s.length(); i++) {
            // 当前字符的最后出现位置
            end = Math.max(end, last[s.charAt(i) - 'a']);
            // 如果到达当前区间的最远边界，说明区间可以切分
            if (i == end) {
                // 区间长度 = 右边界 - 左边界 + 1
                res.add(end - start + 1);
                // 更新下一个区间的起始位置
                start = i + 1;
            }
        }
        return res;
    }

}
