package graph;

import java.io.*;
import java.util.*;

//  Является ли заданный неориентированный граф двудольным
public class IsTwoPart {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int[] data = readNumbersToArray(reader);
            int vCount = data[0];
            int eCount = data[1];

            Map<Integer, List<Integer>> graph = createGraph(reader, eCount);
            boolean[] visitedVertex = new boolean[vCount + 1];

            boolean result = isTwoPart(graph, visitedVertex);
            writer.write(result ? "YES" : "NO");

            /*
                     1 ------ 2
                       \    /
                         3

                         NO
             */
        }
    }

    private static boolean isTwoPart(Map<Integer, List<Integer>> graph, boolean[] visitedVertex) {

        int[] isTwoPart = new int[visitedVertex.length];

        for (int i = 1; i < visitedVertex.length; i++) {
            if (!visitedVertex[i]) {
                boolean result = DFS(graph, visitedVertex, isTwoPart, i);
                if (!result) return false;
            }
        }

        return true;
    }

    private static boolean DFS(
            Map<Integer, List<Integer>> graph, boolean[] visitedVertex, int[] isTwoPart, int vertex
    ) {

        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(vertex);

        visitedVertex[vertex] = true;

        while (!stack.isEmpty()) {

            int currentVertex = stack.pop();
            boolean addToFirst = true;

            if (isTwoPart[currentVertex] == 1) {
                addToFirst = false;
            }

            List<Integer> edges = graph.get(currentVertex);
            if (edges != null) {
                for (Integer e : edges) {

                    if (addToFirst && isTwoPart[e] == 2) {
                        return false;
                    } else if (!addToFirst && isTwoPart[e] == 1) {
                        return false;
                    } else if (addToFirst) {
                        isTwoPart[e] = 1;
                    } else {
                        isTwoPart[e] = 2;
                    }

                    if (!visitedVertex[e]) {
                        visitedVertex[e] = true;
                        stack.push(e);
                    }
                }
            }
        }

        return true;
    }

    // OK but slowly then with - int[] isTwoPart
    /*private static boolean isTwoPart(Map<Integer, List<Integer>> graph, boolean[] visitedVertex) {

        Set<Integer> first = new HashSet<>();
        Set<Integer> second = new HashSet<>();

        for (int i = 1; i < visitedVertex.length; i++) {
            if (!visitedVertex[i]) {
                boolean result = DFS(graph, visitedVertex, first, second, i);
                if (!result) return false;
            }
        }

        return true;
    }

    private static boolean DFS(
            Map<Integer, List<Integer>> graph, boolean[] visitedVertex,
            Set<Integer> first, Set<Integer> second, int vertex
    ) {

        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(vertex);

        visitedVertex[vertex] = true;

        while (!stack.isEmpty()) {

            int currentVertex = stack.pop();
            boolean addToFirst = true;

            if (first.contains(currentVertex) && second.contains(currentVertex)) {
                return false;
            } else if (!first.contains(currentVertex) && !second.contains(currentVertex)) {
                second.add(currentVertex);
            } else if (first.contains(currentVertex)) {
                addToFirst = false;
            }

            List<Integer> edges = graph.get(currentVertex);
            if (edges != null) {
                for (Integer e : edges) {

                    if (addToFirst && second.contains(e)) {
                        return false;
                    } else if (!addToFirst && first.contains(e)) {
                        return false;
                    } else if (addToFirst) {
                        first.add(e);
                    } else {
                        second.add(e);
                    }

                    if (!visitedVertex[e]) {
                        visitedVertex[e] = true;
                        stack.push(e);
                    }
                }
            }
        }

        return true;
    }*/

    private static Map<Integer, List<Integer>> createGraph(BufferedReader reader, int eCount) throws IOException {

        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (int i = 0; i < eCount; i++) {
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

