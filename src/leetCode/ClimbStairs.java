package leetCode;

/**
 * @Package leetCode
 * @Date 2024/9/8 19:26
 * @Author gaojie
 * @description: 70. 爬楼梯
 */
public class ClimbStairs {
    public int climbStairs(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;
        int i1 = 1, i2 = 2, result = 3;
        for (int i = 3; i <= n; i++) {
            result = i1 + i2;
            i1 = i2;
            i2 = result;
        }
        return result;
    }
}
