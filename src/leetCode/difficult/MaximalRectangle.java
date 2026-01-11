package leetCode.difficult;

import java.util.Stack;

/**
 * @author: gj
 * @description: 85. 最大矩形
 */
public class MaximalRectangle {

    public static void main(String[] args) {
        char[][] matrix = {
                {'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'}};
        int i = new MaximalRectangle().maximalRectangle(matrix);
        System.out.println(i);
    }

    /**
     * 思路：
     * 记录每个坐标向上最高高度，这样就可以将每一行转为柱状图，可以采用柱状图求最大矩形的代码了
     * 问题被转换为了求柱形图中的最大矩形了{@link leetCode.difficult.LargestRectangleArea}
     */
    public int maximalRectangle(char[][] matrix) {
        // 1、记录每个坐标向上最高的高度
        int[][] heights = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                heights[i][j] = matrix[i][j] == '1' ? (i == 0 ? 1 : heights[i - 1][j] + 1) : 0;
            }
        }
        int max = 0;
        for (int i = 0; i < heights.length; i++) {
            int[] height = heights[i];
            max = Math.max(max, largestRectangleArea(height));
        }
        return max;
    }

    public int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int max = 0, i = 0;
        while (i < heights.length) {
            // 当前高度 >= 栈顶元素对应的高度，保持单调递增，入栈
            if (stack.isEmpty() || heights[stack.peek()] <= heights[i]) {
                stack.push(i);
                i++;
            } else {
                // 当前坐标小于栈顶元素，栈顶的上一个元素一定是栈顶元素左侧第一个小于其的元素
                // 现在又找到了比栈顶元素小的元素了，宽度就确定了
                int index = stack.pop();
                // 栈为空，则栈顶元素左侧没有比其小的元素，上一个元素坐标默认为-1,这样-（-1）就正好等于1
                int lastIndex = stack.isEmpty() ? -1 : stack.peek();
                // 计算以当前元素为高度的矩形面积
                int currentMax = heights[index] * (i - lastIndex - 1);
                max = Math.max(max, currentMax);
            }
        }
        // 数组遍历结束，栈内还有元素
        while (!stack.isEmpty()) {
            // 此时对于栈内元素而言，右侧是没有比起小的元素，因此栈内任意元素向右可以直接扩展到数组末尾
            int index = stack.pop();
            int height = heights[index];
            int width = stack.isEmpty() ? heights.length : heights.length - stack.peek() - 1;
            int area = height * width;
            max = Math.max(max, area);
        }
        return max;
    }
}
