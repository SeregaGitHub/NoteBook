package graph;

import java.io.*;
import java.util.*;

public class GraphTopSort {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int[] data = readNumbers(reader);
            int vCount = data[0];
            int eCount = data[1];

            Map<Integer, List<Integer>> graph = createGraph(reader, eCount);
            String[] visitedVertex = createArray(vCount);

            Deque<Integer> result = mainTopSort(graph, visitedVertex);
            printResult(writer, result);
        }
    }

    private static void printResult(BufferedWriter writer, Deque<Integer> result) throws IOException {

        while (!result.isEmpty()) {
            int current = result.removeFirst();
            writer.write(String.valueOf(current));
            writer.write(" ");
        }
    }

    // Recursion
    private static void topSort(
            Map<Integer, List<Integer>> graph, String[] visitedVertex, Deque<Integer> result, int startVertex
    ) {
        visitedVertex[startVertex] = "gray";

        if (graph.containsKey(startVertex)) {
            List<Integer> edges = graph.get(startVertex);
            for (Integer i : edges) {
                if (visitedVertex[i].equals("white")) {
                    topSort(graph, visitedVertex, result, i);
                }
            }
        }

        visitedVertex[startVertex] = "black";
        result.addFirst(startVertex);
    }

    // Iteration
    /*private static void topSort(
            Map<Integer, List<Integer>> graph, String[] visitedVertex, Deque<Integer> result, int startVertex
    ) {

        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(startVertex);

        while (!stack.isEmpty()) {

            int current = stack.pop();

            if (visitedVertex[current].equals("white")) {

                visitedVertex[current] = "gray";
                stack.push(current);

                if (graph.containsKey(current)) {
                    List<Integer> edges = graph.get(current);
                    for (Integer i : edges) {
                        if (visitedVertex[i].equals("white")) {
                            stack.push(i);
                        }
                    }
                }
            } else if (visitedVertex[current].equals("gray")) {
                visitedVertex[current] = "black";
                result.addFirst(current);
            }
        }
    }*/

    private static Deque<Integer> mainTopSort(Map<Integer, List<Integer>> graph, String[] visitedVertex) {
        Deque<Integer> result = new ArrayDeque<>();

        for (int i = 1; i < visitedVertex.length; i++) {
            if (visitedVertex[i].equals("white")) {
                topSort(graph, visitedVertex, result, i);
            }
        }

        return result;
    }

    private static String[] createArray(int vCount) {

        String[] visitedVertex = new String[vCount + 1];
        Arrays.fill(visitedVertex, "white");
        visitedVertex[0] = null;

        return visitedVertex;
    }

    private static Map<Integer, List<Integer>> createGraph(BufferedReader reader, int eCount) throws IOException {

        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (int i = 0; i < eCount; i++) {
            int[] data = readNumbers(reader);
            graph.computeIfAbsent(data[0], k -> new ArrayList<>()).add(data[1]);
        }

        return graph;
    }

    private static int[] readNumbers(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
