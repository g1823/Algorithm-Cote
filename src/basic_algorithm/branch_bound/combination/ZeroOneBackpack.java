package basic_algorithm.branch_bound.combination;


import java.util.*;

class ZeroOneBackpack {
    public static void main(String[] args) {
        int maxVolume = 8;
        List<Item> items = new ArrayList<>(Arrays.asList(
                new Item(2, 3),
                new Item(3, 4),
                new Item(4, 5),
                new Item(5, 6)));
        execute(items, maxVolume);

    }

    public static void execute(List<Item> items, int maxVolume) {
        // 将背包中物品按照单位价值排序
        items.sort(Comparator.comparingDouble(Item::getValuePerVolume).reversed());

        // 使用贪心计算下界
        int lowerBound = 0, tempVol = 0, tempVal = 0;
        for (Item item : items) {
            if (tempVol + item.getVolume() > maxVolume) break;
            tempVal += item.getValue();
            tempVol += item.getVolume();
        }
        lowerBound = tempVal;

        // 创建初始节点
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(Node::getUpperBound).reversed());
        Node firstNode = new Node(0, 0, 0, "", calculateUpperBound(0, 0, 0, items, maxVolume));
        queue.add(firstNode);

        // 循环获取最优解
        Node maxNode = firstNode;
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (currentNode.getUpperBound() < maxNode.getCurrentValue()) {
                continue;
            }

            if (currentNode.getIndex() == items.size() - 1) {
                Item item = items.get(currentNode.getIndex());
                if (currentNode.getCurrentVolume() + item.getVolume() <= maxVolume) {
                    currentNode.setCurrentVolume(currentNode.getCurrentVolume() + item.getVolume());
                    currentNode.setCurrentValue(currentNode.getCurrentValue() + item.getValue());
                    currentNode.setItems(currentNode.getItems() + "," + item);
                }
                if (currentNode.getCurrentValue() > maxNode.getCurrentValue()) {
                    maxNode = currentNode;
                }
                continue;
            }

            Item currentItem = items.get(currentNode.getIndex());

            // 放入
            if (currentNode.getCurrentVolume() + currentItem.getVolume() <= maxVolume) {
                Node inNode = new Node(
                        currentNode.getIndex() + 1,
                        currentNode.getCurrentVolume() + currentItem.getVolume(),
                        currentNode.getCurrentValue() + currentItem.getValue(),
                        currentNode.getItems() + " " + currentItem,
                        calculateUpperBound(currentNode.getIndex() + 1, currentNode.getCurrentVolume() + currentItem.getVolume(),
                                currentNode.getCurrentValue() + currentItem.getValue(), items, maxVolume)
                );
                if (inNode.getUpperBound() >= lowerBound) {
                    queue.add(inNode);
                    if (inNode.getCurrentValue() > maxNode.getCurrentValue()) {
                        maxNode = inNode;
                    }
                }
            }

            // 不放入
            Node outNode = new Node(
                    currentNode.getIndex() + 1,
                    currentNode.getCurrentVolume(),
                    currentNode.getCurrentValue(),
                    currentNode.getItems(),
                    calculateUpperBound(currentNode.getIndex() + 1, currentNode.getCurrentVolume(),
                            currentNode.getCurrentValue(), items, maxVolume)
            );
            if (outNode.getUpperBound() >= lowerBound) {
                queue.add(outNode);
                if (outNode.getCurrentValue() > maxNode.getCurrentValue()) {
                    maxNode = outNode;
                }
            }

        }
        System.out.println(maxNode);
    }

    private static double calculateUpperBound(int index, int currentVolume, int currentValue, List<Item> items, int maxVolume) {
        int remainingVolume = maxVolume - currentVolume;
        return currentValue + remainingVolume * items.get(index).getValuePerVolume();
    }
}



