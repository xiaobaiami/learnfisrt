package algorithm.manacher;


import java.util.Arrays;

public class ManacherDemo1 {

    public static void main(String[] args) {

        System.out.println(findMaxHuiwen("11"));
    }

    public static int findMaxHuiwen(String str) {
        str = generateManacherString(str);
        int[] arr = new int[str.length()];
        int R = -1;
        int C = -1;
        int max = 0;
        for (int i = 0; i < str.length(); i++) {
            arr[i] = i < R ? Math.min(arr[2 * C - i], R - i) : 1;
            while (i + arr[i] < str.length() && i - arr[i] >= 0) {
                if (str.charAt(i + arr[i]) == str.charAt(i - arr[i])) {
                    arr[i]++;
                } else {
                    break;
                }
            }
            if (i + arr[i] > R) {
                R = i + arr[i];
                C = i;
            }
            max = Math.max(max, arr[i]);
        }
        return max - 1;
    }

    public static String generateManacherString(String str) {
        StringBuffer buffer = new StringBuffer("#");
        for (int i = 0; i < str.length(); i++) {
            buffer.append(str.charAt(i));
            buffer.append("#");
        }
        return buffer.toString();
    }
}


