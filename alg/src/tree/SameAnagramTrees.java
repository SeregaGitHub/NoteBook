package tree;

public class SameAnagramTrees {

    public static boolean sameTrees(Node root1, Node root2) {

        if (root1 == null && root2 == null) return true;
        if (root1 == null || root2 == null) return false;
        if (root1.getValue() != root2.getValue()) return false;

        return sameTrees(root1.getLeftNode(), root2.getLeftNode()) &&
               sameTrees(root1.getRightNode(), root2.getRightNode());
    }

    // anagramTree()
    public static boolean anagramTree(Node root) {
        return checkAnagramTree(root.getLeftNode(), root.getRightNode());
    }

    private static boolean checkAnagramTree(Node nodeLeft, Node nodeRight) {

        if (nodeLeft == null && nodeRight == null) return true;
        if (nodeLeft == null || nodeRight == null) return false;
        if (nodeLeft.getValue() != nodeRight.getValue()) return false;

        return checkAnagramTree(nodeLeft.getLeftNode(), nodeRight.getRightNode()) &&
               checkAnagramTree(nodeLeft.getRightNode(), nodeRight.getLeftNode());
    }
    // anagramTree()
}


