package queue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class MinInWindow {
/*
Рассмотрим последовательность целых чисел длины n.
По ней двигается «окно» длины k
Требуется для каждого положения «окна» определить минимум в нём.

7 3
1 3 2 4 5 3 1

1
2
2
3
1
 */
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int[] twoNumbers = readNumbers(reader);
        int[] data = readNumbers(reader);

        printResult(writer, data, twoNumbers[1]);

        reader.close();
        writer.close();
    }

    private static void printResult(BufferedWriter writer, int[] data, int window) throws IOException {
        Deque<Integer> queue = new ArrayDeque<>();
        int l = 0;
        for (int r = 0; r < data.length; r++) {
            if (queue.isEmpty() || data[queue.getLast()] < data[r]) {
                queue.addLast(r);
            } else {
                while (true) {
                    if (!queue.isEmpty() && data[queue.getLast()] >= data[r]) {
                        queue.pollLast();
                    } else {
                        queue.addLast(r);
                        break;
                    }
                }
            }
            // 1 3 2 4 5 3 1
            if (r - window == l) {
                l++;
                if (queue.getFirst() < l) {
                    queue.pollFirst();
                }
            }
            if (r - window == l - 1) {
                writer.write(String.valueOf(data[queue.getFirst()]));
                writer.write("\n");
            }

        }
    }

    private static int[] readNumbers(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
