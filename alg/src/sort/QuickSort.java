package sort;

import java.io.*;
import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int count = Integer.parseInt(reader.readLine());
            int[] data = readNumbers(reader);

            if (count == 0) {
                writer.write("");
            } else {
                quickSort(data, 0, data.length - 1);

                for (int i: data) {
                    writer.write(String.valueOf(i));
                    writer.write(" ");
                }
            }
        }
    }

    // Схема Хоара - OK !!!
    private static void quickSort(int[] data, int leftIndex, int rightIndex) {
        if (rightIndex - leftIndex < 8) {
            boolean isNeedSort = true;
            while (isNeedSort) {
                isNeedSort = false;
                for (int i = leftIndex; i < rightIndex; i++) {
                    if (data[i] > data[i + 1]) {
                        swap(data, i, i + 1);
                        isNeedSort = true;
                    }
                }
            }
        } else {
            int pivotIndex = partition(data, leftIndex, rightIndex);
            if (leftIndex < pivotIndex - 1) {
                quickSort(data, leftIndex, pivotIndex - 1);
            }
            if (pivotIndex < rightIndex) {
                quickSort(data, pivotIndex, rightIndex);
            }
        }
    }

    private static int partition(int[] data, int leftIndex, int rightIndex) {
        int middle = data[(leftIndex + rightIndex) / 2];
        int pivot = Math.max(Math.min(data[leftIndex], middle), Math.min(Math.max(data[leftIndex], middle), data[rightIndex]));
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

    // Схема Ломуто - BAD !!! (на массивах с большим числом одинаковых элементов требуется трёхпутевое разбиение)
    /*private static void quickSort(int[] data, int leftIndex, int rightIndex) {
        if (rightIndex - leftIndex < 8) {
            boolean isNeedSort = true;
            while (isNeedSort) {
                isNeedSort = false;
                for (int i = leftIndex; i < rightIndex; i++) {
                    if (data[i] > data[i + 1]) {
                        swap(data, i, i + 1);
                        isNeedSort = true;
                    }
                }
            }
        } else {
            int pivotIndex = partition(data, leftIndex, rightIndex);
            if (leftIndex < pivotIndex - 1) {
                quickSort(data, leftIndex, pivotIndex - 1);
            }
            if (pivotIndex + 1 < rightIndex) {
                quickSort(data, pivotIndex + 1, rightIndex);
            }
        }
    }

    private static int partition(int[] data, int leftIndex, int rightIndex) {
        int middle = data[(leftIndex + rightIndex) / 2];
        int pivot = Math.max(Math.min(data[leftIndex], middle), Math.min(Math.max(data[leftIndex], middle), data[rightIndex]));
        int l = leftIndex;

        if (pivot != data[rightIndex]) {
            swap(data, rightIndex, pivot == data[leftIndex] ? leftIndex : (leftIndex + rightIndex) / 2);
        }

        for (int r = leftIndex; r < rightIndex; r++) {
            if (data[r] < pivot) {
                swap(data, l, r);
                l++;
            }
        }

        swap(data, l, rightIndex);

        return l;
    }*/

    private static void swap(int[] data, int l, int r) {
        int buffer = data[l];
        data[l] = data[r];
        data[r] = buffer;
    }

    private static int[] readNumbers(BufferedReader reader) throws IOException {
        String str = reader.readLine();
        if (str == null || str.isBlank()) {
            return new int[0];
        }
        return Arrays.stream(str.split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}

