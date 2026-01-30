package leetCode.simple;

/**
 * @author: gj
 * @description: 374. 猜数字大小
 */
public class GuessGame {
    // 这个给个空实现，方便过编译
    int guess(int num){
        return 0;
    }
    // 直接二分查找即可
    public int guessNumber(int n) {
        int left = 1, right = n;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int res = guess(mid);
            if (res == 0) {
                return mid;
            } else if (res == -1) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }
}
