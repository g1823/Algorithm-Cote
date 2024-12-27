package leetCode.moderately;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/**
 * @author: gj
 * @description: 739. 每日温度
 */
public class DailyTemperatures {

    /**
     * 单调栈
     * 常用于：在 O(n) 的时间复杂度内求出数组中各个元素右侧第一个更大的元素及其下标，然后一并得到其他信息。
     * 原理：维护一个单调递增或递减的栈，遍历一个新的元素时，判断栈内元素和新元素的大小关系（这里按照递减栈）
     *  当栈内元素小于新元素时，直接将栈内元素取出，并可以得到栈内元素的下标与当前元素的差值
     *
     * @param temperatures 温度数组
     * @return 结果
     */
    public int[] dailyTemperatures(int[] temperatures) {
        Stack<int[]> stack = new Stack<>();
        int[] result = new int[temperatures.length];
        for (int i = 0; i < temperatures.length; i++) {
            if (stack.isEmpty()) {
                int[] t = new int[]{temperatures[i], i};
                stack.push(t);
            } else {
                int temperature = temperatures[i];
                while (!stack.isEmpty() && stack.peek()[0] < temperature) {
                    int[] pop = stack.pop();
                    result[pop[1]] = i - pop[1];
                }
                int[] t = new int[]{temperatures[i], i};
                stack.push(t);
            }
        }
        while (!stack.isEmpty()) {
            int[] pop = stack.pop();
            result[pop[1]] = 0;
        }
        return result;
    }

    /**
     * 根据官方题解优化dailyTemperatures
     */
    public int[] dailyTemperatures2(int[] temperatures) {
        // Stack 类是同步的，基于 Vector，这种设计对大多数应用场景来说显得过于复杂，并且性能较差。
        // Deque 更加灵活，既可以作为栈使用（LIFO），也可以作为双端队列使用（FIFO），可以从两端添加或删除元素。而 Stack 则是严格的栈结构，功能上较为单一。
        Deque<Integer> stack = new LinkedList<Integer>();
        int[] result = new int[temperatures.length];
        for (int i = 0; i < temperatures.length; i++) {
            int temperature = temperatures[i];
            // 这里直接存储下标即可，可以通过下标得到当天温度
            while (!stack.isEmpty() && temperatures[stack.peek()] < temperature) {
                int index = stack.pop();
                result[index] = i - index;
            }
            stack.push(i);
        }
        return result;
    }


    /**
     * 类似于动态规划，从后往前遍历
     */
    public int[] dailyTemperatures3(int[] temperatures) {
        int length = temperatures.length;
        if (length == 1) return new int[length];
        int[] result = new int[length];
        for (int i = length - 2; i >= 0; i--) {
            int temperature = temperatures[i];
            int t = i + 1;
            while (t < length) {
                // 如果当前温度小于后面某一天的温度，找到比其大的那一天温度
                if (temperature < temperatures[t]) {
                    result[i] = t - i;
                    break;
                } else {
                    // 如果当前温度大于后面某一天的温度，则通过result找到比后面那天温度更高的那一天
                    if (result[t] == 0) break;
                    else t = t + result[t];
                }
            }
        }
        return result;
    }

}
