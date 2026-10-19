package graph;

import java.io.*;
import java.util.*;

public class SimpleGraphBFS {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int[] data = readNumbersToArray(reader);
            int vCount = data[0];
            int eCount = data[1];

            Map<Integer, List<Integer>> graph = createGraph(reader, eCount);
            boolean[] visitedVertex = new boolean[vCount + 1];

            mainBFS(writer, graph, visitedVertex);
        }
    }

    private static void mainBFS(
            BufferedWriter writer, Map<Integer, List<Integer>> graph, boolean[] visitedVertex
    ) throws IOException {

        for (int i = 1; i < visitedVertex.length; i++) {
            if (!visitedVertex[i]) {
                BFS(writer, graph, visitedVertex, i);
            }
        }
    }

    private static void BFS(
            BufferedWriter writer, Map<Integer, List<Integer>> graph, boolean[] visitedVertex, int startVertex
    ) throws IOException {

        Deque<Integer> queue = new ArrayDeque<>();

        visitedVertex[startVertex] = true;
        queue.addLast(startVertex);

        while (!queue.isEmpty()) {
            int currentVertex = queue.removeFirst();

            writer.write(String.valueOf(currentVertex));
            writer.write(" ");

            List<Integer> edges = graph.get(currentVertex);
            if (edges != null) {
                for (Integer i : edges) {
                    if (!visitedVertex[i]) {
                        visitedVertex[i] = true;   // ← пометка до push
                        queue.addLast(i);
                    }
                }
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
