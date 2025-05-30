package leetCode.simple;

import leetCode.help.ListNode;

/**
 * @author: gj
 * @description: 234 回文链表
 */
public class Palindrome {
    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        listNode.next = listNode2;
        ListNode listNode3 = new ListNode(1);
        listNode2.next = listNode3;
        Palindrome palindrome = new Palindrome();
        System.out.println(palindrome.isPalindrome(listNode));
    }

    ListNode currentNode;

    /**
     * 判断链表是否为回文：
     * 使用递归方式，从链表尾部回溯，逐层比较前后对称的节点值。
     * 递归隐式构建栈结构，从尾到头回溯时依次比较头指针 currentNode 和当前节点 node 的值是否相等。
     * 每次比较后，currentNode 向后移动。
     * 若任意一次值不等，递归链返回 false。
     */
    public boolean isPalindrome(ListNode head) {
        currentNode = head;
        return recursive(head);
    }

    private boolean recursive(ListNode node) {
        if (node == null) {
            return true;
        }

        boolean res = recursive(node.next);
        if (!res) {
            return false;
        }

        boolean isEqual = (currentNode.val == node.val);
        currentNode = currentNode.next;
        return isEqual;
    }


    /**
     * 反转后半部分链表：
     * 找到前半部分链表的尾节点。
     * 1. 使用快慢指针找到中间节点；
     * 2. 将后半段链表进行原地反转；
     * 3. 从头部和反转后的后半段依次比较；
     * 4. 比较完后恢复链表结构；
     * 5. 返回是否回文的比较结果。
     * @param head 头节点
     * @return 是否回文
     */
    public boolean isPalindrome2(ListNode head) {
        if(head == null){
            return true;
        }
        ListNode midNode = middleNode(head);
        ListNode reverseNode = reverseList(midNode);
        ListNode headNode = head;
        while (reverseNode != null) {
            if (headNode.val != reverseNode.val) {
                reverseList(midNode);
                return false;
            }
            headNode = headNode.next;
            reverseNode = reverseNode.next;
        }
        reverseList(midNode);
        return true;
    }

    private ListNode reverseList(ListNode midNode) {
        ListNode preNode = null;
        ListNode currentNode = midNode;
        while (currentNode != null) {
            ListNode nextNode = currentNode.next;
            currentNode.next = preNode;
            preNode = currentNode;
            currentNode = nextNode;
        }
        return preNode;
    }


    private ListNode middleNode(ListNode head) {
        ListNode fastNode = head, slowNode = head;
        while (fastNode != null && fastNode.next != null) {
            fastNode = fastNode.next.next;
            slowNode = slowNode.next;
        }
        return slowNode;
    }

}
