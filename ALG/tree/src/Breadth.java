import java.util.LinkedList;
import java.util.Queue;

public class Breadth {

    public static int breadthSumItr(Tree root) {
        Queue<Tree> queue = new LinkedList<>();
        int sum = 0;
        if (root != null) queue.add(root);

        while (!queue.isEmpty()) {
            Tree tree = queue.poll();
            sum += tree.getValue();

            if (tree.getLeftNode() != null) queue.add(tree.getLeftNode());
            if (tree.getRightNode() != null) queue.add(tree.getRightNode());
        }
        return sum;
    }
}
