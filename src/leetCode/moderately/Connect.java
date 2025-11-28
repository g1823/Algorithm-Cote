package leetCode.moderately;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author: gj
 * @description: 117. 填充每个节点的下一个右侧节点指针 II
 */
public class Connect {

    public static void main(String[] args) {
        Node node = new Node(1);
        Node node1 = new Node(2);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(3);
        Node node5 = new Node(4);
        Node node6 = new Node(4);
        node.left = node1;
        node.right = node2;
        node1.left = node3;
        node1.right = node4;
        node3.left = node5;
        node3.right = node6;
        System.out.println(new Connect().connect(node));
    }

    /**
     * 填充每个节点的下一个右侧节点指针
     * 思路：
     * 1. 使用层序遍历（BFS）访问二叉树的每一层。
     * 2. 每层用一个 List 保存当前层的节点。
     * 3. 遍历当前层时，将节点的 next 指针指向该层的下一个节点，最后一个节点的 next 保持为 null。
     * 4. 遍历当前层节点的左右子节点，将其加入下层的 List。
     * 5. 将下层 List 替换当前层，重复处理直到所有节点处理完。
     */
    public Node connect(Node root) {
        if (root == null) {
            return null;
        }
        // 用于存储当前层的所有节点
        List<Node> nodes = new ArrayList<>();
        nodes.add(root);
        // 当当前层还有节点时，继续处理
        while (!nodes.isEmpty()) {
            // 新建列表保存下一层节点
            List<Node> curNodes = new ArrayList<>();
            // 遍历当前层节点，为每个节点设置 next 指针
            for (int i = 0; i < nodes.size(); i++) {
                Node node = nodes.get(i);
                if (i < nodes.size() - 1) {
                    node.next = nodes.get(i + 1);
                }
            }
            // 将当前层节点的左右子节点加入下一层列表
            for (Node node : nodes) {
                if (node.left != null) {
                    curNodes.add(node.left);
                }
                if (node.right != null) {
                    curNodes.add(node.right);
                }
            }
            // 下一层成为当前层，继续循环
            nodes = curNodes;
        }

        return root;
    }


    /**
     * connect()的优化：因为重复创建List，避免重复创建（减少 GC 压力）。
     * 这里使用队列 + size 记录当前层节点数，从而判断当前节点是否为当前层最后一个节点。
     */
    public Node connect2(Node root) {
        if (root == null) {
            return null;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        // 层序遍历
        while (!queue.isEmpty()) {
            // 当前层节点数
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                // 非最后一个节点则指向下一个节点
                if (i < size - 1) {
                    node.next = queue.peek();
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return root;
    }

    /**
     * TODO
     * O(1) 空间解法（使用已建立的 next 指针）
     * 核心思想：利用前一层的 next 指针来遍历当前层，而不使用队列或额外 List。
     * 遍历时，用两个指针：
     * - cur：当前层的节点
     * - nextHead 和 prev：用来连接下一层的节点
     */
    public Node connect3(Node root) {
        if (root == null) {
            return null;
        }
        // 当前层起始节点
        Node cur = root;
        // 下一层的起始节点
        Node nextHead = null;
        // 上一个处理的节点，用于建立 next
        Node prev = null;

        while (cur != null) {
            while (cur != null) {
                if (cur.left != null) {
                    if (prev != null) {
                        prev.next = cur.left;
                    } else {
                        nextHead = cur.left;
                    }
                    prev = cur.left;
                }

                if (cur.right != null) {
                    if (prev != null) {
                        prev.next = cur.right;
                    } else {
                        nextHead = cur.right;
                    }
                    prev = cur.right;
                }
                // 利用 next 遍历当前层
                cur = cur.next;
            }

            // 移动到下一层
            cur = nextHead;
            nextHead = null;
            prev = null;
        }

        return root;
    }


    static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    ;
}
