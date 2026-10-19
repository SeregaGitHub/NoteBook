package graph;

import java.io.*;
import java.util.*;

public class GraphMST {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

/*
7 10
1 5 6
4 5 4
1 2 2
2 3 5
2 6 7
3 4 9
3 6 1
4 6 3
5 6 8
5 7 2
1
 */

            int[] data = readNumbers(reader);
            int vCount = data[0];
            int eCount = data[1];

            Map<Integer, List<Edge>> graph = createGraph(reader, eCount);
            List<EdgeMST> mst = getMST(graph, 1);

            mst.stream().sorted((e1, e2) -> {
                return Integer.compare(e1.getFrom(), e2.getFrom());
            }).forEach(i -> {
                try {
                    writer.write(i + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private static List<EdgeMST> getMST(Map<Integer, List<Edge>> graph, int vertex) {

        List<EdgeMST> mst = new ArrayList<>();
        Set<Integer> mstVertex = new HashSet<>();
        PriorityQueue<EdgeMST> queue = new PriorityQueue<>((e1, e2) -> {
            return Integer.compare(e1.getWeight(), e2.getWeight());
        });

        mstVertex.add(vertex);
        addToQueue(queue, vertex, graph.get(vertex));

        while (!queue.isEmpty()) {
            EdgeMST currentEdge = queue.poll();

            if (!mstVertex.contains(currentEdge.getTo())) {
                mst.add(currentEdge);
                mstVertex.add(currentEdge.getTo());
                addToQueue(queue, currentEdge.getTo(), graph.get(currentEdge.getTo()));
            }
        }

        return mst;
    }

    private static void addToQueue(PriorityQueue<EdgeMST> queue, int vertex, List<Edge> edges) {
        for (Edge e : edges) {
            queue.add(toEdgeMST(vertex, e));
        }
    }

    private static EdgeMST toEdgeMST(int from, Edge edge) {
        return new EdgeMST(from, edge.getTo(), edge.getWeight());
    }

    private static Map<Integer, List<Edge>> createGraph(BufferedReader reader, int eCount) throws IOException {

        Map<Integer, List<Edge>> graph = new HashMap<>();

        for (int i = 0; i < eCount; i++) {
            int[] data = readNumbers(reader);
            graph.computeIfAbsent(data[0], k -> new ArrayList<>()).add(new Edge(data[1], data[2]));
            graph.computeIfAbsent(data[1], k -> new ArrayList<>()).add(new Edge(data[0], data[2]));
        }

        return graph;
    }

    private static int[] readNumbers(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().trim().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private static class EdgeMST {
        int from;
        int to;
        int weight;

        public EdgeMST(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "EdgeMST{" +
                    "from=" + from +
                    ", to=" + to +
                    ", weight=" + weight +
                    '}';
        }
    }

}
