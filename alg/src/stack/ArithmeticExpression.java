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

        Set<Character> set = Set.of('+', '-', '*', '/', '(', ')');
        Deque<Character> stack = new ArrayDeque<>();
        StringBuilder builder = new StringBuilder();

        boolean expectedOperand = true;

        for (int i = 0; i < data.length(); i++) {
            char checkChar = data.charAt(i);

            if (checkChar == ' ') continue;
            if (!Character.isDigit(checkChar) && !set.contains(checkChar)) return "WRONG";

            if (Character.isDigit(checkChar)) {
                if (!expectedOperand) return "WRONG";
                int start = i;
                while (i < data.length() && Character.isDigit(data.charAt(i))) {
                    i++;
                }
                builder.append(data, start, i).append(' ');
                i--;
                expectedOperand = false;
            } else if (checkChar == '(') {
                if (!expectedOperand) return "WRONG";
                stack.push(checkChar);
            } else if (checkChar == ')') {
                if (expectedOperand) return "WRONG";
                while (!stack.isEmpty() && stack.peek() != '(') {
                    char cur = stack.pop();
                    if (cur == '~') cur = '-';
                    builder.append(cur).append(' ');
                }
                if (stack.isEmpty()) return "WRONG";
                stack.pop();
            } else if (checkChar == '+' || checkChar == '-') {
                if (expectedOperand) {
                    if (checkChar == '-') {
                        builder.append('0').append(' ');
                        stack.push('~');
                    }
                } else {
                    while (!stack.isEmpty() && stack.peek() != '(') {
                        char cur = stack.pop();
                        if (cur == '~') cur = '-';
                        builder.append(cur).append(' ');
                    }
                    stack.push(checkChar);
                    expectedOperand = true;
                }
            } else if (checkChar == '*' || checkChar == '/') {
                if (expectedOperand) return "WRONG";
                while (!stack.isEmpty() && stack.peek() != '(' && stack.peek() != '+' && stack.peek() != '-') {
                    char cur = stack.pop();
                    if (cur == '~') cur = '-';
                    builder.append(cur).append(' ');
                }
                stack.push(checkChar);
                expectedOperand = true;
            }
        }

        if (expectedOperand) return "WRONG";

        while (!stack.isEmpty()) {
            char cur = stack.pop();
            if (cur == '(') return "WRONG";
            if (cur == '~') cur = '-';
            builder.append(cur).append(' ');
        }

        return builder.isEmpty() ? "WRONG" : builder.deleteCharAt(builder.length() - 1).toString();
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
