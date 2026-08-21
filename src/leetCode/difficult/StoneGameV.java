package leetCode.difficult;

/**
 * @description: 1563. 石子游戏 V（动态规划）
 */
public class StoneGameV {
    public int stoneGameV(int[] stoneValue) {
        int n = stoneValue.length;
        // dp[i][r]：Alice 在闭区间 [i, r] 内能获得的最大得分
        // dp[i][i] = 0：单元素区间无法分割，不得分（数组默认值）
        int[][] dp = new int[n][n];
        // 前缀和：sum(l, r) = prefixSum[r+1] - prefixSum[l]，O(1) 求任意区间和
        int[] prefixSum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + stoneValue[i];
        }
        // 按区间长度从小到大填表，保证更短的子区间先被算好
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int r = i + len - 1;                       // 区间右端点
                int sum = prefixSum[r + 1] - prefixSum[i]; // 区间 [i, r] 总和
                int max = Integer.MIN_VALUE;
                // 切分点 k：切成左 [i, k] 与右 [k+1, r]，k ∈ [i, r-1]
                for (int k = i; k < r; k++) {
                    int left = prefixSum[k + 1] - prefixSum[i]; // 左区间和
                    int right = sum - left;                     // 右区间和
                    if (left < right) {
                        // 左小右大：Bob 拿走右边，Alice 保留左边得 left 分，继续在左边分割
                        max = Math.max(max, left + dp[i][k]);
                    } else if (left > right) {
                        // 左大右小：Bob 拿走左边，Alice 保留右边得 right 分，继续在右边分割
                        max = Math.max(max, right + dp[k + 1][r]);
                    } else {
                        // 相等：Bob 拿走任一边，Alice 拿另一边得 left 分，再任选一个子区间继续
                        max = Math.max(max, left + Math.max(dp[i][k], dp[k + 1][r]));
                    }
                }
                dp[i][r] = max;
            }
        }
        return dp[0][n - 1];
    }

    /**
     * todo：后面需要再回顾复习
     * 与 {@link #stoneGameV(int[])} 的区别（O(n³) → O(n²)）：
     * 原始版每个区间 [i,r] 都要遍历所有切分点 k 取 max，总 O(n³)。
     * 本版利用两个关键性质省掉内层 k 扫描
     * 1. 单调分界点 k0：sum(i,k) 随 k 单调不减、sum(k+1,r) 随 k 单调不增，
     * -   所以"左小右大"与"左大右小"之间存在唯一分界 k0（满足 2·sum(i,k0) < sum(i,r) 的最后一个 k）。
     * -   固定 i 时，r 越大 k0 只增不减 → 用单调指针维护，均摊 O(1)。
     * 2.前缀/后缀最大值表：
     * -   maxL[i][m] = max over k∈[i,m] (sum(i,k) + dp[i][k])   （只依赖左端点 i，可增量构建）
     * -   maxR[m][r] = max over k∈[m,r-1] (sum(k+1,r) + dp[k+1][r])（只依赖右端点 r，可增量构建）
     * -   于是 dp[i][r] = max(maxL[i][k0], maxR[k0+1][r], 相等切分点) 查表 O(1)。
     * 辅助表每个格子 O(1) 增量算出，总复杂度 O(n²)。
     */
    public int stoneGameV2(int[] stoneValue) {
        int n = stoneValue.length;
        if (n <= 1) {
            return 0;
        }
        // dp[i][r]：Alice 在闭区间 [i, r] 内能获得的最大得分，dp[i][i] = 0
        int[][] dp = new int[n][n];
        // 前缀和
        int[] prefixSum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + stoneValue[i];
        }
        // maxL[i][m] = max over k∈[i,m] of (sum(i,k) + dp[i][k])
        // 含义：固定左端点 i，切分点 k 不超过 m 时，"左小右大"分支的最优候选值
        int[][] maxL = new int[n][n];
        // maxR[m][r] = max over k∈[m,r-1] of (sum(k+1,r) + dp[k+1][r])
        // 含义：固定右端点 r，切分点 k 不小于 m 时，"左大右小"分支的最优候选值
        int[][] maxR = new int[n][n];
        // 边界：单元素区间 [i,i] 无任何切分点
        // maxL[i][i] 对应唯一的切分点 k=i（左区间 [i,i]、右区间 [i+1,r]）：sum(i,i)+dp[i][i] = stoneValue[i]+0
        // maxR[i][i] 对应 k∈[i,i-1] 空候选，应为 0（无切分点可选）
        for (int i = 0; i < n; i++) {
            maxL[i][i] = stoneValue[i];
            maxR[i][i] = 0;
        }
        // k0Ptr[i]：固定左端点 i 时当前的分界指针（单调不减），
        // 即最后一个满足 2·sum(i,k) < sum(i,r) 的 k；可为 i-1（表示左小右大分支为空，全部切分点都左大右小）
        int[] k0Ptr = new int[n];
        for (int i = 0; i < n; i++) {
            k0Ptr[i] = i - 1;
        }
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int r = i + len - 1;
                int total = prefixSum[r + 1] - prefixSum[i];
                // 先更新 maxR[i][r]（只依赖更短区间 dp[i+1][r]、maxR[i+1][r]，可提前算好供本区间查询）
                // maxR[i][r] = max over k∈[i,r-1] (sum(k+1,r)+dp[k+1][r])
                // = max( sum(i+1,r)+dp[i+1][r] , maxR[i+1][r] )，其中 sum(i+1,r) 即 total - stoneValue[i]
                maxR[i][r] = Math.max(maxR[i + 1][r], (total - stoneValue[i]) + dp[i + 1][r]);
                // 单调推进分界指针 k0：sum(i, k0+1)*2 < total 则右移（k0+1 <= r-1 才有意义）
                int k0 = k0Ptr[i];
                while (k0 + 1 <= r - 1 && 2 * (prefixSum[k0 + 2] - prefixSum[i]) < total) {
                    k0++;
                }
                k0Ptr[i] = k0;
                // 两部分候选：k ≤ k0 走左小右大（查 maxL，需 k0 >= i 才存在），k > k0 走左大右小（查 maxR）
                int best = Integer.MIN_VALUE;
                if (k0 >= i) {
                    best = Math.max(best, maxL[i][k0]);
                }
                best = Math.max(best, maxR[k0 + 1][r]);
                // 相等切分点（最多一个）：若 k=k0+1 满足 2*sum(i,k) == total，候选 = half + max(dp[i][k], dp[k+1][r])
                int kEq = k0 + 1;
                if (kEq <= r - 1 && 2 * (prefixSum[kEq + 1] - prefixSum[i]) == total) {
                    int half = prefixSum[kEq + 1] - prefixSum[i];
                    best = Math.max(best, half + Math.max(dp[i][kEq], dp[kEq + 1][r]));
                }
                dp[i][r] = best;
                // 再更新 maxL[i][r]（依赖 dp[i][r] 本身，故放在 dp 计算之后，供更大的区间查询）
                // maxL[i][r] = max over k∈[i,r] (sum(i,k)+dp[i][k])
                maxL[i][r] = Math.max(maxL[i][r - 1], total + dp[i][r]);
            }
        }
        return dp[0][n - 1];
    }
}
