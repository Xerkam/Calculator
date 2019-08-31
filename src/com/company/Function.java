package com.company;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

public class Function {

    Stack<String> operator = new Stack<>();
    LinkedList<String> output = new LinkedList<>();
    private String equation;

    public Function(String equation) {
        this.equation = equation;
    }

    public static void main(String[] args) {

    }

//    Returns the precedence of a certain operation

    private static boolean isOperand(String token) {
        if (token.equals("x"))
            return true;
        try {
            int x = Integer.parseInt(token);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

//    Check to see if a token is an operand

    public int precedance(String x) {
        switch (x) {
            case "^":
                return 3;

            case "*":
            case "/":
                return 2;

            case "+":
            case "-":
                return 1;
        }
        return -1;
    }

//    Check to see if a token is an operator

    private boolean isOperator(String token) {
        switch (token) {
            case "^":
            case "*":
            case "/":
            case "+":
            case "-":
            case "(":
                return true;
        }
        return false;
    }

//    Check to see if a token is a function

    private boolean isFunction(String token) {
        switch (token) {
            case "sin":
            case "sec":
            case "tan":
            case "cos":
            case "csc":
            case "cot":
                return true;
        }
        return false;
    }

    public void infixToPostfix() {

        for (int i = 0; i < equation.length(); i++) {

            int e;
            String element = equation.substring(i, i + 1);

            if (isOperand(element)) {
                e = i + 1;

                //Checks to see if a found operand has more operands after it (A.K.A is this a multidigit number) as long
                // index variable (i) is not looking at final character
                while (e < equation.length() && isOperand(equation.substring(i, e + 1))) {
                    e++;
                }

                output.addLast(equation.substring(i, e));
                i = e - 1;// Set I to be the amount of characters skipped

            } else if (isOperator(element))
                operator.push(element);

                //Checks if equation has a trig function within it, index variable (i) cant be more than equation.length() - 5
                // because there are, at the least, 6 characters that a trig function needs to have. ex: sin(x)

            else if (i < equation.length() - 5 && isFunction(equation.substring(i, i + 3))) {

                operator.push(equation.substring(i, i + 3));
                i += 2; // Skip next to characters of trig function
            }
        }


    }
}