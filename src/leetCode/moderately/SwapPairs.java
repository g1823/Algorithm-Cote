package leetCode.moderately;

import leetCode.help.ListNode;

/**
 * @author: gj
 * @description: 24. 两两交换链表中的节点
 */
public class SwapPairs {
    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        new SwapPairs().swapPairs(node1);
    }

    /**
     * 思路：
     * 1. 使用一个虚拟头节点（tHead）简化交换时的头节点处理，避免单独判断头节点。
     * 2. 使用 pre 临时保存当前组的第一个节点。
     * 3. 使用 preParent 保存上一组交换后的尾节点，用于连接下一组的头节点。
     * 4. 遍历链表：
     * - 如果 pre 为空，说明这是本组的第一个节点，记录到 pre。
     * - 如果 pre 不为空，说明这是本组的第二个节点，执行交换：
     * a. 让第一个节点（pre）指向下一组的头节点（tempNode）
     * b. 让第二个节点（cur）指向第一个节点（pre）
     * c. 让上一组的尾节点（preParent）连接到当前组的新头节点（cur）
     * d. 更新 preParent 到当前组的尾节点（pre）
     * e. 清空 pre，准备下一组
     * 5. 返回虚拟头节点的 next（交换后的链表头）。
     */
    public ListNode swapPairs(ListNode head) {
        // 虚拟头节点，方便处理链表头的交换
        ListNode tHead = new ListNode(-1);
        tHead.next = head;

        // pre：当前组的第一个节点
        // cur：当前遍历的节点
        // preParent：上一组交换后的尾节点
        ListNode pre = null, cur = head, preParent = tHead;

        // 遍历链表
        while (cur != null) {
            // 暂存下一个节点（可能是下一组的第一个节点）
            ListNode tempNode = cur.next;

            if (pre == null) {
                // 当前组的第一个节点
                pre = cur;
            } else {
                // 当前组的第二个节点，执行交换
                pre.next = tempNode;
                cur.next = pre;
                preParent.next = cur;
                // 更新上一组尾节点指针
                preParent = pre;
                // 清空 pre，准备下一组
                pre = null;
            }

            cur = tempNode;
        }

        return tHead.next;
    }

    /**
     * 对于swapPairs优化
     * 思路：
     * 1. 使用虚拟头节点（dummy）简化头节点交换的特殊情况。
     * 2. pre 指针永远指向已处理部分的最后一个节点，方便连接下一组交换后的头节点。
     * 3. 遍历链表时，每次取出成对的两个节点 one 和 two，进行指针交换：
     * - pre.next = two        // 让上一组尾节点连接到本组的新头节点
     * - one.next = two.next   // 第一个节点指向下一组的第一个节点
     * - two.next = one        // 第二个节点指向第一个节点（完成交换）
     * 4. pre 移动到当前组交换后的尾节点（即 one）
     * 5. head 移动到下一组的第一个节点（即 one.next）
     */
    public ListNode swapPairs2(ListNode head) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode pre = dummy;
        while (head != null && head.next != null) {
            ListNode one = head;
            ListNode two = head.next;
            // 交换
            pre.next = two;
            one.next = two.next;
            two.next = one;
            pre = one;
            // 移动指针
            head = one.next;
        }
        return dummy.next;
    }
}
