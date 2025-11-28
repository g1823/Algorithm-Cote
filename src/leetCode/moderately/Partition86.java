package leetCode.moderately;

import leetCode.help.ListNode;

/**
 * @author: gj
 * @description: 86. 分隔链表
 */
public class Partition86 {
    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(4);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(2);
        ListNode node5 = new ListNode(5);
        ListNode node6 = new ListNode(2);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        System.out.println(new Partition86().partition(node1, 3));
    }

    /**
     * 两个节点，一个记录小的节点，一个记录大的节点，最后合并
     */
    public ListNode partition(ListNode head, int x) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(-1), dummy2 = new ListNode(-1);
        ListNode cur1 = dummy, cur2 = dummy2, tCur = head;
        while (tCur != null) {
            if (tCur.val < x) {
                cur1.next = tCur;
                cur1 = cur1.next;
            } else {
                cur2.next = tCur;
                cur2 = cur2.next;
            }
            tCur = tCur.next;
        }
        cur2.next = null;
        cur1.next = dummy2.next;
        return dummy.next;
    }
}
