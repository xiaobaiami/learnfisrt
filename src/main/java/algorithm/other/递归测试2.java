package algorithm.other;


public class 递归测试2 {
    public static void printAllCombaination(char[][] resource, int i, String res) {
        if (i == resource.length) {
            System.out.println(res);
            return;
        }
        for (char c : resource[i]) {
            printAllCombaination(resource, i + 1, res + String.valueOf(c));
        }
    }

    public static void printAllArange(String str, String res) {
        if (str.length() == 0) {
            System.out.println(res);
            return;
        }
        for (int i = 0; i < str.length(); i++) {
            if (res.contains(String.valueOf(str.charAt(i)))){

                continue;
            }
            printAllArange(str.substring(0, i) + str.substring(i + 1), res + String.valueOf(str.charAt(i)));
        }
    }


    public static void main(String[] args) {
        printAllArange("abc", "");
    }
}
