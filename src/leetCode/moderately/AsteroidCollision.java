package leetCode.moderately;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * @author: gj
 * @description: 735. 小行星碰撞
 */
public class AsteroidCollision {
    public static void main(String[] args) {
        int[] asteroids = new int[]{3, 5, -6, 2, -1, 4};
        System.out.println(Arrays.toString(new AsteroidCollision().asteroidCollision(asteroids)));
    }

    /**
     * 用栈模拟，总计有如下情况(分支比较复杂)：
     * 1. 栈为空，则直接入栈
     * 2. 栈不为空，栈顶元素和当前元素方向一致，则入栈
     * 3. 栈不为空，栈顶元素和当前元素方向不一致，若栈顶向左，当前元素向右，不会想撞，则入栈
     * 4. 栈不为空，栈顶元素和当前元素方向不一致，若栈顶向右，当前元素向左
     * - 若两个元素相等，则两个元素都爆炸，结束
     * - 若两个元素不相等，栈顶元素绝对值大，则当前元素被吃掉，结束
     * - 若两个元素不相等，栈顶元素绝对值小，则栈顶元素被吃掉，继续对比新栈顶
     */
    public int[] asteroidCollision(int[] asteroids) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (int asteroid : asteroids) {
            if (stack.isEmpty()) {
                stack.push(asteroid);
            } else {
                while (true) {
                    int top = stack.peek();
                    // 方向一致，不需要处理
                    if (top > 0 && asteroid > 0 || top < 0 && asteroid < 0) {
                        stack.push(asteroid);
                        break;
                    }
                    // 方向不一致
                    // 栈顶元素向左移动，当前元素向右移动，不可能碰撞
                    if (top < 0 && asteroid > 0) {
                        stack.push(asteroid);
                        break;
                    }
                    // 栈顶元素小于当前元素，栈顶元素被当前元素吃掉
                    if (Math.abs(top) < Math.abs(asteroid)) {
                        stack.pop();
                        if (stack.isEmpty()) {
                            stack.push(asteroid);
                            break;
                        }
                        continue;
                    }
                    // 栈顶元素等于当前元素，两个元素均爆炸，结束
                    if (top == -asteroid) {
                        stack.pop();
                        break;
                    }
                    // 栈顶元素大于当前元素，当前元素被栈顶元素吃掉，结束
                    break;
                }
            }
        }
        int[] result = new int[stack.size()];
        for (int i = stack.size() - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }
        return result;
    }

    /**
     * 官方题解，思路一致，但是写法分支优雅
     */
    public int[] asteroidCollision2(int[] asteroids) {
        Deque<Integer> stack = new ArrayDeque<Integer>();
        for (int aster : asteroids) {
            boolean alive = true;
            while (alive && aster < 0 && !stack.isEmpty() && stack.peek() > 0) {
                // aster 是否存在
                alive = stack.peek() < -aster;
                // 栈顶行星爆炸
                if (stack.peek() <= -aster) {
                    stack.pop();
                }
            }
            if (alive) {
                stack.push(aster);
            }
        }
        int size = stack.size();
        int[] ans = new int[size];
        for (int i = size - 1; i >= 0; i--) {
            ans[i] = stack.pop();
        }
        return ans;
    }
}
