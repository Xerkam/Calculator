package com.company;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Stack;

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
            double x = Double.parseDouble(token);
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

                //Checks to see if a found operands has more digits after it or a decimal point (indicating a larger/longer
                //number

                while (e < equation.length() && (isOperand(equation.substring(e, e + 1)) || equation.substring(e, e + 1).equals("."))) {
                    e++;
                }

                output.addLast(equation.substring(i, e));
                i = e - 1;// Set I to be the amount of characters skipped

            }

            //Checks if equation has a trig function within it, index variable (i) cant be more than equation.length() - 5
            // because there are, at the least, 6 characters that a trig function needs to have. ex: sin(x)

            else if (i < equation.length() - 5 && isFunction(equation.substring(i, i + 3))) {

                operator.push(equation.substring(i, i + 3));
                i += 2; // Skip next two characters of trig function
            }

            else if (isOperator(element)) { //If element is an operator
                while (!operator.isEmpty()
                        && (isFunction(operator.peek()) //If the top of operator stack is a function
                        || (precedance(operator.peek()) > precedance(element)) //or precedence of top operator is more than current
                        || (precedance(operator.peek()) == precedance(element) && precedance(operator.peek()) != 3)) //Or precedence is equal and not ^
                        && !operator.peek().equals("(")) { // And operator is not "("

                    output.addLast(operator.pop()); //Add operators to output

                }

                operator.push(element);

            }

            else if (element.equals("(")) {
                operator.push(element);
            }

            else if (element.equals(")")) {
                try {

                    while (!operator.peek().equals("("))
                        output.addLast(operator.pop()); //Add operators to output

                    if (operator.peek().equals("("))
                        operator.pop();

                } catch (EmptyStackException e1) {
                    System.out.println("Error: Mismatched Parentheses");
                }
            }
        }

        while (!operator.isEmpty()) //If there are operators left over after tokens have been looked through
            output.addLast(operator.pop()); //Add them all into output queue

    }


}