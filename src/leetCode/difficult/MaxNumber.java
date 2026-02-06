package leetCode.difficult;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author: gj
 * @description: 321. 拼接最大数
 */
public class MaxNumber {
    public static void main(String[] args) {
        int[] nums1 = {5, 2, 2};
        int[] nums2 = {6, 4, 1};
        System.out.println(new MaxNumber().maxNumber(nums1, nums2, 3));
    }

    /**
     * - 单调栈+贪心
     * - 本题要求从 nums1 和 nums2 两个数组中选取 k 个数字，保持各自数组内部的相对顺序，
     * - 并通过任意穿插的方式组成字典序最大的结果。
     * - 一、最初的直觉与单数组类比
     * - ------------------------------------------------
     * - 第一反应是将问题类比为 316 / 402 等“单数组选取最大子序列”的问题：
     * - - 目标是让高位尽可能大；
     * - - 相对顺序不可改变；
     * - - 可以使用单调栈贪心，在保证“剩余元素足以补齐目标长度”的前提下，
     * -   弹出较小的栈顶元素，用更大的当前元素替换。
     * -
     * - 对于“单数组中选取长度为 k 的最大子序列”，这一思路是完全正确且成熟的。
     * -
     * - 二、遇到的第一个问题：双数组导致贪心条件失效
     * - ------------------------------------------------
     * - 当尝试将上述思路直接推广到两个数组时，会立刻遇到困难：
     * - - 在单数组中，“后面是否还能补齐 k 个”是确定的；
     * - - 在双数组中，未来元素来源于 nums1 或 nums2，是不确定的；
     * - - 因此，无法直接判断当前是否可以安全地“弹栈”。
     * - 失去了“还能不能补齐”的确定性
     * - 这说明：不能把两个数组简单当成一个更大的数组来做贪心。
     * -
     * - 三、关键转折：将原问题拆分为三个子问题
     * - ------------------------------------------------
     * - 经过分析发现，问题可以拆解为：
     * - 1）从 nums1 中选取 i 个数字，组成长度为 i 的最大子序列；
     * - 2）从 nums2 中选取 k - i 个数字，组成长度为 k - i 的最大子序列；
     * - 3）将两个“已经各自最优”的子序列合并，得到字典序最大的整体序列。
     * - 其中 i 需要枚举所有合法取值（包括 0 和 k），因为最优解可能完全来自某一个数组。
     * -
     * - 前两个子问题本质仍是“单数组最大子序列”，可以直接使用单调栈贪心解决。
     * - 证明：两个子序列最优可以组合出全局最优
     * - 对于任意全局最优解，其在 nums1（或 nums2）中选取的长度固定的子序列，
     * - 若不是该数组下的最大子序列，则可用更大的子序列替换而不破坏相对顺序，
     * - 从而使整体字典序不减甚至增大，产生矛盾。
     * - 因此，全局最优解必然由两个单数组最优子序列组成。
     * - 在此基础上，通过逐位比较剩余整体字典序进行合并，可保证全局最优。
     * -
     * - 四、第二个核心难点：合并两个子序列
     * - ------------------------------------------------
     * - 合并阶段最初的直觉是：
     * - - 类似归并排序；
     * - - 比较当前元素，谁大就取谁；
     * - - 若相等，则“随便取一个”，后续再比较下一位。
     * -
     * - 但这一策略是错误的。
     * -
     * - 原因在于：与归并排序不同，本题中“取走哪个元素”会直接改变剩余序列，
     * - 从而影响未来的比较结果。一旦在相等元素处做出错误选择，
     * - 可能会导致后续更大的数字被推迟，整体字典序变小。
     * -
     * - 五、关键修正：相等时必须比较“剩余整体字典序”
     * - ------------------------------------------------
     * - 当 nums1Sub[j1] == nums2Sub[j2] 时，不能只看当前或下一位，
     * - 而必须比较 nums1Sub[j1:] 与 nums2Sub[j2:] 的整体字典序：
     * -
     * - - 若 nums1Sub[j1:] 字典序更大，则应优先选择 nums1Sub[j1]；
     * - - 否则选择 nums2Sub[j2]；
     * - - 若一路相等直到一方耗尽，则选择剩余更长的那一方。
     * -
     * - 这样做的本质是：
     * - 当前选择必须保证“剩余整体仍然尽可能大”，
     * - 才能确保全局最优。
     * -
     * - 六、边界问题修正
     * - ------------------------------------------------
     * - 1）枚举 i 时，必须包含 i = 0 和 i = k：
     * -    - i = 0：全部从 nums2 取；
     * -    - i = k：全部从 nums1 取。
     * -
     * - 2）合并阶段需严格区分“哪一边先耗尽”，避免下标错位或数组越界。
     * -
     * - 七、最终结论
     * - ------------------------------------------------
     * - 本题并非单纯的贪心失败，而是：
     * - - 单数组贪心仍然正确；
     * - - 双数组问题必须通过“先拆分、再合并”的方式恢复确定性；
     * - - 合并阶段需要前瞻比较剩余序列，以保证字典序最优。
     */

    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        Deque<Integer> nums1Deque = new LinkedList<>();
        Deque<Integer> nums2Deque = new LinkedList<>();
        int[] nums1Sub = new int[Math.min(nums1.length, k)];
        int[] nums2Sub = new int[Math.min(nums2.length, k)];
        int[] result = new int[k];
        boolean hasResult = false;
        // 可能全部从nums1中取，也可能全部从nums2中取，因此需要从0到k进行遍历
        for (int i = 0; i <= k; i++) {
            nums1Deque.clear();
            nums2Deque.clear();
            // nums1中取不出i个数，num2中取不出k-i个数，则跳过
            if (i > nums1.length || k - i > nums2.length) {
                continue;
            }
            // 获取nums1中长度为i的最大的子序列
            for (int j = 0; j < nums1.length; j++) {
                if (nums1Deque.size() + nums1.length - j == i) {
                    nums1Deque.offerLast(nums1[j]);
                    continue;
                }
                // nums1中剩余元素还足以凑够i个数。且栈顶元素小于当前元素，则弹出栈顶元素，替换为当前元素
                while (!nums1Deque.isEmpty() && nums1Deque.peekLast() < nums1[j] && nums1.length - j > i - nums1Deque.size()) {
                    nums1Deque.pollLast();
                }
                nums1Deque.offerLast(nums1[j]);
            }

            // 获取nums2中长度为k-i的最大的子序列
            for (int j = 0; j < nums2.length; j++) {
                if (nums2Deque.size() + nums2.length - j == k - i) {
                    nums2Deque.offerLast(nums2[j]);
                    continue;
                }
                while (!nums2Deque.isEmpty() && nums2Deque.peekLast() < nums2[j] && nums2.length - j > k - i - nums2Deque.size()) {
                    nums2Deque.pollLast();
                }
                nums2Deque.offerLast(nums2[j]);
            }

            for (int j = 0; j < i; j++) {
                nums1Sub[j] = nums1Deque.pollFirst();
            }
            for (int j = 0; j < k - i; j++) {
                nums2Sub[j] = nums2Deque.pollFirst();
            }

            int[] r = new int[k];
            int j = 0, j1 = 0, j2 = 0;

            while (j1 < i && j2 < k - i) {
                // nums1中当前元素大于nums2中当前元素，则nums1中当前元素加入结果中
                if (nums1Sub[j1] > nums2Sub[j2]) {
                    r[j++] = nums1Sub[j1++];
                }
                // nums1中当前元素小于nums2中当前元素，则nums2中当前元素加入结果中
                else if (nums1Sub[j1] < nums2Sub[j2]) {
                    r[j++] = nums2Sub[j2++];
                }
                // nums1和nums2的当前元素相等，则继续比较整体谁大
                else {
                    int j3 = j1, j4 = j2;
                    while (j3 < i && j4 < k - i && nums1Sub[j3] == nums2Sub[j4]) {
                        j3++;
                        j4++;
                    }
                    // nums1整体更大，先选nums1可以让nums1后面的跟大元素先放，进而是的结果更大
                    // 或nums1还有剩余，而nums2没有剩余
                    if (j4 == k - i || (j3 < i && nums1Sub[j3] > nums2Sub[j4])) {
                        r[j++] = nums1Sub[j1++];
                    } else {
                        r[j++] = nums2Sub[j2++];
                    }
                }
            }

            while (j1 < i) {
                r[j++] = nums1Sub[j1++];
            }
            while (j2 < k - i) {
                r[j++] = nums2Sub[j2++];
            }

            if (!hasResult) {
                System.arraycopy(r, 0, result, 0, k);
                hasResult = true;
            } else {
                for (int p = 0; p < k; p++) {
                    if (result[p] == r[p]) {
                        continue;
                    }
                    if (result[p] < r[p]) {
                        System.arraycopy(r, 0, result, 0, k);
                    }
                    break;
                }
            }
        }
        return result;
    }

}
