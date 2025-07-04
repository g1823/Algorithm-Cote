package leetCode.difficult;

import java.util.Stack;

/**
 * @author: gj
 * @description: 42. 接雨水
 */
public class Trap {
    public static void main(String[] args) {
        int[] height = {4, 1, 2, 3};
        int result = new Trap().trap(height);
        System.out.println(result);
    }

    /**
     * 本解法核心在于将每个柱子右侧“可接水空间”看作一个可填的矩形或台阶，由后续更高的柱子来决定这段空间能否被完全或部分填满，并以此计算面积。
     * 前提假设：
     * - 局部负责原则：每根柱子只负责计算自己右侧是否能够接水，左侧的水由左边的柱子负责。
     * - 判断是否能接水：若右侧柱子 height[i+1] ≥ height[i]，则 i 右边不可能形成低洼地带，不能接水。
     * - 预处理剩余接水高度：通过 remainHeights[i] = max(height[i] - height[i+1], 0) 表示从该柱子向右“理论上”最多能接多少水。
     * 换句话说，就是每根柱子的每个单位高度都寻找与其对应的右柱子，组成封闭区间
     * 核心操作逻辑：
     * 对于每个柱子下标 i，我们用栈维护递减柱子的索引，进行如下操作：
     * 遇到一个新柱子 j（j > i）时，进行以下判断：
     * - 情况 1：height[j] ≥ height[i]
     * -- 表示 j 这根柱子高度足够，可以完全填平之前 i 柱子右侧的接水区域（达到 remainHeights[i] 的最大理论值）。
     * -- 操作：将 remainHeights[i] 乘以宽度 (j - i - 1)，累加进总接水量。然后将 i 出栈，因为其接水区域已经被填满。
     * - 情况 2：height[j] ∈ (height[i] - remainHeights[i], height[i])
     * -- 表示新柱子虽然不足以完全填满，但可以部分填水，中间还是可以接一部分水。
     * -- 操作：计算新水高 fill = remainHeights[i] - (height[i] - height[j])，乘以宽度 (j - i - 1) 累加进总接水量。
     * ---更新 remainHeights[i] = height[i] - height[j]，表示还有多少高度差可以继续填。
     * - 情况 3：height[j] < height[i] - remainHeights[i]
     * -- 表示新柱子高度太低，连“可接水面”都没触及 —— 无法填水，不做任何处理，等待下一个柱子。
     * 总结：
     * remainHeights[i] 实际上记录的是：该柱子上方还能往右延伸出多少“水位高度”空间。
     * 然后通过后续柱子的高度来逐步减少或填满这个空间。这本质上是一种：
     * - “水面推进”+“局部单调栈控制 + 减水高度差”的方式
     */
    public int trap(int[] height) {
        int n = height.length;
        if (n <= 2) {
            return 0;
        }

        int result = 0;
        Stack<Integer> stack = new Stack<>();
        int[] remainHeights = new int[n];

        // 计算每个位置相对于右边的高度差（可接水空间）
        for (int i = 1; i < n; i++) {
            remainHeights[i - 1] = Math.max(height[i - 1] - height[i], 0);
        }

        // 找到第一个有高度差的位置，作为起点
        int start = 0;
        while (start < n && remainHeights[start] == 0) {
            start++;
        }
        if (start >= n) {
            return 0;
        }

        stack.push(start);

        for (int i = start + 1; i < n; i++) {
            while (!stack.isEmpty()) {
                int topIndex = stack.peek();
                int expectedHeight = height[topIndex] - remainHeights[topIndex];

                if (height[i] < expectedHeight) {
                    break;
                }

                if (height[i] >= height[topIndex]) {
                    stack.pop();
                    result += (i - topIndex - 1) * remainHeights[topIndex];
                } else {
                    int actualDrop = height[topIndex] - height[i];
                    int fill = remainHeights[topIndex] - actualDrop;
                    result += (i - topIndex - 1) * fill;
                    remainHeights[topIndex] = actualDrop;
                    break;
                }
            }
            // 可能存在空间接雨滴的下标才放入
            if (remainHeights[i] > 0) {
                stack.push(i);
            }
        }

        return result;
    }

