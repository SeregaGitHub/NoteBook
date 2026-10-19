package sum;

import java.io.*;
import java.util.*;

public class FourSum {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

/*
10
6
4 8 1 1 5 5 16
*/

/*
private static List<List<Integer>> getTwoNumbers(int[] data, long target,
                                                 int skipA, int skipB) {
    List<List<Integer>> twoNumbers = new ArrayList<>();
    int left = 0, right = data.length - 1;

    while (left < right) {
        if (left == skipA || left == skipB) { left++; continue; }
        if (right == skipA || right == skipB) { right--; continue; }

        long check = (long) data[left] + data[right];

        if (check < target) {
            left++;
        } else if (check > target) {
            right--;
        } else {
            List<Integer> list = new ArrayList<>();
            list.add(data[left]);
            list.add(data[right]);
            twoNumbers.add(list);
            left++;
            right--;
        }
    }
    return twoNumbers;
}

private static Set<List<Integer>> getThreeNumbers(int[] data, long target, int skipA) {
    Set<List<Integer>> threeNumbers = new HashSet<>();

    for (int j = 0; j < data.length; j++) {
        if (j == skipA) continue;
        List<List<Integer>> pairs = getTwoNumbers(data, target - data[j], skipA, j);
        for (List<Integer> pair : pairs) {
            pair.add(data[j]);
            Collections.sort(pair);
            threeNumbers.add(pair);
        }
    }
    return threeNumbers;
}

private static Set<List<Integer>> getResult(int[] data, int sum) {
    Set<List<Integer>> result = new HashSet<>();
    Arrays.sort(data);

    for (int i = 0; i < data.length; i++) {
        Set<List<Integer>> triples = getThreeNumbers(data, (long) sum - data[i], i);
        for (List<Integer> triple : triples) {
            triple.add(data[i]);
            Collections.sort(triple);
            result.add(triple);
        }
    }
    return result;
}
 */


            int sum = Integer.parseInt(reader.readLine());
            int count = Integer.parseInt(reader.readLine());
            int[] data = readNumbersToArray(reader);

            Set<List<Integer>> result = getResult(data, sum);

            if (!result.isEmpty()) {
                for (List<Integer> list : result) {
                    writer.write(String.valueOf(list.getFirst()));
                    writer.write(" ");
                    writer.write(String.valueOf(list.get(1)));
                    writer.write(" ");
                    writer.write(String.valueOf(list.getLast()));
                    writer.write("\n");
                }
            } else {
                writer.write("NO");
            }
        }
    }

    private static List<List<Integer>> getTwoNumbers(int[] data, int target, int notCheckIndex) {

        List<List<Integer>> twoNumbers = new ArrayList<>();

        int leftIndex = 0;
        int rightIndex = data.length - 1;

        while (leftIndex < rightIndex) {

            if (leftIndex == notCheckIndex) {
                leftIndex++;
                continue;
            }
            if (rightIndex == notCheckIndex) {
                rightIndex--;
                continue;
            }

            int check = data[leftIndex] + data[rightIndex];

            if (check < target) {
                leftIndex++;
            } else if (check > target) {
                rightIndex--;
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(data[leftIndex]);
                list.add(data[rightIndex]);
                twoNumbers.add(list);
                leftIndex++;
                rightIndex--;
            }
        }
        return twoNumbers;
    }

    private static Set<List<Integer>> getResult(int[] data, int sum) {

        Set<List<Integer>> result = new HashSet<>();
        Arrays.sort(data);

        for (int i = 0; i < data.length; i++) {
            List<List<Integer>> twoNumbers = getTwoNumbers(data, sum - data[i], i);
            if (!twoNumbers.isEmpty()) {
                for (List<Integer> list : twoNumbers) {
                    list.add(data[i]);
                    Collections.sort(list);
                    result.add(list);
                }
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

