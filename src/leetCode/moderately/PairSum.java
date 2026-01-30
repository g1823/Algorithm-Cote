package leetCode.moderately;

import leetCode.help.ListNode;
import leetCode.simple.ReverseList;

/**
 * @author: gj
 * @description: 2130. 链表最大孪生和
 */
public class PairSum {
    /**
     * 思路：
     * 最开始，想着用栈来记录节点值，实现链表倒序，然后遍历链表和栈求和，但是需要额外的O(n)时间复杂度存储倒叙后的数据
     * 思路2：
     * 可以使用快慢指针获取后半段第一个节点，然后反转后半段链表{@link ReverseList#reverseList2(leetCode.help.ListNode)}
     * 这样第一个节点的卵生节点就转换为了后半段的第一个节点了，然后前后同时遍历，求和，返回最大值
     */
    public int pairSum(ListNode head) {
        ListNode midNode = middleNode(head);
        ListNode reverseNode = reverseList(midNode);
        int max = Integer.MIN_VALUE;
        while (reverseNode != null) {
            max = Math.max(max, head.val + reverseNode.val);
            head = head.next;
            reverseNode = reverseNode.next;
        }
        return max;
    }

    public ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            // 当前节点指向上一个节点
            ListNode tempNode = cur.next;
            cur.next = pre;
            // 当前节点替换为下一个节点
            pre = cur;
            cur = tempNode;
        }
        return pre;
    }
}
