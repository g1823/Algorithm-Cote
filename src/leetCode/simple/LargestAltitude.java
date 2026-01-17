package leetCode.simple;

/**
 * @author: gj
 * @description: 1732. 找到最高海拔
 */
public class LargestAltitude {

    /**
     * 前缀和
     */
    public int largestAltitude(int[] gain) {
        int result = 0, current = 0;
        for (int i = 0; i < gain.length; i++) {
            current += gain[i];
            result = Math.max(current, result);
        }
        return result;
    }
}
