package tree;

import java.util.ArrayList;

public class MainTree {
    public static void main(String[] args) {

        Node tree52 = new Node(52, null, null);
        Node tree38 = new Node(38, null, null);
        Node tree28 = new Node(28, null, null);
        Node tree6 = new Node(6, null, null);
        Node tree40 = new Node(40, tree38, tree52);
        Node tree31 = new Node(31, tree28, null);
        Node tree9 = new Node(9, null, null);
        Node tree4 = new Node(4, null, tree6);
        Node tree35 = new Node(35, tree31, tree40);
        Node tree7 = new Node(7, tree4, tree9);
        Node tree20 = new Node(20, tree7, tree35);

        /*
                                20

                 7                        35

           4          9             31            40

             6                    28            38   52

         */

        System.out.println(Depth.depthSumRec(tree20, 0));
        System.out.println(Depth.depthSumItr(tree20));
        System.out.println(Width.breadthSumItr(tree20));
        PrintSortTree.printSortTree(tree20);
        System.out.println();
        PrintSortTree.listSortTree(tree20, new ArrayList<>()).forEach(
                integer -> System.out.print(integer + " ")
        );
        System.out.println();
        System.out.println(MaxDepthAndMaxValue.maxValue(tree20));
        System.out.println(MaxDepthAndMaxValue.maxDepth(tree20));
    }
}
