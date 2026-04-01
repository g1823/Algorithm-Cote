package leetCode.simple;

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

    /**
     * 反转单链表（迭代方法）
     * 思路：使用三个指针（prev、curr、next）遍历链表，每次迭代都将当前节点的 next 指向前驱节点，
     * 然后整体向后移动指针，最终 prev 指向原链表的最后一个节点，即反转后的头节点。
     */
    public ListNode reverseList2(ListNode head) {
        // 初始化前驱节点为 null，因为反转后头节点的 next 应指向 null
        ListNode prev = null;
        // 当前节点从链表的头节点开始
        ListNode curr = head;
        // 遍历链表，直到当前节点为 null（即遍历完所有节点）
        while (curr != null) {
            // 临时保存当前节点的下一个节点，避免修改 curr.next 后丢失对后续节点的引用
            ListNode tempNode = curr.next;
            // 将当前节点的 next 指针指向前驱节点，完成当前节点的反转
            curr.next = prev;
            // 将前驱节点移动到当前节点（为下一次迭代做准备）
            prev = curr;
            // 将当前节点移动到下一个节点（即之前保存的 tempNode）
            curr = tempNode;
        }
        // 循环结束后，prev 指向原链表的最后一个节点，也就是反转后的新头节点
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
