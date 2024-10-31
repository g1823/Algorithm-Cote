package leetCode.help;

public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int x) {
        val = x;
        next = null;
    }

    public ListNode(int x,ListNode node) {
        val = x;
        next = node;
    }

    @Override
    public String toString() {
        return "val = " + val;
    }
}