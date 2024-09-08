package leetCode;

import leetCode.help.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package leetCode
 * @Date 2024/9/8 18:02
 * @Author gaojie
 * @description: 94. 二叉树的中序遍历
 */
public class InorderTraversal {

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfs(root, result);
        return result;
    }

    public void dfs(TreeNode node, List<Integer> result) {
        if (node == null) return;
        dfs(node.left, result);
        result.add(node.val);
        dfs(node.right, result);
    }

}
