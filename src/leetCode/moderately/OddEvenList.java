package leetCode.moderately;

import leetCode.help.ListNode;

/**
 * @author: gj
 * @description: 328. 奇偶链表
 */
public class OddEvenList {
    public static void main(String[] args) {

    }

    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }
        ListNode evenHeadNode = head.next;
        ListNode oddCurNode = head, evenCurNode = evenHeadNode;
        ListNode curNode = head.next.next;
        boolean flag = true;
        while (curNode != null) {
            ListNode temp = curNode.next;
            // flag为true时，处理奇数节点
            if (flag) {
                oddCurNode.next = curNode;
                oddCurNode = curNode;
            } else {
                evenCurNode.next = curNode;
                evenCurNode = curNode;
            }
            flag = !flag;
            curNode = temp;
        }
        // 处理完后，将奇数链表最后一个元素的next连接到偶数链表的头结点
        oddCurNode.next = evenHeadNode;
        // 将偶数链表最后一个元素的next置为null
        evenCurNode.next = null;
        return head;
    }
}
