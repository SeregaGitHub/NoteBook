package graph;

import java.io.*;
import java.util.*;

public class SimpleGraphRecursionDFS {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            /*
5 4
3 2
4 3
1 4
1 2

                1 --> 2 <-- 3
                |           ^
                |           |
               \ /          ^
                4 ---------->
             */
            int[] data = readNumbersToArray(reader);
            int vCount = data[0];
            int eCount = data[1];

            Map<Integer, List<Integer>> graph = createGraph(reader, eCount);
            boolean[] visitedVertex = new boolean[vCount + 1];

            mainSimpleDFS(writer, graph, visitedVertex);
        }
    }

    private static void simpleDFS(
            BufferedWriter writer, Map<Integer, List<Integer>> graph, boolean[] visitedVertex, int vertex
    ) throws IOException {

        visitedVertex[vertex] = true;

        writer.write(String.valueOf(vertex));
        writer.write(" ");

        if (graph.containsKey(vertex)) {
            List<Integer> edges = graph.get(vertex);
            for (Integer i : edges) {
                if (!visitedVertex[i]) {
                    simpleDFS(writer, graph, visitedVertex, i);
                }
            }
        }
    }

    private static void mainSimpleDFS(
            BufferedWriter writer, Map<Integer, List<Integer>> graph, boolean[] visitedVertex
    ) throws IOException {

        for (int i = 1; i < visitedVertex.length; i++) {
            if (!visitedVertex[i]) {
                simpleDFS(writer, graph, visitedVertex, i);
            }
        }
    }

    private static Map<Integer, List<Integer>> createGraph(BufferedReader reader, int edgeCount) throws IOException {

        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (int i = 0; i < edgeCount; i++) {
            int[] edge = readNumbersToArray(reader);
            graph.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(edge[1]); // ориентированный граф
            // graph.computeIfAbsent(edge[1], k -> new ArrayList<>()).add(edge[0]); // неориентированный граф
        }

        return graph;
    }

    private static int[] readNumbersToArray(BufferedReader reader) throws IOException {
        String str = reader.readLine();
        if (str == null || str.isBlank()) return new int[0];
        return Arrays.stream(str.trim().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}

