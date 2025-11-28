package leetCode.moderately;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author: gj
 * @description: 71. 简化路径
 */
public class SimplifyPath {
    public static void main(String[] args) {
        String path = "/home//foo/";
        System.out.println(simplifyPath(path));
    }

    /**
     * 思路：
     * 1. 使用栈（Stack）来存储路径中的有效目录。
     * 2. 先将路径按 "/" 分割，得到每个部分。
     * 3. 遍历每个部分：
     * - 如果是空字符串 "" 或 "."，忽略（表示当前目录或连续斜杠）。
     * - 如果是 ".."，表示返回上一级目录，从栈中弹出一个目录（若栈非空）。
     * - 否则，将当前目录压入栈。
     * 4. 遍历完成后，栈中就是简化后的路径各个目录顺序。
     * 5. 使用 String.join("/", list) 将栈中的目录拼接为最终路径，并在前面加 "/"。
     * 6. 如果栈为空，表示路径为根目录，返回 "/"。
     */
    public static String simplifyPath(String path) {
        // 使用栈存储有效目录
        Stack<String> stack = new Stack<>();
        // 按 "/" 分割路径
        String[] paths = path.split("/");
        // 遍历每个部分
        for (String s : paths) {
            if ("".equals(s)) {
                // 空字符串（连续斜杠）忽略
                continue;
            } else if (".".equals(s)) {
                // 当前目录，忽略
                continue;
            } else if ("..".equals(s)) {
                // 上一级目录，若栈非空则弹出
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                // 普通目录，压入栈
                stack.push(s);
            }
        }
        // 将栈中的目录拼接为规范路径
        List<String> list = new ArrayList<>(stack);
        return "/" + String.join("/", list);
    }

}
