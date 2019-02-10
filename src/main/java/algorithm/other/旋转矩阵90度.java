package algorithm.other;

import java.util.Arrays;

public class 旋转矩阵90度 {

    public static void main(String[] args) {
        int[][] arr = {
                {1,2,3,4},
                {5,6,7,8},
                {9,10,11,12},
                {13,14,15,16}
        };
        solve(arr);
        for (int[] ints : arr) {
            System.out.println(Arrays.toString(ints));
        }
    }

    public static void solve(int[][] arr) {
        int i = 0;
        while (i < arr.length - 1 - i) {
            round(arr, new int[]{i, i}, new int[]{arr.length - 1 - i, arr.length - 1 - i});
            i++;
        }
    }

    private static void round(int[][] arr, int[] leftUp, int[] rightDown) {
        int[] rightUp = {leftUp[0], rightDown[1]};
        int[] leftDown = {rightDown[0], leftUp[1]};
        for (int i = leftUp[1]; i < rightUp[1]; i++) {
            int t = arr[leftDown[0] - i][leftDown[1]];
            arr[leftDown[0] - i][leftDown[1]] = arr[rightDown[0]][rightDown[1] - i];
            arr[rightDown[0]][rightDown[1] - i] = arr[rightUp[0] + i][rightDown[1]];
            arr[rightUp[0] + i][rightDown[1]] = arr[leftUp[0]][leftUp[1] + i];
            arr[leftUp[0]][leftUp[1] + i] = t;
        }
    }

}
