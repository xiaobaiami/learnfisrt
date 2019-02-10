package algorithm.kmp;

import java.util.Arrays;

public class KMPDemo1 {

    public static int kmpContains(String str1, String str2) {
        int[] next = getNext(str2);
        int p1 = 0;
        int p2 = 0;
        while (p1 < str1.length() && p2 < str2.length()) {
            if (str1.charAt(p1) == str2.charAt(p2)) {
                p1++;
                p2++;
            } else if (next[p2] == -1) {
                p1++;
            } else {
                p2 = next[p2];
            }
        }
        if (p2 == str2.length())
            return p1 - p2;
        return -1;
    }

    public static int[] getNext(String str) {
        if (str.length() == 0)
            return new int[]{};
        if (str.length() == 1)
            return new int[]{-1};

        int[] next = new int[str.length()];
        next[0] = -1;
        next[1] = 0;
        int i = 2;
        int cn = next[1];
        while (i < str.length()) {
            if (str.charAt(i - 1) == str.charAt(cn))
                next[i++] = ++cn;
            else if (cn > 0)
                cn = next[cn];
            else
                next[i++]=0;
        }
        return next;
    }

    public static void main(String[] args) {

        int[] next = getNext("ababcababak");
        System.out.println(Arrays.toString(next));
    }
}
