package leetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @Package leetCode
 * @Date 2024/9/7 22:48
 * @Author gaojie
 * @description: 20. 有效的括号
 */
public class BracketIsValid {
    public static void main(String[] args) {
        BracketIsValid bracketIsValid = new BracketIsValid();
        String s = "){";
        System.out.println(bracketIsValid.isValid(s));
    }

    /**
     * 参考计算前缀表达式
     * @param s 待校验字符串
     * @return 是否匹配
     */
    public boolean isValid(String s) {
        boolean result = true;
        char[] charArray = s.toCharArray();
        // 单数直接淘汰
        if ((charArray.length & 1) == 1) return false;
        List<Character> leftBracket = new ArrayList<>(Arrays.asList('(', '[', '{'));
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < charArray.length; i++) {
            if (leftBracket.contains(charArray[i])) {
                stack.push(charArray[i]);
            } else {
                // 检查到右括号，无左括号直接淘汰
                if (stack.isEmpty()) {
                    result = false;
                    break;
                }
                Character left = stack.pop();
                char right = charArray[i];
                switch (right) {
                    case ')':
                        result = left == '(';
                        break;
                    case ']':
                        result = left == '[';
                        break;
                    case '}':
                        result = left == '{';
                        break;
                }
                // 不匹配直接淘汰
                if (!result) break;
            }
        }
        // 栈内还有元素直接淘汰
        if (!stack.isEmpty()) return false;
        return result;
    }
}
