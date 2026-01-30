package leetCode.simple;

/**
 * @author: gj
 * @description: 1137. 第 N 个泰波那契数
 */
public class Tribonacci {
    public int tribonacci(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        int x1 = 0, x2 = 1, x3 = 1;
        for (int i = 3; i <= n; i++) {
            int temp = x1 + x2 + x3;
            x1 = x2;
            x2 = x3;
            x3 = temp;
        }
        return x3;
    }
}
