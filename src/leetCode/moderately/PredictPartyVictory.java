package leetCode.moderately;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author: gj
 * @description: 649. Dota2 参议院
 */
public class PredictPartyVictory {
    public static void main(String[] args) {
        String s = "RDD";
        System.out.println(new PredictPartyVictory().predictPartyVictory(s));
    }

    /**
     * 模拟：
     * 首先，当前参议员必须贪心的ban掉最近的敌方参议员，否则轮到该敌方参议员，将会损失本部的一个参议员。
     * 因此，直接两个队列，一个是D，一个是R，然后每次都办掉对方最考前的参议员。
     */
    public String predictPartyVictory(String senate) {
        Queue<Integer> queueD = new LinkedList<>();
        Queue<Integer> queueR = new LinkedList<>();
        for (int i = 0; i < senate.length(); i++) {
            if (senate.charAt(i) == 'D') {
                queueD.add(i);
            } else {
                queueR.add(i);
            }
        }
        while (!queueD.isEmpty() && !queueR.isEmpty()) {
            int dSize = queueD.size();
            int rSize = queueR.size();
            int dIndex = 0, rIndex = 0;
            while (dIndex < dSize && rIndex < rSize) {
                Integer d = queueD.poll();
                Integer r = queueR.poll();
                if (d < r) {
                    queueD.add(d);
                } else {
                    queueR.add(r);
                }
                dIndex++;
                rIndex++;
            }
            while (dIndex < dSize && !queueR.isEmpty()){
                queueD.add(queueD.poll());
                dIndex++;
                queueR.poll();
            }
            while (rIndex < rSize && !queueD.isEmpty()){
                queueR.add(queueR.poll());
                rIndex++;
                queueD.poll();
            }
        }
        return queueD.isEmpty() ? "Radiant" : "Dire";
    }
}
