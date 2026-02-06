package leetCode.moderately;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: gj
 * @description: 402. 移掉 K 位数字
 */
public class RemoveKdigits {

    public static void main(String[] args) {
        System.out.println(new RemoveKdigits().removeKdigits("1432219", 3));
    }

    /**
     * - 贪心（递归超时，极限情况下退化为n^2）：
     * - 1. 本题是一个典型的贪心问题，核心目标是：
     * -    在保持字符相对顺序不变的前提下，删除 k 个字符，使最终数字最小。
     * - 2. 数字大小的比较规则决定了贪心方向：
     * -    从左往右比较，越靠左的字符权重越大，
     * -    因此应尽可能让高位（左侧）的数字变小。
     * - 3. 若当前还剩 k 次删除机会，则“最终结果的下一位字符”
     * -    一定来自原字符串当前位置 i 开始的前 k+1 个字符中：
     * -        原因：
     * -        - 若选择的位置 > i+k，则左侧至少需要删除 >k 个字符，非法
     * -        - 因此合法区间只能是 [i, i+k]
     * - 4. 在该区间中，选择最小字符作为当前保留位，
     * -    并删除它左侧的所有字符（这些字符必然会让结果变大）。
     * - 5. 删除后，问题规模缩小，递归处理剩余部分。
     * - 6. 但需要注意：
     * -    若整个过程中始终未出现“右边更小”的情况（例如整体单调递增），
     * -    则递归不会触发任何删除。
     * -    此时，为了删够 k 个字符，只能从右往左删除（低权重位）。
     * - 7. 实现策略：
     * -    - 不在递归过程中真正删除字符
     * -    - 统一记录需要删除的下标
     * -    - 最后一次性构造结果字符串
     */
    public String removeKdigits(String num, int k) {
        Set<Integer> removeSet = new HashSet<>();
        char[] chars = num.toCharArray();
        getRemoveSet(chars, k, removeSet, 0);
        /**
         * 递归结束后，若仍未删够 k 个字符：
         * 说明整个数字在递归处理区间内是“非递减”的，
         * 即不存在“右边更小、左边更大”的局部逆序结构。
         * 在这种情况下，继续删除左侧高位只会让结果变大，
         * 因此最优策略只能是：
         *   —— 从右往左删除（删除低权重位）
         * 这一步在单调栈解法中，对应的是：
         *   while (k > 0) stack.pop();
         */
        for (int i = chars.length - 1; i >= 0 && removeSet.size() < k; i--) {
            if (!removeSet.contains(i)) {
                removeSet.add(i);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (!removeSet.contains(i)) {
                if (sb.length() == 0 && chars[i] == '0') {
                    continue;
                }
                sb.append(chars[i]);
            }
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }

    /**
     * 递归语义说明：
     * 在当前位置 i，且还剩 k 次删除机会的前提下，
     * 决定“当前结果的下一位”应该是谁，
     * 并删除其左侧所有必然劣于它的字符。
     */
    private void getRemoveSet(char[] chars, int k, Set<Integer> removeSet, int i) {
        if (k == 0 || i >= chars.length) {
            return;
        }
        /**
         * 关键点（也是之前容易犯错的地方）：
         * 搜索最小字符的范围必须是：
         *   [i, i + k]（包含 i+k）
         * 原因：
         *   当前位若选择 chars[i+k]，
         *   只需要删除 i 到 i+k-1 共 k 个字符，仍是合法方案。
         * 若遗漏 i+k 这个位置（例如只遍历到 i+k-1），
         * 会直接导致错误解，例如：
         *   num = "21", k = 1
         */
        int minIndex = i;
        char min = chars[i];
        for (int j = i + 1; j <= i + k && j < chars.length; j++) {
            if (chars[j] < min) {
                min = chars[j];
                minIndex = j;
            }
        }
        // 删除 minIndex 左侧的
        for (int j = i; j < minIndex && k > 0; j++) {
            if (!removeSet.contains(j)) {
                removeSet.add(j);
                k--;
            }
        }

        // 递归处理剩余部分
        getRemoveSet(chars, k, removeSet, minIndex + 1);
    }

    /**
     * - 单调栈
     * - 本题目标：在不改变字符相对顺序的前提下，删除 k 个字符，
     * - 使得到的数字最小。
     * - 数字大小的比较由“高位优先”决定，因此贪心的核心目标只有一个：
     * -   —— 尽可能让左侧（高权重位）的字符更小。
     * - 一、单调栈的核心语义
     * - 栈中存放的是：当前已经“确认保留”的结果前缀。
     * - 维护规则：
     * -   - 栈从底到顶保持【单调递增】
     * -   - 只要还有删除额度 k：
     * -       如果当前字符 < 栈顶字符，
     * -       说明：当前字符更适合站在更靠左的位置，
     * -       那么栈顶字符必然不可能出现在最优解中，
     * -       应当被删除（弹栈）。
     * - 这一过程会持续进行，直到：
     * -   - k 用完，或
     * -   - 栈顶字符 <= 当前字符
     * - ------------------------------------------------------------
     * - 二、与前面“递归解法”的一一对应关系
     * - 递归解法中：
     * -   1. 在区间 [i, i+k] 中寻找最小字符
     * -   2. 删除该最小字符左侧的所有字符
     * -   3. 递归处理剩余部分
     * - 单调栈中：
     * -   - 当前字符进入时，自动与“已保留前缀”比较
     * -   - while (栈顶 > 当前字符 && k > 0)：
     * -         等价于：
     * -         “删除最小字符左侧的所有更大字符”
     * - 区别仅在于：
     * -   - 递归是“先看一段，再统一删除”
     * -   - 单调栈是“边扫描，边即时删除”
     * - 两者在贪心语义上是完全等价的。
     * - ------------------------------------------------------------
     * - 三、为什么扫描结束后，还要从右往左删除
     * - 如果整个数字是单调不减的（例如 "12345"），
     * - 那么在扫描过程中，while 弹栈的条件永远不会触发，
     * - 但题目仍然要求删除 k 个字符。
     * - 此时：
     * -   - 左侧字符已经是最小可能
     * -   - 再删除左侧只会让结果变大
     * - 因此只能删除右侧（低权重位），
     * - 这一步在单调栈中体现为：
     * -   while (k > 0) stack.pop()
     */
    public String removeKdigits2(String num, int k) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : num.toCharArray()) {
            // 当前字符比已保留的右端字符更小，
            // 且还有删除额度，则删除右端（栈顶）的大字符
            while (!stack.isEmpty() && k > 0 && stack.peekLast() > c) {
                stack.pollLast();
                k--;
            }
            stack.offerLast(c);
        }
        // 若删除额度仍未用完，说明整体是非递减的，只能从右往左删
        while (k > 0 && !stack.isEmpty()) {
            stack.pollLast();
            k--;
        }
        // 构造最终结果，同时处理前导 0
        StringBuilder sb = new StringBuilder();
        boolean leadingZero = true;
        for (char c : stack) {
            if (leadingZero && c == '0') {
                continue;
            }
            leadingZero = false;
            sb.append(c);
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }
}
