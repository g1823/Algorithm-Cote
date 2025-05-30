package leetCode.simple;

import leetCode.help.ListNode;

/**
 * @author: gj
 * @description: 160 相交链表
 */
public class IntersectionNode {
    /**
     * 双指针:
     * 假设:
     * - 链表A的长度为m，链表B的长度为n；
     * - 链表A头部到相交节点的长度为a，链表B头部到相交节点的长度为b；
     * - 相交节点到尾部的长度为c；
     * 则：a + c = m . b + c = n
     * 使用指针Pa和Pb，分别指向链表A和链表B的头部，两个指针分别向后移动，当Pa和Pb相遇时(即Pa==Pb)，返回相遇的节点。
     * 现在考虑两种情况:
     * 1、有相交节点：
     * - a = b  : 由于两个指针都是从链表头部开始移动，他们前进速度一致，a=b的话，二者会直接在相交点相遇，此时Pa==Pb，返回相交节点。
     * - a != b : a和b长度不一致，因此Pa和Pb在第一次遍历时，会错过相交节点；
     * -- 两个指针在到达尾部节点时（即next=null），将Pa指针放在链表B的头节点，将Pb指针放在链表A的头节点。
     * -- 继续向后移动，当Pa到达相交节点时，Pa走过的长度 = m + b =  a + c + b ，即链表A的长度 + b
     * -- 当Pb到达相交节点时，Pb走过的长度 = n + a =  b + c + a ，即链表B的长度 + a
     * -- 二者走过的长度一致，均为 a + b + c，行进速度一致，因此一定会在此处相遇
     * 2、无相交节点：
     * - 当不存在相交节点时，在两个指针换了头节点之后，会再次到达对应链表的尾节点（Pa到达链表B的尾部，Pb到达链表A的尾部）
     * -- Pa走过的长度 = m + n ; Pb走过的长度 = n + m
     * -- 二者速度一致，一定会同时到达链表的尾部，null = null ,停止遍历，返回null
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode a = headA, b = headB;
        if (a.next == null || b.next == null) {
            return null;
        }
        while (a != b) {
            a = a.next == null ? headB : a.next;
            b = b.next == null ? headA : b.next;
        }
        return a;
    }
}

