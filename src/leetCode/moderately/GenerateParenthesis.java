package leetCode.moderately;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gj
 * @description: 22. 括号生成
 */
public class GenerateParenthesis {
    public static void main(String[] args) {
        System.out.println(generateParenthesis(3));
    }


    /**
     * 采用递归，每次加一个括号
     */
    public static List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        recursion(result, "", n, 0, 0);
        return result;
    }

    public static void recursion(List<String> result, String s, int n, int left, int right) {
        // 左右括号数量 = n，结束
        if (left == n && right == n) {
            result.add(s);
            return;
        }
        // 左括号数量 < n，还可以加左括号
        if (left < n) {
            // 左括号数量 = 右括号数量，只能加左括号
            if (left == right) {
                recursion(result, s + "(", n, left + 1, right);
            // 左括号数量 > 右括号数量，可以加左括号或者右括号
            } else {
                recursion(result, s + "(", n, left + 1, right);
                recursion(result, s + ")", n, left, right + 1);
            }
        } else {
            // 左括号数量 > n，只能加右括号
            recursion(result, s + ")", n, left, right + 1);
        }
    }


    /**
     * generateParenthesis优化
     * @param n
     * @return
     */
    public static List<String> generateParenthesis2(int n) {
        List<String> result = new ArrayList<>();
        backtrack(result, "", 0, 0, n);
        return result;
    }

    /**
     * 回溯构造所有合法括号组合
     * @param result 结果列表
     * @param current 当前构造的字符串
     * @param left 当前已经放置的左括号数
     * @param right 当前已经放置的右括号数
     * @param max 总共需要放置的括号对数
     */
    private static void backtrack(List<String> result, String current, int left, int right, int max) {
        // 如果左右括号都已用完，加入结果
        if (current.length() == max * 2) {
            result.add(current);
            return;
        }

        // 只要左括号没放完，就可以继续放
        if (left < max) {
            backtrack(result, current + "(", left + 1, right, max);
        }
        // 右括号数量小于左括号数量时才能放，保证合法
        if (right < left) {
            backtrack(result, current + ")", left, right + 1, max);
        }
    }
}
