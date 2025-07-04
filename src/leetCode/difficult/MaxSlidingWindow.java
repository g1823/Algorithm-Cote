package leetCode.difficult;

/**
 * @author: gj
 * @description: 239. 滑动窗口最大值
 */
public class MaxSlidingWindow {

    public static void main(String[] args) {
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int[] result = new MaxSlidingWindow().maxSlidingWindow(nums, 3);
    }

    class Node {
        int index;
        Node next;
        Node prev;

        public Node(int index) {
            this.index = index;
        }
    }

    class Deque {
        Node head;
        Node tail;
        int[] nums;

        public Deque(int[] nums) {
            this.nums = nums;
        }

        public void addLast(int index) {
            if (head == null) {
                head = new Node(index);
                tail = head;
                return;
            }
            Node node = tail;
            while (node != null && nums[node.index] < nums[index]) {
                node = node.prev;
            }
            Node addNode = new Node(index);
            if (node == null) {
                head = addNode;
                tail = addNode;
            } else {
                // node 之后的所有节点要断掉,可能引发“悬挂指针”或链断问题
                if (node.next != null) {
                    node.next.prev = null;
                }
                node.next = addNode;
                addNode.prev = node;
                tail = addNode;
            }
        }

        public int getMax() {
            return nums[head.index];
        }

        public void removeFirst() {
            if (head == null) {
                return;
            }
            if (head == tail) {
                head = null;
                tail = null;
                return;
            }
            head = head.next;
            head.prev = null;
        }
    }

    /**
     * 思考过程：
     * 1. 最容易想到的是每次窗口滑动后，对窗口内元素全部遍历一遍，找最大值。时间复杂度为 O(nk)，效率低。
     * 2. 使用大根堆可以快速获取最大值，但堆中删除任意元素成本较高，需配合延迟删除，时间复杂度约 O(n log k)。
     * 3. 维护最大值和次大值+计数，看似更省空间，但当最大值被移除且次数为1时，必须重新寻找最大值，本质仍需遍历。
     * 4. 最大栈（类似最小栈）也难以解决滑动窗口中“中间元素”被移除的问题，仍要线性扫描更新最大值。
     * <p>
     * 最终结论：
     * 上述方案都难以避免在“窗口左端元素移除时”重新查找最大值的问题。
     * 考虑到这样一个关键点：若当前值大于窗口中所有元素，那么在它进入窗口之前，之前那些更小的值永远不会成为最大值，可以提前淘汰。
     * <p>
     * 操作方式（使用单调队列，维护下标）：
     * - 每次新进一个元素时，移除所有比它小的尾部元素（它们永远不会是未来的最大值）
     * - 将当前元素下标加入队列尾部
     * - 若队首元素已不在窗口范围内（i - k），则从队首弹出
     * - 当前窗口最大值就是队首所指元素
     * <p>
     * 队列中存的是元素下标，而不是值本身，便于判断是否超出窗口范围。
     * <p>
     * 该方法时间复杂度为 O(n)，是滑动窗口最大值问题的最优解。
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];
        Deque deque = new Deque(nums);
        for (int i = 0; i < nums.length; i++) {
            deque.addLast(i);
            if (i >= k - 1) {
                result[i - k + 1] = deque.getMax();
                if (deque.head.index == i - k + 1) {
                    deque.removeFirst();
                }
            }
        }
        return result;
    }
}
