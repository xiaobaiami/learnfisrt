package algorithm.bfprt;

import java.util.Arrays;

public class BFPRTDemo1 {

    public static void main(String[] args) {
        int[] arr = {4, 5, 3, 2, 1, 6, 7, 8};
        for (int i = 0; i < arr.length; i++) {
            System.out.println(solver(arr, 0, arr.length - 1, i));
        }
    }

    public static int solver(int[] arr, int lo, int hi, int k) {
        if (k < lo || k > hi)
            throw new RuntimeException("无解");
        int[] ints = heLanFlag(arr, lo, hi);
        if (k >= ints[0] && k <= ints[1])
            return arr[k];
        if (k < ints[0])
            return solver(arr, lo, ints[0] - 1, k);
        if (k > ints[1])
            return solver(arr, ints[1] + 1, hi, k);
        throw new RuntimeException("无解");
    }

    public static int[] heLanFlag(int[] arr, int lo, int hi) {
        int cur = lo;
        int left = lo - 1;
        int right = hi + 1;
        int target = arr[lo];
        while (cur < right) {
            if (arr[cur] < target) {
                swap(arr, cur++, ++left);
            } else if (arr[cur] == target) {
                cur++;
            } else {
                swap(arr, cur, --right);
            }
        }
        return new int[]{left + 1, right - 1};
    }


    public static void swap(int[] arr, int a, int b) {
        int t = arr[a];
        arr[a] = arr[b];
        arr[b] = t;
    }


}
