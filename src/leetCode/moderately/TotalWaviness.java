package leetCode.moderately;

import java.util.Arrays;

/**
 * @description: 3751. 范围内总波动值 I
 */
public class TotalWaviness {
    public static void main(String[] args) {
        TotalWaviness totalWaviness = new TotalWaviness();
        System.out.println(totalWaviness.totalWaviness(120, 130));
    }

    /**
     * 思考过程：
     * - 最开始思考如何计算一个数字的波动值。由于一个数字是否为峰或谷，只与它左右相邻的两个数字有关，因此对于单个数字，可以不断取出数位，
     * - 或转成字符串后遍历，通过连续的三个数字(pre2、pre、cur)判断中间数字pre是否满足：
     * - pre2 > pre && pre < cur（谷）或 pre2 < pre && pre > cur（峰）。
     * <p>
     * - 接着思考如何计算一个区间内所有数字的总波动值。最直接的想法是寻找规律，考虑当某一位及其左右两位都可以自由取0~9时，
     * - 中间位产生峰谷的数量是固定的，因此尝试在遇到“不受限制”的后缀时，直接通过：
     * - 剩余可形成峰谷的位置数 * 570
     * - 来一次性计算后续所有贡献。
     * <p>
     * - 但是这种想法存在问题。虽然后续位都可以自由取值0~9，但当前位置产生的峰谷还依赖于前面已经确定的数字。
     * - 例如已经确定前两位为(3,7)和(3,3)时，后续虽然都完全自由，但(3,7,x)与(3,3,x)产生的贡献显然不同，
     * - 因此仅根据剩余位数无法唯一确定答案，还必须知道最近的两个有效数字。
     * <p>
     * - 随后进一步考虑直接处理区间[num1, num2]。最开始打算同时维护：
     * - 当前前缀是否与下界一致；
     * - 当前前缀是否与上界一致。
     * - 这样虽然可行，但状态和转移都会变得更加复杂，需要同时维护上下界限制。
     * <p>
     * - 观察到本题本质上属于区间统计问题，可以借鉴前缀和思想：
     * - [num1, num2]的答案 = [0, num2]的答案 - [0, num1 - 1]的答案。
     * - 因此无需同时考虑上下界，只需要实现一个函数calc(n)，计算区间[0, n]内所有数字的总波动值即可，
     * - 最终通过两次计算相减得到区间答案，从而将状态中的“双边界限制”简化为“单边界限制”。
     * <p>
     * 解题思路：
     * - 使用数位DP统计区间[0, n]内所有数字的总波动值。
     * <p>
     * - 定义dfs(pos, pre, pre2, limit, leadZero)：
     * - pos：当前处理到的位置；
     * - pre：最近一个有效数字；
     * - pre2：最近第二个有效数字；
     * - limit：当前前缀是否仍与上界前缀一致；
     * - leadZero：当前是否仍处于前导零阶段。
     * <p>
     * - dfs返回两个值：
     * - count：当前状态下能够构造出的合法数字个数；
     * - sum：当前状态下所有数字产生的总波动值。
     * <p>
     * - 枚举当前位digit后，递归计算后续状态，得到：
     * - childCount：后续能够构造出的数字个数；
     * - childSum：后续所有数字产生的波动值总和。
     * <p>
     * - 如果pre2、pre、digit已经构成完整三元组，则可以确定pre是否成为峰或谷。
     * - 一旦pre成为峰或谷，那么这个贡献会出现在当前状态下所有后续数字中，因此需要额外增加：
     * - childCount * 1
     * <p>
     * - 状态转移：
     * - sum += childSum；
     * - 若pre形成峰或谷，则：
     * - sum += childCount；
     * - count += childCount。
     * <p>
     * - 当：
     * - 当前已经开始构造数字；
     * - 当前前缀不再受上界限制；
     * - 最近两个有效数字已经确定；
     * - 时，后续状态只与(pos, pre, pre2)有关，与具体前缀无关。
     * - 因此可以使用记忆化搜索缓存memoCount和memoSum，大量减少重复计算。
     * <p>
     * 时间复杂度：O(len * 10 * 10 * 10)，其中len为数字位数。
     * 空间复杂度：O(len * 10 * 10)。
     */
    public int totalWaviness(int num1, int num2) {
        return Math.toIntExact(getWaviness(num2) - getWaviness(num1 - 1));
    }

    private long getWaviness(int num) {
        // 初始化
        Waviness waviness = new Waviness(num);
        long[] result = dfs(waviness, 0, -1, -1, true, true);
        return result[1];
    }

    class Waviness {
        // memoCount[i][j][k] 表示前i位，第i-1位值为j,第i-2位值为k时的有效数字个数和总波动值
        // i取16是因为题目说明最大15位,j,k取10是因为0-9
        long[][][] memoCount = new long[16][10][10];
        long[][][] memoSum = new long[16][10][10];
        int[] nums;
        int n;

        Waviness(int num) {
            char[] chars = String.valueOf(num).toCharArray();
            nums = new int[chars.length];
            for (int i = 0; i < chars.length; i++) {
                nums[i] = chars[i] - '0';
            }
            n = chars.length;
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 10; j++) {
                    // -1 表示未计算过
                    Arrays.fill(memoCount[i][j], -1);
                    Arrays.fill(memoSum[i][j], -1);
                }
            }
        }
    }

    private long[] dfs(Waviness waviness, int pos, int pre, int pre2, boolean limit, boolean leadZero) {
        // 如果当前位是最后一位，则有效数字就1个，后续无法构成峰谷，则峰谷数为0
        if (pos == waviness.n) {
            return new long[]{1, 0};
        }
        // 如果前两位不是-1，且当前位不受限制，则可以直接取缓存中的值，不用单独计算
        if (!leadZero && !limit && pre != -1 && pre2 != -1 && waviness.memoCount[pos][pre][pre2] != -1) {
            return new long[]{waviness.memoCount[pos][pre][pre2], waviness.memoSum[pos][pre][pre2]};
        }
        // 如果当前位受限制，则上界为当前位值，否则上界为9
        int up = limit ? waviness.nums[pos] : 9;
        long count = 0;
        long sum = 0;
        for (int i = 0; i <= up; i++) {
            // 新的是否受限：之前受限且当前位的值已经等于上届，则新的Limit为true
            boolean newLimit = limit && i == up;
            // 判断是否有前导0：之前全是0，当前位还是0，则新的leadZero为true
            boolean newLeadZero = leadZero && i == 0;
            int newPre2 = pre;
            int newPre = newLeadZero ? -1 : i;
            long[] result = dfs(waviness, pos + 1, newPre, newPre2, newLimit, newLeadZero);
            // 前两位有效，且不再有前导0时，若pre2、pre、i 形成峰谷，若后续有n个有效数字，则这个峰谷会对后续每个数字贡献一次
            if (!leadZero && pre != -1 && pre2 != -1) {
                // pre2、pre、i 形成谷
                if (pre2 > pre && pre < i) {
                    sum += result[0];
                }
                // pre2、pre、i 形成峰
                if (pre2 < pre && pre > i) {
                    sum += result[0];
                }
            }
            count += result[0];
            sum += result[1];
        }
        if (!leadZero && !limit && pre != -1 && pre2 != -1) {
            waviness.memoCount[pos][pre][pre2] = count;
            waviness.memoSum[pos][pre][pre2] = sum;
        }
        return new long[]{count, sum};
    }
}
