package sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: gj
 * @date: 2024/5/29 16:14
 * @description: 桶排序
 */
public class BucketSort {

    public static void main(String[] args) {
        int[] data = {5, 0, 3, 20, 6, 2, 1, 4, 7, 8};
        System.out.println(Arrays.toString(execute(data)));
    }

    public static int[] execute(int[] data) {
        int maxData = Integer.MIN_VALUE, minData = Integer.MAX_VALUE, bucketCount = 5, dataRange = 0, index = 0;
        int[] result = new int[data.length];
        // 找到待排序元素中最大的和最小的
        for (int j : data) {
            maxData = Math.max(j, maxData);
            minData = Math.min(j, minData);
        }
        dataRange = maxData - minData;
        List<ArrayList<Integer>> buckets = new ArrayList<>();
        // 新建桶
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }
        // 遍历原始数组，将元素分别放入不同的桶中
        if (dataRange == 0) return data;
        for (int datum : data) {
            int bucketIndex = (int) ((datum - minData) / (dataRange * 1.0) * (bucketCount - 1));
            buckets.get(bucketIndex).add(datum);
        }
        // 对桶进行排序并将排序后的元素放到结果集中
        for (ArrayList<Integer> bucket : buckets) {
            bucket.sort(null);
            for (Integer integer : bucket) {
                result[index++] = integer;
            }
        }
        return result;
    }
}
