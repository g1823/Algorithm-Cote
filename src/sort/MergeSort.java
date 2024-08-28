package sort;

import java.util.Arrays;

/**
 * @author: gj
 * @date: 2024/5/28 14:28
 * @description: 归并排序
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] data = {8, 4, 5, 7, 1, 3, 6, 2};
        int[] result = MergeSort.execute(data, 0, data.length - 1);
        Arrays.stream(result).forEach(System.out::println);
    }

    public static int[] execute(int[] data, int left, int right) {
        if (left == right) {
            return new int[]{data[left]};
        }
        int midIndex = (right - left) / 2 + left, leftIndex = 0, rightIndex = 0, resultIndex = 0;
        int[] leftResult = execute(data, left, midIndex);
        int[] rightResult = execute(data, midIndex + 1, right);
        int[] result = new int[right - left + 1];
        while (leftIndex < leftResult.length && rightIndex < rightResult.length) {
            result[resultIndex++] = leftResult[leftIndex] <= rightResult[rightIndex] ? leftResult[leftIndex++] : rightResult[rightIndex++];
        }
        while (leftIndex < leftResult.length) {
            result[resultIndex++] = leftResult[leftIndex++];
        }
        while (rightIndex < rightResult.length) {
            result[resultIndex++] = rightResult[rightIndex++];
        }
        return result;
    }

}
