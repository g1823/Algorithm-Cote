package leetCode.moderately;

import leetCode.help.ListNode;

/**
 * @author: gj
 * @description: 61. 旋转链表
 */
public class RotateRight {

    public static void main(String[] args) {
        ListNode node = new ListNode(1);
        node.next = new ListNode(2);
        node.next.next = new ListNode(3);
        node.next.next.next = new ListNode(4);
        node.next.next.next.next = new ListNode(5);
        new RotateRight().rotateRight(node, 2);
        System.out.println();
    }

    /**
     * 思路：
     * 1. 获取链表的长度n和尾节点tTail
     * 2. 根据链表长度以及移动k次计算出移动后的头节点cur和移动后的头节点的父节点pre
     * - 每移动n次就会回到初始位置，所以k取余n
     * 3. 将cur移动至头节点，tTail指向原始的头节点head，再将pre节点的next指向null避免出现环
     */
    public ListNode rotateRight(ListNode head, int k) {
        if(head == null || head.next == null || k == 0){
            return head;
        }
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode tTail = dummy.next;
        int n = 1;
        while (tTail.next != null) {
            n++;
            tTail = tTail.next;
        }
        // 每移动n次就会恢复到初始位置，直接取余
        k = k % n;
        if (k == 0) {
            return head;
        }
        // 获取到移动k次之后的头节点以及头节点的父节点
        ListNode pre = dummy, cur = dummy.next;
        for (int i = 0; i < n - k; i++) {
            pre = cur;
            cur = cur.next;
        }
        dummy.next = cur;
        tTail.next = head;
        pre.next = null;
        return dummy.next;
    }
}
