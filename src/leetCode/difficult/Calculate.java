package leetCode.difficult;

import java.util.*;

/**
 * @author: gj
 * @description: 224. 基本计算器
 */
public class Calculate {

    public static void main(String[] args) {
        String s = "1-(     -2)";
        String s2 = "(1+(4+5+2)-3)+(6+8)";
        String s3 = "- (3 + (4 + 5))";
        String s4 = "-(3+4)+5";
        String s5 = " 2-1 + 2 ";
        System.out.println(new Calculate().calculate2(s3));
    }

    /**
     * 基本计算器（仅包含 + - ()）
     * 错误解法
     * 【思路说明】
     * 1. 首先将输入字符串解析为 token 列表（数字、运算符、括号）。
     * -   - 遇到空格跳过；
     * -   - 遇到数字连续读取；
     * -   - 遇到运算符（+、-、(、)）单独作为一个 token；
     * -   - 对负号 `-` 进行特殊处理：
     * -      * 若出现在开头（i == 0）或前一个 token 是运算符或左括号，说明此时是负数前缀；
     * -         则读取接下来的数字并拼接为负数（例如 "-3"）。
     * -       * 否则视为减法运算符。
     * 2. 使用两个栈计算表达式：
     * -   - 数字栈 numStack；
     * -   - 运算符栈 opStack；
     * -   按中缀表达式的优先级规则进行计算（括号最高，+ - 相同优先级）。
     * 3. 遇到右括号时计算括号内的内容；
     * -   最后清空栈中剩余的运算符。
     * 【已知问题】
     * 当前分词逻辑无法正确处理 "−(" 这种情况，例如 "-(1+2)"。
     * 原因：负号后不是数字，而是括号，读取时未识别为负号作用于括号整体。
     * 因此 "−(" 被解析为运算符减号而不是负号。
     */
    public int calculate(String s) {
        List<String> tokens = new ArrayList<>();
        int i = 0, n = s.length();
        // ------------------------ 1. 表达式解析 ------------------------
        while (i < n) {
            char c = s.charAt(i);
            // 跳过空格
            if (c == ' ') {
                i++;
                continue;
            }
            // 运算符 + ( )
            if (c == '+' || c == '(' || c == ')') {
                i++;
                tokens.add(c + "");
            } else if (c == '-') {
                // ------------------ 负号特殊处理 ------------------
                // ① 出现在第一个位置或前一个 token 是运算符或左括号 -> 表示负数
                if (i == 0 || tokens.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    i++;
                    while (i < s.length() && Character.isDigit(s.charAt(i))) {
                        sb.append(s.charAt(i));
                        i++;
                    }
                    tokens.add("-" + sb);
                } else {
                    // ② 若上一个 token 是运算符 (+、-、( )，也表示负数
                    String s1 = tokens.get(tokens.size() - 1);
                    if ("(".equals(s1) || "-".equals(s1) || "+".equals(s1)) {
                        StringBuilder sb = new StringBuilder();
                        i++;
                        while (i < s.length() && Character.isDigit(s.charAt(i))) {
                            sb.append(s.charAt(i));
                            i++;
                        }
                        tokens.add("-" + sb);
                    } else {
                        // ③ 否则，当前 - 是减号
                        tokens.add("-");
                        i++;
                    }
                }
            } else {
                // 读取完整数字
                StringBuilder sb = new StringBuilder();
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    sb.append(s.charAt(i));
                    i++;
                }
                tokens.add(sb.toString());
            }
        }

        // ------------------------ 2. 计算表达式（双栈法） ------------------------
        Stack<Integer> numStack = new Stack<>();
        Stack<String> opStack = new Stack<>();

        for (String token : tokens) {
            if (isNumber(token)) {
                // 数字直接入栈
                numStack.push(Integer.parseInt(token));
            } else {
                if ("(".equals(token)) {
                    opStack.push(token);
                } else if (")".equals(token)) {
                    // 右括号：计算到左括号为止
                    while (!opStack.isEmpty() && !"(".equals(opStack.peek())) {
                        String op = opStack.pop();
                        int b = numStack.pop();
                        int a = numStack.pop();
                        switch (op) {
                            case "+":
                                numStack.push(a + b);
                                break;
                            case "-":
                                numStack.push(a - b);
                                break;
                            default:
                                throw new RuntimeException("Invalid operator: " + op);
                        }
                    }
                    opStack.pop(); // 弹出左括号
                } else {
                    // + 或 -：根据优先级计算
                    while (!opStack.isEmpty() && getPriority(opStack.peek()) >= getPriority(token) && !"(".equals(opStack.peek())) {
                        String op = opStack.pop();
                        int b = numStack.pop();
                        int a = numStack.pop();
                        switch (op) {
                            case "+":
                                numStack.push(a + b);
                                break;
                            case "-":
                                numStack.push(a - b);
                                break;
                            default:
                        }
                    }
                    opStack.push(token);
                }
            }
        }

