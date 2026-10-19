package sort;

import java.io.*;
import java.util.Arrays;

public class HeapSort {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int count = Integer.parseInt(reader.readLine());
            if (count <= 0) {
                writer.write("");
            } else {
                int[] data = readNumbers(reader);
                heapSort(data);
                for (int i: data) {
                    writer.write(String.valueOf(i));
                    writer.write(" ");
                }
            }
        }
    }

    private static void heapSort(int[] data) {
        int[] heap = new int[data.length + 1];
        int heapSize = 0;

        for (int i : data) {
            heapSize = up(heap, heapSize, i);
        }

        for (int i = 1; i < heap.length; i++) {
            int number = heap[1];
            data[i - 1] = number;
            heapSize = down(heap, heapSize);
        }
    }

    private static int down(int[] heap, int heapSize) {

        heap[1] = heap[heapSize];
        heap[heapSize] = 0;
        heapSize = heapSize - 1;

        int index = 1;
        int leftIndex = index * 2;
        int rightIndex = index * 2 + 1;

        while (true) {
            if (rightIndex <= heapSize && heap[index] > Math.min(heap[leftIndex], heap[rightIndex])) {
                int minIndex;
                if (heap[leftIndex] < heap[rightIndex]) {
                    minIndex = leftIndex;
                } else {
                    minIndex = rightIndex;
                }
                swap(heap, index, minIndex);
                index = minIndex;
                leftIndex = index * 2;
                rightIndex = index * 2 + 1;
            } else if (leftIndex <= heapSize && heap[index] > heap[leftIndex]) {
                swap(heap, index, leftIndex);
                break;
            } else {
                break;
            }
        }

        return heapSize;
    }

    private static int up(int[] heap, int heapSize, int number) {

        int index = heapSize + 1;
        int parentIndex = index / 2;
        heap[index] = number;

        while (parentIndex > 0) {
            if (heap[index] < heap[parentIndex]) {
                swap(heap, index, parentIndex);
                index = parentIndex;
                parentIndex = parentIndex / 2;
            } else {
                break;
            }
        }

        return ++heapSize;
    }

    private static void swap(int[] heap, int firstIndex, int secondIndex) {
        int buffer = heap[firstIndex];
        heap[firstIndex] = heap[secondIndex];
        heap[secondIndex] = buffer;
    }

    private static int[] readNumbers(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
