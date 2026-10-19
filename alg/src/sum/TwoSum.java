package sum;

import java.io.*;
import java.util.*;

public class TwoSum {
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

        Set<Integer> set = new HashSet<>();
        set.add(data[0]);

        for (int i = 1; i < data.length; i++) {
            int checkInt = sum - data[i];
            if (set.contains(checkInt)) {
                result.add(checkInt);
                result.add(data[i]);
                break;
            }
            set.add(data[i]);
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
