package tree;

import java.io.*;
import java.util.*;

public class CreateTree {
    // 7 3 2 1 1 9 5 4 6 8 8 0
    // 7 - root    0 - end of tree (0 - not node)
    // Каждое уникальное число должно встречаться в дереве ровно один раз
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int[] data = readNumbers(reader);

            Node root = createTree(data);
            printTree(writer, root);
        }
    }

    private static Node createTree(int[] data) {
        Node root = new Node(data[0], null, null);
        for (int i : data) {
            root = addNode(root, i);
        }
        return root;
    }

    private static Node addNode(Node root, int newValue) {
        if (root == null) return new Node(newValue, null, null);

        if (root.value < newValue) {
            root.right = addNode(root.right, newValue);
        } else if (root.value > newValue) {
            root.left = addNode(root.left, newValue);
        } else {
            return root;
        }
        return root;
    }

    private static void printTree(BufferedWriter writer, Node root) throws IOException {

        if (root.left != null) {
            printTree(writer, root.left);
        }
        writer.write(String.valueOf(root.value));
        writer.write(" ");
        if (root.right != null) {
            printTree(writer, root.right);
        }
    }

    private static int[] readNumbers(BufferedReader reader) throws IOException {
        String[] array = reader.readLine().split(" ");
        return Arrays.stream(array, 0, array.length - 1)
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    static class Node {
        int value;
        Node left;
        Node right;

        Node(int value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }
}
