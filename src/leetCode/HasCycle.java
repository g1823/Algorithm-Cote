package leetCode;

import leetCode.help.ListNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: gj
 * @description: 141. 环形链表
 */
public class HasCycle {
    public static void main(String[] args) {

    }

    public boolean hasCycle(ListNode head) {
        Map<ListNode, Object> map = new HashMap<>();
        Object o = new Object();
        while (head != null) {
            if (map.containsKey(head)) return true;
            map.put(head, o);
            head = head.next;
        }
        return false;
    }

    /**
     * 快慢指针
     *  时间复杂度：O(N)，其中 N 是链表中的节点数。
     *  当链表中不存在环时，快指针将先于慢指针到达链表尾部，链表中每个节点至多被访问两次。
     *  当链表中存在环时，每一轮移动后，快慢指针的距离将减小一。而初始距离为环的长度，因此至多移动 N 轮
     * @param head 指针头部
     * @return 是否有环
     */
    public boolean hasCycle2(ListNode head) {
        if (head == null || head.next == null) return false;
        ListNode slowNode = head, fastNode = head.next;
        while (slowNode != fastNode) {
            if (fastNode == null || fastNode.next == null) return false;
            slowNode = slowNode.next;
            fastNode = fastNode.next.next;
        }
        return true;
    }
}