    /**
     * 动态规划：
     * 分析可知，实际上，每个下标可以接的水的最大高度取决于其左右两侧的最大高度的柱子，只要两侧存在比当前柱子高的柱子，那么当前柱子就可以接水。
     * 即：min(左边最高的柱子, 右边最高的柱子) - 当前柱子高度
     * 那么对每个下标都计算左右两侧最大高度，这其中存在大量的重复计算，因此，可以使用动态规划来优化。
     * 步骤：
     * 1、分别计算每个坐标左右侧的最高柱子高度
     * 2、统计结果
     */
    public int trap2(int[] height) {
        int n = height.length;
        if (n <= 2) {
            return 0;
        }
        int[] leftMax = new int[n];
        leftMax[0] = -1;
        int[] rightMax = new int[n];
        rightMax[n - 1] = -1;
        for (int i = 1; i < n - 1; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i - 1]);
            rightMax[n - i - 1] = Math.max(rightMax[n - i], height[n - i]);
        }
        int result = 0;
        for (int i = 1; i < n - 1; i++) {
            int temp = Math.min(leftMax[i], rightMax[i]);
            if (temp > height[i]) {
                result += temp - height[i];
            }
        }
        return result;
    }


    /**
     * 单调栈解法：
     * 栈中存储的是柱子的下标，维护一个从栈底到栈顶高度递减的顺序。
     * 当遇到一个比栈顶柱子高的新柱子时，说明可能形成一个“凹槽区域”，此时出栈并尝试计算接水。
     * <p>
     * 每次出栈的柱子被视作“凹槽底部”，其两侧的柱子为：
     * - 弹出后栈顶的柱子（左边界）
     * - 当前遍历到的柱子（右边界）
     * <p>
     * 接水的高度为两边较矮的柱子减去“凹槽底”的高度，宽度为两边柱子之间的距离减一。
     * <p>
     * 注意：一个柱子的水量可能由多个右侧柱子逐步决定，因此这里计算的只是其参与的“局部凹槽水量”，不是它的最终总水量。
     * <p>
     * 本次并非直接统计当前坐标的最终接水量，只统计了左侧比起高的高度和右侧高度之间的局部高度，当前下标更高的高度由其左侧与右侧来填充
     */
    public int trap3(int[] height) {
        int n = height.length;
        if (n <= 2) {
            return 0;
        }
        int result = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        for (int i = 1; i < n; i++) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int bottom = stack.pop();
                if (stack.isEmpty()) {
                    break;
                }
                result += (i - stack.peek() - 1) * (Math.min(height[stack.peek()], height[i]) - height[bottom]);
            }
            stack.push(i);
        }
        return result;
    }


    /**
     * 双指针:
     * trap2()动态规划中，对每个下标求得其左侧和右侧的最大值，导致需要O(n)的空间复杂度存储这些信息，能否优化？
     * 考虑到本题目的特殊性，每个下标存储的最大水量取决于Min(Max(left),Max(right))，即左侧和右侧最大值中较小的一个。
     * 我们只要可以明确当前下标的左侧最大值小于右侧值（不一定是右侧最大值）即可，因为上限此时由左侧决定，右侧最大值不会影响结果。
     * 反之，右侧同理。
     * 因此，我们可以使用left和right两个指针指向当前位置，leftMax和rightMax记录左侧目前的最大值和右侧目前的最大值。
     * left=0,right=n-1,leftMax=0,rightMax=0;
     * 然后比较左侧和右侧元素大小，当height[left]<height[right]时，此时说明左侧一定小于右侧，那么左侧也一定小于
     */
    public int trap4(int[] height) {
        int n = height.length;
        if (n <= 2) {
            return 0;
        }
        int result = 0;
        return result;
    }
}
