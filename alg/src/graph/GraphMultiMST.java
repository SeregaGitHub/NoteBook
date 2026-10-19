package graph;

import java.io.*;
import java.util.*;

public class GraphMultiMST {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

/*
7 8
1 2 2
2 3 5
2 6 7
3 4 9
3 6 1
4 6 3
5 6 8
5 7 2
1
2
1 5 6
4 5 4
 */

            int[] data = readNumbers(reader);
            int vCount = data[0];
            int eCount = data[1];

            Map<Integer, List<Edge>> graph = new HashMap<>();
            createGraph(reader, graph, eCount);

            int startVertex = Integer.parseInt(reader.readLine());

            List<EdgeMST> mst = getMST(graph, startVertex);

            printMST(writer, mst);

            int addCount = Integer.parseInt(reader.readLine());
            writer.write("\n");
            printNewMST(reader, writer, graph, mst, addCount);
        }
    }

    private static void printNewMST(
            BufferedReader reader, BufferedWriter writer,
            Map<Integer, List<Edge>> graph, List<EdgeMST> mst, int addCount
    ) throws IOException {

        Map<Integer, List<Edge>> mapMST = new HashMap<>();
        for (EdgeMST e : mst) {
            mapMST.computeIfAbsent(e.getFrom(), k -> new ArrayList<>()).add(new Edge(e.getTo(), e.getWeight()));
            mapMST.computeIfAbsent(e.getTo(), k -> new ArrayList<>()).add(new Edge(e.getFrom(), e.getWeight()));
        }

        for (int i = 0; i < addCount; i++) {
            int[] data = readNumbers(reader);
            findMaxWeightEdge(mst, mapMST, data[0], data[1], data[2]);
            printMST(writer, mst);
            writer.write("\n");
        }
    }

    private static void findMaxWeightEdge(
            List<EdgeMST> mst, Map<Integer, List<Edge>> mapMST, int v1, int v2, int weight
    ) {
        int startVertex = mapMST.containsKey(v1) ? v1 : v2;
        int endVertex = mapMST.containsKey(v1) ? v2 : v1;

        EdgeMST maxWeightEdge = DFS(mapMST, startVertex, endVertex, weight);

        if (maxWeightEdge.getWeight() > weight) {
            mst.remove(maxWeightEdge);
            mst.add(new EdgeMST(startVertex, endVertex, weight));


            mapMST.get(maxWeightEdge.getFrom()).removeIf(
                    edge -> edge.getTo() == maxWeightEdge.getTo() && edge.weight == maxWeightEdge.getWeight()
            );
            mapMST.get(maxWeightEdge.getTo()).removeIf(
                    edge -> edge.getTo() == maxWeightEdge.getTo() && edge.weight == maxWeightEdge.getWeight()
            );

            mapMST.computeIfAbsent(v1, k -> new ArrayList<>()).add(new Edge(v2, weight));
            mapMST.computeIfAbsent(v2, k -> new ArrayList<>()).add(new Edge(v1, weight));
        }
    }

    /*
    private static EdgeMST findMaxEdgeOnPath(Map<Integer, List<Edge>> mapMST,
                                         int start, int end) {
    Map<Integer, EdgeMST> parent = new HashMap<>();
    Set<Integer> visited = new HashSet<>();
    Deque<Integer> stack = new ArrayDeque<>();

    stack.push(start);
    visited.add(start);

    while (!stack.isEmpty()) {
        int current = stack.pop();
        if (current == end) break;
        for (Edge e : mapMST.getOrDefault(current, Collections.emptyList())) {
            if (visited.add(e.getTo())) {
                parent.put(e.getTo(), new EdgeMST(current, e.getTo(), e.getWeight()));
                stack.push(e.getTo());
            }
        }
    }

    EdgeMST maxEdge = null;
    Integer node = end;
    while (parent.containsKey(node)) {
        EdgeMST e = parent.get(node);
        if (maxEdge == null || e.getWeight() > maxEdge.getWeight()) {
            maxEdge = e;
        }
        node = e.getFrom();
    }
    return maxEdge;
}
     */

    private static EdgeMST DFS(Map<Integer, List<Edge>> mapMST, int startVertex, int endVertex, int weight) {

        EdgeMST maxWeightEdge = null;
        Set<Integer> visitedVertex = new HashSet<>();
        Map<Integer, EdgeMST> prev = new HashMap<>();
        Deque<Integer> stack = new ArrayDeque<>();

        visitedVertex.add(startVertex);
        stack.add(startVertex);

        boolean isContinue = true;
        while (!stack.isEmpty() || isContinue) {

            int currentVertex = stack.pop();

            if (mapMST.containsKey(currentVertex)) {
                List<Edge> edges = mapMST.get(currentVertex);
                if (edges != null) {
                    for (Edge e : edges) {
                        int nextVertex = e.getTo();
                        if (!visitedVertex.contains(nextVertex)) {
                            visitedVertex.add(nextVertex);
                            prev.put(nextVertex, new EdgeMST(currentVertex, nextVertex, e.getWeight()));
                            if (nextVertex == endVertex) {
                                isContinue = false;
                                break;
                            }
                            stack.push(nextVertex);
                        }
                    }
                }
            }
        }

        // Need
        return maxWeightEdge;
    }

    // Need
    private static EdgeMST findMaxWeight(
            Map<Integer, List<Edge>> mapMST, Map<Integer, EdgeMST> prev, int startVertex, int endVertex, int weight
    ) {

        List<EdgeMST> way = new ArrayList<>();
        EdgeMST currentEdge = prev.get(endVertex);

        int k = -1;
        int w = -1;
        while (currentEdge.getFrom() != startVertex) {
            if (currentEdge.getWeight() > w) {
                w = currentEdge.getWeight();
                k = currentEdge.getFrom();
            }
            currentEdge = prev.get(currentEdge.getTo());
        }

        return w < weight ? null : new EdgeMST(startVertex, endVertex, weight);
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

    private static void printMST(BufferedWriter writer, List<EdgeMST> mst) {
        mst.stream().sorted((e1, e2) -> {
            return Integer.compare(e1.getFrom(), e2.getFrom());
        }).forEach(i -> {
            try {
                writer.write(i + " ");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void addToQueue(PriorityQueue<EdgeMST> queue, int vertex, List<Edge> edges) {
        for (Edge e : edges) {
            queue.add(toEdgeMST(vertex, e));
        }
    }

    private static EdgeMST toEdgeMST(int from, Edge edge) {
        return new EdgeMST(from, edge.getTo(), edge.getWeight());
    }

    private static void createGraph(
            BufferedReader reader, Map<Integer, List<Edge>> graph, int eCount
    ) throws IOException {

        for (int i = 0; i < eCount; i++) {
            int[] data = readNumbers(reader);
            graph.computeIfAbsent(data[0], k -> new ArrayList<>()).add(new Edge(data[1], data[2]));
            graph.computeIfAbsent(data[1], k -> new ArrayList<>()).add(new Edge(data[0], data[2]));
        }
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
