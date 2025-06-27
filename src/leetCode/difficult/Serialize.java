package leetCode.difficult;

import leetCode.help.TreeNode;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.List;

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
        String result = serialize.serialize(root);
        TreeNode result1 = serialize.deserialize(result);
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
}
