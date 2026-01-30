package leetCode.simple;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author: gj
 * @description: 933. 最近的请求次数
 */
class RecentCounter {
    List<Integer> list;

    public RecentCounter() {
        list = new ArrayList<>();
    }

    public int ping(int t) {
        list.add(t);
        int count = 0;
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i) >= t - 3000) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }


    /**
     * 上面解决，会导致list不断变大
     * 可以使用队列，因为保证每次ping的t更大，因此每次进入一个可以将当前时间小于t-3000的元素全部移除
     */
    class RecentCounter2 {

        Queue<Integer> queue1;

        public RecentCounter2() {
            queue1 = new LinkedList<>();
        }

        public int ping(int t) {
            queue1.add(t);
            while (!queue1.isEmpty() && queue1.peek() < t - 3000) {
                queue1.poll();
            }
            return queue1.size();
        }
    }
}