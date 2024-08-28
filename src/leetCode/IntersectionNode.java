package leetCode;

import leetCode.help.ListNode;

/**
 * @author: gj
 * @description: 160 相交链表
 */
public class IntersectionNode {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode a = headA, b = headB;
        if (a.next == null || b.next == null) {
            return null;
        }
        while (a != b) {
            a = a.next == null ? headB : a.next;
            b = b.next == null ? headA : b.next;
        }
        return a;
    }
}

