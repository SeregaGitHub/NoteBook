package stack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ArithmeticExpression {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            // -6 + 2 * ((21 - 1) * 2 - 20) / 4   // 4

            String data = reader.readLine();
            List<String> postfix = postfixNotation(data);

            //List<String> postfix = List.of("-6", "2", "21", "1", "-", "2", "*", "20", "-", "*", "4", "/", "+");

            int result = getResult(postfix);
            writer.write(String.valueOf(result));
        }
    }

    private static List<String> postfixNotation(String data) {

        List<String> postfix = new ArrayList<>();



        return postfix;
    }

    private static int getResult(List<String> data) {
        Stack<Integer> stack = new Stack<>();
        for (String s: data) {
            int currentInt;
            Integer firstInt;
            Integer secondInt;

            try {
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
                        currentInt = firstInt / secondInt;
                        stack.push(currentInt);
                    }
                }
            }
        }
        return stack.pop();
    }
}
