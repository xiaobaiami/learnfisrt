package algorithm.other;

import java.util.Arrays;
import java.util.LinkedList;

public class 单调栈 {

    public static void main(String[] args) {
        int[] arr = {5, 5, 5,3, 6, 7};
//        int[][] generate = generate(arr);
        int[][] generate = generate2(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(String.format("%d 左 %s，右 %s", arr[i],
                    generate[0][i],
                    generate[1][i]));
        }
    }

    public static void pprint(int[][] arr) {
        for (int[] ints : arr) {
            System.out.println(Arrays.toString(ints));
        }
    }

    public static int[][] generate(int[] arr) {
        int[][] rs = new int[2][arr.length];
        LinkedList<LinkedList<Integer>> stacks = new LinkedList();
        for (int i = 0; i < arr.length; i++) {
            if (stacks.isEmpty()) {
                LinkedList<Integer> ls = new LinkedList<>();
                ls.push(i);
                stacks.push(ls);
                continue;
            }
            int stack_top_value = arr[stacks.peek().get(0)];
            if (arr[i] < stack_top_value) {
                LinkedList<Integer> ls = new LinkedList<>();
                ls.push(i);
                stacks.push(ls);
            } else if (arr[i] == stack_top_value) {
                stacks.peek().push(i);
            } else {
                while (!stacks.isEmpty() && arr[stacks.peek().peek()] < arr[i]) {
                    LinkedList<Integer> pop = stacks.pop();
                    for (Integer integer : pop) {
                        if (stacks.isEmpty())
                            rs[0][integer] = -1;
                        else
                            rs[0][integer] = stacks.peek().peek();
                        rs[1][integer] = i;
                    }
                }
                LinkedList<Integer> ls = new LinkedList<>();
                ls.push(i);
                stacks.push(ls);
            }
        }

        while (!stacks.isEmpty()) {
            LinkedList<Integer> pop = stacks.pop();
            for (Integer integer : pop) {
                if (stacks.isEmpty())
                    rs[0][integer] = -1;
                else
                    rs[0][integer] = stacks.peek().peek();
                rs[1][integer] = -1;
            }
        }
        return rs;
    }

    public static int[][] generate2(int[] arr) {
        int[][] rs = new int[2][arr.length];
        LinkedList<Integer> stacks = new LinkedList();
        for (int i = 0; i < arr.length; i++) {
            while (!stacks.isEmpty() && arr[i] >= arr[stacks.peek()]) {
                Integer pop = stacks.pop();
                rs[0][pop] = stacks.isEmpty() ? -1 : stacks.peek();
                rs[1][pop] = i;
            }
            stacks.push(i);
        }
        while (!stacks.isEmpty()) {
            Integer pop = stacks.pop();
            rs[0][pop] = stacks.isEmpty() ? -1 : stacks.peek();
            rs[1][pop] = arr.length;
        }
        return rs;
    }
}
