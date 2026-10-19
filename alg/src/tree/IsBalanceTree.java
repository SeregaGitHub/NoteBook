package tree;

public class IsBalanceTree {
    public static void main(String[] args) {
        Node node6 = new Node(6, null, null);
        Node node7 = new Node(7, node6, null);
        Node node9 = new Node(9, node7, null);

        System.out.println(treeSolution(node9));
        Node node11 = new Node(11, null, null);
        node9.setRightNode(node11);
        System.out.println(treeSolution(node9));
    }

    public static boolean treeSolution(Node head) {
        return isBalance(head) != -1;
    }

    private static int isBalance(Node head) {

        if (head == null) return 0;

        int left = isBalance(head.getLeftNode());
        int right = isBalance(head.getRightNode());

        if ((left == -1 || right == -1) || Math.abs(left - right) > 1) {
            return -1;
        } else {
            return 1 + Math.max(left, right);
        }
    }
}
