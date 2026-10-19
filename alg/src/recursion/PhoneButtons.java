package recursion;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneButtons {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            Map<Character, List<Character>> dict = new HashMap<>();
            dict.put('2', List.of('a', 'b', 'c'));
            dict.put('3', List.of('d', 'e', 'f'));
            dict.put('4', List.of('g', 'h', 'i'));
            dict.put('5', List.of('j', 'k', 'l'));
            dict.put('6', List.of('m', 'n', 'o'));
            dict.put('7', List.of('p', 'q', 'r', 's'));
            dict.put('8', List.of('t', 'u', 'v'));
            dict.put('9', List.of('w', 'x', 'y', 'z'));

            StringBuilder result = new StringBuilder();

            String str = reader.readLine();
            printResult(writer, dict, result, str, 0);
        }
    }

    private static void printResult(
            BufferedWriter writer, Map<Character, List<Character>> dict, StringBuilder result, String str, int depth
    ) throws IOException {

        if (depth == str.length()) {
            writer.write(result.toString());
            writer.write(" ");
        } else {
            List<Character> list = dict.get(str.charAt(depth));
            for (char c : list) {
                result.append(c);
                printResult(writer, dict, result, str, depth + 1);
                result.deleteCharAt(result.length() - 1);
            }
        }
    }
}
