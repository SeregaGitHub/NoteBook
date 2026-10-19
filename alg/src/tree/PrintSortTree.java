package tree;

import java.util.List;

public class PrintSortTree {

    public static void printSortTree(Node root) {
        if (root.getLeftNode() != null) {
            printSortTree(root.getLeftNode());
        }

        System.out.print(root.getValue() + " ");

        if (root.getRightNode() != null) {
            printSortTree(root.getRightNode());
        }
    }

    public static List<Integer> listSortTree(Node root, List<Integer> list) {
        if (root.getLeftNode() != null) {
            listSortTree(root.getLeftNode(), list);
        }

        list.add(root.getValue());

        if (root.getRightNode() != null) {
            listSortTree(root.getRightNode(), list);
        }
        return list;
    }
}
