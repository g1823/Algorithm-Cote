package leetCode.moderately;

/**
 * @author: gj
 * @description: 718. 最长重复子数组
 */
public class FindLength {
    /**
     * * 方法注释：
     * * 思路发展过程：
     * * 1. 蛮力方法：
     * *    - 对 nums1 的每个起始元素 nums1[i]，尝试从 nums2 的每个起始元素 nums2[j] 开始匹配连续子数组。
     * *    - 对每一对起点 (i, j)，逐个比较元素直到不相等，记录匹配长度。
     * *    - 时间复杂度过高：O(m * n * min(m,n))，大数组会超时。
     * *
     * * 2. 尝试优化 Map：
     * *    - 将 nums2 中每个元素出现的下标存入 Map，遍历 nums1 时只匹配可能存在的 nums2 下标。
     * *    - 虽然减少了一些无效匹配，但最坏情况下复杂度仍接近蛮力。
     * *
     * * 3. 动态规划方法（最终实现）：
     * *    - 定义状态：dp[i][j] 表示以 nums1[i-1] 和 nums2[j-1] 结尾的最长连续重复子数组长度。
     * *    - 状态转移：
     * *        if (nums1[i-1] == nums2[j-1])
     * *            dp[i][j] = dp[i-1][j-1] + 1;
     * *        else
     * *            dp[i][j] = 0;
     * *      理由：由于要求连续，只有前一个元素也匹配时才能延长连续长度。
     * *    - 边界条件：dp[0][*] = dp[*][0] = 0
     * *    - 最终结果：遍历 dp[i][j] 所有位置，取最大值。
     * *    - 时间复杂度：O(m * n)
     * *    - 空间优化：
     * *        - dp[i][j] 只依赖 dp[i-1][j-1]，可使用一维数组 dp[j+1] 逆序更新，空间降为 O(n)。
     * *
     * * 4. 代码实现逻辑（空间优化版）：
     * *    - 遍历 nums1 的每个元素 i
     * *    - 内层从 nums2 的末尾向前遍历 j（逆序更新保证 dp[j] 不被覆盖）
     * *    - 如果 nums1[i] == nums2[j]，dp[j+1] = dp[j] + 1
     * *    - 否则 dp[j+1] = 0
     * *    - 每次更新后维护 max 值，最终返回 max
     * *
     * * 5. 特点与优势：
     * *    - 正确性：dp[i][j] 完整记录所有以 nums1[i] 和 nums2[j] 结尾的连续重复子数组长度。
     * *    - 最优性：取 dp 最大值即可得到全局最长重复子数组。
     * *    - 空间优化：利用一维数组逆序更新，空间复杂度 O(n)。
     */
    public int findLength(int[] nums1, int[] nums2) {
        int[] dp = new int[nums2.length + 1];
        int max = 0;
        for (int i = 0; i < nums1.length; i++) {
            for (int j = nums2.length - 1; j >= 0; j--) {
                if (nums1[i] == nums2[j]) {
                    dp[j + 1] = dp[j] + 1;
                } else {
                    dp[j + 1] = 0;
                }
                max = Math.max(max, dp[j + 1]);
            }
        }
        return max;
    }


