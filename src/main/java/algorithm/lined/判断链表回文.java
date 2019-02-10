package algorithm.lined;

public class 判断链表回文 {

    public static void main(String[] args) {
        int[] arr = {2, 35, 2,222,12,1,23,12,3};
        Node linked = Node.build(arr);
        Node.print(linked);
        boolean solve = solve(linked);
        System.out.println(solve);
        Node.print(linked);
    }

    public static Node reverse(Node head) {
        Node pre = head;
        Node cur = pre.next;
        pre.next = null;
        while (cur != null) {
            Node post = cur.next;
            cur.next = pre;
            pre = cur;
            cur = post;
        }
        return pre;
    }

    public static boolean solve(Node head) {
        if (head == null)
            return false;
        Node h = head;
        Node mid = head;
        while (h.next != null && h.next.next != null) {
            mid = mid.next;
            h = h.next.next;
        }

        Node tail = reverse(mid);

        h = head;
        Node h2 = tail;
        boolean flag = true;
        while (h != null && h2 != null) {
            if (h.value != h2.value) {
                flag = false;
                break;
            }
            h = h.next;
            h2 = h2.next;
        }

        reverse(tail);
        return flag;
    }
}
