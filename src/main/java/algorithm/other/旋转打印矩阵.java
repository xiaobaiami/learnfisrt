package algorithm.other;

public class 旋转打印矩阵 {

    public static void main(String[] args) {
        int[][] arr = {
                {1, 2, 3, 4},
                {11, 12, 13, 14}

        };
        printMatrix(arr);
    }

    public static void printMatrix(int[][] arr) {
        int round = 1;
        while (round * 2 <= Math.min(arr.length, arr[0].length) + 1) {
            printEdge(arr, round++);
        }
    }

    private static void printEdge(int[][] arr, int round) {
        int[] leftUp = {round - 1, round - 1};
        int[] rightDown = {arr.length - round, arr[0].length - round};
        if (leftUp[0] == rightDown[0]) {
            for (int i = leftUp[1]; i <= rightDown[1]; i++) {
                System.out.print(arr[leftUp[0]][i] + " ");
            }
            return;
        } else if (leftUp[1] == rightDown[1]) {
            for (int i = leftUp[0]; i <= rightDown[0]; i++) {
                System.out.print(arr[i][rightDown[1]] + " ");
            }
            return;
        }

        for (int i = leftUp[1]; i < rightDown[1]; i++) {
            System.out.print(arr[leftUp[0]][i] + " ");
        }
        for (int i = leftUp[0]; i < rightDown[0]; i++) {
            System.out.print(arr[i][rightDown[1]] + " ");
        }

        for (int i = rightDown[1]; i > leftUp[1]; i--) {
            System.out.print(arr[rightDown[0]][i] + " ");
        }

        for (int i = rightDown[0]; i > leftUp[0]; i--) {
            System.out.print(arr[i][leftUp[1]] + " ");
        }

    }
}
