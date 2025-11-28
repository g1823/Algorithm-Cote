package leetCode.moderately;

import javafx.util.Pair;

import java.util.*;

/**
 * @author: gj
 * @description: 373. 查找和最小的 K 对数字
 */
public class KSmallestPairs {
    public static void main(String[] args) {
        int[] nums1 = new int[]{1, 7, 11};
        int[] nums2 = new int[]{2, 4, 6};
        System.out.println(new KSmallestPairs().kSmallestPairs(nums1, nums2, 3));
    }


    /**
     * 思路：
     * 由于两个数组均为有序数组，那么二者之和在某种程度上也是有序的。
     * 假设d[i][j]表示：nums1[i] + nums2[j]
     * 那么，d这个二维数组，同一行是有序的，同一列也是有序的。（同一行从左到右递增，同一列从上到下递增）
     * 使用一个小根堆存储数据，初始将d[i][0](0 <= i < nums1.length)全部加入堆，然后每取出一个最小元素，则将其右侧元素加入堆中。
     * 比如：当前d[i][j]目前最小，取出d[i][j]后，将d[i][j+1]加入堆中。
     * 想象一下，就像是一个波浪一样，从左往右一点点扩展，每次都扩展一个比当前最小稍大的元素。
     * 而未扩展元素可以保证不存在比当前元素小，因为未扩展的元素(X)一定比已扩展元素中同一行的元素(Y)要大，而Y又一定小于等于堆顶元素。
     */
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        PriorityQueue<Pair<Integer, Integer>> minHeap = new PriorityQueue<>((a, b) -> nums1[a.getKey()] + nums2[a.getValue()] - nums1[b.getKey()] - nums2[b.getValue()]);
        int m = nums1.length, n = nums2.length;
        for (int i = 0; i < m; i++) {
            minHeap.add(new Pair<>(i, 0));
        }
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            Pair<Integer, Integer> node = minHeap.poll();
            result.add(Arrays.asList(nums1[node.getKey()], nums2[node.getValue()]));
            if (node.getValue() + 1 < n) {
                minHeap.add(new Pair<>(node.getKey(), node.getValue() + 1));
            }
        }
        return result;
    }

    /**
     * kSmallestPairs优化：
     * 分析一下，题目仅要求取前k个元素，而方法一无论如何都会把nums1的全部元素初始化到堆中，并不划算。
     * 再分析：
     * 假设d[i][j]表示：nums1[i] + nums2[j].
     * 实际上，第一次只需要初始化往堆内放入d[0][0]即可，之后扩展只需要同时扩展两个元素，下边元素和右边元素
     * 即假设当前最小元素未d[i][j]，取出d[i][j]后，向堆内添加d[i+1][j]和d[i][j+1]
     * 原因：
     * 1、d[0][0]一定是最小的元素
     * 2、第二小的元素一定是d[1][0]或d[0][1]
     * 3、当弹出某个点 (i,j) 时，它的右 (i,j+1) 和下 (i+1,j)只可能比它大，不会比未入堆里其他未来元素更小
     * 因为d数组的单调性，所有不属于这一行/列且还未扩展的元素，都有它们更小的“父节点”尚未弹出(还在堆内)。
     * 上面三点就可以保证每次堆内弹出的都是未去除(未去除指的是没有进入到结果列表）元素中最小的
     * 换句话说，方法一中，将第一列全部加入，然后每次向右扩展，只使用了行单调性
     * 而本次优化，还使用到了列单调性：
     * 当前元素在已扩展到的元素中最小，那么所有未扩展元素一定大于等于该元素，因为:
     * 将元素分为如下部分，设当前对内最小元素下标为(i,j)，nums1元素为m个，nums2元素为n个：
     * - 当前元素右下角元素集合C，假设元素坐标为(x,y)，则 i < x < m, j < y < n 的元素
     * - 当前元素上方已入堆的元素集合A，假设元素坐标为(x,y)，则 0 =< x < i，且已经入堆还未出堆的元素
     * - 当前元素上方未入堆的元素集合A1，假设元素坐标为(x,y)，则 0 =< x < i，且还未出入堆的元素
     * - 当前元素左边已入堆的元素集合B，假设元素坐标为(x,y)，则 0 =< y < j，且已经入堆还未出堆的元素
     * - 当前元素左边已入堆的元素集合B1，假设元素坐标为(x,y)，则 0 =< y < j，且还未入堆的元素
     * 1、对于集合C，根据行和列的单调性，一定比当前元素大
     * 2、对于集合A，因为最小堆，且当前元素目前堆内最小，一定大于当前元素。
     * 3、对于集合A1，因为行单调性，其一定大于等于同一行的集合A内元素，那自然比当前元素大
     * 4、对于集合B，因为最小堆，且当前元素目前堆内最小，一定大于当前元素。
     * 5、对于集合B1，因为列单调性，其一定大于等于同一列的集合B内元素，那自然比当前元素大
     * 需要注意：
     * - 需要记录已经扩展的元素，因为一个元素可能从两个方向上(上方元素和左侧元素)扩展到，那么就有可能被两次加入到堆中造成重复，因此需要记录
     */
    public List<List<Integer>> kSmallestPairs2(int[] nums1, int[] nums2, int k) {
        PriorityQueue<Pair<Integer, Integer>> minHeap = new PriorityQueue<>((a, b) -> nums1[a.getKey()] + nums2[a.getValue()] - nums1[b.getKey()] - nums2[b.getValue()]);
        int m = nums1.length, n = nums2.length;
        Set<String> set = new HashSet<>();
        minHeap.add(new Pair<>(0, 0));
        set.add("0_0");
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            Pair<Integer, Integer> node = minHeap.poll();
            int r = node.getKey(), c = node.getValue();
            result.add(Arrays.asList(nums1[r], nums2[c]));
            if (c + 1 < n) {
                String key = r + "_" + (c + 1);
                if (!set.contains(key)) {
                    minHeap.add(new Pair<>(r, c + 1));
                    set.add(key);
                }
            }
            if (r + 1 < m) {
                String key = (r + 1) + "_" + c;
                if (!set.contains(key)) {
                    minHeap.add(new Pair<>(r + 1, c));
                    set.add(key);
                }
            }
        }
        return result;
    }
}
