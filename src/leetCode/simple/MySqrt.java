package leetCode.simple;

/**
 * @author: gj
 * @description: 69. x 的平方根
 */
public class MySqrt {
    public static void main(String[] args) {
        System.out.println(new MySqrt().mySqrt(4));
    }

    /** =========================
      方法 1：暴力枚举 mySqrt
      思路与推导（思考过程）：
        - 最直观的做法是从 1、2、3... 依次尝试 k*k，并找出最大的 k 使得 k*k <= x。
        - 这是正确的，因为如果 k*k <= x 且 (k+1)*(k+1) > x，则 floor(sqrt(x)) = k。
        - 但时间复杂度为 O(sqrt(x)) —— 对于大 x 不够好（例如 x 约 2e9，sqrt(x) ~ 46340，虽然不是灾难性的但仍比 log 复杂度慢）。
      注意点：
        - x 为 0 或 1 的特殊处理可以直接返回 x。
        - 计算 k*k 时要用 long 避免 int 溢出（但 k 最大约 46340，int*k*k 在 int 范围内也安全，但以防万一使用 long 更保险）。
      复杂度：
        - 时间 O(sqrt(x))，空间 O(1)。
    */
    public static int mySqrt(int x) {
        if (x < 2) {
            return x;
        }
        int k = 1;
        while (true) {
            long square = (long) k * k;
            if (square > x) {
                return k - 1;
            }
            k++;
        }
    }

    /** =========================
       方法 2：二分查找 mySqrt1
       思路与推导（思考过程）：
         - f(mid) = mid*mid 随 mid 单调递增，所以要找最大的 mid 使得 mid*mid <= x。
         - 这是典型的单调函数搜索，适合二分查找。
         - 终止时 left > right，其中 right 是最后满足条件的值。
       关键细节：
         - 对于小的 x（0 或 1）直接返回 x。
         - 搜索区间选择：通常 [1, x/2 + 1] 对于 x >= 2 是足够的（因为对于 x>=2，sqrt(x) <= x/2）。
         - 每次比较 mid*mid 与 x 时用 long，避免 mid*mid 溢出 int。
       边界/返回值：
         - 采用经典的「当 mid*mid > x 则 right = mid-1；否则 left = mid+1」模式，最终返回 right。
       复杂度：
         - 时间 O(log x)，空间 O(1)。
     */
    public static int mySqrt1(int x) {
        if (x < 2) {
            return x;
        }
        int left = 1;
        int right = x / 2 + 1;
        int ans = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            long sq = (long) mid * mid;
            if (sq == x) {
                return mid;
            } else if (sq < x) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }

    /** =========================
       方法 3：袖珍计算器算法 mySqrt2（exp + ln）
       思路与推导（思考过程）：
         - 使用数学恒等式： sqrt(x) = x^(1/2) = exp( ln(x) / 2 )
         - 在没有 sqrt 函数的环境下但有 ln 与 exp 的环境（例如老式小型计算器），可以用这个转换来近似求平方根。
       实现细节：
         - 计算：double t = Math.exp(0.5 * Math.log(x));
         - 将 t 转为整数答案时会产生浮点误差（可能比真实 floor(sqrt(x)) 少 1 或 多 1），因此必须做整数校正：
             - 强制将 t 转为 int ans = (int) t;
             - 如果 ans*ans > x，ans-- 直到不大于 x（通常只需 0 或 1 次）。
             - 如果 (ans+1)*(ans+1) <= x，ans++ 直到超出（通常只需 0 或 1 次）。
       优点/缺点：
         - 优点：时间 O(1)（主要是常数次的浮点函数开销）
         - 缺点：依赖浮点计算，需额外校正；在面试或对精度有严格要求时不优。
       复杂度：
         - 时间 O(1)（但有浮点函数开销），空间 O(1)。
     */
    public static int mySqrt2(int x) {
        if (x < 2) {
            return x;
        }
        double approx = Math.exp(0.5 * Math.log(x));
        int ans = (int) approx;

        while ((long) ans * ans > x) {
            ans--;
        }

        while ((long) (ans + 1) * (ans + 1) <= x) {
            ans++;
        }

        return ans;
    }

    /** TODO
       方法 4：牛顿迭代 mySqrt3（整数版本）
       思路与推导（思考过程）：
         - 要解 y^2 = x，等价于求方程 f(y) = y^2 - x = 0 的根。
         - 牛顿法通用公式： y_{n+1} = y_n - f(y_n) / f'(y_n)
           对 f(y) = y^2 - x，f'(y) = 2y，代入得：
             y_{n+1} = y_n - (y_n^2 - x) / (2 y_n) = (y_n + x / y_n) / 2
         - 这是经典的平方根迭代公式。
         - 牛顿法通常用浮点数实现并具有二次收敛（位数翻倍），但我们也可以用整数迭代（用整除）以得到精确整数解（floor）。
       整数实现细节：
         - 取初始猜测 y = x （或 x/2+1，也可）
         - 迭代条件：当 y > x / y 时继续（因为 y*y > x 等价于 y > x / y，后者能避免 y*y 的中间 overflow 用 long 检查也可）
         - 每次更新： y = (y + x / y) / 2 （整除）
         - 终止时 y 是 floor(sqrt(x)) 或接近它；使用 long 做防溢检测或用 x/y 的方式避免 y*y 溢出。
       优点/缺点：
         - 优点：收敛非常快，整数版本在实际中几次迭代就到位。
         - 注意：初始 y 不能为 0（x==0 已经单独处理）
       复杂度：
         - 迭代次数通常很少（对 32-bit int，大约 5 次左右），时间视为 O(log log x)（二次收敛）。
     */
    public static int mySqrt3(int x) {
        if (x < 2) {
            return x;
        }
        long y = x;
        while (y > x / y) {
            y = (y + x / y) / 2;
        }
        return (int) y;
    }
}
