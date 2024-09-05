package leetCode;

import leetCode.help.ListNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: gj
 * @description: 141. 环形链表
 */
public class HasCycle {
    public static void main(String[] args) {

    }

    public boolean hasCycle(ListNode head) {
        Map<ListNode, Object> map = new HashMap<>();
        Object o = new Object();
        while (head != null) {
            if (map.containsKey(head)) return true;
            map.put(head, o);
            head = head.next;
        }
        return false;
    }
}
