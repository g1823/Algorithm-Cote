package leetCode.simple;

/**
 * @author: gj
 * @description: 191. 位1的个数
 */
public class HammingWeight {
    /**
     * 直接使用338. 比特位计数算法使用的Brian Kernighan 算法{@link leetCode.simple.CountBits#countBits1(int)}
     */
    public int hammingWeight(int n) {
        int count = 0;
        int t = n;
        while (t > 0) {
            t = t & (t - 1);
            count++;
        }
        return count;
    }
}