    /**
     * 滑动窗口
     * 设nums1.length = m, nums2.length = n
     * 首先，分析蛮力法：
     * - 尝试枚举所有可能的组合起点，即外层nums1 从 0 -> m-1 遍历，内层nums2 从 n-1 -> 0 遍历，作为起点，然后尝试向后匹配
     * - 伪代码：
     * - for i = 0 -> m-1
     * -    for j = n-1 -> 0
     * -        while i < m && j >= 0 && nums1[i] == nums2[j] 结果++
     * - 时间复杂度分析：O(m * n * min(m,n)) ≈ O(n^3)，外层n，内层n,匹配还需要n
     * <p>
     * 然后分析findLength()中动态规划实际逻辑：
     * - 在dp过程中，可以发现任意一个点dp[i][j]都依赖于dp[i-1][j-1]，实际上就是在列举对角线的点
     * - 相当与将nums1[0]与nums2[j]对齐，然后开始看当前重合部分的最大匹配长度。
     * - 比如：nums1 = [1,2,3,2,1], nums2 = [3,2,1,4,7]
     * - 可以看到最长长度为3，即 [3,2,1]，相当于将nums1[2]与nums2[0]对齐，然后开始匹配
     * - 1、dp[2][0] = 1，因为nums1[2] == nums2[0]，相当于将nums1[2]与nums2[0]对齐后，重叠部分第1个元素的比较
     * - 2、dp[2+1][0+1] = dp[3][1] = 1+1，因为nums1[3] == nums2[1]，相当于将nums1[2]与nums2[0]对齐后，重叠部分第二个元素的比较
     * - 3、dp[2+2][0+2] = dp[4][2] = 1+1+1，因为nums1[4] == nums2[2]，相当于将nums1[2]与nums2[0]对齐后，重叠部分第三个元素的比较
     * - 可见，动态规划相当于比较展开状态下的对角线，每个(i,j)状态只计算一次，然后根据dp[i-1][j-1]的值，进行更新
     * - 而蛮力法相当于每个i和j，在外层nums1的下标小于i,内层nums2的下标小于j时，都可能会比较一次
     * - 换句话说，蛮力会从每个(i,j)作为起点重新扫描，导致同一条对角线上的元素被重复比较多次。
     * - 举个例子，从(0,0)开始扫描，此时(1,1)，(2,2)会被扫描一次
     * - 然后从(1,1)开始扫描 ，(1,1)，(2,2)会被扫描一次
     * - 然后从(2,2)开始扫描，(2,2) 会被再扫描一次
     * <p>
     * 滑动窗口：
     * 根据上面的分析，可以发现动态规划的实现，从物理可视化角度来看，就是将nums1[i]与nums2[j]对齐，然后开始计算重叠部分的最长重复子数组
     * - 实际上，就是列举nums1与nums2所有的对齐方式，然后开始计算重叠部分最长的子数组长度
     * - 而所有列举方式实际上只有 m + n - 1 种(将nums2[0]与 nums1从0到m-1对齐共 m种；将nums1[0]与nums2从0到n-1对齐共n种；重叠0,0对齐再 - 1 )，所以时间复杂度可以优化为O((m+n-1) * min(m,n)) 也就是O(n^2)
     * 步骤：
     * 1、固定nums1,移动nums2[0]，计算对齐后重叠部分的最长重复子数组长度
     * 2、固定nums2,移动nums1[0]，计算对齐后重叠部分最长重复子数组长度
     * 3、得到最长重复子数组长度
     * 滑动窗口的本质：
     * 将动态规划中对整个 dp 矩阵的计算，
     * 转化为按对角线逐条扫描，从而避免构造 dp 数组。
     */
    public int findLength2(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int result = 0;
        // 固定 nums1，移动 nums2[0] 对齐
        for (int i = 0; i < m; i++) {
            int count = 0;
            for (int j = 0; j < Math.min(n, m - i); j++) {
                if (nums1[i + j] == nums2[j]) {
                    count++;
                    result = Math.max(result, count);
                } else {
                    count = 0;
                }
            }
        }
        // 固定 nums2，移动 nums1[0] 对齐
        for (int j = 0; j < n; j++) {
            int count = 0;
            for (int i = 0; i < Math.min(m, n - j); i++) {
                if (nums1[i] == nums2[j + i]) {
                    count++;
                    result = Math.max(result, count);
                } else {
                    count = 0;
                }
            }
        }
        return result;
    }

    /**
     * 二分查找 + 哈希
     */
    public int findLength3(int[] nums1, int[] nums2) {
        return 0;
    }
}
