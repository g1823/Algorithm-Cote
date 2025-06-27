package leetCode.difficult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: gj
 * @description: 301. 删除无效的括号
 */
public class RemoveInvalidParentheses {

    /**
     * 回溯-思路：
     * 整体思路：
     * 1.计算出最少需要删除的无效括号数量（左右括号数量）
     * 2.回溯，遍历字符串，对当前字符判断是否删除，是否保留，提前剪枝，遍历所有情况。
     * ---------------------------------------------------------------------------------------------------------
     * 计算最少需要删除的括号数量（目的是计算整个字符串合法所需的最小删除括号数量）：
     * 遇到括号匹配，可以直接想到一点：
     * -- 对于任意一个下标，其左侧"("的数量 要大于等于")"的数量，根据这一性质可以确认，至少要删除多少个")"
     * 1、根据这一性质，我们可以知道任意一个下标，其左侧左右括号数量，以及至少需要删除的")"数量。
     * 2、我们需要求整个字符串的最少删除数量，如何根据某个下标至少需要删除的括号数量推到出整个字符串的最少删除数量呢
     * 3、可以想到，对于下标i，假设至少需要删除一个右括号，那么对于i+k，若在i处已经删除了右括号，那么i+k处需要删除的右括号数量会受到影响
     * 4、我们可以累计统计，设定变量balance，其值等于当前下标的左括号数量减去当前下标右括号数量
     * -- 当balance小于0时，说明当右括号数量大于左括号，不合法，因此需要维护balance大于0
     * 5、遍历字符串：
     * -- 当字符为左括号时，balance加1
     * -- 当字符为右括号时
     * -- -- 当balance等于0时，此时说明已经没有可以与当前右括号匹配的左括号了，需要删除掉当前右括号，因此右括号删除数量+1
     * -- -- 当balance大于0时，此时说明存在与当前右括号匹配的左括号，因此balance减1
     * 6、这样就计算出了最少需要删除的右括号数量，最少需要删除的左括号数据就是balance的值
     * ---------------------------------------------------------------------------------------------------------
     * 回溯递归思路：
     * 1、跟计算最少删除括号一样，维护一个当前下标的balance、left（最少删除的左括号数量）、right（最少删除的右括号数量）
     * 2、遍历字符串
     * -- 遇到左括号：
     * -- -- 不删除这个括号：balance++
     * -- -- 删除这个括号：可删除左括号数量-1，balance不变（若left<0，则直接剪枝，因为此时已经没有可删除的左括号余额了）
     * -- 遇到右括号：
     * -- -- 不删除这个括号：balance--（若balance==0，直接剪枝，因为左侧没有可与之匹配的左括号了）
     * -- -- 删除这个括号：可删除右括号数量-1，balance不变（若right<0，则直接剪枝，因为此时已经没有可删除的右括号余额了）
     * -- 遇到其他字符：
     * -- -- 直接加入字符串中，因为其不影响左右括号的匹配
     */
    public static List<String> removeInvalidParentheses(String s) {
        // 1、计算组成合法表达式所需要的最小删除左右括号数量
        int minLeftRemove = 0, minRightRemove = 0, balance = 0;
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                if (balance == 0) {
                    minRightRemove++;
                } else {
                    balance--;
                }
            }
        }
        minLeftRemove = balance;
        Set<String> result = new HashSet<>();
        backtrack(result, new StringBuilder(), chars, 0, 0, minLeftRemove, minRightRemove);
        return new ArrayList<>(result);
    }

    public static void backtrack(Set<String> result, StringBuilder sb, char[] chars, int index, int balance, int left, int right) {
        if (index == chars.length) {
            if (left == 0 && right == 0 && balance == 0) {
                result.add(sb.toString());
            }
            return;
        }

        if (chars[index] == '(') {
            // 不删除左括号
            sb.append('(');
            backtrack(result, sb, chars, index + 1, balance + 1, left, right);
            sb.deleteCharAt(sb.length() - 1);
            // 删除当前左括号
            if (left > 0) {
                backtrack(result, sb, chars, index + 1, balance, left - 1, right);
            }
        } else if (chars[index] == ')') {
            // 不删除右括号
            if (balance > 0) {
                sb.append(')');
                backtrack(result, sb, chars, index + 1, balance - 1, left, right);
                sb.deleteCharAt(sb.length() - 1);
            }
            // 删除右括号
            if (right > 0) {
                backtrack(result, sb, chars, index + 1, balance, left, right - 1);
            }
        } else {
            sb.append(chars[index]);
            backtrack(result, sb, chars, index + 1, balance, left, right);
            sb.deleteCharAt(sb.length() - 1);
        }

    }


    /**
     * 广度优先BFS
     * 思路：
     * -- 层序遍历，每一层都是所有删除k个括号后的所有可能，只要当前层有一个合法的字符串，说明最少删除数量在当前层，就不需要遍历下一层了
     * -- 使用set避免本层出现重复元素
     * 1、创建一个Set，里面存放删除了i个字符的字符串，只删除左右括号
     * 2、循环这个Set，判断每个字符串是否合法，如果合法则将当前Set内所有的字符串全部判断一边是否合法，并记录结果后退出
     * 3、循环Set的每一个元素，对每一个元素再删除一个字符，只删除左右括号，并将删除结果放入新的Set
     * 4、替换原Set为当前Set
     * 5、若Set内元素删除完，则直接结束
     */
    public static List<String> removeInvalidParentheses2(String s) {
        Set<String> result = new HashSet<>();
        Set<String> set = new HashSet<>();
        set.add(s);
        while (!set.isEmpty()) {
            Set<String> newSet = new HashSet<>();
            boolean flag = false;
            for (String str : set) {
                if (isValid(str)) {
                    result.add(str);
                    flag = true;
                }
            }
            if (flag) {
                break;
            }
            for (String str : set) {
                for (int i = 0; i < str.length(); i++) {
                    if (str.charAt(i) != '(' && str.charAt(i) != ')') {
                        continue;
                    }
                    String next = str.substring(0, i) + str.substring(i + 1);
                    newSet.add(next);
                }
            }
            set = newSet;
        }
        return new ArrayList<>(result);
    }

    private static boolean isValid(String s) {
        int balance = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                balance--;
                if (balance < 0) {
                    return false;
                }
            }
        }
        return balance == 0;
    }



}
