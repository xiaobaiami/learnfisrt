package algorithm.divmerge;

import algorithm.util.Tools;
import java.util.Random;

public class QuickSortDemo1 {

    public static void main(String[] args) {
        int[] arr = {2, 2, 6, 7, 1, 2, 9, 33, 21, 22};
        quickSort(arr);
        Tools.parr(arr);
    }

    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    public static void quickSort(int[] arr, int l, int h) {
        if (l >= h)
            return;
        int[] range = randomPartition(arr, l, h);
        quickSort(arr, l, range[0] - 1);
        quickSort(arr, range[1] + 1, h);
    }

    /**
     * 以数组的最后一位作为标准，将arr分为< = > 三部分，返回=的range
     *
     * @param arr
     * @param l   需要分组的左边界含
     * @param h   需要分组的有边界含
     * @return =的range
     */
    public static int[] partition(int[] arr, int l, int h) {
        int less = l - 1;
        int more = h + 1;
        int cur = l;
        int target = arr[h];
        while (cur < more) {
            if (arr[cur] < target) {
                Tools.swap(arr, ++less, cur++);
            } else if (arr[cur] > target) {
                Tools.swap(arr, --more, cur);
            } else {
                cur++;
            }
        }
        return new int[]{less + 1, more - 1};
    }

    private static Random random = new Random();

    public static int[] randomPartition(int[] arr, int l, int h) {
        int t = random.nextInt(h - l) + l;
        Tools.swap(arr, t, h);
        return partition(arr, l, h);
    }
}
