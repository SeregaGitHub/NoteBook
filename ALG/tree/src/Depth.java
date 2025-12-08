import java.util.Stack;

public class Depth {

    public static int depthSumRec(Tree root, int sum) {
        if (root == null) return 0;

        sum = root.getValue() +
                depthSumRec(root.getLeftNode(), sum) +
                depthSumRec(root.getRightNode(), sum);
        return sum;
    }

    public static int depthSumItr(Tree root) {
        Stack<Tree> stack = new Stack<>();
        int sum = 0;
        if (root != null) stack.push(root);

        while (!stack.isEmpty()) {
            Tree tree = stack.pop();
            sum += tree.getValue();

            if (tree.getLeftNode() != null) stack.push(tree.getLeftNode());
            if (tree.getRightNode() != null) stack.push(tree.getRightNode());
        }
        return sum;
    }
}
