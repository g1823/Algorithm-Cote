package leetCode.moderately;

import java.util.*;

/**
 * @author: gj
 * @description: 133. 克隆图
 */
public class CloneGraph {
    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        node1.neighbors.add(node2);
        node1.neighbors.add(node4);
        node2.neighbors.add(node1);
        node2.neighbors.add(node3);
        node3.neighbors.add(node2);
        node3.neighbors.add(node4);
        node4.neighbors.add(node1);
        node4.neighbors.add(node3);
        Node node = new CloneGraph().cloneGraph(node1);
        System.out.println();
    }

    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }
        Node clone = new Node(node.val);
        Map<Integer, Node> map = new HashMap<>();
        dfs(node, clone, map);
        return clone;
    }

    public void dfs(Node node, Node clone, Map<Integer, Node> map) {
        if (node == null || map.containsKey(node.val)) {
            return;
        }
        List<Node> neighbors = node.neighbors;
        if (neighbors == null || neighbors.size() == 0) {
            return;
        }
        map.put(node.val, clone);
        for (Node neighbor : neighbors) {
            if (map.containsKey(neighbor.val)) {
                clone.neighbors.add(map.get(neighbor.val));
                continue;
            }
            Node cloneNeighbor = new Node(neighbor.val);
            clone.neighbors.add(cloneNeighbor);
            dfs(neighbor, cloneNeighbor, map);
        }
    }

    static class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }

        @Override
        public String toString() {
            return "val=" + val;
        }
    }
}
