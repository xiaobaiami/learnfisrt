package algorithm.other;

import java.util.HashMap;

public class 递归测试 {
    private static class Node {
        private int i;

        public Node(int i) {
            this.i = i;
        }

        @Override
        public String toString() {
            return i + " ";
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Node))
                return false;
            return i == ((Node) obj).i;
        }

        @Override
        public int hashCode() {
            return i;
        }
    }

    private static HashMap<Node, Node> fatherMap = new HashMap<>();

    public static Node test(Node node) {
        Node father = fatherMap.get(node);
        if (father != node) {
            father = test(father);
        }
        System.out.println(node + " " + father);
        return father;
    }

    public static void main(String[] args) {
        Node node4 = new Node(4);
        Node node3 = new Node(3);
        Node node2 = new Node(2);
        Node node1 = new Node(1);
        fatherMap.put(node4, node3);
        fatherMap.put(node3, node2);
        fatherMap.put(node2, node1);
        fatherMap.put(node1, node1);
        test(node4);

    }
}
