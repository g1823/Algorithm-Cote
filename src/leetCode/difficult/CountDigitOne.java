package leetCode.difficult;

/**
 * @description: 233. 数字 1 的个数 (数位DP dfs+记忆化搜索)
 */
public class CountDigitOne {
    /**
     * 缓存：limit=false 且 leadZero=false 的子问题结果。
     * 为什么只缓存这两种情况？
     * - limit=true：受限路径只有一条（每步都选上界值），数据量极小，无需缓存。
     * - limit=false 时当前位置可自由选 0~9，产海量分支，才是缓存目标。
     * - leadZero=true 也要维持前导零每层都只能选 0，只有唯一路径，无需缓存；
     * - leadZero=false 时之前选 1、2、3… 等都能汇聚到同一子问题，缓存才有意义。
     * 格式：cache[cur] = [有效数字个数, 1的个数]，全 -1 表示未计算
     */
    int[][] cache;
    /**
     * 原始数字的每一位
     */
    int[] numbers;

    public int countDigitOne(int n) {
        return getCount(n);
    }

    public int getCount(int n) {
        // 提取每一位数字，按高位→低位顺序存入 numbers[]
        // 以 n=1234 为例：len=4, numbers=[1,2,3,4]
        int temp = n;
        int len = 0;
        while (temp > 0) {
            len++;
            temp /= 10;
        }
        numbers = new int[len];
        // 三步提取当前位的值：
        // 1、定位：pow10 = 10^(len-1-i)，确定当前位的权重
        // 2、右移：n / pow10，将当前位移到个位，去掉低位干扰
        // 3、取模：% 10，取出个位上的数字
        // 高位在前是为了和 dfs 按索引顺序递归保持一致
        for (int i = 0; i < len; i++) {
            int pow10 = (int) Math.pow(10, len - 1 - i);
            numbers[i] = (n / pow10) % 10;
        }
        // 初始化缓存，长度与数字位数一致，全 -1 表示未计算
        cache = new int[len][2];
        for (int i = 0; i < len; i++) {
            cache[i][0] = -1;
            cache[i][1] = -1;
        }
        // 从最高位第0位开始，limit=true（前缀完全一致），leadZero=true（尚未遇到非零数字）
        return dfs(0, true, true)[1];
    }

    /**
     * 递归计算
     *
     * @param cur      当前位
     * @param limit    从[0,cut-1]是否和n前缀一样。一样的话当前位受限
     * @param leadZero 是否还存在前导零
     * @return [0]：从cur到最后一位有效数字个数；[1]：从cur到最后一位的1的个数
     */
    public int[] dfs(int cur, boolean limit, boolean leadZero) {
        // 所有位处理完毕，构建出了一个完整数字
        // 为什么返回 [1, 0]？因为 1 的个数已在递归过程中通过
        // "j == 1 时累加 next[0]" 逐层计完，终止层只需计数。
        if (cur == numbers.length) {
            if (leadZero) {
                return new int[]{0, 0};
            }
            return new int[]{1, 0};
        }

        // 无限制 + 前导零已结束 → 查缓存
        if (!limit && !leadZero && cache[cur][0] != -1) {
            return new int[]{cache[cur][0], cache[cur][1]};
        }

        int up = limit ? numbers[cur] : 9;
        int count = 0;
        int sum = 0;
        for (int i = 0; i <= up; i++) {
            int[] next = dfs(cur + 1, limit && i == up, leadZero && i == 0);
            count += next[0];
            sum += next[1];
            // 当前位选了 1，它在 next[0] 个后续数字中各出现一次
            if (i == 1) {
                sum += next[0];
            }
        }
        // 无限制 + 前导零已结束 → 写缓存
        if (!limit && !leadZero) {
            cache[cur][0] = count;
            cache[cur][1] = sum;
        }
        return new int[]{count, sum};
    }
}
