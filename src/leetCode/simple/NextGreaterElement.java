package leetCode.simple;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 496. 下一个更大元素 I
 */
public class NextGreaterElement {
    public static void main(String[] args) {
        int[] nums1 = {4, 1, 2};
        int[] nums2 = {1, 3, 4, 2};
        System.out.println(Arrays.toString(new NextGreaterElement().nextGreaterElement(nums1, nums2)));
    }

    /**
     * 单调栈：
     * 待记录：
     * 可以使用数组模拟单调栈，不使用队列（需对比什么时候适合数组）：
     * -public void fastMonotonicStack(int[] nums) {
     * -    int n = nums.length;
     * -    // 预分配数组，相当于栈空间
     * -    int[] stack = new int[n];
     * -    // top 指针指向栈顶元素索引，-1 表示空栈
     * -    int top = -1;
     * -    for (int num : nums) {
     * -        // 维护单调递增（这里以递增为例）：栈顶元素 >= 当前元素则出栈
     * -        while (top >= 0 && stack[top] >= num) {
     * -            top--; // 弹出栈顶
     * -        }
     * -        // 入栈：指针后移并赋值
     * -        stack[++top] = num;
     * -    }
     * -}
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        // 使用单调栈，记录nums2每个元素后是否还存在更大的元素，并记录每个元素出现的位置
        int n = nums2.length;
        int[] t = new int[n];
        // 题目说明了不存哎重复元素，直接可以用map记录元素->索引
        Map<Integer, Integer> nums2Map = new HashMap<>();
        // 默认值设为 -1，表示没有更大的下一个元素
        Arrays.fill(t, -1);

        // 用 int[] 模拟栈，存储索引
        int[] stack = new int[n];
        // 栈顶指针
        int top = -1;

        // 从左向右构建单调递减栈
        for (int i = 0; i < n; i++) {
            // 记录索引
            nums2Map.put(nums2[i], i);
            // 当前元素 nums2[i] 作为“潜在的更大值”，
            // 只要栈不空且栈顶元素 < 当前元素，就说明栈顶元素找到了下一个更大元素（就是当前元素）
            while (top >= 0 && nums2[stack[top]] < nums2[i]) {
                int idx = stack[top--];
                t[idx] = nums2[i];
            }
            // 此时栈顶元素 >= 当前元素（或栈为空），当前元素入栈，等待后续更大的元素来“解救”它
            stack[++top] = i;
        }
        // 遍历结束后，栈中剩余的索引对应的元素，右侧没有更大值，t 中保持 -1
        int[] res = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            int index = nums2Map.get(nums1[i]);
            res[i] = index == -1 ? -1 : t[index];
        }
        return res;
    }

}
