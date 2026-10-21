package stack;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class PostfixNotation {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            // 8 9 + 1 7 - *
            // -102
            List<String> data = readData(reader);
            int result = getResult(data);
            writer.write(String.valueOf(result));
        }
    }

    private static int getResult(List<String> data) {
        Stack<Integer> stack = new Stack<>();
        for (String s: data) {
            int currentInt;
            Integer firstInt;
            Integer secondInt;

            if (Character.isDigit(s.charAt(0)) || (s.length() > 1 && Character.isDigit(s.charAt(1)))) {
                currentInt = Integer.parseInt(s);
                stack.push(currentInt);
            } else {
                secondInt = stack.pop();
                firstInt = stack.pop();
                switch (s) {
                    case "+" -> {
                        currentInt = firstInt + secondInt;
                        stack.push(currentInt);
                    }
                    case "-" -> {
                        currentInt = firstInt - secondInt;
                        stack.push(currentInt);
                    }
                    case "*" -> {
                        currentInt = firstInt * secondInt;
                        stack.push(currentInt);
                    }
                    default -> {
                        // It is guaranteed that there is no division by zero (10 / 0)
                        currentInt = firstInt / secondInt;
                        stack.push(currentInt);
                    }
                }
            }

            /*try {
                currentInt = Integer.parseInt(s);
                stack.push(currentInt);
            } catch (Exception e) {
                secondInt = stack.pop();
                firstInt = stack.pop();
                switch (s) {
                    case "+" -> {
                        currentInt = firstInt + secondInt;
                        stack.push(currentInt);
                    }
                    case "-" -> {
                        currentInt = firstInt - secondInt;
                        stack.push(currentInt);
                    }
                    case "*" -> {
                        currentInt = firstInt * secondInt;
                        stack.push(currentInt);
                    }
                    default -> {
                        // It is guaranteed that there is no division by zero (10 / 0)
                        currentInt = firstInt / secondInt;
                        stack.push(currentInt);
                    }
                }
            }*/
        }
        return stack.pop();
    }

    private static List<String> readData(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().split("\\s+"))
                .toList();
    }
}
