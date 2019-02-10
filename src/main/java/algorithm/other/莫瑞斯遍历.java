package algorithm.other;

public class 莫瑞斯遍历 {

    public static void morrisTraversal(Node root) {
        if (root == null)
            return;
        Node cur = root;
        Node mostright = null;
        while (cur != null) {
            mostright = cur.left;
            if (mostright == null)
                cur = cur.right;
            else {
                while (mostright.right != null && mostright.right != cur)
                    mostright = mostright.right;
                if (mostright.right == null) {
                    mostright.right = cur;
                    cur = cur.left;
                } else {
                    mostright.right = null;
                    cur = cur.right;
                }
            }
        }
    }
}

class Node {
    Node left;
    Node right;
    int val;

}
