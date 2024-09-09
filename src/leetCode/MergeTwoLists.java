package leetCode;

import leetCode.help.ListNode;

/**
 * @Package leetCode
 * @Date 2024/9/7 21:47
 * @Author gaojie
 * @description: 21. 合并两个有序链表
 */
public class MergeTwoLists {

    /**
     * 参考归并排序
     * @param list1 待排序链表1
     * @param list2 待排序链表2
     * @return 合并后链表
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode root = new ListNode(-1), node1 = list1, node2 = list2;
        ListNode t = root;
        while (node1 != null && node2 != null) {
            if (node1.val <= node2.val) {
                t.next = node1;
                node1 = node1.next;
            } else {
                t.next = node2;
                node2 = node2.next;
            }
            t = t.next;
        }
        while (node1 != null) {
            t.next = node1;
            node1 = node1.next;
            t = t.next;
        }
        while (node2 != null) {
            t.next = node2;
            node2 = node2.next;
            t = t.next;
        }
        root = root.next;
        return root;
    }
}
