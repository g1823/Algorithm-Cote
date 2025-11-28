package leetCode.moderately;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: gj
 * @description: 54. 螺旋矩阵
 */
public class SpiralOrder {
    public static void main(String[] args) {
        int[][] matrix = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println(new SpiralOrder().spiralOrder(matrix));
    }

    /**
     * 思路：
     * 1. 使用四个边界变量（left、right、top、bottom）限制遍历范围；
     * 2. 用 isHorizontal 标识当前是横向遍历还是纵向遍历；
     * 3. 用 isForward 表示遍历方向：
     * - 1、2 表示正向（左→右 或 上→下）
     * - 3、4 表示反向（右→左 或 下→上）
     * 4. 每次遍历后更新对应的边界，并切换方向标识，直到所有元素遍历完。
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        // 左右边界
        int left = 0, right = matrix[0].length - 1;
        // 上下边界
        int top = 0, bottom = matrix.length - 1;
        // 当前遍历是否是横向
        boolean isHorizontal = true;
        // 遍历方向（1、2=正向；3、4=反向）
        int isForward = 1;
        List<Integer> res = new ArrayList<>();
        // 当仍有可遍历的行和列时继续
        while (left <= right && top <= bottom) {
            if (isHorizontal) {
                if (isForward <= 2) {
                    for (int i = left; i <= right; i++) {
                        res.add(matrix[top][i]);
                    }
                    top++;
                } else {
                    for (int i = right; i >= left; i--) {
                        res.add(matrix[bottom][i]);
                    }
                    bottom--;
                }
            } else {
                if (isForward <= 2) {
                    for (int i = top; i <= bottom; i++) {
                        res.add(matrix[i][right]);
                    }
                    right--;
                } else {
                    for (int i = bottom; i >= top; i--) {
                        res.add(matrix[i][left]);
                    }
                    left++;
                }
            }
            isForward += 1;
            isForward = isForward > 4 ? 1 : isForward;
            // 横纵遍历切换
            isHorizontal = !isHorizontal;
        }
        return res;
    }

    /**
     * 思路：
     * 1. 使用四个边界变量（left、right、top、bottom）限制遍历范围；
     * 2. 用 dir 表示方向（0=右，1=下，2=左，3=上）；
     * 3. 每次按照当前方向遍历一条边，然后更新对应的边界；
     * 4. 循环切换 dir，直到遍历完所有元素。
     */
    public List<Integer> spiralOrder1(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if (matrix.length == 0) {
            return res;
        }

        int m = matrix.length, n = matrix[0].length;
        int left = 0, right = n - 1, top = 0, bottom = m - 1;
        // 0:右, 1:下, 2:左, 3:上
        int dir = 0;

        while (left <= right && top <= bottom) {
            if (dir == 0) {
                // 左→右
                for (int i = left; i <= right; i++) {
                    res.add(matrix[top][i]);
                }
                top++;
            } else if (dir == 1) {
                // 上→下
                for (int i = top; i <= bottom; i++) {
                    res.add(matrix[i][right]);
                }
                right--;
            } else if (dir == 2) {
                // 右→左
                for (int i = right; i >= left; i--) {
                    res.add(matrix[bottom][i]);
                }
                bottom--;
            } else {
                // 下→上
                for (int i = bottom; i >= top; i--) {
                    res.add(matrix[i][left]);
                }
                left++;
            }
            // 方向切换
            dir = (dir + 1) % 4;
        }
        return res;
    }
}
