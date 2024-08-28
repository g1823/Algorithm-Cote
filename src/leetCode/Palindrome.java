package leetCode;

import leetCode.help.ListNode;

/**
 * @author: gj
 * @description: 234 回文链表
 */
public class Palindrome {
    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        listNode.next = listNode2;
        ListNode listNode3 = new ListNode(1);
        listNode2.next = listNode3;
        Palindrome palindrome = new Palindrome();
        System.out.println(palindrome.isPalindrome(listNode));
    }

    ListNode currentNode;
    boolean isExit = false;

    public boolean isPalindrome(ListNode head) {
        currentNode = head;
        return recursive(head);
    }

    public boolean recursive(ListNode node) {
        if (node != null) {
            boolean b = recursive(node.next);
            if (currentNode == node) {
                isExit = true;
            }
            if (isExit) {
                return b;
            }
            if (b && currentNode.val == node.val) {
                currentNode = currentNode.next;
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

}
