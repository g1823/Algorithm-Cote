package leetCode.moderately;

import leetCode.help.ListNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gj
 * @description: 143. 重排链表
 */
public class ReorderList {

    /**
     * 重排时，难点在于难以找到当前节点对应的节点，因为不支持随机获取任意下标的节点
     * 因此，直接用List记录所有节点，这样节点i=0可以迅速找到i=n-i-1，即i=0节点配对的节点
     * 然后用第二个List将最终排序后的结果放入即可，这样可以避免将原List所有节点后移操作了
     */
    public void reorderList(ListNode head) {
        List<ListNode> list = new ArrayList<>();
        ListNode cur = head;
        while (cur != null) {
            list.add(cur);
            cur = cur.next;
        }
        int n = list.size(), i = 0;
        List<ListNode> r = new ArrayList<>(n);
        while (i < n - i) {
            r.add(list.get(i));
            r.add(list.get(n - i - 1));
            i++;
        }
        for (int j = 0; j < r.size() - 1; j++) {
            r.get(j).next = r.get(j + 1);
        }
        r.get(n - 1).next = null;
    }

    /**
     * 观察可以发现，重排链表后的数据实际上就是将链表后半部分反转，然后在和前半部分拼接起来，因此可以进行如下操作：
     * 1、快慢指针找到链表中间节点
     * 2、对后半部分节点进行反转链表
     * 3、合并两个链表
     */
    public void reorderList2(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }

        // 1. 找到中间节点
        ListNode fast = head, slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        // 2. 反转后半部分链表（从slow.next开始）
        ListNode mid = slow;
        ListNode cur = mid.next;
        // 断开前后两部分
        mid.next = null;

        ListNode pre = null;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        // 3. 合并两个链表
        ListNode left = head;
        ListNode right = pre;
        while (left != null && right != null) {
            ListNode leftNext = left.next;
            ListNode rightNext = right.next;
            left.next = right;
            right.next = leftNext;
            left = leftNext;
            right = rightNext;
        }
    }
}
