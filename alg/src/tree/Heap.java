package tree;

import java.io.*;

public class Heap {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int count = Integer.parseInt(reader.readLine());
            Integer[] heap = new Integer[count + 1];
            int heapSize = 0;

            for (int i = 0; i < count; i++) {
                String command = reader.readLine();
                if (command.startsWith("0")) {
                    heapSize = siftUp(heap, Integer.parseInt(command.substring(2)), heapSize);
                } else {
                    writer.write(String.valueOf(heap[1]));
                    writer.write("\n");
                    heapSize = siftDown(heap, heapSize);
                }
            }
        }
    }

    private static int siftDown(Integer[] heap, int heapSize) {

        if (heapSize > 1) {
            heap[1] = heap[heapSize];
            heap[heapSize] = null;

            int current = heap[1];
            int index = 1;
            int leftIndex = index * 2;
            int rightIndex = index * 2 + 1;

            while (true) {
                if (rightIndex < heap.length && heap[leftIndex] != null && heap[rightIndex] != null) {
                    int intMax = Math.max(heap[leftIndex], heap[rightIndex]);
                    if (current < intMax) {
                        boolean isLeftMax = heap[leftIndex] > heap[rightIndex];
                        if (isLeftMax) {
                            swap(heap, index, leftIndex);
                            index = leftIndex;
                        } else {
                            swap(heap, index, rightIndex);
                            index = rightIndex;
                        }
                        leftIndex = index * 2;
                        rightIndex = index * 2 + 1;
                    } else {
                        break;
                    }
                } else if (leftIndex < heap.length && heap[leftIndex] != null) {
                    if (current < heap[leftIndex]) {
                        swap(heap, index, leftIndex);
                        index = leftIndex;
                        leftIndex = index * 2;
                        rightIndex = index * 2 + 1;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        } else {
            heap[1] = null;
        }

        return --heapSize;
    }

    private static int siftUp(Integer[] heap, int number, int heapSize) {
        int index = heapSize + 1;
        heap[index] = number;

        while (index > 1) {
            int check = heap[index / 2];
            if (number > check) {
                swap(heap, index / 2, index);
                index = index / 2;
            } else {
                break;
            }
        }
        return ++heapSize;
    }

    private static void swap(Integer[] heap, int leftIndex, int rightIndex) {
        int buffer = heap[leftIndex];
        heap[leftIndex] = heap[rightIndex];
        heap[rightIndex] = buffer;
    }
}
