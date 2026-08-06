package leetCode.moderately;

/**
 * @description: 1094. 拼车
 */
public class CarPooling {

    /**
     * 差分
     * 题目说明了：1 <= trips.length <= 1000，数量有限，最多1000乘客
     */
    public boolean carPooling(int[][] trips, int capacity) {
        int[] diff = new int[1001];
        for (int[] trip : trips) {
            int num = trip[0];
            int start = trip[1];
            int end = trip[2];
            diff[start] += num;
            diff[end] -= num;
        }
        int sum = 0;
        for (int i = 0; i < 1001; i++) {
            sum += diff[i];
            if (sum > capacity) {
                return false;
            }
        }
        return true;
    }
}
