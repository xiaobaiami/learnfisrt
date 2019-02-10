package algorithm.other;

public class 之字形打印矩阵 {

    public static void main(String[] args) {
        int[][] arr = {
                {1, 2, 3, 4},
                {11, 12, 13, 14}

        };
        solve(arr);
    }


    public static void solve(int[][] arr) {
        int[] rightUp = {0, 0};
        int[] leftDown = {0, 0};
        boolean up2down = true;

        while (rightUp[0] <= arr.length - 1 && rightUp[1] <= arr[0].length - 1) {
            printDiagonal(arr, rightUp, leftDown, up2down);
            up2down = !up2down;
            if (rightUp[1] == arr[0].length - 1) {
                rightUp[0] += 1;
            } else {
                rightUp[1] += 1;
            }

            if (leftDown[0] == arr.length - 1) {
                leftDown[1] += 1;
            } else {
                leftDown[0] += 1;
            }
        }
    }

    private static void printDiagonal(int[][] arr, int[] rightUp, int[] leftDown, boolean up2down) {
        int times = rightUp[1] - leftDown[1];
        if (up2down) {
            for (int i = 0; i <= times; i++) {
                System.out.print(arr[rightUp[0] + i][rightUp[1] - i] + " ");
            }
        } else {
            for (int i = 0; i <= times; i++) {
                System.out.print(arr[leftDown[0] - i][leftDown[1] + i] + " ");
            }
        }
    }
}
