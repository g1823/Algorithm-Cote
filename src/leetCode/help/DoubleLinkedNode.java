package leetCode.help;

/**
 * @author: gj
 * @description: 双向节点
 */
public class DoubleLinkedNode {
    public int val;
    public DoubleLinkedNode next;
    public DoubleLinkedNode prev;

    public DoubleLinkedNode() {
    }

    public DoubleLinkedNode(int val) {
        this.val = val;
    }


    @Override
    public String toString() {
        return "val=" + val;
    }
}
