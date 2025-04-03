package leetCode.moderately;

import leetCode.help.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author: gj
 * @description: 102. 二叉树的层序遍历
 */
public class LevelOrder {
    /**
     * 思路：
     * 1、使用队列，每次取出一层，然后加入下一层的节点
     * 2、每次取出一层，然后加入下一层的节点
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        Queue<Queue<TreeNode>> queue = new LinkedList<>();
        if (root != null) {
            LinkedList<TreeNode> rootQueue = new LinkedList<>();
            rootQueue.add(root);
            queue.add(rootQueue);
        }
        List<List<Integer>> result = new LinkedList<>();
        while (!queue.isEmpty()) {
            Queue<TreeNode> thisQueue = queue.poll();
            Queue<TreeNode> nextQueue = new LinkedList<>();
            List<Integer> data = new ArrayList<>();
            while (!thisQueue.isEmpty()) {
                TreeNode node = thisQueue.poll();
                data.add(node.val);
                if (node.left != null) {
                    nextQueue.add(node.left);
                }
                if (node.right != null) {
                    nextQueue.add(node.right);
                }
            }
            if (data.size() > 0) {
                result.add(data);
            }
            if (nextQueue.size() > 0) {
                queue.add(nextQueue);
            }
        }
        return result;
    }

    /**
     * 思路：
     * 先获取当前队列的元素个数，遍历到这个数量后则说明本层遍历结束。
     * 不需要跟levelOrder()一样，多个队列来标识层级关系。
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder2(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.add(root);
        }
        List<List<Integer>> result = new LinkedList<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> data = new ArrayList<>();
            while (size > 0) {
                TreeNode node = queue.poll();
                data.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
                size--;
            }
            if (data.size() > 0) {
                result.add(data);
            }
        }
        return result;
    }

}
