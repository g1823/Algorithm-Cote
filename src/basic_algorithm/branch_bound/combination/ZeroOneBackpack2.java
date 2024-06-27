package basic_algorithm.branch_bound.combination;

import java.util.*;

/**
 * @author: gj
 * @description: 分治限界-贪心限界函数
 */
public class ZeroOneBackpack2 {
    public static void main(String[] args) {
        int maxVolume = 8;
        List<Item> items = new ArrayList<>(Arrays.asList(
                new Item(2, 3),
                new Item(3, 4),
                new Item(4, 5),
                new Item(5, 6)));
        execute(items, maxVolume);

        int maxVolume2 = 10;
        List<Item> items2 = new ArrayList<>(Arrays.asList(
                new Item(8, 9),
                new Item(3, 2),
                new Item(3, 3),
                new Item(4, 4)));
        execute(items2, maxVolume2);

        int maxVolume3 = 15;
        List<Item> items3 = new ArrayList<>(Arrays.asList(
                new Item(12, 24),
                new Item(7, 13),
                new Item(11, 23),
                new Item(9, 20),
                new Item(8, 15)));
        execute(items3, maxVolume3);
    }

    public static void execute(List<Item> items, int maxVolume) {
        // 将物品按照单位体积价值排序
        items.sort(Comparator.comparingDouble(Item::getValuePerVolume).reversed());
        // 初始化第一个节点以及优先队列
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(Node::getUpperBound).reversed());
        Node firstNode = new Node();
        firstNode.setIndex(-1);
        firstNode.setCurrentValue(0);
        firstNode.setCurrentVolume(0);
        firstNode.setUpperBound(getBound(firstNode, items, maxVolume));
        queue.add(firstNode);
        Node maxNode = firstNode;
        while (!queue.isEmpty()) {
            // 获取预期价值最大的节点
            Node currentNode = queue.poll();
            // 已经全部放入
            if (currentNode.getIndex() == items.size() - 1) {
                if (currentNode.getCurrentValue() > maxNode.getCurrentValue()) {
                    maxNode = currentNode;
                }
                continue;
            }
            Item currentItem = items.get(currentNode.getIndex() + 1);
            // 放入当前节点
            if (currentNode.getCurrentVolume() + currentItem.getVolume() <= maxVolume) {
                Node inNode = new Node();
                inNode.setIndex(currentNode.getIndex() + 1);
                inNode.setCurrentVolume(currentNode.getCurrentVolume() + currentItem.getVolume());
                inNode.setCurrentValue(currentNode.getCurrentValue() + currentItem.getValue());
                inNode.setItems(currentNode.getItems() + " " + currentItem);
                // 因为使用贪心法计算上界，放入的话，当前节点上界就跟父节点一样
                inNode.setUpperBound(currentNode.getUpperBound());
                queue.add(inNode);
                if (inNode.getCurrentValue() > maxNode.getCurrentValue()) maxNode = inNode;
            }
            // 不放入
            Node outNode = new Node();
            outNode.setIndex(currentNode.getIndex() + 1);
            outNode.setCurrentVolume(currentNode.getCurrentVolume());
            outNode.setCurrentValue(currentNode.getCurrentValue());
            outNode.setItems(currentNode.getItems());
            double bound = getBound(outNode, items, maxVolume);
            // 不放入的话，计算出来上界小于当前最优解，剪枝
            if (bound > maxNode.getCurrentValue()) {
                outNode.setUpperBound(bound);
                queue.add(outNode);
            }
        }
        System.out.println(maxNode);
    }

    /**
     * 计算界限
     * @param node 待计算节点
     * @param items 物品数组
     * @param maxVolume 最大体积
     * @return 带计算节点的上界
     */
    private static double getBound(Node node, List<Item> items, int maxVolume) {
        int tempVolume = node.getCurrentVolume();// 当前节点体积
        int tempValue = node.getCurrentValue();// 当前节点价值
        int i = node.getIndex() + 1;// 从下一个节点开始，使用贪心
        while ((i < items.size()) && tempVolume + items.get(i).getVolume() <= maxVolume) {
            tempVolume += items.get(i).getVolume();
            tempValue += items.get(i).getValue();
            i++;
        }
        // 背包体积未装满但又装不下下一个节点，因为是计算上界，所以直接将下一个物品掰开，只取一部分装满背包
        if ((tempVolume < maxVolume) && (i < items.size())) {
            return tempValue + (maxVolume - tempVolume) * items.get(i).getValuePerVolume();
        }
        return tempValue;
    }
}