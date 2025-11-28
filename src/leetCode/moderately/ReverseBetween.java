package leetCode.moderately;

import leetCode.help.ListNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: gj
 * @description: 92. 反转链表 II
 */
public class ReverseBetween {

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        System.out.println(new ReverseBetween().reverseBetween2(node1, 2, 4));

    }

    /**
     * 方法一：利用 List 辅助反转（空间换时间）
     * <p>
     * 思路分析：
     * 1. 对于单链表而言：
     * - 很难直接获取节点的父节点，因此即使拿到两个待交换节点，也无法直接交换它们。
     * - 无法回退：区间右侧节点无法向左寻找父节点。
     * 2. 解决方案：
     * - 将链表所有节点存入 List，这样可以通过下标方便获取任意节点及其父节点。
     * - 使用空间换时间，通过交换 List 中的节点实现区间反转。
     * <p>
     * 步骤：
     * 1. 遍历链表，将所有节点存入 List。
     * 2. 对反转区间 [left, right]，使用 Collections.swap 交换 List 中节点位置。
     * 3. 根据 List 的顺序，重新设置 next 指针。
     * 4. 返回新的头节点。
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        // 1. 将链表节点放入 List
        List<ListNode> list = new ArrayList<>();
        ListNode cur = head;
        while (cur != null) {
            list.add(cur);
            cur = cur.next;
        }

        // 2. 反转 List 中的区间节点
        while (left < right) {
            Collections.swap(list, left - 1, right - 1);
            left++;
            right--;
        }

        // 3. 根据 List 顺序重建链表
        for (int i = 0; i < list.size() - 1; i++) {
            list.get(i).next = list.get(i + 1);
        }
        list.get(list.size() - 1).next = null;

        // 4. 返回新头节点
        return list.get(0);
    }

    /**
     * 方法二：原地反转（指针操作，不使用额外空间）
     * <p>
     * 思路分析：
     * 1. 对于单链表，区间反转可以通过修改指针实现，无需交换节点。
     * 2. 核心思路：头插法
     * - 记录反转区间前一个节点（父节点 pre）。
     * - 每次取 cur 的下一个节点 next，插入到 pre 的后面，依次完成整个区间反转。
     * <p>
     * 步骤：
     * 1. 创建 dummy 节点，方便处理 left=1 的情况。
     * 2. pre 指向反转区间前一个节点。
     * 3. 循环将区间内每个节点依次头插到 pre 后，实现原地反转。
     * 4. 返回 dummy.next 作为新的头节点。
     */
    public ListNode reverseBetween2(ListNode head, int left, int right) {
        // 1. 创建 dummy 节点
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;

        // 2. 移动 pre 到反转区间前一个节点
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }

        int count = right - left;
        // 3. cur 指向反转区间第一个节点
        ListNode cur = pre.next;

        // 4. 头插法反转区间
        while (count > 0) {
            // 暂存下一个节点
            ListNode next = cur.next;
            // 当前节点跳过 next
            cur.next = next.next;
            // next 插入到 pre 后
            next.next = pre.next;
            // 更新 pre.next
            pre.next = next;
            count--;
        }

        // 5. 返回新头节点
        return dummy.next;
    }

}
