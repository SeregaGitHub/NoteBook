package graph;

import java.io.*;
import java.util.*;

public class GraphCycle {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int[] data = readNumbersToArray(reader);
            int vCount = data[0];
            int eCount = data[1];

            Map<Integer, List<Integer>> graph = createGraph(reader, eCount);
            VertexState[] visitedVertex = createVisitedVertexArray(vCount);

            boolean isCycleExist = findGraphCycle(graph, visitedVertex);
            writer.write(String.valueOf(isCycleExist));
        }
    }

    private static boolean findGraphCycle(Map<Integer, List<Integer>> graph, VertexState[] visitedVertex) {

        boolean isCycleExist = false;

        for (int i = 1; i < visitedVertex.length; i++) {

            if (isCycleExist) return isCycleExist;

            if (visitedVertex[i] == VertexState.WHITE) {
                isCycleExist = DFS(graph, visitedVertex, i);
                // depthWay(writer, graph, visitedVertex, i);
            }
        }

        return isCycleExist;
    }

    // Recursion
    private static boolean DFS(Map<Integer, List<Integer>> graph, VertexState[] visitedVertex, int startVertex) {

        visitedVertex[startVertex] = VertexState.GRAY;

        if (graph.containsKey(startVertex)) {
            List<Integer> edges = graph.get(startVertex);
            for (Integer i : edges) {
                if (visitedVertex[i] == VertexState.WHITE) {

                    boolean isCycleExist = DFS(graph, visitedVertex, i);
                    if (isCycleExist) return true;

                } else if (visitedVertex[i] == VertexState.GRAY) {
                    return true;
                }
            }
        }

        visitedVertex[startVertex] = VertexState.BLACK;

        return false;
    }

    // Iteration (this is from depthWay)
    /*private static void depthWay(
            BufferedWriter writer, Map<Integer, List<Integer>> graph, String[] visitedVertex, int startVertex
    ) throws IOException {

        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(startVertex);

        while (!stack.isEmpty()) {
            int curVertex = stack.pop();

            if (visitedVertex[startVertex].equals("white")) {

                writer.write(String.valueOf(curVertex));
                writer.write(" ");

                visitedVertex[curVertex] = "gray";

                List<Integer> curVertexEdges = graph.get(curVertex);
                Collections.sort(curVertexEdges);

                stack.push(curVertex);

                for (Integer i : curVertexEdges) {
                    if (visitedVertex[startVertex].equals("white")) {
                        stack.push(i);
                    }
                }
            } else if (visitedVertex[startVertex].equals("gray")) {
                visitedVertex[curVertex] = "black";
            }
        }
    }*/

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
