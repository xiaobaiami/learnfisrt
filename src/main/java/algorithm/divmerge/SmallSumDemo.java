package algorithm.divmerge;

public class SmallSumDemo {
    public static void main(String[] args) {
        int[] a = {1,3,4,2,5};
        System.out.println(calcSmallSum(a));
        // 1 4 1 10
    }

    public static int calcSmallSum(int[] arr) {
        return calcSmallSum(arr, 0, arr.length - 1);
    }

    public static int calcSmallSum(int[] arr, int l, int r) {
        if (l == r)
            return 0;
        int mid = l + (r - l) / 2;
        int leftResult = calcSmallSum(arr, l, mid);
        int rightResult = calcSmallSum(arr, mid + 1, r);
        int mergeResult = merge(arr, l, mid, r);
        return leftResult + rightResult + mergeResult;
    }

    public static int merge(int[] arr, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int index = 0;
        int p1 = l;
        int p2 = mid + 1;
        int result = 0;
        while (p1 <= mid && p2 <= r) {
            if (arr[p1] < arr[p2]) {
                result += arr[p1] * (r - p2 + 1);
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
        return result;
    }
}
