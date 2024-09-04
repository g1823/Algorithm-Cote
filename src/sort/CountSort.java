package sort;

import java.util.Arrays;

/**
 * @author: gj
 * @description: 计数排序
 */
public class CountSort {
    public static void main(String[] args) {
        int[] data = {5, 0, 3, 20, 6, 2, 1, 4, 7, 8};
        execute(data);
    }

    public static void execute(int[] data) {
        int maxData = Integer.MIN_VALUE, minData = Integer.MAX_VALUE, n = data.length;
        for (int i = 0; i < data.length; i++) {
            maxData = Math.max(data[i], maxData);
            minData = Math.min(data[i], minData);
        }
        int length = maxData - minData + 1;
        int[] tempData = new int[length];
        Arrays.fill(tempData, 0);
        for (int i = 0; i < data.length; i++) {
            tempData[data[i] - minData] += 1;
        }
        for (int i = 0; i < tempData.length; i++) {
            int count = tempData[i];
            for (int j = 0; j < count; j++) {
                System.out.print((i + minData) + " ");
            }
        }
    }
}
