package leetCode.moderately;

import leetCode.help.ListNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gj
 * @description: 25. K 个一组翻转链表
 */
public class ReverseKGroup {
    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        System.out.println(new ReverseKGroup().reverseKGroup(listNode1, 2));
    }

    /**
     * 思路：
     * 1. 使用 dummy 节点统一处理链表头的翻转边界。
     * 2. 从当前分组的前驱节点 pre 开始，收集接下来的 k 个节点到 window 中。
     * 3. 如果不足 k 个节点，直接结束（剩余节点保持原顺序）。
     * 4. 如果正好 k 个节点：
     * - 记录翻转后这一组的尾节点（翻转前的第一个节点）的下一个节点 tail（下一组的第一个节点）。
     * - 将 window 中的节点反向连接，完成当前组翻转。
     * - 把当前组的尾节点连接到 tail，把 pre 连接到翻转后的头节点。
     * - pre 移动到当前组的尾节点，准备处理下一组。
     * 5. 返回 dummy.next 作为新的链表头。
     * 时间复杂度：O(n)，每个节点仅访问一次。
     * 空间复杂度：O(k)，来自 window 数组存储当前组的节点。
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        // 虚拟头节点，方便处理头节点翻转
        ListNode dummy = new ListNode(-1), pre = dummy;
        dummy.next = head;
        // 存储当前窗口的 k 个节点
        List<ListNode> window = new ArrayList<>();
        // 遍历链表
        while (pre != null) {
            window.clear();
            // 收集 k 个节点到 window
            ListNode cur = pre.next;
            for (int i = 0; i < k && cur != null; i++) {
                window.add(cur);
                cur = cur.next;
            }
            // 如果不足 k 个，直接结束
            if (window.size() < k) {
                pre.next = window.size() > 0 ? window.get(0) : null;
                break;
            }
            // 下一组的第一个节点（翻转后当前组的尾节点会指向它）
            ListNode tail = window.get(k - 1).next;
            // 反转 window 中的节点
            for (int i = k - 1; i > 0; i--) {
                window.get(i).next = window.get(i - 1);
            }
            // 翻转后当前组的尾节点（原来的第一个节点）连接到下一组
            window.get(0).next = tail;
            // pre 连接到翻转后的当前组头节点
            pre.next = window.get(k - 1);
            // pre 移动到当前组的尾节点（翻转前的第一个节点）
            pre = window.get(0);
        }

        return dummy.next;
    }

    /**
     * TODO: 待完成，原地反转
     */
    public ListNode reverseKGroup2(ListNode head, int k) {
        return null;
    }
}
