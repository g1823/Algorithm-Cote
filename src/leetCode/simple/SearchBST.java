package leetCode.simple;

import leetCode.help.TreeNode;

/**
 * @author: gj
 * @description: 700. 二叉搜索树中的搜索
 */
public class SearchBST {
    public TreeNode searchBST(TreeNode root, int val) {
        return dfs(root, val);
    }
    public TreeNode dfs(TreeNode node, int val) {
        if(node == null){
            return null;
        }
        if(node.val == val){
            return node;
        }
        return val < node.val ? dfs(node.left, val) : dfs(node.right, val);
    }
}
