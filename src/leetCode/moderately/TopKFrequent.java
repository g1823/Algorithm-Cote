package leetCode.moderately;

import java.util.*;

/**
 * @author: gj
 * @description: 347. 前 K 个高频元素
 */
public class TopKFrequent {
    public static void main(String[] args) {
        int[] nums = new int[]{4, 1, -1, 2, -1, 2, 3};
        System.out.println(Arrays.toString(new TopKFrequent().topKFrequent2(nums, 2)));
    }

    /**
     * 与官方题解2类似，先将所有元素出现次数通过一个map映射
     * 然后建立一个最小堆，每遍历一个元素就将其与堆内出现次数最少的元素相比，如果大于这个元素则放入堆，删除出现次数最小的，并维护好最小次数
     * 时间复杂度O(nlog(k))
     * 这里是手动写了一个堆 TopKNum
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (map.containsKey(num)) {
                Integer count = map.get(num);
                map.put(num, count + 1);
            } else {
                map.put(num, 1);
            }
        }
        TopKNum topKNum = new TopKNum();
        topKNum.k = k;
        // 官方题解，直接采用最小堆
//        遍历map，用最小堆保存频率最大的k个元素
//        PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
//            @Override
//            public int compare(Integer a, Integer b) {
//                return map.get(a) - map.get(b);
//            }
//        });
//        for (Integer key : map.keySet()) {
//            if (pq.size() < k) {
//                pq.add(key);
//            } else if (map.get(key) > map.get(pq.peek())) {
//                pq.remove();
//                pq.add(key);
//            }
//        }

        map.forEach((num, count) -> {
            if (count >= topKNum.getMinCount()) {
                topKNum.insert(num, count);
            }
        });
        int[] result = new int[k];
        TopKNum.Node node = topKNum.head.child;
        int i = 0;
        while (node != null) {
            result[i++] = node.val;
            node = node.child;
        }
        return result;
    }

    class TopKNum {
        Node head = new Node();
        int minCount = 0;
        int nodeCount = 0;
        int k = 0;

        public void insert(int val, int count) {
            Node node = head.child, parent = head;
            Node newNode = new Node(val, count);
            boolean hasInsert = false;
            while (node != null) {
                if (node.count < count) {
                    newNode.child = node;
                    newNode.parent = node.parent;
                    node.parent.child = newNode;
                    node.parent = newNode;
                    nodeCount++;
                    hasInsert = true;
                    break;
                }
                node = node.child;
                parent = parent.child;
            }
            if (!hasInsert) {
                parent.child = newNode;
                newNode.parent = parent;
                minCount = count;
                nodeCount++;
            }
            if (nodeCount > k) {
                while (parent.child != null) parent = parent.child;
                minCount = parent.count;
                parent.parent.child = null;
            }
        }

        public int getMinCount() {
            return nodeCount < k ? 0 : minCount;
        }

        class Node {
            int val;
            int count;
            Node parent;
            Node child;

            public Node() {
            }

            public Node(int val, int count) {
                this.val = val;
                this.count = count;
            }
        }

    }


    /**
     * 官方题解3，采用桶排序的思想，先用map记录元素和元素出现次数的映射
     * 然后按照出现次数作为下标存入一个数组中
     * 之后按下标从大到小遍历即可
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] topKFrequent2(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }

        List<Integer>[] lists = new ArrayList[nums.length + 1];
        map.forEach((val, count) -> {
            List<Integer> list = lists[count];
            if (list == null) list = new ArrayList<>();
            list.add(val);
            lists[count] = list;
        });
        int[] result = new int[k];
        int t = 0;
        i:
        for (int i = nums.length; i >= 0; i--) {
            List<Integer> list = lists[i];
            if (list == null || list.size() == 0) continue;
            for (Integer val : list) {
                result[t++] = val;
                if (t >= k) break i;
            }
        }
        return result;
    }
}
