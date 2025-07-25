package leetCode.difficult;

import java.util.Stack;

/**
 * @author: gj
 * @description: 32. 最长有效括号
 */
public class LongestValidParentheses {

    public static void main(String[] args) {
        String s = ")()())";
        System.out.println(new LongestValidParentheses().longestValidParentheses4(s));
    }

    /**
     * 蛮力：将每个坐标作为起始坐标，统计最长有效括号
     * 添加剪枝：
     * 1、剩余整体长度不足已有最长长度时，直接返回
     * 2、具体某次遍历时，剩余长度小于balance长度，即无法平衡前面的左括号时结束本次循环
     */
    public int longestValidParentheses(String s) {
        int balance = 0, result = 0, n = s.length();
        for (int i = 0; i < s.length(); i++) {
            balance = 0;
            if (n - i < result) {
                return result;
            }
            for (int j = i; j < n; j++) {
                if (n - j < balance) {
                    break;
                }
                if (s.charAt(j) == '(') {
                    balance++;
                } else {
                    balance--;
                }
                if (balance == 0) {
                    result = Math.max(result, j - i + 1);
                }
                if (balance < 0) {
                    break;
                }
            }
        }
        return result;
    }


    /**
     * 动态规划：
     * 在longestValidParentheses中，分别计算以每一个下标为起始坐标的最长有效括号长度
     * 这回导致大量的重复计算，计算以i为开头的最长有效括号长度后，计算i+1时又把计算i时计算过的重新遍历一遍。
     * 优化：
     * 为了避免重复计算，我们可以转换思路，计算以每个下标为结尾的最长有效括号长度。
     * 转移方程：
     * s.charAt(i) == '('：
     * - dp[i]=0。因为有效的括号不会以(结尾，所以dp[i] = 0
     * s.charAt(i) == ')':
     * - charAt(i-1) 是'('，i和i-1可以组成一对有效括号'()'，且可以与 以i-2位置结尾的最长有效括号 组成最长的连续括号。
     * -- dp[i] = dp[i-2]+2
     * - charAt(i-1) 是')'，那么以i-1结尾的最长有效括号长度为dp[i-1]，
     * - 若 i-dp[i-1]-1 位置是'('，那么可以与 i 形成一个最外层括号包裹住 以i-1结尾的最长有效括号。
     * - 此时可以与 i-dp[i-1]-2位置结尾的最长有效括号并列连起来，形成以i结尾的最长有效括号。
     * -- dp[i] = dp[i-1]+2 + dp[i-dp[i-1]-2]
     */
    public int longestValidParentheses2(String s) {
        if (s.length() < 2) {
            return 0;
        }
        int[] dp = new int[s.length()];
        int result = 0;
        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ')') {
                char c1 = s.charAt(i - 1);
                /**
                 * c 是 )：
                 * - charAt(i-1) 是(，i和i-1可以组成一对有效括号()，且可以与 以i-2位置结尾的最长有效括号组成最长的连续括号。
                 * 若 以i-2结尾的最长有效括号为 xxxxx，那么以i位置结尾的最长有效括号为 xxxxx()
                 */
                if (c1 == '(') {
                    dp[i] = i - 2 >= 0 ? dp[i - 2] + 2 : 2;
                    result = Math.max(result, dp[i]);
                }
                /**
                 * c 是 )：
                 * - charAt(i-1) 是)，那么若以i-1结尾的最长长度为l，即dp[i-1]=k，此时若charAt（i-k-1）的位置是(
                 * 就可以与i位置形成一个最外层的括号，包裹住以i-1结尾的括号。同时可以连接起以i-k-2位置的左括号。
                 */
                if (c1 == ')') {
                    int left = i - dp[i - 1] - 1;
                    if (left >= 0 && s.charAt(left) == '(') {
                        dp[i] = dp[i - 1] + 2 + (left > 0 ? dp[left - 1] : 0);
                        result = Math.max(result, dp[i]);
                    }
                }
            } else {
                // 不可能以(结尾，因此(一定为0
                dp[i] = 0;
            }
        }
        return result;
    }


    /**
     * 栈解法：
     * 括号具有“成对出现”和“嵌套”的结构，天然适合用“后进先出”的栈来处理。
     * <p>
     * 思路：
     * - 用一个栈保存下标，记录还未匹配的左括号，或者遇到断裂时的“基准点”。
     * - 初始化时，栈中压入 -1，表示“上一个无效位置”的起点。
     * <p>
     * 遍历字符串：
     * - 遇到 '('：说明可能是一个新配对的起点，压入栈中（存下标）；
     * - 遇到 ')'：
     * - 若栈顶是未匹配的 '('：弹出，说明匹配成功；
     * - 匹配成功后，此时“栈顶”就是当前这段连续有效括号的 上一个断裂点，所以当前长度为：`i - 栈顶`
     * - 若栈空（或栈顶不是可配对的左括号）：说明当前右括号无法配对，是新的断裂点，压入当前下标作为“新起点”
     * <p>
     * 本质解释：
     * - 栈中始终存储的是：上一个未匹配的 '(' 的下标，或者上一个断裂的位置；
     * - 只要成功匹配，就可以用当前下标减去“上一个未配对点”来计算长度；
     * - 每次断裂，就更新这个“基准点”为当前下标，等待下一轮配对。
     */
    public int longestValidParentheses3(String s) {
        if (s.length() < 2) {
            return 0;
        }
        int result = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(i);
            } else {
                Integer peek = stack.peek();
                if (peek != -1 && s.charAt(peek) == '(') {
                    stack.pop();
                    result = Math.max(result, i - stack.peek());
                } else {
                    stack.pop();
                    stack.push(i);
                }
            }
        }
        return result;
    }

    /**
     * 双指针解法：
     * 由于括号的匹配要求连续，同时必须保证左括号和右括号数量匹配，
     * 我们可以利用左右两个指针分别从字符串两端扫描，
     * 统计左括号和右括号数量，找出最长的有效括号长度。
     * <p>
     * 为什么要左右各扫一次？
     * - 从左往右扫描时：
     * - 我们统计 '(' 和 ')' 的数量；
     * - 当 '(' 和 ')' 数量相等时，说明当前位置之前的子串是一个有效括号串；
     * - 若 ')' 的数量超过 '('，说明此时括号串不可能是有效的（出现了多余的右括号），需要重置计数，重新开始计数。
     * - 但从左往右扫描无法覆盖所有情况，例如字符串以多余的 '(' 结尾时无法识别，
     * 因为左括号过多但没有右括号匹配，不会更新结果；
     * - 所以我们需要从右往左再做一次扫描：
     * - 同理统计 '(' 和 ')' 的数量；
     * - 当数量相等时更新结果；
     * - 如果 '(' 数量超过 ')'，说明左括号过多，无法形成有效配对，需要重置计数；
     * <p>
     * 这样双向扫描能兼顾所有情况，确保不会漏掉以左括号结尾或以右括号开头的有效子串。
     * <p>
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    public int longestValidParentheses4(String s) {
        if (s.length() < 2) {
            return 0;
        }
        int result = 0;
        // 从左到右扫描的左右括号计数
        int leftNumsLTR = 0, rightNumsLTR = 0;
        // 从右到左扫描的左右括号计数
        int leftNumsRTL = 0, rightNumsRTL = 0;

        for (int i = 0; i < s.length(); i++) {
            // 从左往右统计
            if (s.charAt(i) == '(') {
                leftNumsLTR++;
            } else if (s.charAt(i) == ')') {
                rightNumsLTR++;
            }

            // 从右往左统计
            int j = s.length() - 1 - i;
            if (s.charAt(j) == ')') {
                rightNumsRTL++;
            } else if (s.charAt(j) == '(') {
                leftNumsRTL++;
            }

            // 当左右括号数相等，更新最大长度（乘以2是因为括号对数）
            if (leftNumsLTR == rightNumsLTR) {
                result = Math.max(result, 2 * leftNumsLTR);
            }
            // 如果右括号多于左括号，从左向右扫描时遇到多余右括号，重置计数
            else if (rightNumsLTR > leftNumsLTR) {
                leftNumsLTR = 0;
                rightNumsLTR = 0;
            }

            // 同理，从右向左扫描时左右括号数相等，更新最大长度
            if (leftNumsRTL == rightNumsRTL) {
                result = Math.max(result, 2 * leftNumsRTL);
            }
            // 如果左括号多于右括号，从右向左扫描遇到多余左括号，重置计数
            else if (leftNumsRTL > rightNumsRTL) {
                leftNumsRTL = 0;
                rightNumsRTL = 0;
            }
        }
        return result;
    }
}
