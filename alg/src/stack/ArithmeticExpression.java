package stack;

import java.io.*;
import java.util.*;

public class ArithmeticExpression {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            String data = reader.readLine();

            String postfix = postfixNotation(data);

            boolean isTrue;
            if (postfix.equals("WRONG")) {
                writer.write(postfix);
            } else {
                Deque<Integer> stack = new ArrayDeque<>();
                isTrue = getResult(List.of(postfix.split(" ")), stack);
                writer.write(isTrue && stack.size() == 1 ? String.valueOf(stack.pop()) : "WRONG");
            }
        }
    }

    private static String postfixNotation(String data) {

        if (data == null || data.isBlank()) return "WRONG";

        Set<Character> set = Set.of('+', '-', '*', '/', ' ', '(', ')');
        Deque<Character> stack = new ArrayDeque<>();
        StringBuilder builder = new StringBuilder();

        boolean isUnaryOperator = true;
        boolean expectedOperator = false;

        for (int i = 0; i < data.length(); i++) {
            char checkChar = data.charAt(i);
            if (!Character.isDigit(checkChar) && !set.contains(checkChar)) return "WRONG";

            if (Character.isDigit(checkChar)) {

                //if (!isUnaryOperator) return "WRONG";
                //
                if (expectedOperator) return "WRONG";
                builder.append(checkChar);

                if (i == data.length() - 1 || (i < data.length() - 1 && !Character.isDigit(data.charAt(i + 1)))) {
                    builder.append(' ');
                    isUnaryOperator = false;
                    if (!stack.isEmpty() && stack.peek() == '~') {
                        builder.append('-').append(' ');
                        stack.pop();
                    }
                    //
                    expectedOperator = true;
                }
            } else if (checkChar == '(') {
                //
                if (expectedOperator) return "WRONG";
                stack.push(checkChar);
                isUnaryOperator = true;
                //
                //expectedOperator = false;
            } else if (checkChar == ')') {
                if (stack.isEmpty()) return "WRONG";
                while (true) {
                    char cur = stack.pop();
                    if (cur != '(') {
                        builder.append(cur).append(' ');
                    } else {
                        break;
                    }
                    if (stack.isEmpty()) return "WRONG";
                }
                if (!stack.isEmpty() && stack.peek() == '~') {
                    builder.append('-').append(' ');
                    stack.pop();
                }
                isUnaryOperator = false;
                //
                expectedOperator = true;
            } else if (checkChar == '*' || checkChar == '/') {
                //
                if (!expectedOperator) return "WRONG";
                if (!stack.isEmpty() && (stack.peek() == '*' || stack.peek() == '/')) {
                    changeStackHead(stack, builder, checkChar);
                } else {
                    stack.push(checkChar);
                }
                isUnaryOperator = true;
                //
                expectedOperator = false;

            } else if (checkChar == '+' || checkChar == '-') {
                //
                if (!expectedOperator) return "WRONG";
                if (!isUnaryOperator) {
                    if (stack.isEmpty() || stack.peek() == '(') {
                        stack.push(checkChar);
                    } else {
                        changeStackHead(stack, builder, checkChar);
                    }
                    isUnaryOperator = true;
                } else {
                    if (checkChar == '+') {
                        continue;
                    } else {
                        builder.append('0').append(' ');
                        stack.push('~');
                        isUnaryOperator = false;
                    }
                }
                //
                expectedOperator = false;
            }
        }

        while (!stack.isEmpty()) {
            char cur = stack.pop();
            if (cur == '(') return "WRONG";
            if (cur == '~') cur = '-';
            builder.append(cur).append(' ');
        }

        return builder.isEmpty() ? "WRONG" : builder.deleteCharAt(builder.length() - 1).toString();
    }

    private static void changeStackHead(Deque<Character> stack, StringBuilder builder, char checkChar) {
        char cur = stack.pop();
        if (cur == '~') cur = '-';
        builder.append(cur).append(' ');
        stack.push(checkChar);
    }

    private static boolean getResult(List<String> data, Deque<Integer> stack) {

        for (String s : data) {
            int currentInt;
            Integer firstInt;
            Integer secondInt;

            try {
                currentInt = Integer.parseInt(s);
                stack.push(currentInt);
            } catch (Exception e) {
                if (stack.size() < 2) return false;
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
                    case "/" -> {
                        if (secondInt != 0) {
                            currentInt = firstInt / secondInt;
                            stack.push(currentInt);
                        } else {
                            return false;
                        }
                    }
                    default -> {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
