import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Example {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in), 600);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

        }
    }

    private static List<Integer> readNumbers(BufferedReader reader) throws IOException {
        String str = reader.readLine();
        if (str == null || str.isBlank()) {
            return new ArrayList<>();
        }
        return Arrays.stream(str.split("\\s+"))
                //.map(elem -> Integer.parseInt(elem))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private static int[] readNumbersToArray(BufferedReader reader) throws IOException {
        String str = reader.readLine();
        if (str == null || str.isBlank()) return new int[0];
        return Arrays.stream(str.trim().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private static List<String> readStrings(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().split("\\s+"))
                .collect(Collectors.toList());
    }

    private static String[] readStringsToArray(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().split("\\s+"))
                .toArray(String[]::new);
    }

    private static char[] readCharsNoSpaces(BufferedReader reader) throws IOException {
        return reader.readLine().replaceAll("\\s+", "").toCharArray();
    }

    private static List<String> readLines(BufferedReader reader) throws IOException {
        List<String> strings = new ArrayList<>();

        String str;
        while ((str = reader.readLine()) != null) {
            if (str.isEmpty()) break;

            // Speed best when -  String[] array = str.split(" ");
            int firstSpace = str.indexOf(' ');
            int secondSpace = str.indexOf(' ', firstSpace + 1);

            String lastname = str.substring(0, firstSpace);
            String product  = str.substring(firstSpace + 1, secondSpace);
            long value = Long.parseLong(str.substring(secondSpace + 1));

            strings.add(lastname);
            strings.add(product);
            strings.add(str.substring(secondSpace + 1));
        }

        return strings;
    }

    private static List<String> readString(BufferedReader reader) throws IOException {
        List<String> strings = new ArrayList<>();

        String str;
        while ((str = reader.readLine()) != null) {
            if (str.isEmpty()) break;
            strings.add(str);
        }

        return strings;
    }

    private static List<Integer> readList(BufferedReader reader) throws IOException {
        String str = reader.readLine();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char check = str.charAt(i);
            if (check == '-') {
                builder.append(check);
            } else if (Character.isDigit(check)) {
                builder.append(check);
            } else if (i < str.length() - 1 && str.charAt(i) == ' ' && str.charAt(i + 1) != ' ') {
                builder.append(check);
            } else {
                continue;
            }
        }

        String newStr = builder.toString();

        return Arrays.stream(newStr.split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    enum VertexState {
        WHITE,
        GRAY,
        BLACK
    }
}
