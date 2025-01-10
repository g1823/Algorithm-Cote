package leetCode.moderately;

import leetCode.help.ListNode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: gj
 * @description: 142. 环形链表 II
 */
public class DetectCycle {
    public static void main(String[] args) {
//        ListNode head = new ListNode(1);
//        ListNode node1 = new ListNode(2);
//        ListNode node2 = new ListNode(3);
//        ListNode node3 = new ListNode(4);
//        head.next = node1;
//        node1.next = node2;
//        node2.next = node3;
//        node3.next = head;
        ListNode head = new ListNode(3);
        ListNode node1 = new ListNode(2);
        ListNode node2 = new ListNode(0);
        ListNode node3 = new ListNode(-4);
        head.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node1;
        new DetectCycle().detectCycle2(head);
    }

    /**
     * 蛮力法
     *
     * @param head 头节点
     * @return ListNode
     */
    public ListNode detectCycle(ListNode head) {
        ListNode tNode = head;
        if (tNode == null || tNode.next == null) {
            return null;
        }
        Set<ListNode> listNodeSet = new HashSet<>();
        while (tNode != null) {
            if (!listNodeSet.contains(tNode)) {
                listNodeSet.add(tNode);
                tNode = tNode.next;
            } else {
                return tNode;
            }
        }
        return null;
    }

    /**
     * 快慢指针：
     * 快指针一次走两步。慢指针一次一步
     * 如果没有环，那么快指针只需要遍历一次就到尾部(null)
     * 如果有环，快指针一定会和慢指针相遇，假设：
     * - 从起点到环开始的位置长度为 ：a
     * - 从环开始的位置到快慢指针相遇的位置长度为 ：b
     * - 从相遇位置到环结束位置长度为：c
     * 那么可得出以下等式：
     * 慢指针走了： a + b
     * 快指针走了a后又走了k圈环： a + k(b+c) + b
     * 快指针是慢指针的2倍：2 * （ a + b ） = a + k(b+c) + b
     * -> a = k(b + c) - b
     * 此时，快指针和慢指针均在相遇位置，需要求出a处的节点
     * 那么只需要让一个指针从起始位置一次一步前进，另一个指针从b点(也就是相遇处)一次一步前进，二者相遇时则正好是a点
     * @param head
     * @return
     */
    public ListNode detectCycle2(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        ListNode slowNode = head.next, fastNode = head.next.next;
        while (fastNode != null) {
            if (fastNode.next == null) return null;
            if (fastNode == slowNode) break;
            fastNode = fastNode.next.next;
            slowNode = slowNode.next;
        }
        if(fastNode==null) return null;
        ListNode t = head;
        while (slowNode != t) {
            slowNode = slowNode.next;
            t = t.next;
        }
        return t;
    }
}
