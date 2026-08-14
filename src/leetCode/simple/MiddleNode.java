package leetCode.simple;

import leetCode.help.ListNode;

/**
 * @description: 876. 链表的中间结点
 */
public class MiddleNode {

    /**
     * 快慢指针(双指针)
     */
    public ListNode middleNode(ListNode head) {
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
}
