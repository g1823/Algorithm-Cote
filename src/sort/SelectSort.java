package sort;

import java.util.Arrays;

/**
 * @author: gj
 * @description: 选择排序
 *  * 基本思想：每一躺从待排序数据中找出最小（或最大）的元素，然后按照要求放在最后或最前面。
 *  * 步骤：
 *  * 将待排序数据分区，分为有序区域和无序区域，初始时所有数据均在无序区域。
 *  * 每次从无序区域内找到最大的数，并将其与无序区域最后一个元素替换，然后无序区域范围缩小。
 *  * 不断重复步骤2，直到无序区只剩一个元素，此时排序完成。
 */
public class SelectSort {
    public static void main(String[] args) {
        int[] data = {5, 0, 3, 20, 6, 2, 1, 4, 7, 8};
        System.out.println(Arrays.toString(execute(data)));
    }

    public static int[] execute(int[] data) {
        for (int i = 0; i < data.length; i++) {
            int maxIndex = 0;
            // 从无序区域内找到最大值的下标
            for (int j = 1; j < data.length - i; j++) {
                maxIndex = data[maxIndex] > data[j] ? maxIndex : j;
            }
            // 并最大值与无序区域最后一个元素替换，然后无序区域范围缩小（通过i++实现）
            int temp = data[maxIndex];
            data[maxIndex] = data[data.length - i - 1];
            data[data.length - i - 1] = temp;
        }
        return data;
    }
}
