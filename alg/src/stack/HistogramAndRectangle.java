package stack;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class HistogramAndRectangle {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int[] data = readData(reader);
            long result = getResult(data);
            writer.write(String.valueOf(result));
        }
    }

    private static long getResult(int[] data) {
        long result = 0;

        Deque<Integer> stack = new ArrayDeque<>();

        int leftBorder = 0;
        for (int i = 0; i < data.length; i++) {
            if (stack.isEmpty()) {
                if (data[i] == 0) {
                    leftBorder = i + 1;
                } else {
                    stack.push(i);
                }
            } else if (data[i] == 0) {
                result = getLong(data, stack, leftBorder, result, i);
                leftBorder = i + 1;
            } else {
                int checkInt = data[i];
                int headStackInt = stack.peek();
                if (checkInt >= data[headStackInt]) {
                    stack.push(i);
                } else {
                    while (!stack.isEmpty()) {
                        headStackInt = stack.peek();
                        if (checkInt >= data[headStackInt]) {
                            break;
                        } else {
                            int index = stack.pop();
                            int y = data[index];
                            int x = stack.isEmpty() ? i - leftBorder : i - stack.peek() - 1;
                            result = Math.max(result, (long) x * y);
                        }
                    }
                    stack.push(i);
                }
            }
        }
        return getLong(data, stack, leftBorder, result, data.length);
    }

    private static long getLong(int[] data, Deque<Integer> stack, int leftBorder, long result, int rightBorder) {

        int y = Integer.MAX_VALUE;

        while (!stack.isEmpty()) {
            int index = stack.pop();
            y = Math.min(y, data[index]);
            int x = stack.isEmpty() ? rightBorder - leftBorder : rightBorder - stack.peek() - 1;
            result = Math.max(result, (long) x * y);
        }
        return result;
    }

    private static int[] readData(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().split("\\s+"))
                .skip(1)
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
