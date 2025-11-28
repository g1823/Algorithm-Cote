package leetCode.moderately;

import leetCode.help.ListNode;

/**
 * @author: gj
 * @description: 82. 删除排序链表中的重复元素 II
 */
public class DeleteDuplicates {
    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(3);
        ListNode node5 = new ListNode(4);
        ListNode node6 = new ListNode(4);
        ListNode node7 = new ListNode(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node7;
        System.out.println(new DeleteDuplicates().deleteDuplicates(node1));
    }

    /**
     * 思路：
     * 1. 创建一个虚拟节点，指向head
     * 2. 遍历链表，每次找到一个不重复的节点，并记录当前节点是否重复
     * 3. 如果当前节点重复，则将当前节点的next指向下一个不重复的节点，否则将当前节点移动到下一个节点
     * 4. 直到遍历结束
     */
    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode pre = dummy;
        while (pre != null && pre.next != null) {
            // 每次找到一个不重复的节点
            ListNode cur = pre.next;
            int val = cur.val;
            boolean flag = false;
            while (cur.next != null && cur.next.val == val) {
                cur = cur.next;
                flag = true;
            }
            if (flag) {
                pre.next = cur.next;
            } else {
                pre = pre.next;
            }
        }
        return dummy.next;
    }
}
