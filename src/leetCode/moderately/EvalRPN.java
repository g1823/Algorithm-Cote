package leetCode.moderately;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author: gj
 * @description: 150. 逆波兰表达式求值
 */
public class EvalRPN {

    /**
     * 思路：
     * 1. 栈
     * 2. 遇到数字则入栈
     * 3. 遇到运算符则从栈中弹出两个数字，进行运算，结果入栈
     * 4. 栈中只有一个数字，即为结果
     */
    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (String token : tokens) {
            if (isNumber(token)) {
                stack.push(Integer.parseInt(token));
            } else {
                int a = stack.pop();
                int b = stack.pop();
                switch (token) {
                    case "+":
                        stack.push(a + b);
                        break;
                    case "-":
                        stack.push(b - a);
                        break;
                    case "*":
                        stack.push(a * b);
                        break;
                    case "/":
                        stack.push(b / a);
                        break;
                    default:
                        throw new RuntimeException("Invalid operator: " + token);
                }
            }
        }
        return stack.pop();
    }

    public boolean isNumber(String s) {
        return s.matches("-?\\d+(\\.\\d+)?");
    }
}
