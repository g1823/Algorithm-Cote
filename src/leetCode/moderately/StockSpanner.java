package leetCode.moderately;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author: gj
 * @description: 901. 股票价格跨度
 */
public class StockSpanner {
    public static void main(String[] args) {
        StockSpanner stockSpanner = new StockSpanner();
        System.out.println(stockSpanner.next(1));
        System.out.println(stockSpanner.next(1));
        System.out.println(stockSpanner.next(2));
        System.out.println(stockSpanner.next(3));
        System.out.println(stockSpanner.next(4));
    }

    class Node {
        int price;
        int index;
    }

    int i = 0;
    Deque<Node> stack = new LinkedList<Node>();

    public StockSpanner() {

    }

    public int next(int price) {
        Node node = new Node();
        node.price = price;
        node.index = i;
        i++;
        if (stack.isEmpty()) {
            stack.push(node);
            return 1;
        }
        while (!stack.isEmpty() && stack.peek().price <= price) {
            stack.pop();
        }
        int res;
        if (stack.isEmpty()) {
            res = node.index + 1;
        } else {
            res = node.index - stack.peek().index;
        }
        stack.push(node);
        return res;
    }
}
