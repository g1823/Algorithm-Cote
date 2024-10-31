package leetCode.moderately;

import leetCode.help.ListNode;


/**
 * @author: gj
 * @description: 148.排序链表
 */
public class SortList {

    public static void main(String[] args) {
        ListNode head = new ListNode(4);
        ListNode node1 = new ListNode(2);
        ListNode node2 = new ListNode(1);
        ListNode node3 = new ListNode(3);
        head.next = node1;
        node1.next = node2;
        node2.next = node3;
        SortList sortList = new SortList();
        ListNode listNode = sortList.sortList(head);
        while (listNode != null) {
            System.out.print(listNode.val + " ");
            listNode = listNode.next;
        }

    }

    public ListNode sortList(ListNode head) {
        if (head == null) return null;
        ListNode tailNode = head;
        while (tailNode.next != null) {
            tailNode = tailNode.next;
        }
        return recursion(head, tailNode);
    }

    public ListNode recursion(ListNode head, ListNode tail) {
        if (head == tail) return new ListNode(head.val);
        ListNode midNode = getMidNode(head, tail);
        ListNode leftNode = recursion(head, midNode);
        ListNode rightNode = recursion(midNode.next, tail);
        ListNode thisNode = new ListNode(-1), thisTemp = thisNode;
        while (leftNode != null && rightNode != null) {
            if (leftNode.val <= rightNode.val) {
                thisTemp.next = new ListNode(leftNode.val);
                leftNode = leftNode.next;
            } else {
                thisTemp.next = new ListNode(rightNode.val);
                rightNode = rightNode.next;
            }
            thisTemp = thisTemp.next;
        }
        while (leftNode != null) {
            thisTemp.next = new ListNode(leftNode.val);
            leftNode = leftNode.next;
            thisTemp = thisTemp.next;
        }
        while (rightNode != null) {
            thisTemp.next = new ListNode(rightNode.val);
            rightNode = rightNode.next;
            thisTemp = thisTemp.next;
        }
        return thisNode.next;
    }

    // TODO:查找链表的中间结点（快慢指针用法）
    public ListNode getMidNode(ListNode head, ListNode tail) {
        ListNode tHead = head, tTail = head;
        while (tTail != tail) {
            if (tTail.next != tail && tTail.next != null) tTail = tTail.next.next;
            else break;
            tHead = tHead.next;
        }
        return tHead;
    }
}
