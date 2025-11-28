package leetCode.moderately;

import leetCode.help.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: gj
 * @description: 138. 随机链表的复制
 */
public class CopyRandomList {
    public static void main(String[] args) {
        Node node1 = new Node(7);
        Node node2 = new Node(13);
        Node node3 = new Node(11);
        Node node4 = new Node(10);
        Node node5 = new Node(1);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = null;
        node1.random = null;
        node2.random = node1;
        node3.random = node5;
        node4.random = node3;
        node5.random = node1;
        Node node = new CopyRandomList().copyRandomList(node1);
        System.out.println(node);
    }

    /**
     * 这个问题的难点在于 random 指针指向的是链表中任意节点，无法在第一时间定位克隆链表中对应的目标节点。为了克隆整个链表，必须同时完成 next 和 random 的复制。
     * 思路：
     * 1. 使用 HashMap 建立原节点 -> 克隆节点的映射关系；
     * 2. 第一遍遍历原链表，复制每个节点的 val 和 next，并存入 HashMap；
     * 3. 第二遍遍历原链表，根据原节点的 random 指针，在 HashMap 中找到对应的克隆节点，并赋值；
     * 时间复杂度：O(n) - 遍历链表两次；
     * 空间复杂度：O(n) - 使用 HashMap 存储原始节点与克隆节点的映射。
     */
    public Node copyRandomList(Node head) {
        // 哈希表：原节点 -> 克隆节点
        Map<Node, Node> map = new HashMap<>();
        // dummy 节点用于构建克隆链表，cur 是克隆链表的游标指针
        Node dummy = new Node(-1), cur = dummy;
        // 第一次遍历：复制 val 和 next，建立映射
        while (head != null) {
            // 创建新节点，复制 val
            Node node = new Node(head.val);
            // 暂时复制 random（注意：这里实际这一步是冗余的，会被后续覆盖）
            node.random = head.random;
            // 构建 next 链
            cur.next = node;
            cur = cur.next;
            // 记录原节点 -> 克隆节点的映射关系
            map.put(head, node);
            // 移动到下一个原节点
            head = head.next;
        }
        // 第二次遍历：修正 random 指针
        cur = dummy.next;  // 从克隆链表头开始
        while (cur != null) {
            if (cur.random != null) {
                // cur.random 是原链表中的节点，map.get(cur.random) 是对应的克隆节点
                cur.random = map.get(cur.random);
            }
            cur = cur.next;
        }
        // 返回克隆链表的头节点
        return dummy.next;
    }

    /**
     * 复制一个带有随机指针的链表，使用 O(1) 空间优化版。
     * 思路：
     * 本解法避免使用 HashMap 来存储原节点和克隆节点的映射关系，
     * 而是将克隆节点直接插入在原节点之后，从而借助链表结构本身建立隐式映射关系。
     * 三步处理流程如下：
     * 1. 遍历链表，为每个原节点创建克隆节点，并插入到原节点之后；
     * 2. 再次遍历，设置每个克隆节点的 random 指针（利用 cur.random.next 定位克隆的 random）；
     * 3. 第三次遍历，拆分原链表和克隆链表，恢复原链表，返回克隆链表的头节点。
     * 相较于传统使用 HashMap 的做法，本解法空间复杂度优化为 O(1)，适合链表指针操作熟练者使用。
     * 时间复杂度：O(n) - 三次遍历；
     * 空间复杂度：O(1) - 原地构建，无需额外存储映射。
     */
    public Node copyRandomList2(Node head) {
        if (head == null) {
            return null;
        }

        // 第一步：复制每个节点，插入到原节点后
        Node cur = head;
        while (cur != null) {
            Node clone = new Node(cur.val);
            clone.next = cur.next;
            cur.next = clone;
            cur = clone.next;
        }

        // 第二步：复制 random 指针
        cur = head;
        while (cur != null) {
            if (cur.random != null) {
                cur.next.random = cur.random.next;
            }
            // 移动到下一个原节点
            cur = cur.next.next;
        }

        // 第三步：拆分两个链表，恢复原链表 + 提取克隆链表
        cur = head;
        Node cloneHead = head.next;
        while (cur != null) {
            Node clone = cur.next;
            // 恢复原链表
            cur.next = clone.next;
            if (clone.next != null) {
                // 设置克隆链表的 next
                clone.next = clone.next.next;
            }
            // 移动到下一个原节点
            cur = cur.next;
        }

        return cloneHead;
    }
}
