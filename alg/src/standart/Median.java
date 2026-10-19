package standart;

import java.io.*;
import java.util.*;

public class Median {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int count = Integer.parseInt(reader.readLine());
            if (count == 0) {
                writer.write("");
            } else {
                int[] data = readNumbers(reader);
                printResult(writer, data);
            }
        }
    }

    private static void printResult(BufferedWriter writer, int[] data) throws IOException {
        PriorityQueue<Integer> minNumbersQueue = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> maxNumbersQueue = new PriorityQueue<>(Comparator.naturalOrder());

        for (int currentInt : data) {
            if (minNumbersQueue.size() == maxNumbersQueue.size()) {
                if (minNumbersQueue.isEmpty()) {
                    minNumbersQueue.add(currentInt);
                } else if (currentInt > maxNumbersQueue.peek()) {
                    minNumbersQueue.add(maxNumbersQueue.poll());
                    maxNumbersQueue.add(currentInt);
                } else if (currentInt < maxNumbersQueue.peek()) {
                    minNumbersQueue.add(currentInt);
                }
            } else if (minNumbersQueue.size() > maxNumbersQueue.size()) {
                if (currentInt > minNumbersQueue.peek()) {
                    maxNumbersQueue.add(currentInt);
                } else {
                    maxNumbersQueue.add(minNumbersQueue.poll());
                    minNumbersQueue.add(currentInt);
                }
            }
            writer.write(String.valueOf(minNumbersQueue.peek()));
            writer.write(" ");
        }
    }

    private static int[] readNumbers(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
