package leetCode.moderately;

/**
 * @author gaojie
 * @date 2026/1/11 12:10
 * @description: 443. 压缩字符串
 */
public class Compress {
    public static void main(String[] args) {
        char[] chars = {'a', 'a', 'b', 'b', 'c', 'c', 'c'};
        int i = new Compress().compress(chars);
        System.out.println(i);
    }

    /**
     * 类似双指针：
     * - 变量i记录当前压缩后的下一个下标
     * - 变量l记录当前连续字符的起始下标
     * - 变量r记录当前连续字符的结束下标，也是已经遍历到的下标
     * 遍历过程中：
     * - 若 chars[l] == chars[r]，说明还是连续字符，r++
     * - 若 chars[l] != chars[r]，说明当前字符和前面的连续字符不相等.
     * -    连续字符的起始位置为l, 结束位置为r-1，那么长度就是 r-1 - l + 1 = r - l
     * -    然后将长度转为字符串，以此写入chars[i]中，控制 i的移动
     * -    最后，将l = r，赋值为新字符的起始位置
     * - 循环结束后，将剩余的字符写入chars[i]中，返回i+1
     */
    public int compress(char[] chars) {
        int i = 0, l = 0, r = 0;
        while (r < chars.length) {
            if (chars[l] != chars[r]) {
                int count = r - l;
                chars[i] = chars[l];
                if (count > 1) {
                    char[] charArray = String.valueOf(count).toCharArray();
                    for (char c : charArray) {
                        i++;
                        chars[i] = c;
                    }
                }
                i++;
                l = r;
            }
            r++;
        }
        int count = r - l;
        chars[i] = chars[l];
        if (count > 1) {
            char[] charArray = String.valueOf(count).toCharArray();
            for (char c : charArray) {
                i++;
                chars[i] = c;
            }
        }
        return i;
    }
}
