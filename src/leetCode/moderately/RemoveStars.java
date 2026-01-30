package leetCode.moderately;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * @author: gj
 * @description: 2390. 从字符串中移除星号
 */
public class RemoveStars {
    public String removeStars(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == '*') {
                stack.pop();
            } else {
                stack.push(c);
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.reverse().toString();
    }
}
