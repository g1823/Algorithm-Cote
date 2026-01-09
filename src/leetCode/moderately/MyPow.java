package leetCode.moderately;

/**
 * @author: gj
 * @description: 50. Pow(x, n)
 */
public class MyPow {

    public static void main(String[] args) {
        System.out.println(new MyPow().myPow2(2.0, 10));
    }

    /**
     * 错误，初始想要使用快速幂解决，对于这种情况处理不好：
     * n = 10 , 那么快速幂过程为 1 -> 2 -> 4 -> 16（在4*4的时候会超出10了）
     */
    public double myPow(double x, int n) {
        if (n == 0) {
            return 1;
        }
        int i = 1, k = 1, tn = n > 0 ? n : -n;
        double res = x, tmp = x;
        while (i < tn) {
            if (i + k * 2 > tn) {
                k = 1;
                res *= x;
                i += k;
                tmp = x;
            } else {
                k *= 2;
                tmp *= tmp;
                res *= tmp;
                i += k;
            }
        }
        return n > 0 ? res : 1 / res;
    }

    /**
     * 错误：
     * 最开始思路：
     * 针对上面的问题，主要在于快速幂不能简单的一直累乘，需要只乘一次出现奇数.
     * 比如对于n=10: 1 -> 2 (1+1) -> 3(2+1) -> 5(3 + 2) -> 10(5 + 5)
     * 可以发现，在乘积的过程中，会出现单独乘1次 x 的情况(上面的3)
     * 正向思考时，很难知道什么时候该乘以1，什么时候用前面出现过的乘积。
     * 但是逆向思考，我们从上往下思考：10 = 5 + 5 ， 5 = 3 + 2。 3 = 2 + 1， 2 = 1 + 1.
     * 就可以很容易发现这个问题，那么直接递归就可以了：
     * 1. 递归终止条件：n = 0，返回 1.
     * 2. 递归过程：
     * - 偶数：x^n = x^(n/2) * x^(n/2)
     * - 奇数：x^n = [x^(n/2) * x]  *  [x^(n/2)]
     * 问题：
     */
    public double myPow2(double x, int n) {
        if (n == 0) {
            return 1;
        }
        if (n < 0) {
            x = 1 / x;
            n = -n;
        }
        return divide(x, n, new double[n + 1]);
    }

    private double divide(double x, int n, double[] memo) {
        if (memo[n] != 0) {
            return memo[n];
        }
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return x;
        }
        double res = 0;
        if (n % 2 == 0) {
            res = divide(x, n / 2, memo) * divide(x, n / 2, memo);
        } else {
            res = x * divide(x, n / 2, memo) * divide(x, n / 2, memo);
        }
        memo[n] = res;
        return res;
    }


    /**
     * 对myPow2错误修正：
     * 1、使用long存储n，避免 n = Integer.MIN_VALUE 时取反溢出
     * 2、不再使用memo记忆，因为double[Integer.MAX_VALUE]太大了，是一个完全不可用的状态
     * 3、去除了memo，因此递归式不能直接divide(x, n / 2, memo) * divide(x, n / 2, memo)，这样会走两次，会重复计算
     * 改为：
     * double half = divide(x, n / 2);
     * // 如果 n 是偶数：
     * // x^n = x^(n/2) * x^(n/2)
     * if (n % 2 == 0) {
     * - return half * half;
     * }
     * // 如果 n 是奇数：
     * // x^n = x * x^(n/2) * x^(n/2)
     * return x * half * half;
     */
    public double myPow3(double x, int n) {
        // 使用 long 保存指数，避免 n = Integer.MIN_VALUE 时取反溢出
        long N = n;
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }
        return divide(x, N);
    }

    private double divide(double x, long n) {
        // 递归终止条件 1：
        // x^0 = 1
        if (n == 0) {
            return 1;
        }
        // 递归终止条件 2：
        // x^1 = x
        if (n == 1) {
            return x;
        }
        // 先递归计算 x^(n/2)，注意：这里的 n/2 是向下取整
        double half = divide(x, n / 2);
        // 如果 n 是偶数：
        if (n % 2 == 0) {
            return half * half;
        }
        // 如果 n 是奇数：
        return x * half * half;
    }


    /**
     * todo:当递归关系是 f(n) = f(n/2) 时，一定存在一个等价的「位运算 + while」解法
     * 50. Pow(x, n) —— 快速幂（最优解）
     * 【一、最初的直觉与问题】
     * 最直接的想法是对 x 连续相乘 n 次，但当 n 很大时会超时。
     * 因此需要利用“指数快速下降”的性质来降低复杂度。
     * 【二、递归（分治）视角的本质】
     * 将 x^n 看成一个可以不断“拆小”的问题：
     * 1）递归过程：
     * -   每一层递归都将 n 除以 2，递归深度为 O(log n)；
     * 2）回溯过程（关键）：
     * -   真正的乘法并不发生在递归向下时，而是发生在回溯阶段：
     * -   - 若当前 n 为偶数：
     * -       x^n = x^(n/2) * x^(n/2)
     * -   - 若当前 n 为奇数：
     * -       x^n = x * x^(n/2) * x^(n/2)
     * -   因此：
     * -   “n 是否为奇数”决定了在这一层回溯时是否需要额外乘一个 x。
     * 3）递归的含义总结：
     * - 一路递归：不断将 n 除 2，直到 n == 0；
     * - 回溯时：对应层级的幂次不断翻倍，
     * 且在 n 为奇数的层级额外乘一次 x。
     * 【三、为什么可以转成 while + 位运算（最优解）】
     * 递归的“回溯过程”可以用二进制的方式正向展开：
     * - 任意整数 n 都可以表示为二进制：
     * -    n = b0 * 2^0 + b1 * 2^1 + b2 * 2^2 + ...
     * - 每一层递归中：
     * -    n 是否为奇数 ⇔ 当前二进制最低位是否为 1
     * - 递归中每一层的“幂次翻倍”：
     * -    对应 while 循环中的 x = x * x
     * -    因此 while 循环可以理解为：
     * “从二进制最低位开始，逐位模拟递归从最底层向上回溯的过程”
     * 循环中三步操作的含义：
     * 1）(n & 1) == 1：
     * - 当前二进制位为 1，对应递归中该层为奇数，需要额外乘一次 x；
     * 2）x *= x：
     * - 当前层处理完，进入上一层，对应幂次翻倍；
     * 3）n >>= 1：
     * - 丢弃已处理的最低位，继续处理更高位。
     * 【四、负指数的处理】
     * 利用数学等价关系：
     * - x^(-n) = (1 / x)^n
     * - 先将 n 转为 long，避免 n == Integer.MIN_VALUE 时取反溢出，
     * - 再统一使用快速幂逻辑处理正指数。
     * 【五、复杂度】
     * 时间复杂度：O(log n)
     * 空间复杂度：O(1)
     */
    public double myPow4(double x, int n) {
        // 使用 long，避免 Integer.MIN_VALUE 取反溢出
        long N = n;
        // 处理负指数
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }
        double res = 1.0;
        // 二进制快速幂
        while (N > 0) {
            // 当前二进制最低位为 1
            if ((N & 1) == 1) {
                res *= x;
            }
            // 幂次翻倍，对应递归回溯上一层
            x *= x;
            // 处理下一位
            N >>= 1;
        }
        return res;
    }
}
