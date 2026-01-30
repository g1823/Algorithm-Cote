package leetCode.moderately;

import leetCode.help.ListNode;

/**
 * @author: gj
 * @description: 2095. 删除链表的中间节点
 */
public class DeleteMiddle {
    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(3);
        ListNode node3 = new ListNode(4);
        ListNode node4 = new ListNode(7);
        ListNode node5 = new ListNode(1);
        ListNode node6 = new ListNode(2);
        ListNode node7 = new ListNode(6);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node7;
        System.out.println(new DeleteMiddle().deleteMiddle(node1));
    }

    public ListNode deleteMiddle(ListNode head) {
        // 只有一个节点直接返回null
        if (head == null || head.next == null) {
            return null;
        }
        // 直接快慢指针，快指针依次走两步，慢指针依次走一步，当快指针走到末尾时，慢指针指向的节点就是中间节点
        ListNode slow = head, fast = head, pre = null;
        while (true) {
            if (fast == null || fast.next == null) {
                if (pre.next != null) {
                    pre.next = pre.next.next;
                }
                break;
            }
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        return head;
    }
}
