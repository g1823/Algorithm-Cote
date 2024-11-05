package leetCode.moderately;

import leetCode.help.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: gj
 * @description: 142. 环形链表 II
 */
public class DetectCycle {
    /**
     * 蛮力法
     * @param head 头节点
     * @return ListNode
     */
    public ListNode detectCycle(ListNode head) {
        ListNode tNode = head;
        if (tNode == null || tNode.next == null) {
            return null;
        }
        Set<ListNode> listNodeSet = new HashSet<>();
        while (tNode != null) {
            if (!listNodeSet.contains(tNode)) {
                listNodeSet.add(tNode);
                tNode = tNode.next;
            } else {
                return tNode;
            }
        }
        return null;
    }

    public ListNode detectCycle2(ListNode head) {

        return null;
    }
}
