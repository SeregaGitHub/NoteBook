package tree;

public class IsBinaryTree {
    public static void main(String[] args) {
        Node bNode6 = new Node(9, null, null);
        Node bNode5 = new Node(4, null, null);
        Node bNode4 = new Node(4, null, null);
        Node bNode3 = new Node(1, null, null);
        Node btree2 = new Node(8, bNode5, bNode6);
        Node btree1 = new Node(3, bNode3, bNode4);
        Node btree0 = new Node(5, btree1, btree2);

        /*
                                5
                        3                8
                    1      4         4       9
         */

        System.out.println(isBiTree(btree0, Integer.MIN_VALUE, Integer.MAX_VALUE));
    }

    private static boolean isBiTree(Node head, int minValue, int maxValue) {
        
        if (head == null) return true;
        if (head.getValue() <= minValue || head.getValue() >= maxValue) return false;

        boolean l = isBiTree(head.getLeftNode(), minValue, head.getValue());
        boolean r = isBiTree(head.getRightNode(), head.getValue(), maxValue);

        return l && r;
    }
}
