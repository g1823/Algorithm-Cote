package leetCode.moderately;

import java.util.*;

/**
 * @author: gj
 * @description: 394. 字符串解码
 */
public class DecodeString {
    public static void main(String[] args) {
        String s = "3[a2[c]]";
        System.out.println(new DecodeString().decodeString(s));
    }

    /**
     * 类似于求中缀表达式，将字符入栈，遇到"]"开始出栈，直到遇到"["，然后拿到"["左侧的数字后将出栈的字符串*该倍数后再次入栈即可
     * @param s
     * @return
     */
    public String decodeString(String s) {
        Stack<String> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            String c = s.substring(i, i + 1);
            if ("]".equals(c)) {
                StringBuilder sb = new StringBuilder();
                while (!stack.isEmpty() && !"[".equals(stack.peek())) {
                    sb.insert(0, stack.pop());
                }
                if ("[".equals(stack.peek())) {
                    stack.pop();
                }
                int count = getCount(stack);
                StringBuilder tempSb = new StringBuilder(sb);
                for (int j = 1; j < count; j++) {
                    sb.append(tempSb);
                }
                stack.push(sb.toString());
            } else {
                stack.push(c);
            }
        }
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.insert(0, stack.pop());
        }
        return result.toString();
    }

    public int getCount(Stack<String> stack) {
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            String peek = stack.peek();
            if (peek.length() != 1) {
                break;
            } else {
                char c = peek.charAt(0);
                if (c <= '9' && c >= '0') {
                    sb.insert(0, stack.pop());
                } else {
                    break;
                }
            }
        }
        if (sb.length() == 0) return 1;
        return Integer.parseInt(sb.toString());
    }
}
