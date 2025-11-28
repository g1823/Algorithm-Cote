package leetCode.moderately;

import leetCode.help.TreeNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: gj
 * @description: 103. 二叉树的锯齿形层序遍历
 */
public class ZigzagLevelOrder {
    /**
     * 本质上要求层序遍历，不过一次从左往右，一次从右往左
     * 采用双端队列：
     * - 一次从左往右取，新元素从队尾添加
     * - 一次从右往左取，新元素从队头添加
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {
            return new LinkedList<>();
        }
        Deque<TreeNode> deque = new LinkedList<>();
        deque.offer(root);
        List<List<Integer>> res = new LinkedList<>();
        // flag为true标识从左往右，为false标识从右往左
        boolean flag = true;
        while (!deque.isEmpty()) {
            int size = deque.size();
            List<Integer> list = new LinkedList<>();
            if (flag) {
                for (int i = 0; i < size; i++) {
                    TreeNode node = deque.poll();
                    list.add(node.val);
                    if (node.left != null) {
                        deque.offer(node.left);
                    }
                    if (node.right != null) {
                        deque.offer(node.right);
                    }
                }
            } else {
                for (int i = 0; i < size; i++) {
                    TreeNode node = deque.pollLast();
                    list.add(node.val);
                    if (node.right != null) {
                        deque.offerFirst(node.right);
                    }
                    if (node.left != null) {
                        deque.offerFirst(node.left);
                    }
                }
            }
            res.add(list);
            flag = !flag;
        }
        return res;
    }
}
