package graph;

import java.io.*;
import java.util.*;

public class MaxWayByEdgesCount {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int[] data = readNumbersToArray(reader);
            int vCount = data[0];
            int eCount = data[1];

            Map<Integer, List<Integer>> graph = createGraph(reader, eCount);
            VertexState[] visitedVertex = createVisitedVertexArray(vCount);
            int[] distance = new int[vCount + 1]; // показывает дистанцию до вершин
            Arrays.fill(distance, -1);
            int[] prev = new int[vCount + 1];     // нужно для восстановления путей до вершин
            Arrays.fill(prev, -1);

            int startVertex = Integer.parseInt(reader.readLine());

            BFS(graph, visitedVertex, distance, prev, startVertex);

            int maxDistance = Arrays.stream(distance)
                    .reduce(Integer::max)
                    .getAsInt();
            writer.write(String.valueOf(maxDistance));
        }
    }

    private static void BFS(
            Map<Integer, List<Integer>> graph, VertexState[] visitedVertex,
            int[] distance, int[] prev, int startVertex
    ) {

        Deque<Integer> queue = new ArrayDeque<>();

        queue.addLast(startVertex);
        visitedVertex[startVertex] = VertexState.GRAY;
        distance[startVertex] = 0;

        while (!queue.isEmpty()) {
            int currentVertex = queue.removeFirst();

            if (graph.containsKey(currentVertex)) {
                List<Integer> edges = graph.get(currentVertex);
                for (Integer i : edges) {
                    if (visitedVertex[i] == VertexState.WHITE) {

                        visitedVertex[i] = VertexState.GRAY;
                        distance[i] = distance[currentVertex] + 1;
                        prev[i] = currentVertex;

                        queue.addLast(i);
                    }
                }
            }
            visitedVertex[currentVertex] = VertexState.BLACK;
        }
    }

    private static VertexState[] createVisitedVertexArray(int vCount) {
        VertexState[] visitedVertex = new VertexState[vCount + 1];
        Arrays.fill(visitedVertex, VertexState.WHITE);
        visitedVertex[0] = null;

        return visitedVertex;
    }

    private static Map<Integer, List<Integer>> createGraph(BufferedReader reader, int edgeCount) throws IOException {

        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (int i = 0; i < edgeCount; i++) {
            int[] edge = readNumbersToArray(reader);
            graph.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(edge[1]);
            graph.computeIfAbsent(edge[1], k -> new ArrayList<>()).add(edge[0]);
        }

        return graph;
    }

    private static int[] readNumbersToArray(BufferedReader reader) throws IOException {
        String str = reader.readLine();
        if (str == null || str.isBlank()) return new int[0];
        return Arrays.stream(str.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
