package basic_algorithm.divide_conquer;

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

    /**
     * @param data 待排序数组
     * @param left 待排序数组起始下标
     * @param right 待排序数组终止下标
     * @return
     */
    public static int[] execute(int[] data, int left, int right) {
        // 达到最小子问题，即只有一个元素，那么这个元素一定是有序的
        if (left == right) {
            return new int[]{data[left]};
        }
        /**
         * 定义临时变量
         * midIndex:数组中间值，即将数组划分为 left -> midIndex 和 midIndex+1 -> right 两部分
         * leftIndex：合并两个有序子数组时，记录 left -> midIndex 数组的下标
         * rightIndex：合并两个有序子数组时，记录 midIndex+1 -> right 数组的下标
         * resultIndex：合并两个有序子数组时，记录 合并后有序数组 的下标
         */
        int midIndex = (right - left) / 2 + left, leftIndex = 0, rightIndex = 0, resultIndex = 0;
        // 递归求解 left -> midIndex 部分，使这部分有序
        int[] leftResult = execute(data, left, midIndex);
        // 递归求解 midIndex+1 -> right 部分，使这部分有序
        int[] rightResult = execute(data, midIndex + 1, right);
        // 存储合并后的有序数组
        int[] result = new int[right - left + 1];
        // 合并
        while (leftIndex < leftResult.length && rightIndex < rightResult.length) {
            result[resultIndex++] = leftResult[leftIndex] <= rightResult[rightIndex] ? leftResult[leftIndex++] : rightResult[rightIndex++];
        }
        // left -> midIndex 部分有剩余，依次填入合并后有序数组
        while (leftIndex < leftResult.length) {
            result[resultIndex++] = leftResult[leftIndex++];
        }
        // midIndex+1 -> right 部分有剩余，依次填入合并后有序数组
        while (rightIndex < rightResult.length) {
            result[resultIndex++] = rightResult[rightIndex++];
        }
        return result;
    }

}
