package leetCode.moderately;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author: gj
 * @description: 146. LRU 缓存（数据结构）
 * 使用队列和hash表实现热度缓存，但是存在一些问题：
 * 1. queue 退化为“伪双端队列”，效率低，。
 * - 用了一个 LinkedList 来保存所有被访问过的 key，但 key 会重复出现。
 * - 每次淘汰都要 poll()，然后靠 countMap 判断是否“真淘汰”，导致可能要循环多次才能找到一个可淘汰的 key。
 * - 最坏时间复杂度是 O(n)，并不是 LRU 题要求的 O(1)。
 * 2. countMap 是冗余的 hack
 * -用来弥补 queue 无法 O(1) 删除中间某个节点的问题。
 * -但本质上是为了掩盖 queue 不适合做 LRU 的设计缺陷。
 */
public class LRUCache {

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(2);
        lruCache.put(1, 1);
        lruCache.put(2, 2);
        lruCache.get(1);
        lruCache.put(3, 3);
        lruCache.get(2);
        lruCache.put(4, 4);
        lruCache.get(1);
        lruCache.get(3);
        lruCache.get(4);
    }

    Map<Integer, Integer> countMap;
    Queue<Integer> queue;
    Map<Integer, Integer> map;
    int capacity;

    public LRUCache(int capacity) {
        countMap = new HashMap<>(capacity);
        queue = new LinkedList<>();
        map = new HashMap<>(capacity);
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        countMap.put(key, countMap.getOrDefault(key, 0) + 1);
        queue.offer(key);
        return map.get(key);
    }

    public void put(int key, int value) {
        if (map.size() < capacity) {
            map.put(key, value);
            queue.offer(key);
            countMap.put(key, countMap.getOrDefault(key, 0) + 1);
        } else {
            if (map.containsKey(key)) {
                map.put(key, value);
                queue.offer(key);
                countMap.put(key, countMap.getOrDefault(key, 0) + 1);
            } else {
                while (map.size() == capacity) {
                    int temp = queue.poll();
                    countMap.put(temp, countMap.get(temp) - 1);
                    if (countMap.get(temp) == 0) {
                        countMap.remove(temp);
                        map.remove(temp);
                        queue.offer(key);
                        map.put(key, value);
                        countMap.put(key, countMap.getOrDefault(key, 0) + 1);
                        break;
                    }
                }
            }
        }
    }
}
