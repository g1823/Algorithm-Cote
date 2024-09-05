package leetCode;

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
}
