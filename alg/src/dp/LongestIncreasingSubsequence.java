package dp;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LongestIncreasingSubsequence {

// Наибольшая возрастающая подпоследовательность (НВП)

// 12
// 5 6 2 3 7 8 10 1 4 11 5 6

// 6: 5,6,7,8,10,11  or  2,3,7,8,10,11

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int n = Integer.parseInt(reader.readLine());
            int[] data = readNumbersToArray(reader);

            List<Integer> result = findLongestIncreasingSubsequence(data);
            //List<Integer> result = findMaxIncreasingSubsequence(data);

            writer.write(String.valueOf(result.size()));
            writer.write("\n");

            for (int i = result.size() - 1; i >= 0; i--) {
                writer.write(String.valueOf(result.get(i)));
                writer.write(" ");
            }
        }
    }

    private static List<Integer> findLongestIncreasingSubsequence(int[] data) {

        List<Integer> result = new ArrayList<>();
        int[] array = new int[data.length];
        Arrays.fill(array, 1);
        int index = 0;

        // 5 6 2 3 7 8 10 1 4 11 5 6
        // 1 2 1 2 3 4 5  1 3  6 4 5
        for (int i = 1; i < data.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (data[i] > data[j] && array[i] <= array[j]) {
                    array[i] = array[j] + 1;
                    if (array[i] > array[index]) index = i;
                }
            }
        }

        int lis = array[index];
        result.add(data[index]);

        for (int i = index - 1; i >= 0; i--) {
            if (lis > 1 && array[i] == lis - 1) {
                result.add(data[i]);
                lis--;
            }
        }

        return result;
    }

    // O n*(log n)
    private static List<Integer> findMaxIncreasingSubsequence(int[] data) {

        // tailsIndexes[i] хранит ИНДЕКС элемента из data, который является
        // наименьшим хвостом подпоследовательности длины i + 1
        int[] tailsIndexes = new int[data.length];
        // prevIndexes[i] хранит ИНДЕКС предшественника элемента data[i]
        // в самой длинной возрастающей подпоследовательности, заканчивающейся на i
        int[] prevIndexes = new int[data.length];
        int length = 0; // Текущая длина наибольшей подпоследовательности

        // 5 6 2 3 7 8 10 1 4 11 5 6
        for (int i = 0; i < data.length; i++) {
            int left = 0;
            int right = length;

            // Бинарный поиск (lower bound): ищем первое число >= data[i]
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (data[tailsIndexes[mid]] < data[i]) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            // Если элемент встает не на первое место, значит у него есть предшественник
            if (left > 0) {
                prevIndexes[i] = tailsIndexes[left - 1];
            } else {
                prevIndexes[i] = -1; // Элемент начинает новую цепочку
            }

            // Обновляем хвост для найденной длины
            tailsIndexes[left] = i;

            // Если элемент добавился в конец, увеличиваем максимальную длину
            if (left == length) {
                length++;
            }
        }

        // Восстановление ответа с конца
        List<Integer> result = new ArrayList<>(length);
        int currIndex = tailsIndexes[length - 1]; // Индекс последнего элемента LIS
        while (currIndex != -1) {
            result.add(data[currIndex]);
            currIndex = prevIndexes[currIndex]; // Переходим к предшественнику
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
