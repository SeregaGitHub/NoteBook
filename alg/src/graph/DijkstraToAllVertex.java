package graph;

import java.io.*;
import java.util.*;

public class DijkstraToAllVertex {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
/*
6 14
1 3 15
1 2 5
1 4 10
2 4 2
2 6 13
4 1 3
4 6 4
4 5 3
6 5 2
5 6 6
5 3 2
3 5 6
3 4 3
4 3 4
1
 */

            int[] data = readNumbers(reader);
            int vCount = data[0];
            int eCount = data[1];

            Map<Integer, List<Edge>> graph = createGraph(reader, eCount);

            boolean[] visitedVertex = new boolean[vCount + 1];
            int[] distance = new int[vCount + 1];
            int[] prev = new int[vCount + 1];  // Only if need write the way !!!

            int startVertex = Integer.parseInt(reader.readLine());

            Arrays.fill(prev, -1);
            Arrays.fill(distance, Integer.MAX_VALUE);
            distance[0] = -1;

            dijkstra(graph, visitedVertex, distance, prev, startVertex);

            for (int i = 1; i <= vCount; i++) {
                writer.write(String.valueOf(i));
                writer.write(" - ");
                writer.write(String.valueOf(distance[i]));
                writer.write("; ");
            }
        }
    }

    private static void dijkstra(
            Map<Integer, List<Edge>> graph, boolean[] visitedVertex, int[] distance, int[] prev, int startVertex
    ) {
        PriorityQueue<MinDistance> priorityQueue = new PriorityQueue<>((e1, e2) -> {
            return Integer.compare(e1.getMinDistance(), e2.getMinDistance());
        });

        priorityQueue.add(new MinDistance(startVertex, 0));
        distance[startVertex] = 0;

        while (!priorityQueue.isEmpty()) {

            MinDistance currentDistance = priorityQueue.poll();
            int currentVertex = currentDistance.getVertex();
            int minCurDist = currentDistance.getMinDistance();

            if (!visitedVertex[currentVertex]) {

                visitedVertex[currentVertex] = true;
                List<Edge> edges = graph.get(currentVertex);

                if (edges != null) {
                    for (Edge e : edges) {
                        int nextVertex = e.getTo();
                        int newWeight = minCurDist + e.getWeight();

                        if (distance[nextVertex] > newWeight) {
                            distance[nextVertex] = newWeight;
                            prev[nextVertex] = currentVertex;
                            priorityQueue.add(new MinDistance(nextVertex, newWeight));
                        }
                    }
                }
            }
        }
    }

    private static Map<Integer, List<Edge>> createGraph(BufferedReader reader, int eCount) throws IOException {

        Map<Integer, List<Edge>> graph = new HashMap<>();

        for (int i = 0; i < eCount; i++) {
            int[] array = readNumbers(reader);
            Edge edge = new Edge(array[1], array[2]);
            graph.computeIfAbsent(array[0], k -> new ArrayList<>()).add(edge);
        }

        return graph;
    }

    private static int[] readNumbers(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    static class MinDistance {

        int vertex;
        int minDistance;

        public MinDistance(int vertex, int minDistance) {
            this.vertex = vertex;
            this.minDistance = minDistance;
        }

        public int getVertex() {
            return vertex;
        }

        public void setVertex(int vertex) {
            this.vertex = vertex;
        }

        public int getMinDistance() {
            return minDistance;
        }

        public void setMinDistance(int minDistance) {
            this.minDistance = minDistance;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            MinDistance that = (MinDistance) o;
            return vertex == that.vertex && minDistance == that.minDistance;
        }

        @Override
        public int hashCode() {
            return Objects.hash(vertex, minDistance);
        }
    }
}
