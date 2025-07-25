package leetCode.difficult;

import leetCode.help.ListNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author: gj
 * @description: 23. 合并 K 个升序链表
 */
public class MergeKLists {

    /**
     * 思路：分治法
     * 跟归并排序类似，两两合并
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }
        if (lists.length == 1) {
            return lists[0];
        }
        Queue<ListNode> queue = new LinkedList<>();
        for (ListNode listNode : lists) {
            queue.offer(listNode);
        }

        if (queue.isEmpty()) {
            return null;
        }

        while (queue.size() > 1) {
            ListNode l1 = queue.poll();
            ListNode l2 = queue.poll();
            queue.offer(mergeTwoLists(l1, l2));
        }
        return queue.poll();
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(-1);
        ListNode node = head;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                node.next = l1;
                l1 = l1.next;
            } else {
                node.next = l2;
                l2 = l2.next;
            }
            node = node.next;
        }
        node.next = l1 == null ? l2 : l1;
        return head.next;
    }


    /**
     * TODO：堆排序版本
     */
    public ListNode mergeKLists2(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }
        if (lists.length == 1) {
            return lists[0];
        }

        return null;
    }


}
