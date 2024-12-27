package leetCode.moderately;

import leetCode.help.TreeNode;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: gj
 * @description: 437. 路径总和 III
 */
public class PathSum {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1000000000);
        TreeNode node1 = new TreeNode(1000000000);
        TreeNode node2 = new TreeNode(294967296);
        TreeNode node3 = new TreeNode(1000000000);
        TreeNode node4 = new TreeNode(1000000000);
        TreeNode node5 = new TreeNode(1000000000);
        root.left = node1;
        node1.left = node2;
        node2.left = node3;
        node3.left = node4;
        node4.left = node5;
        int target = 0;
        int i = new PathSum().pathSum(root, target);
        System.out.println(i);
    }



    /**
     * 深度优先
     * 记录从根节点到当前节点的和、根节点的下一个节点到当前节点的和、....父节点到当前节点的和
     * 然后遍历这些节点，加上当前节点值判断是否等于目标值
     */
    int result = 0;
    public int pathSum(TreeNode root, int targetSum) {
        dfs(root, targetSum, new ArrayList<>());
        return result;
    }
    public void dfs(TreeNode node, int targetSum, List<Long> list) {
        if (node == null) {
            return ;
        }
        int val = node.val;
        for (int i = 0; i < list.size(); i++) {
            Long newNum = list.get(i) + val;
            if (newNum == targetSum) result++;
            list.set(i, newNum);
        }
        if (val == targetSum) result++;
        list.add((long) val);
        dfs(node.left, targetSum, list);
        dfs(node.right, targetSum, list);
        list.remove(list.size() - 1);
        for (int i = 0; i < list.size(); i++) {
            Long newNum = list.get(i) - val;
            list.set(i, newNum);
        }
    }

    /**
     * 官方题解1：
     * 上面深度优先为了保存前缀和，采用list记录了从根节点到当前节点的和、根节点的下一个节点到当前节点的和、....父节点到当前节点的和
     * 当前解法则是计算每一个node的所有和等于targetSum的数量
     * 对于node，计算以其为根节点的所有等于targetSum路径和时，分为如下几个情况：
     * --当前节点node的val值等于targetSum，路径数量+1
     * --计算以当前节点node的left节点为根据点的val值等于 targetSum - node.val的路径和
     * --计算以当前节点node的right节点为根据点的val值等于 targetSum - node.val的路径和
     * 然后将三种情况和相加即可。
     */
    public int pathSum2(TreeNode root, long targetSum) {
        if (root == null) {
            return 0;
        }

        int ret = rootSum(root, targetSum);
        ret += pathSum2(root.left, targetSum);
        ret += pathSum2(root.right, targetSum);
        return ret;
    }

    public int rootSum(TreeNode root, long targetSum) {
        int ret = 0;

        if (root == null) {
            return 0;
        }
        int val = root.val;
        if (val == targetSum) {
            ret++;
        }

        ret += rootSum(root.left, targetSum - val);
        ret += rootSum(root.right, targetSum - val);
        return ret;
    }


    /**
     * 官方题解2：
     * 与第一种解法类似，都是记录前缀和减少计算量
     * 但是当前解法只记录前缀和，然后计算当前节点时，想要看从根节点到当前节点的任意连续路径和是否等于 targetSum 时
     * 只需要计算从 根节点到当前节点的前缀和 - targetSum 是否存在于所有前缀和中即可。
     * 比如：
     * 已知 root,node1,node2,node3,...currentNode的所有前缀和，存在map<prefix>中，且从root到currentNode的和为curr
     * 然后计算当前currentNode时，计算 curr(总前缀和) - targetSum 是否存在于上述 prefix中，
     * 如果存在，假设是从root到nodeI的和，那么说明从nodeI 到 currentNode的和等于targetSum
     *
     * 这样就把前面方法一中需要不断维护从根节点到当前节点的所有连续路径和O(n) 改进为 O(1)了
     */
    public int pathSum3(TreeNode root, int targetSum) {
        Map<Long, Integer> prefix = new HashMap<Long, Integer>();
        prefix.put(0L, 1);
        return dfs(root, prefix, 0, targetSum);
    }

    public int dfs(TreeNode root, Map<Long, Integer> prefix, long curr, int targetSum) {
        if (root == null) {
            return 0;
        }

        int ret = 0;
        curr += root.val;

        ret = prefix.getOrDefault(curr - targetSum, 0);
        prefix.put(curr, prefix.getOrDefault(curr, 0) + 1);
        ret += dfs(root.left, prefix, curr, targetSum);
        ret += dfs(root.right, prefix, curr, targetSum);
        prefix.put(curr, prefix.getOrDefault(curr, 0) - 1);

        return ret;
    }


}
