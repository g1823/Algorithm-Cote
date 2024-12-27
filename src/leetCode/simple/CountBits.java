package leetCode.simple;

/**
 * @Package leetCode
 * @Date 2024/9/5 21:38
 * @Author gaojie
 * @description: 338. 比特位计数
 */
public class CountBits {
    /**
     * 0： 0
     * 1： 1
     * 2： 1 0
     * 3： 1 1
     * 4： 1 0 0
     * 5： 1 0 1
     * 6： 1 1 0
     * 7： 1 1 1
     * 8： 1 0 0 0
     * 9： 1 0 0 1
     * 10：1 0 1 0
     */
    public int[] countBits(int n) {
        int[] result = new int[n + 1];
        if (n == 0) {
            result[0] = 0;
            return result;
        }
        if (n == 1) {
            result[0] = 0;
            result[1] = 1;
            return result;
        }
        result[0] = 0;
        result[1] = 1;
        for (int i = 2; i <= n; i++) {
            if (i % 2 == 0) result[i] = result[i / 2];
            else result[i] = result[i / 2] + 1;
        }
        return result;
    }

    /**
     * Brian Kernighan 算法：
     * 对于任意整数 x，令 x= x & (x−1)，该运算将 x 的二进制表示的最后一个 1 变成 0。因此，对 x 重复该操作，直到 x 变成 0，则操作次数即为 x 的「一比特数」。
     *
     * @param n 入参
     * @return 每个数1的个数
     */
    public int[] countBits1(int n) {
        int[] result = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            int count = 0;
            int t = i;
            while (t > 0) {
                t = t & (t - 1);
                count++;
            }
            result[i] = count;
        }
        return result;
    }

    /**
     * 动态规划——最高有效位
     *
     * @param n
     * @return
     */
    public int[] countBits2(int n) {
        int[] result = new int[n + 1];
        int highBit = 0;
        for (int i = 1; i <= n; i++) {
            if ((i & (i - 1)) == 0) {
                highBit = i;
                result[i] = 1;
            } else {
                result[i] = result[i - highBit] + 1;
            }
        }
        return result;
    }

    /**
     * 动态规划——最低有效位 ，countBits()的改进写法
     *
     * @param n
     * @return
     */
    public int[] countBits3(int n) {
        int[] bits = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            // TODO:是否能被2整除、以及除二
            bits[i] = bits[i >> 1] + (i & 1);
        }
        return bits;
    }

    /**
     * 动态规划——最低设置位
     *  定义正整数 x 的「最低设置位」为 x 的二进制表示中的最低的 1 所在位。
     *  例如，10 的二进制表示是 1010(2)，其最低设置位为 2，对应的二进制表示是 10(2)。
     *  令 y=x&(x−1)，则 y 为将 x 的最低设置位从 1 变成 0 之后的数，显然 0≤y<x，bits[x]=bits[y]+1。
     *  因此对任意正整数 x，都有 bits[x]=bits[x&(x−1)]+1。
     *
     * @param n
     * @return
     */
    public int[] countBits4(int n) {
        int[] bits = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            bits[i] = bits[i & (i - 1)] + 1;
        }
        return bits;
    }


}
