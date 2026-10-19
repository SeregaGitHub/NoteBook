package graph;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// Матрица кратчайших расстояний между вершинами графа
public class FloydWarshall {
    public static void main(String[] args) throws IOException {

/*
4 4
1 2 1
2 3 3
3 4 5
1 4 2

0 1 4 2
1 0 3 3
4 3 0 5
2 3 5 0

 */

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            List<Integer> integerList = readLine(reader);
            int n = integerList.get(0);
            int m = integerList.get(1);

            int[][] matrix = new int[n][n];
            for (int[] ins : matrix) {
                Arrays.fill(ins, -1);
            }
            for (int i = 0; i < n; i++) {
                matrix[i][i] = 0;
            }

            addValuesToGraph(reader, matrix, m);

            int[][] graph = getMinDistance(matrix, n);

            for (int[] ins: graph) {
                Arrays.stream(ins).forEach(i -> {
                    try {
                        writer.write(i + " ");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                writer.write("\n");
            }
        }
    }

    private static int[][] getMinDistance(int[][] graph, Integer n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (graph[j][i] > 0 && graph[i][k] > 0 && graph[j][k] > 0) {
                        graph[j][k] = Math.min(graph[j][k], graph[j][i] + graph[i][k]);
                    } else if (graph[j][i] > 0 && graph[i][k] > 0 && graph[j][k] < 0) {
                        graph[j][k] = graph[j][i] + graph[i][k];
                    }
                }
            }
        }
        return graph;
    }

    private static void addValuesToGraph(BufferedReader reader, int[][] graph, Integer m) throws IOException {
        for (int i = 0; i < m; i++) {
            List<Integer> edge = readLine(reader);
            int fromIndex = edge.get(0) - 1;
            int toIndex = edge.get(1) - 1;
            int weightIndex = edge.get(2);

            if (graph[fromIndex][toIndex] < 0) {
                graph[fromIndex][toIndex] = weightIndex;
                graph[toIndex][fromIndex] = weightIndex;
            } else {
                graph[fromIndex][toIndex] = Math.min(weightIndex, graph[fromIndex][toIndex]);
                graph[toIndex][fromIndex] = Math.min(weightIndex, graph[fromIndex][toIndex]);
            }
        }
    }

    private static List<Integer> readLine(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
