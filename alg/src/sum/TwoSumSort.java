package sum;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwoSumSort {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int sum = Integer.parseInt(reader.readLine());
            int count = Integer.parseInt(reader.readLine());
            int[] data = readNumbersToArray(reader);

            List<Integer> result = getResult(data, sum);

            if (!result.isEmpty()) {
                writer.write(String.valueOf(result.getFirst()));
                writer.write(" ");
                writer.write(String.valueOf(result.getLast()));
            } else {
                writer.write("NO");
            }
        }
    }

    private static List<Integer> getResult(int[] data, int sum) {

        List<Integer> result = new ArrayList<>();

        int leftIndex = 0;
        int rightIndex = data.length - 1;

        while (leftIndex < rightIndex) {

            int check = data[leftIndex] + data[rightIndex];

            if (check < sum) {
                leftIndex++;
            } else if (check > sum) {
                rightIndex--;
            } else {
                result.add(data[leftIndex]);
                result.add(data[rightIndex]);
                break;
            }
        }

        return result;
    }

    private static int[] readNumbersToArray(BufferedReader reader) throws IOException {
        String str = reader.readLine();
        if (str == null || str.isBlank()) return new int[0];
        return Arrays.stream(str.trim().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}

