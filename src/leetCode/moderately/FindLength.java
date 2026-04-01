package leetCode.moderately;

import java.util.HashSet;
import java.util.Set;

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
     * * 二分查找 + Rolling Hash
     * * 思路：
     * * 1. 使用二分查找枚举最长重复子数组的长度 L。
     * * 2. 对每个 L，使用 Rolling Hash 判断两个数组是否存在长度为 L 的相同子数组。
     * * -------------------------
     * * 一、为什么可以二分（单调性证明）
     * * 设 f(L) 表示：是否存在长度为 L 的重复子数组。
     * * 若 f(L) = true，说明存在长度为 L 的相同子数组：
     * *    nums1[i ... i+L-1] == nums2[j ... j+L-1]
     * * 那么必然有：
     * *    nums1[i ... i+L-2] == nums2[j ... j+L-2]
     * * 即长度 L-1 也一定存在。
     * * 满足单调性：
     * *    若某长度存在，则所有更短长度一定存在
     * *    若某长度不存在，则所有更长长度一定不存在
     * * 因此可以二分搜索最大满足条件的 L。
     * * 搜索范围：
     * *    L ∈ [0 , min(nums1.length , nums2.length)]
     * * -------------------------
     * * 二、如何判断是否存在长度为 L 的重复子数组
     * * 使用 Rolling Hash（滚动哈希）。
     * * 将长度为 L 的子数组映射为一个哈希值：
     * * hash(a0 ... a(L-1)) = a0 * base^(L-1) + a1 * base^(L-2) + ... + a(L-1)
     * * 为防止溢出，计算时对 mod 取模。
     * * -------------------------
     * * 三、Rolling Hash 的滑动更新原理
     * * 假设当前窗口：
     * *    nums[i ... i+L-1]
     * * 其哈希值为：
     * * hash_i = ai * base^(L-1) + a(i+1) * base^(L-2) + ... + a(i+L-1)
     * * 下一个窗口：
     * *    nums[i+1 ... i+L]
     * * 哈希值应为：
     * * hash_(i+1) = a(i+1) * base^(L-1) + ... + a(i+L)
     * * 可以由 hash_i O(1) 推出：
     * * hash_(i+1) = (hash_i - ai * base^(L-1)) * base + a(i+L)
     * * -------------------------
     * * 四、取模后仍然正确的原因
     * * 模运算满足同余性质：
     * * (a - b) mod k = ((a mod k) - (b mod k) + k) mod k
     * * 因此：
     * * (hash_i - ai * power) % mod 与 ((hash_i % mod) - (ai * power % mod) + mod) % mod
     * * 等价。
     * * 所以在每一步计算中取模不会破坏 Rolling Hash 的正确性。
     * * -------------------------
     * * 五、判断两个数组是否存在长度 L 的重复子数组
     * * 1. 计算 nums1 所有长度 L 子数组的哈希值，存入 HashSet
     * * 2. 滑动计算 nums2 的长度 L 子数组哈希
     * * 3. 若某个哈希存在于 HashSet 中，则说明存在重复子数组
     * * -------------------------
     * * 六、时间复杂度
     * * 二分次数：O(log(min(m,n)))
     * * 每次检查：Rolling Hash 遍历两个数组：O(m + n)
     * * 总复杂度：O((m + n) log(min(m,n)))
     * *
     */
    public int findLength3(int[] nums1, int[] nums2) {
        int left = 0, right = Math.min(nums1.length, nums2.length);
        while (left < right) {
            int mid = (right + left) >> 2;
            if (check(nums1, nums2, mid)) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    int mod = 1000000009, base = 113;

    private boolean check(int[] nums1, int[] nums2, int len) {

        long hashA = 0;
        // 初始化
        for (int i = 0; i < len; i++) {
            hashA = (hashA * base + nums1[i]) % mod;
        }
        Set<Long> bucketA = new HashSet<Long>();
        bucketA.add(hashA);
        long mult = qPow(base, len - 1);
        for (int i = len; i < nums1.length; i++) {
            hashA = ((hashA - nums1[i - len] * mult % mod + mod) % mod * base + nums1[i]) % mod;
            bucketA.add(hashA);
        }
        long hashB = 0;
        for (int i = 0; i < len; i++) {
            hashB = (hashB * base + nums2[i]) % mod;
        }
        if (bucketA.contains(hashB)) {
            return true;
        }
        for (int i = len; i < nums2.length; i++) {
            hashB = ((hashB - nums2[i - len] * mult % mod + mod) % mod * base + nums2[i]) % mod;
            if (bucketA.contains(hashB)) {
                return true;
            }
        }
        return false;
    }

    // 使用快速幂计算 x^n % mod 的值
    public long qPow(long x, long n) {
        long ret = 1;
        while (n != 0) {
            if ((n & 1) != 0) {
                ret = ret * x % mod;
            }
            x = x * x % mod;
            n >>= 1;
        }
        return ret;
    }

}
