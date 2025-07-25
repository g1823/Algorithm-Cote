package leetCode.difficult;

import java.util.Stack;

/**
 * @author: gj
 * @description: 84. 柱状图中最大的矩形
 */
public class LargestRectangleArea {
    public static void main(String[] args) {
        int[] heights = {2, 1, 2};
        int i = largestRectangleArea(heights);
        System.out.println(i);
    }

    /**
     * 思路：
     * 枚举每一个柱子作为高度的可能性，并且找到了它能形成的最大宽度，所以所有可能的矩形都被考虑到了，最大值自然也包含在其中。
     * 通过列举将每个柱子作为矩形高度的操作，将遍历变得有规则，而不是对于每个下标，都要左右一直遍历，造成重复计算。
     * 继续思考：
     * 每个柱子作为高度，那么就是需要向左右两侧遍历，分别寻找比其小的第一个元素，这样就得到了当前柱子作为高度的矩形的最大宽度。
     * 只需要找到左右两侧比其小的第一个元素即可，因为当前元素作为矩形的高，只要高度小于当前元素，就无法组合矩形了。
     * 继续思考：
     * 找比当前元素小的第一个元素，就可以使用单调栈。
     * 栈内元素单调递增，对于栈内任意一个元素而言，其上一个元素就是其左侧第一个比其小的元素，只需要找到第一个比其小的右侧元素即可。
     * 遍历数组，下标为i
     * - 若栈为空或 stack.peek() < heights[i]，则当前元素入栈
     * - 若栈不为空且 stack.peek() > heights[i]，则找到了栈顶元素右侧第一个比起小的元素，可以计算以栈顶元素为高度的矩形面积了。
     */
    public static int largestRectangleArea(int[] heights) {
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
