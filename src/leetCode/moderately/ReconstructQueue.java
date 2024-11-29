package leetCode.moderately;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author: gj
 * @description: 406. 根据身高重建队列
 */
public class ReconstructQueue {
    public static void main(String[] args) {
        int[][] people = new int[][]{{7, 0}, {4, 4}, {7, 1}, {5, 0}, {6, 1}, {5, 2}};
        System.out.println(new ReconstructQueue().reconstructQueue(people));
    }

    class Node {
        int[] data;
        Node child;
        Node parent;
    }

    /**
     * 底身高不会对高身高产生任何影响，因此可以从高到低排序后直接插入
     * 可以采用List的add方法
     * 也可以像这里一样，新建一个node类来存储
     * @param people
     * @return
     */
    public int[][] reconstructQueue(int[][] people) {
        int length = people.length;
        int[][] result = new int[length][];
        List<int[]> sortData = new ArrayList<>(Arrays.asList(people));
        sortData.sort((o1, o2) -> {
            if (o1[0] < o2[0]) return 1;
            if (o1[0] > o2[0]) return -1;
            return Integer.compare(o1[1], o2[1]);
        });
        Node node = new Node();
        node.data = sortData.get(0);
        for (int i = 1; i < length; i++) {
            int[] data = sortData.get(i);
            int index = data[1];
            int count = 0;
            boolean isLast = false;
            Node targetNode = node;
            while (count != index) {
                if (targetNode.child == null) {
                    isLast = true;
                    break;
                }
                targetNode = targetNode.child;
                count++;
            }
            Node newNode = new Node();
            newNode.data = data;
            if (index == 0) {
                newNode.child = node;
                node.parent = newNode;
                node = newNode;
                continue;
            }
            if (isLast) {
                targetNode.child = newNode;
                newNode.parent = targetNode;
            } else {
                newNode.child = targetNode;
                newNode.parent = targetNode.parent;
                targetNode.parent.child = newNode;
                targetNode.parent = newNode;
            }
        }
        for (int i = 0; node != null; i++) {
            result[i] = node.data;
            node = node.child;
        }
        return result;
    }
}
