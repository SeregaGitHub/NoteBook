public class MaxDepthAndMaxValue {

    public static int maxValue(Tree root) {
        if (root == null) return Integer.MIN_VALUE;

        int left = maxValue(root.getLeftNode());
        int right = maxValue(root.getRightNode());

        return Math.max(Math.max(left, right), root.getValue());
    }

    public static int maxDepth(Tree root) {
        if (root == null) return 0;

        int left = maxDepth(root.getLeftNode());
        int right = maxDepth(root.getRightNode());

        return Math.max(left, right) + 1;
    }
}
