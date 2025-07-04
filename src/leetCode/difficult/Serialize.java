package leetCode.difficult;

import leetCode.help.TreeNode;
import sun.reflect.generics.tree.Tree;

import java.util.*;

/**
 * @author: gj
 * @description: 297. 二叉树的序列化与反序列化
 */
public class Serialize {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(3);
        TreeNode node3 = new TreeNode(4);
        TreeNode node4 = new TreeNode(5);
        root.left = node1;
        root.right = node2;
        node2.left = node3;
        node2.right = node4;
        Serialize serialize = new Serialize();
        String result = serialize.serialize3(root);
        TreeNode result1 = serialize.deserialize3(result);
        System.out.println(result);
    }

    /**
     * 层序遍历（超出内存限制）：
     * 序列化：
     * 为了保留空子节点信息，将所有 null 子节点用一个特殊的 TreeNode 对象 nullNode 替代。
     * 这样可以构造出一个结构类似于满二叉树的序列化数据，避免丢失节点结构信息。
     * 反序列化：
     * 在反序列化时，同样按照层序构造节点，并使用 nullNode 来占位空节点。
     * 为了还原为真正的二叉树，最后还需要再进行一次层序遍历，将所有 nullNode 替换回 null。
     */
    public String serialize(TreeNode root) {
        if (root == null) {
            return "";
        }
        List<TreeNode> list = new ArrayList<>();
        TreeNode nullNode = new TreeNode(0);
        list.add(root);
        StringBuilder sb = new StringBuilder();
        while (!list.isEmpty()) {
            List<TreeNode> temp = new ArrayList<>();
            boolean hasNext = false;
            for (TreeNode node : list) {
                if (node != nullNode) {
                    hasNext = true;
                    break;
                }
            }
            if (!hasNext) {
                break;
            }
            for (TreeNode node : list) {
                if (node == nullNode) {
                    sb.append("null,");
                    temp.add(nullNode);
                    temp.add(nullNode);
                } else {
                    sb.append(node.val).append(",");
                    temp.add(node.left == null ? nullNode : node.left);
                    temp.add(node.right == null ? nullNode : node.right);
                }
            }
            list = temp;
        }
        return sb.charAt(sb.length() - 1) == ',' ? sb.substring(0, sb.length() - 1) : sb.toString();
    }

    public TreeNode deserialize(String data) {
        if (data.isEmpty()) {
            return null;
        }
        String[] split = data.split(",");
        TreeNode root = new TreeNode(Integer.parseInt(split[0]));
        TreeNode nullNode = new TreeNode(0);
        int index = 1;
        List<TreeNode> list = new ArrayList<>();
        list.add(root);
        while (!list.isEmpty() && index < split.length) {
            List<TreeNode> temp = new ArrayList<>();
            for (TreeNode node : list) {
                String leftValue = split[index++];
                TreeNode leftNode;
                if ("null".equals(leftValue)) {
                    leftNode = nullNode;
                } else {
                    leftNode = new TreeNode(Integer.parseInt(leftValue));
                }
                node.left = leftNode;
                temp.add(leftNode);
                String rightValue = split[index++];
                TreeNode rightNode;
                if ("null".equals(rightValue)) {
                    rightNode = nullNode;
                } else {
                    rightNode = new TreeNode(Integer.parseInt(rightValue));
                }
                node.right = rightNode;
                temp.add(rightNode);
            }
            list = temp;
        }

        list = new ArrayList<>();
        list.add(root);
        while (!list.isEmpty()) {
            List<TreeNode> temp = new ArrayList<>();
            for (TreeNode node : list) {
                if (node.left == nullNode || node.left == null) {
                    node.left = null;
                } else {
                    temp.add(node.left);
                }
                if (node.right == nullNode || node.right == null) {
                    node.right = null;
                } else {
                    temp.add(node.right);
                }
            }
            list = temp;
        }
        return root;
    }


    /**
     * 层序遍历（去除nullNode虚拟节点，避免内存爆掉）：
     * serialize为了保留节点位置，采用NullNode构造了满二叉树，会对NullNode继续构造子节点NullNode，导致内存爆掉。
     * 这里仅对实际非空节点构造子节点（null也会保留）
     * 思路说明：
     * - 为了在反序列化时能够精确还原原树结构，必须记录 null 节点的位置。
     * - 每个节点（包括空节点）都必须被处理并按顺序加入序列化结果中。
     * - 空节点使用字符串 "null" 进行占位，而不是使用虚拟节点对象（避免内存溢出问题）。
     * - 层序遍历天然保证了节点的顺序为：从上到下，从左到右。
     * 注意事项：
     * - 最后序列中可能会有连续的 "null"，表示尾部的空叶子节点，这些在反序列化中不会影响结构。
     */
    public String serialize2(TreeNode root) {
        if (root == null) {
            return "";
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                sb.append("null,");
            } else {
                sb.append(node.val + ",");
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }
        return sb.charAt(sb.length() - 1) == ',' ? sb.substring(0, sb.length() - 1) : sb.toString();
    }

    public TreeNode deserialize2(String data) {
        if (data.isEmpty()) {
            return null;
        }
        String[] split = data.split(",");
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(split[0]));
        queue.add(root);
        for (int i = 1; i < split.length && !queue.isEmpty(); ) {
            TreeNode node = queue.poll();
            String leftValue = split[i++];
            String rightValue = split[i++];
            if (!"null".equals(leftValue)) {
                TreeNode leftNode = new TreeNode(Integer.parseInt(leftValue));
                node.left = leftNode;
                queue.add(leftNode);
            }
            if (!"null".equals(rightValue)) {
                TreeNode rightNode = new TreeNode(Integer.parseInt(rightValue));
                node.right = rightNode;
                queue.add(rightNode);
            }
        }
        return root;
    }


    /**
     * 深度优先遍历，先根遍历
     */
    public String serialize3(TreeNode root) {
        if (root == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        dfsSerialize(root, sb);
        return sb.charAt(sb.length() - 1) == ',' ? sb.substring(0, sb.length() - 1) : sb.toString();
    }

    public void dfsSerialize(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("null,");
        } else {
            sb.append(node.val).append(",");
            dfsSerialize(node.left, sb);
            dfsSerialize(node.right, sb);
        }
    }

    public TreeNode deserialize3(String data) {
        if (data.isEmpty()) {
            return null;
        }
        String[] split = data.split(",");
        TreeNode root = new TreeNode(Integer.parseInt(split[0]));
        dfsDeserialize(split, 1, root);
        return root;
    }

    public int dfsDeserialize(String[] split, int index, TreeNode node) {
        if (index >= split.length) {
            return index;
        }
        if ("null".equals(split[index])) {
            index++;
        } else {
            TreeNode leftNode = new TreeNode(Integer.parseInt(split[index]));
            node.left = leftNode;
            index = dfsDeserialize(split, index + 1, leftNode);
        }

        if ("null".equals(split[index])) {
            return index + 1;
        } else {
            TreeNode rightNode = new TreeNode(Integer.parseInt(split[index]));
            node.right = rightNode;
            return dfsDeserialize(split, index + 1, rightNode);
        }
    }


    /**
     * 深度优先(先根)反序列化：
     * 避免使用index进行控制索引，容易出现越界问题，且难以理解
     * 本质上，我们需要的是依次遍历字符串每个元素，原方法采用index不断的传递
     * 完全可以直接采用队列，直接从队列中取也可以实现依次遍历，还不用控制索引，不会出现越界
     *
     * @param data
     * @return
     */
    public TreeNode deserialize3_optimize(String data) {
        if (data.isEmpty()) {
            return null;
        }
        String[] split = data.split(",");
        Queue<String> queue = new LinkedList<>(Arrays.asList(split));
        return dfsDeserialize(queue);
    }

    public TreeNode dfsDeserialize(Queue<String> queue) {
        String val = queue.poll();
        if ("null".equals(val)) {
            return null;
        }
        TreeNode node = new TreeNode(Integer.parseInt(val));
        node.left = dfsDeserialize(queue);
        node.right = dfsDeserialize(queue);
        return node;
    }

}
