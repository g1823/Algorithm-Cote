package sort;

import java.util.Arrays;

/**
 * @author: gj
 * @date: 2024/5/28 17:32
 * @description: 快排
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] data = {8, 4, 5, 7, 1, 3, 6, 2};
        System.out.println(Arrays.toString(execute(data, 0, data.length - 1)));
    }

    public static int[] execute(int[] data, int left, int right) {
        if (left >= right) {
            return null;
        }
        int[] result = quickSort(data, left, right);
        if (result == null) {
            return null;
        }
        execute(data, left, result[0] - 1);
        execute(data, result[1] + 1, right);
        return data;
    }

    public static int[] quickSort(int[] data, int left, int right) {
        if (left >= right) {
            return null;
        }
        /**
         * key:待计较元素
         * smallIndex:小于key的元素的下标
         * bigIndex:大于key的元素的下标
         * thisIndex：当前遍历到的元素下标
         */
        int key = data[left], smallIndex = left, bigIndex = right, thisIndex = left;
        while (thisIndex <= bigIndex) {
            if (data[thisIndex] == key) {
                thisIndex++;
            } else if (data[thisIndex] < key) {
                swap(data, smallIndex++, thisIndex++);
            } else {
                swap(data, bigIndex, thisIndex);
                bigIndex--;
            }
        }
        return new int[]{smallIndex, bigIndex};
    }

    public static void swap(int[] arr, int l, int r) {
        int temp = arr[l];
        arr[l] = arr[r];
        arr[r] = temp;
    }
}
