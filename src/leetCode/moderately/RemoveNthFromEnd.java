package leetCode.moderately;

import leetCode.help.ListNode;

/**
 * @author: gj
 * @description: 19. 删除链表的倒数第 N 个结点
 */
public class RemoveNthFromEnd {
    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        ListNode listNode1 = removeNthFromEnd(listNode, 1);
    }

    /**
     * 使用递归构造栈，从末尾回退的过程中计数，得到倒数第n哥节点，然后删除
     * @param head 头节点
     * @param n 倒数第n个节点
     * @return 结果
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        int l = dfs(head, n);
        if (l == n) {
            head = head.next;
        }
        return head;
    }

    public static int dfs(ListNode node, int n) {
        if (node == null) {
            return 0;
        }
        int l = dfs(node.next, n);
        if (l == n) {
            node.next = node.next == null ? null : node.next.next;
        }
        return l + 1;
    }

    /**
     * 双指针，快慢指针：
     * 快指针先走n步，然后快慢指针一起走，当快指针走到末尾时，慢指针指向的节点就是倒数第n个节点
     * @param head 头节点
     * @param n 倒数第n个节点
     * @return 结果
     */
    public static ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode first = dummy, second = dummy;

        // 先让fast走n+1步，确保slow指向删除节点前一个节点
        for (int i = 0; i <= n; i++) {
            first = first.next;
        }

        // 快慢指针同时移动，直到快指针到末尾
        while (first != null) {
            first = first.next;
            second = second.next;
        }

        // 删除节点
        second.next = second.next.next;
        return dummy.next;
    }
}