        // ------------------------ 3. 处理剩余运算符 ------------------------
        while (!opStack.isEmpty()) {
            String op = opStack.pop();
            if ("-".equals(op)) {
                // 处理单个负号情况（例如表达式为 -3）
                if (numStack.size() == 1) {
                    numStack.push(-numStack.pop());
                    continue;
                }
            }
            int b = numStack.pop();
            int a = numStack.pop();
            switch (op) {
                case "+":
                    numStack.push(a + b);
                    break;
                case "-":
                    numStack.push(a - b);
                    break;
                default:
                    throw new RuntimeException("Invalid operator: " + op);
            }
        }
        return numStack.pop();
    }

    // 运算符优先级（+ - 同级）
    public int getPriority(String op) {
        if ("+".equals(op) || "-".equals(op)) {
            return 1;
        }
        return 2;
    }

    // 判断是否为数字（支持负号）
    public boolean isNumber(String s) {
        return s.matches("-?\\d+(\\.\\d+)?");
    }

    /**
     * 采用“一元运算符消除法”，将一元运算符“-”转换为二元减法（-1 改为 0 - 1）
     */
    public int calculate2(String s) {
        List<String> tokens = new ArrayList<>();
        int i = 0, n = s.length();
        // ------------------------ 1. 表达式解析 ------------------------
        while (i < n) {
            char c = s.charAt(i);
            // 跳过空格
            if (c == ' ') {
                i++;
                continue;
            }
            // 运算符 + ( )
            if (c == '+' || c == '(' || c == ')') {
                i++;
                tokens.add(c + "");
            } else if (c == '-') {
                // ------------------ 负号特殊处理 ------------------
                // 把“一元负号”统一转化为普通的二元减法: 即通过补0的方式，把“一元负号”统一转化为普通的二元减法。
                if (tokens.isEmpty()) {
                    tokens.add("0");
                    tokens.add("-");
                } else {
                    String s1 = tokens.get(tokens.size() - 1);
                    if ("(".equals(s1) || "+".equals(s1) || "-".equals(s1)) {
                        tokens.add("0");
                    }
                    tokens.add("-");
                }
                i++;
            } else {
                // 读取完整数字
                StringBuilder sb = new StringBuilder();
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    sb.append(s.charAt(i));
                    i++;
                }
                tokens.add(sb.toString());
            }
        }
        // ------------------------ 2. 计算表达式（双栈法） ------------------------
        Stack<Integer> numStack = new Stack<>();
        Stack<String> opStack = new Stack<>();

        for (String token : tokens) {
            if (isNumber(token)) {
                // 数字直接入栈
                numStack.push(Integer.parseInt(token));
            } else if ("(".equals(token)) {
                opStack.push(token);
            } else if (")".equals(token)) {
                // 遇到右括号，持续计算到左括号为止
                while (!opStack.isEmpty() && !"(".equals(opStack.peek())) {
                    applyOp(numStack, opStack.pop());
                }
                opStack.pop(); // 弹出左括号
            } else {
                // 当前为运算符 + 或 -
                while (!opStack.isEmpty() && !"(".equals(opStack.peek())
                        && getPriority(opStack.peek()) >= getPriority(token)) {
                    applyOp(numStack, opStack.pop());
                }
                opStack.push(token);
            }
        }

        // ------------------------ 3. 清算剩余运算符 ------------------------
        while (!opStack.isEmpty()) {
            applyOp(numStack, opStack.pop());
        }
        return numStack.pop();
    }

    /**
     * 执行一次运算（简化重复逻辑）
     * 支持 + 和 - 运算
     */
    private void applyOp(Stack<Integer> numStack, String op) {
        // 若仅有一个数字且为前缀负号情况，例如 "-3"
        if ("-".equals(op) && numStack.size() == 1) {
            numStack.push(-numStack.pop());
            return;
        }

        int b = numStack.pop();
        int a = numStack.pop();

        switch (op) {
            case "+":
                numStack.push(a + b);
                break;
            case "-":
                numStack.push(a - b);
                break;
            default:
                throw new RuntimeException("Invalid operator: " + op);
        }
    }
}
