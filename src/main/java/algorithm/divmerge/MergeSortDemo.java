package algorithm.divmerge;

import java.util.Arrays;

public class MergeSortDemo {

    public static void main(String[] args) {
        int[] a = {2,2,6,7,1,2,9,33,21,22};
        mergeSort(a);
        System.out.println(Arrays.toString(a));
    }

    public static void mergeSort(int[] arr) {
        mergeSort(arr,0,arr.length-1);
    }

    public static void mergeSort(int[] arr, int l, int r) {
        if (l == r)
            return;
        int mid = l + (r - l) / 2;
        mergeSort(arr, l, mid);
        mergeSort(arr, mid + 1, r);
        merge(arr, l, mid, r);
    }

    public static void merge(int[] arr, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int index = 0;
        int p1 = l;
        int p2 = mid + 1;
        while (p1 <= mid && p2 <= r) {
            if (arr[p1] <= arr[p2]) {
                help[index++] = arr[p1++];
            } else {
                help[index++] = arr[p2++];
            }
        }
        while (p1 <= mid)
            help[index++] = arr[p1++];
        while (p2 <= r)
            help[index++] = arr[p2++];
        for (int i = l; i <= r; i++) {
            arr[i] = help[i - l];
        }
    }
}
