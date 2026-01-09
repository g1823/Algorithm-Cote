package leetCode.moderately;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 380. O(1) 时间插入、删除和获取随机元素
 *
 * =========================
 * 一、整体思考过程
 * =========================
 *
 * 题目要求：
 * - insert(val)：插入元素，若已存在返回 false
 * - remove(val)：删除元素，若不存在返回 false
 * - getRandom()：等概率随机返回当前集合中的一个元素
 *
 * 且以上操作的时间复杂度均要求为 O(1)。
 *
 * -------------------------
 * 1. 为什么不能只用 Set
 * -------------------------
 * Set（如 HashSet）可以做到：
 * - 插入 O(1)
 * - 删除 O(1)
 *
 * 但问题在于：
 * - Set 无法按下标访问
 * - 无法在 O(1) 时间内随机定位到某一个元素
 *
 * 因此，单独使用 Set 无法完成 getRandom() 的 O(1) 要求。
 *
 * -------------------------
 * 2. 为什么需要 ArrayList
 * -------------------------
 * ArrayList 支持：
 * - 尾部插入 O(1)
 * - 按下标访问 O(1)
 *
 * 非常适合用于实现 getRandom()：
 * - 随机生成一个 [0, size) 的下标
 * - 直接返回 list.get(index)
 *
 * 但 ArrayList 存在一个问题：
 * - 按值删除元素是 O(n)
 *
 * -------------------------
 * 3. 核心矛盾
 * -------------------------
 * - ArrayList：随机访问快，删除慢
 * - HashMap / HashSet：定位快，但无法随机访问
 *
 * 解决方案：
 * - 用 ArrayList 存储“当前有效元素”
 * - 用 HashMap 记录“元素值 -> 在 ArrayList 中的下标”
 *
 * -------------------------
 * 4. 删除操作的关键技巧（本题精髓）
 * -------------------------
 * 题目没有要求元素顺序，因此：
 *
 * 删除某个元素时：
 * - 不需要保持 ArrayList 的顺序
 * - 可以用「最后一个元素」覆盖要删除的位置
 * - 再删除 ArrayList 的最后一个元素
 *
 * 这样就避免了整体移动，保证 O(1)。
 *
 * =========================
 * 二、数据结构设计
 * =========================
 *
 * - List<Integer> list
 *   用于存储当前所有有效元素，支持 O(1) 随机访问
 *
 * - Map<Integer, Integer> map
 *   key   ：元素值
 *   value ：该元素在 list 中的下标
 *
 * 两者始终保持一致。
 */
class RandomizedSet {

    private Map<Integer, Integer> map;
    private List<Integer> list;

    public RandomizedSet() {
        map = new HashMap<>();
        list = new ArrayList<>();
    }

    /**
     * 插入元素
     *
     * 思路：
     * - 若元素已存在，直接返回 false
     * - 否则：
     *   1. 将元素追加到 list 尾部（O(1)）
     *   2. 在 map 中记录该元素对应的下标
     */
    public boolean insert(int val) {
        if (map.containsKey(val)) {
            return false;
        }
        map.put(val, list.size());
        list.add(val);
        return true;
    }

    /**
     * 删除元素
     *
     * 核心思路：
     * - 通过 map O(1) 找到 val 在 list 中的下标 index
     * - 用 list 最后一个元素 last 覆盖 index 位置
     * - 更新 last 在 map 中的新下标
     * - 删除 list 最后一个元素
     *
     * 特别注意：
     * - 当 val 本身就是最后一个元素时，不需要做覆盖操作
     *   直接删除即可
     */
    public boolean remove(int val) {
        if (!map.containsKey(val)) {
            return false;
        }

        int index = map.get(val);
        int lastIndex = list.size() - 1;
        int last = list.get(lastIndex);

        // 如果删除的不是最后一个元素，才需要覆盖
        if (index != lastIndex) {
            list.set(index, last);
            map.put(last, index);
        }

        // 删除最后一个元素
        list.remove(lastIndex);
        map.remove(val);

        return true;
    }

    /**
     * 随机获取一个元素
     *
     * 思路：
     * - ArrayList 是紧凑存储的有效元素
     * - 在 [0, list.size()) 之间随机生成一个下标
     * - 每个元素被选中的概率都是 1 / size
     *
     * 时间复杂度：O(1)
     */
    public int getRandom() {
        int index = (int) (Math.random() * list.size());
        return list.get(index);
    }
}
