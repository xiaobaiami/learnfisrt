package algorithm.divmerge;

/**
 * 打印逆序对
 */
public class ReverseTupleDemo {

    public static void main(String[] args) {
        int[] a ={1,3,4,2,5,0};
        solve(a);
    }

    public static void solve(int[] arr) {
        divide(arr, 0, arr.length - 1);
    }

    public static void divide(int[] arr, int l, int r) {
        if (l == r)
            return;
        int mid = l + (r - l) / 2;
        divide(arr, l, mid);
        divide(arr, mid + 1, r);
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
                for (int i = p1; i <= mid; i++) {
                    System.out.println(String.format("(%d, %d) is reserve tuple", arr[i], arr[p2]));
                }
                help[index++] = arr[p2++];
            }
        }
        while (p1 <= mid){
            help[index++] = arr[p1++];
        }
        while (p2 <= r){
            help[index++] = arr[p2++];
        }
        for (int i = l; i <= r; i++) {
            arr[i] = help[i - l];
        }
    }
}
