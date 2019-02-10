package algorithm.leetcode;

import java.util.Arrays;

public class Solution {
    public String reverseWords(String s) {
        String[] split = s.trim().split("\\s+");
        String rs = "";
        for (int i = split.length - 1; i >= 0; i--) {
            rs += split[i];
            if (i!=0)
                rs +=" ";
        }
        return rs;
    }

    public static void main(String[] args) {
        String s = " 1";
        System.out.println(new Solution().reverseWords(s)+"dd");
    }
}
