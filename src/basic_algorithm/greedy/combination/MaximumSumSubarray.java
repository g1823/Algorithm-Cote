package basic_algorithm.greedy.combination;

/**
 * @Package chapter01
 * @Author gaojie
 * @description: 最大子序列和问题求解
 */
public class MaximumSumSubarray {
    public static void main(String[] args) {
        int[] data = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4, 5};
        System.out.println(solution1(data, data.length));
        System.out.println(solution2(data, data.length));
        System.out.println(solution3(data, 0, data.length - 1));
        System.out.println(solution4(data, data.length));
    }

    /**
     * 蛮力法，暴力枚举所有可能的情况
     *
     * @param data 原始数组
     * @param n    数组长度
     * @return 最大子序列和，为负数时则返回0
     */
    public static int solution1(int[] data, int n) {
        if (n < 0 || (n == 1 && data[0] <= 0)) {
            return 0;
        }
        if (n == 1) {
            return data[0];
        }
        int maxSum = 0, thisSum;
        // 计算i到i,i到i+1,...,i到n的和，选出最大的一个子序列。
        // 当i从0遍历到n-1时，则计算过了所有的子序列
        for (int i = 0; i < n; i++) { // i 表示子序列左侧的位置
            for (int j = i; j < n; j++) { // j 表示子序列右边的位置
                thisSum = 0;
                // 汇总 i到j 的所有数之和
                for (int k = i; k <= j; k++) { // k表示当前位置
                    thisSum += data[k];
                }
                maxSum = Math.max(maxSum, thisSum);
            }
        }
        return maxSum;
    }

    /**
     * 蛮力法，暴力枚举所有可能的情况（根据动态规划思想优化）
     *
     * @param data 原始数组
     * @param n    数组长度
     * @return 最大子序列和，为负数时则返回0
     */
    public static int solution2(int[] data, int n) {
        if (n < 0 || (n == 1 && data[0] <= 0)) {
            return 0;
        }
        if (n == 1) {
            return data[0];
        }
        int maxSum = 0, thisSum;
        // 计算i到i,i到i+1,...,i到n的和，选出最大的一个子序列。  
        // 当i从0遍历到n-1时，则计算过了所有的子序列
        for (int i = 0; i < n; i++) {
            thisSum = 0;// i 表示子序列左侧的位置
            for (int j = i; j < n; j++) { // j 表示子序列右边的位置
                // 汇总 i到j 的所有数之和时，对比solution1发现
                // 其实每次求和不需要都从i位置开始，因为 i到k的和 = i到K-1的和 + data[k]
                thisSum += data[j];
                maxSum = Math.max(maxSum, thisSum);
            }
        }
        return maxSum;
    }

    /**
     * 分治法，递归解决：二分求 左边子序列的最大子序列（即不包括中间的左侧子序列）、右边子序列的最大子序列（即不包括中间的右侧子序列）、包括中间的最大子序列，选三个结果中最大的则是子序列和的最大值
     *
     * @param data  原始数组
     * @param left  当前子序列左边坐标
     * @param right 当前子序列右边坐标
     * @return 当前子序列的最大子序列和
     */
    public static int solution3(int[] data, int left, int right) {
        // 如果 left = right ，则说明只有一个元素，返回
        if (left == right) {
            return data[left];
        }
        int mid = left + (right - left) / 2;
        // mid+1 到 right 的最大值
        int temp = 0, tempRightMax = 0, tempLeftMax = 0;
        for (int i = mid + 1; i <= right; i++) {
            temp += data[i];
            tempRightMax = Math.max(temp, tempRightMax);
        }
        temp = 0;
        // mid 到 left 的最大值
        for (int i = mid; i >= left; i--) {
            temp += data[i];
            tempLeftMax = Math.max(temp, tempLeftMax);
        }
        int leftMax = solution3(data, left, mid);
        int rightMax = solution3(data, mid + 1, right);
        return Math.max(leftMax, Math.max(rightMax, (tempLeftMax + tempRightMax)));
    }

    /**
     * 在线处理：使用贪心算法，当当前和小于0就直接抛弃重新计算，只需要遍历一遍数组即可，复杂度为O(n)
     *
     * @param data 数组
     * @param n    计算到哪个下标的最大子序列和
     * @return 返回最大子序列和
     */
    public static int solution4(int[] data, int n) {
        int thisSum = 0, maxSum = 0; //thisSum存储当前序列和,maxSum存储最大值
        for (int i = 0; i < n; i++) {
            // 累加当前值
            thisSum += data[i];
            if (thisSum > 0) { // 当前值大于0，说明与之后的数字的和可能会变大
                // 如果当前最大值大于了最大值，那么替换最大值
                maxSum = Math.max(thisSum, maxSum);
            } else { // 当前和小于0，那么与之后数字的和必定变小
                // 抛弃当前序列和，重新开始
                thisSum = 0;
            }
        }
        return maxSum;
    }

}