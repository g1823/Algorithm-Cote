package leetCode;

import leetCode.help.ListNode;

/**
 * @author: gj
 * @description: 206 反转链表
 */
public class ReverseList {
    public static void main(String[] args) {
        ListNode listNode = new ListNode(1, new ListNode(2, new ListNode(3)));
        ReverseList reverseList = new ReverseList();
        //ListNode reversedList = reverseList.reverseList(listNode);
        ListNode reversedList = reverseList.reverseList2(listNode);

        // 输出反转后的链表
        while (reversedList != null) {
            System.out.print(reversedList.val + " ");
            reversedList = reversedList.next;
        }
    }

    ListNode resultNode;

    public ListNode reverseList2(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null){
            ListNode tempNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = tempNode;
        }
        return prev;
    }


    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        recursive(head);
        head.next = null;
        return resultNode;
    }

    public ListNode recursive(ListNode node) {
        if (node.next == null) {
            resultNode = node;
            return resultNode;
        } else {
            ListNode listNode = recursive(node.next);
            listNode.next = node;
            return listNode.next;
        }
    }
}
