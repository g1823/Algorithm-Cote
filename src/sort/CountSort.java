package sort;

/**
 * @author: gj
 * @description: 计数排序
 */
public class CountSort {
    public static void main(String[] args) {

    }

    public static void execute(int[] data) {
        int maxData = Integer.MIN_VALUE, minData = Integer.MAX_VALUE, n = data.length;
        for (int i = 0; i < data.length; i++) {
            maxData = Math.max(data[i], maxData);
            minData = Math.min(data[i], minData);
        }
        int length = maxData - minData + 1;
        int[] tempData = new int[length];


    }
}
