package leetCode.moderately;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author: gj
 * @description: 316. 去除重复字母
 */
public class RemoveDuplicateLetters {

    /**
     * - 单调栈+贪心
     * -
     * - 一、问题本质
     * - 本题要求在保持原字符串相对顺序不变（子序列）的前提下，
     * - 删除多余字符，使得：
     * - 1）每个字符只出现一次
     * - 2）最终结果的字典序最小
     * -
     * - 核心矛盾在于：
     * - - 同一个字符出现多次，必须删除
     * - - 删除哪一个，会影响字典序
     * -
     * - 二、人类直觉到贪心目标
     * - 对于重复字符：
     * - - 字符越小，越应该尽量靠前保留
     * - - 字符越大，越应该尽量往后放
     * -
     * - 因此在遍历过程中，当遇到一个更小的字符时，
     * - 如果前面已经选入结果的某些字符“不够优”，
     * - 且它们还能在后面再次出现，则应当删除它们。
     * -
     * - 这实际上是在所有合法结果中，贪心地构造字典序最小的前缀。
     * -
     * - 三、为什么使用单调栈
     * - 使用栈来维护“当前已选择的最优前缀”，并维持如下不变量：
     * -
     * - —— 栈中字符串始终是：
     * -    在当前扫描位置之前，
     * -    所有「仍可扩展为合法解」的方案中，
     * -    字典序最小的那个前缀。
     * -
     * - 栈的特性保证了：
     * - - 所有可撤销的选择都只发生在末尾（栈顶）
     * - - 删除操作不会破坏已确定的前缀结构
     * -
     * - 四、删除条件的严格约束（贪心合法性的关键）
     * - 在处理当前字符 c 时，只能在以下条件同时满足时删除栈顶字符 x：
     * -
     * - 1）x > c
     * -    —— 删除 x、让 c 靠前，才能使字典序变小
     * -
     * - 2）x 在当前位置之后还会再次出现
     * -    —— 删除 x 不会导致最终结果缺少该字符
     * -
     * - 若栈顶字符 x 在后续不会再出现，则 x 绝不可删除，
     * - 否则将无法构造合法解。
     * -
     * - 五、为什么只允许删除“栈顶”
     * - 单调栈的一个关键性质是：
     * - - 所有删除操作只能发生在栈顶
     * - - 不可能跳过栈顶删除更早的字符
     * -
     * - 因此，一旦某个字符因为“无法补回”而被保留在栈顶，
     * - 它会阻断所有更早字符的删除操作，
     * - 不存在“删除较小字符却被迫提前较大字符”的情况。
     * -
     * - 这也解释了：
     * - 不会出现删除 c 却导致更大的 x 被提前、从而使结果变大的反例。
     * -
     * - 六、为什么要跳过已入栈的字符
     * - 每个字符在结果中只能出现一次。
     * - 当某字符已经入栈时：
     * - - 再次入栈不会让字典序更小
     * - - 反而会干扰后续更优字符的位置
     * -
     * - 因此，字符是否“已经被选入结果”，
     * - 本身就是一个是否具有决策价值的判断条件。
     * -
     * - 七、贪心正确性的总结
     * - 本算法的正确性基于三点：
     * - 1）字典序的前缀单调性：前面一位更小，整体必然更优
     * - 2）可补回约束：只有能在后续补回的字符才允许删除
     * - 3）栈顶唯一性：所有可撤销决策都局限于当前前缀的末尾
     * -
     * - 在上述约束下，反复删除满足条件的栈顶字符，
     * - 可以保证构造出的结果既合法，又是全局字典序最小。
     */

    public String removeDuplicateLetters(String s) {
        int[] lastIndex = new int[26];
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            lastIndex[chars[i] - 'a'] = i;
        }
        boolean[] visited = new boolean[26];
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < chars.length; i++) {
            // 栈内存在该字符，则跳过
            if (visited[chars[i] - 'a']) {
                continue;
            }
            // 栈顶元素大于当前字符，且当前字符在栈内位置更靠后，则弹出栈顶元素
            while (!stack.isEmpty() && stack.peekLast() > chars[i] && i < lastIndex[stack.peekLast() - 'a']) {
                visited[stack.peekLast() - 'a'] = false;
                stack.pollLast();
            }
            // 添加当前字符
            stack.offerLast(chars[i]);
            visited[chars[i] - 'a'] = true;
        }
        StringBuilder sb = new StringBuilder();
        for (Character c : stack) {
            sb.append(c);
        }
        return sb.toString();
    }
}
