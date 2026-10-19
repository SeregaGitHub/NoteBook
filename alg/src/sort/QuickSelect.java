package sort;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class QuickSelect {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int count = Integer.parseInt(reader.readLine());
            int[] data = readNumbers(reader);
            int k = Integer.parseInt(reader.readLine());

            int result = quickSelect(data, 0, data.length - 1, k);
            writer.write(String.valueOf(result));

            List<Integer> list = Arrays.stream(data).boxed().sorted().toList();
            writer.write("\n");
            writer.write(String.valueOf(list.get(k - 1)));
        }
    }

    private static int quickSelect(int[] data, int leftIndex, int rightIndex, int k) {
        if (leftIndex == rightIndex) return data[leftIndex];

        int result;
        int pivot = partition(data, leftIndex, rightIndex);

        int leftSize = pivot - leftIndex;
        if (k <= leftSize) {
            result = quickSelect(data, leftIndex, pivot - 1, k);
        } else {
            k = k - leftSize;
            result = quickSelect(data, pivot, rightIndex, k);
        }

        return result;
    }

    private static int partition(int[] data, int leftIndex, int rightIndex) {
        int middle = data[(leftIndex + rightIndex) / 2];
        int pivot = Math.max(
                Math.min(data[leftIndex], middle), Math.min(Math.max(data[leftIndex], middle), data[rightIndex])
        );

        while (leftIndex <= rightIndex) {
            while (data[leftIndex] < pivot) {
                leftIndex++;
            }
            while (data[rightIndex] > pivot) {
                rightIndex--;
            }
            if (leftIndex <= rightIndex) {
                swap(data, leftIndex, rightIndex);
                leftIndex++;
                rightIndex--;
            }
        }
        return leftIndex;
    }

    private static void swap(int[] data, int leftIndex, int rightIndex) {
        int buffer = data[leftIndex];
        data[leftIndex] = data[rightIndex];
        data[rightIndex] = buffer;
    }

    private static int[] readNumbers(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
