package algorithm.lined;

public class Node {
    int value;
    Node next;
    Node random;

    public Node(int value) {
        this.value = value;
    }

    public Node() {
    }

    public static Node build(int[] arr) {
        Node head = new Node();
        Node cur = head;
        for (int i : arr) {
            cur.next = new Node(i);
            cur = cur.next;
        }
        return head.next;
    }

    public static void print(Node c) {
        String s = "";
        Node cur = c;
        while (cur != null) {
            s += cur.value + " ";
            cur = cur.next;
        }
        System.out.println(s);
    }
}
