package algorithm.util;

import java.util.Arrays;

public class Tools {


    public static void swap (int[] arr , int a, int b){
        int t = arr[a];
        arr[a] = arr[b];
        arr[b] = t;
    }

    public static void parr(int[] arr){
        System.out.println(Arrays.toString(arr));
    }

    public static void parr(int[][] arr){
        for (int[] ints : arr) {
            System.out.println(Arrays.toString(ints));
        }
    }
}
