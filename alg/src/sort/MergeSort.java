package sort;

import java.io.*;
import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int count = Integer.parseInt(reader.readLine());
            int[] data = readNumbersToArray(reader);

            data = sort(data);
            for (int i: data) {
                writer.write(String.valueOf(i));
                writer.write(" ");
            }
        }
    }

    private static int[] sort(int[] data) {
        if (data.length <= 1) return data;

        int middle = data.length / 2;

        int[] leftArray = new int[middle];
        int[] rightArray = new int[data.length - middle];
        System.arraycopy(data, 0, leftArray, 0, middle);
        System.arraycopy(data, middle, rightArray, 0, data.length - middle);

        int[] left = sort(leftArray);
        int[] right = sort(rightArray);

        int[] mergeArray = new int[left.length + right.length];
        mergeArrays(mergeArray, left, right);

        return mergeArray;
    }

    private static void mergeArrays(int[] result, int[] firstArray, int[] secondArray) {
        int firstIndex = 0;
        int secondIndex = 0;
        int resultIndex = 0;
        while (resultIndex < result.length) {
            if (firstIndex == firstArray.length) {
                System.arraycopy(
                        secondArray, secondIndex, result, resultIndex, secondArray.length - secondIndex
                );
                break;
            } else if (secondIndex == secondArray.length) {
                System.arraycopy(
                        firstArray, firstIndex, result, resultIndex, firstArray.length - firstIndex
                );
                break;
            } else {
                int f = firstArray[firstIndex];
                int s = secondArray[secondIndex];
                if (f < s) {
                    result[resultIndex] = f;
                    firstIndex++;
                } else {
                    result[resultIndex] = s;
                    secondIndex++;
                }
                resultIndex++;
            }
        }
    }

    private static int[] readNumbersToArray(BufferedReader reader) throws IOException {
        String str = reader.readLine();
        if (str == null || str.isBlank()) {
            return new int[0];
        }
        return Arrays.stream(str.split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
