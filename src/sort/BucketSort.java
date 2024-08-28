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
        int[] data = {8, 4, 5, 7, 1, 3, 6, 2};
        System.out.println(Arrays.toString(execute(data)));
    }

    public static int[] execute(int[] data) {
        int maxData = 0, minData = 0, bucketSize = 0, bucketCount = 0, index = 0;
        int[] result = new int[data.length];
        // 找到待排序元素中最大的和最小的
        for (int j : data) {
            maxData = Math.max(j, maxData);
            minData = Math.min(j, minData);
        }
        /**
         * 元素之间最大值最小值的差值 ➗ 元素的个数 得到每两个元素之间的平均差值，作为桶的大小
         * 如果元素均匀分布，那么桶的大小就大致等于元素的个数。
         */
        bucketSize = (maxData - minData) / data.length + 1;
        bucketCount = data.length / bucketSize + 1;
        List<ArrayList<Integer>> buckets = new ArrayList<>();
        // 新建桶
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }
        // 遍历原始数组，将元素分别放入不同的桶中
        for (int datum : data) {
            buckets.get((datum - minData) / bucketSize).add(datum);
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
