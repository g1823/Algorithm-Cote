package leetCode.moderately;


import java.util.Arrays;

/**
 * @description: 567. 字符串的排列
 */
public class CheckInclusion {
    /**
     * 滑动窗口
     */
    public boolean checkInclusion(String s1, String s2) {
        if (s2.length() < s1.length()) {
            return false;
        }
        // 1、计算s1的字符频次
        int[] s1Freq = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            s1Freq[s1.charAt(i) - 'a']++;
        }
        // 先往s2里放s1.length()个字符,如果两个相同直接返回
        int[] s2Freq = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            s2Freq[s2.charAt(i) - 'a']++;
        }
        if(Arrays.equals(s1Freq, s2Freq)){
            return true;
        }
        // 依次向右移动窗口，由于只有26个字母，因此对比起来是常数时间复杂度
        for(int i = s1.length(); i < s2.length(); i++){
            s2Freq[s2.charAt(i) - 'a']++;
            s2Freq[s2.charAt(i - s1.length()) - 'a']--;
            if(Arrays.equals(s1Freq, s2Freq)){
                return true;
            }
        }
        return false;
    }
}
