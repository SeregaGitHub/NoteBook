package stack;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class IndexMinimumNumberOnTheLeft {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int count = Integer.parseInt(reader.readLine());
            int[] numbers = readNumbers(reader);
            int[] result = new int[count];

            Deque<int[]> queue = new ArrayDeque<>();
            for (int i = 0; i < count; i++) {
                int[] position = new int[2];
                position[0] = numbers[i];
                position[1] = i;
                if (queue.isEmpty()) {
                    queue.push(position);
                } else {
                    if (numbers[i] >= queue.peek()[0]) {
                        queue.push(position);
                    } else {
                        while (!queue.isEmpty()) {
                            if (numbers[i] < queue.peek()[0]) {
                                int[] prev = queue.poll();
                                result[prev[1]] = i;
                            } else {
                                break;
                            }
                        }
                        queue.push(position);
                    }
                }
            }
            while (!queue.isEmpty()) {
                int[] position = queue.poll();
                result[position[1]] = -1;
            }

            for (int i : result) {
                writer.write(i + " ");
            }
        }
    }

    private static int[] readNumbers(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
