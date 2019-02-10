package algorithm.other;

import java.util.Arrays;

public class 荷兰国旗 {

    public static void main(String[] args) {
        int[] arr = {3, 5, 2, 3, 2, 1, 7, 5, 3, 8, 4, 2};
        int[] pp = partition(arr, 4);

        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(pp));

    }

    public static int[] partition(int[] arr, int p) {
        if (arr == null)
            return null;
        int less = -1;
        int more = arr.length;
        int cur = 0;
        while (cur < more) {
            if (arr[cur] == p) {
                cur++;
            } else if (arr[cur] < p) {
                swap(arr, ++less, cur++);
            } else {
                swap(arr, --more, cur);
            }
        }
        return new int[]{less + 1, more - 1};
    }

    public static void swap(int[] arr, int a, int b) {
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
}
