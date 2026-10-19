package graph;

import java.io.*;
import java.util.*;

public class DistanceBetweenVertex {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int[] data = readNumbers(reader);
            int vCount = data[0];
            int eCount = data[1];

            Map<Integer, List<Integer>> graph = createGraph(reader, eCount);

            boolean[] visitedVertex = new boolean[vCount + 1];

            int[] dataVertex = readNumbers(reader);
            int start = dataVertex[0];
            int end = dataVertex[1];

            int result = BFS(graph, visitedVertex, start, end);
            writer.write(String.valueOf(result));
        }
    }

    private static int BFS(Map<Integer, List<Integer>> graph, boolean[] visitedVertex, int start, int end) {

        int[] distance = new int[visitedVertex.length];

        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(start);

        visitedVertex[start] = true;

        while (!queue.isEmpty()) {

            int current = queue.removeFirst();
            if (current == end) return distance[current];

            List<Integer> edges = graph.get(current);
            if (edges != null) {
                for (Integer i : edges) {
                    if (!visitedVertex[i]) {
                        visitedVertex[i] = true;
                        distance[i] = distance[current] + 1;
                        queue.addLast(i);
                    }
                }
            }
        }

        return -1;
    }

    private static Map<Integer, List<Integer>> createGraph(BufferedReader reader, int eCount) throws IOException {

        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (int i = 0; i < eCount; i++) {
            int[] edge = readNumbers(reader);
            graph.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(edge[1]);
            graph.computeIfAbsent(edge[1], k -> new ArrayList<>()).add(edge[0]);
        }

        return graph;
    }

    private static int[] readNumbers(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().trim().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
