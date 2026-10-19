package tree;

import java.util.Stack;

public class Depth {

    public static int depthSumRec(Node root, int sum) {
        if (root == null) return 0;

        sum = root.getValue() +
                depthSumRec(root.getLeftNode(), sum) +
                depthSumRec(root.getRightNode(), sum);
        return sum;
    }

    public static int depthSumItr(Node root) {
        Stack<Node> stack = new Stack<>();
        int sum = 0;
        if (root != null) stack.push(root);

        while (!stack.isEmpty()) {
            Node tree = stack.pop();
            sum += tree.getValue();

            if (tree.getLeftNode() != null) stack.push(tree.getLeftNode());
            if (tree.getRightNode() != null) stack.push(tree.getRightNode());
        }
        return sum;
    }
}
