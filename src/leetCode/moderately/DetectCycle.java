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
