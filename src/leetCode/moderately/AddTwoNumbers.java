package leetCode.moderately;

import leetCode.help.ListNode;

/**
 * @author: gj
 * @description: 2. 两数相加
 */
public class AddTwoNumbers {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);

        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        ListNode listNode = addTwoNumbers(l1, l2);
        System.out.println(listNode);
    }

    private static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        ListNode leftNode = l1, rightNode = l2, resultNode = new ListNode(-1), thisTemp = resultNode;
        boolean flag = false;
        while (leftNode != null && rightNode != null) {
            int result = flag ? 1 + leftNode.val + rightNode.val : rightNode.val + leftNode.val;
            if (result >= 10) {
                thisTemp.next = new ListNode(result % 10);
                flag = true;
            } else {
                thisTemp.next = new ListNode(result);
                flag = false;
            }
            thisTemp = thisTemp.next;
            leftNode = leftNode.next;
            rightNode = rightNode.next;
        }
        while (leftNode != null) {
            int result = flag ? 1 + leftNode.val : leftNode.val;
            if (result >= 10) {
                thisTemp.next = new ListNode(result % 10);
                flag = true;
            } else {
                thisTemp.next = new ListNode(result);
                flag = false;
            }
            thisTemp = thisTemp.next;
            leftNode = leftNode.next;
        }
        if (flag) {
            thisTemp.next = new ListNode(1);
        }
        while (rightNode != null) {
            int result = flag ? 1 + rightNode.val : rightNode.val;
            if (result >= 10) {
                thisTemp.next = new ListNode(result % 10);
                flag = true;
            } else {
                thisTemp.next = new ListNode(result);
                flag = false;
            }
            thisTemp = thisTemp.next;
            rightNode = rightNode.next;
        }
        if (flag) {
            thisTemp.next = new ListNode(1);
        }
        return resultNode.next;
    }
}
