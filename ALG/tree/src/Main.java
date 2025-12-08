import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Tree tree52 = new Tree(52, null, null);
        Tree tree38 = new Tree(38, null, null);
        Tree tree28 = new Tree(28, null, null);
        Tree tree6 = new Tree(6, null, null);
        Tree tree40 = new Tree(40, tree38, tree52);
        Tree tree31 = new Tree(31, tree28, null);
        Tree tree9 = new Tree(9, null, null);
        Tree tree4 = new Tree(4, null, tree6);
        Tree tree35 = new Tree(35, tree31, tree40);
        Tree tree7 = new Tree(7, tree4, tree9);
        Tree tree20 = new Tree(20, tree7, tree35);

        /*
                                20

                 7                        35

           4          9             31            40

             6                    28            38   52

         */

        System.out.println(Depth.depthSumRec(tree20, 0));
        System.out.println(Depth.depthSumItr(tree20));
        System.out.println(Breadth.breadthSumItr(tree20));
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
