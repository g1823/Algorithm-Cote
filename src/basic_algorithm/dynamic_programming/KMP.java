package basic_algorithm.dynamic_programming;

/**
 * @author: gj
 * @description: KMP算法
 */
public class KMP {
    public static void main(String[] args) {
        String pattern = "google";
        String text = "goodgoogle";
        System.out.println(execute(text, pattern, 0));
        System.out.println(execute(text, pattern, 1));
    }

    public static int execute(String text, String pattern, int type) {
        int[] next = new int[pattern.length()];
        if (type == 0) {
            next = getNextArray(pattern);
        } else if (type == 1) {
            next = getNextArray2(pattern);
        }
        int i = 0, j = 0;// i为text当前下标，j为pattern下标
        while (i < text.length()) {
            if (j == -1 || text.charAt(i) == pattern.charAt(j)) {
                if (j == pattern.length() - 1) {
                    return i - pattern.length() + 1;
                }
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        return -1;
    }


    /**
     * 使用蛮力法求next数组，每个位置都直接通过字符串截取的方式判断。
     *
     * @param pattern 子串
     * @return next数组
     */
    public static int[] getNextArray(String pattern) {
        int m = pattern.length();
        int[] tempNextArray = new int[m];
        for (int i = 1; i < m; i++) {
            int maxMatchLength = 0;
            // 遍历所有可能的前缀长度
            for (int j = 1; j <= i; j++) {
                // 判断是否存在长度为 j 的前缀和后缀相同
                if (pattern.substring(0, j).equals(pattern.substring(i - j + 1, i + 1))) {
                    maxMatchLength = j;
                }
            }
            tempNextArray[i] = maxMatchLength;
        }
        int[] nextArray = new int[m];
        // 下标0为-1，是为了避免下标为0的元素不匹配，然后不断循环
        nextArray[0] = -1;
        for (int i = 1; i < m; i++) {
            nextArray[i] = tempNextArray[i - 1];
        }
        return nextArray;
    }


    public static int[] getNextArray2(String pattern) {
        int m = pattern.length(), i = 1, j = 0;
        int[] nextArray = new int[m];
        // 下标0为-1，是为了避免下标为0的元素不匹配，然后不断循环
        nextArray[0] = -1;
        while (i < m - 1) {
            if (j == -1 || pattern.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                nextArray[i] = j;
            } else {
                j = nextArray[j];
            }
        }
        return nextArray;
    }
}
