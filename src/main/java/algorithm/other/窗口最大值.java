package algorithm.other;

import java.util.Arrays;
import java.util.LinkedList;

public class 窗口最大值 {

    public static void main(String[] args) {
        int[] arr = {1, 6};
//        int[] ints = windowMax(arr, 3);
//        System.out.println(Arrays.toString(ints));
        System.out.println(subArrNumber(arr,5));
    }

    public static int[] windowMax(int[] arr, int window) {
        LinkedList<Integer> queue = new LinkedList<>();
        int i = 0;
        int[] rs = new int[arr.length - window + 1];
        while (i < arr.length) {
            while (!queue.isEmpty() && arr[queue.peekLast()] <= arr[i])
                queue.pollLast();
            queue.addLast(i);
            if (queue.peekFirst() + window == i)
                queue.pollFirst();
            if (i >= window - 1)
                rs[i - window + 1] = arr[queue.peekFirst()];
            i++;
        }
        return rs;
    }

    public static int subArrNumber(int[] arr, int limit) {
        LinkedList<Integer> minQueue = new LinkedList<>();
        LinkedList<Integer> maxQueue = new LinkedList<>();
        int rs = 0;
        int lo = 0;
        int hi = 0;
        while (lo < arr.length) {
            while (hi < arr.length) {
                while (!maxQueue.isEmpty() && arr[maxQueue.peekLast()] <= arr[hi])
                    maxQueue.pollLast();
                maxQueue.addLast(hi);
                while (!minQueue.isEmpty() && arr[minQueue.peekLast()] >= arr[hi])
                    minQueue.pollLast();
                minQueue.addLast(hi);
                if (arr[maxQueue.peekFirst()] - arr[minQueue.peekFirst()] <= limit)
                    hi++;
                else
                    break;
            }

            if (lo == maxQueue.peekFirst())
                maxQueue.pollFirst();
            if (lo == minQueue.peekFirst())
                minQueue.pollFirst();
            rs += (hi - lo);
            lo++;
        }
        return rs;
    }
}
