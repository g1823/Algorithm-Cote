package leetCode.moderately;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: gj
 * @description: 146. LRU 缓存（数据结构）{@link leetCode.moderately.LRUCache}的优化
 * 原始基于队列的 LRUCache 通过先进先出顺序标识访问时间，元素越早访问越靠前。
 * 但由于同一 key 可能被多次访问或更新，导致队列中存在多个重复元素，
 * 通常需要借助哈希表记录每个 key 的出现次数，在淘汰时判断是否真的可以删除，逻辑复杂且效率低。
 * 本实现使用哈希表 + 双向链表组合：
 * - 哈希表用于 O(1) 查找节点；
 * - 双向链表用于维护访问顺序，每次访问将节点移到链表头部；
 * - 插入、删除节点都是 O(1)，且避免了重复 key 的问题；
 * 最终实现真正意义上的 O(1) LRU 缓存。
 */
public class LRUCache2 {
    int capacity;
    Map<Integer, Node> map;

    Node head, tail;

    class Node {
        int key, value;
        Node prev, next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    public LRUCache2(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>(capacity);
        // 初始化头尾，避免为空
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        Node node = map.get(key);
        deleteNode(node);
        insertToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            deleteNode(node);
            insertToHead(node);
        } else {
            Node node = new Node(key, value);
            if (map.size() == capacity) {
                Node tailNode = tail.prev;
                deleteNode(tailNode);
                map.remove(tailNode.key);
            }
            map.put(key, node);
            insertToHead(node);
        }
    }

    private void deleteNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void insertToHead(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

}
