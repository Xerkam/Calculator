package com.company;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Stack;

public class Function {

    private Stack<String> operator = new Stack<>();
    private LinkedList<String> output = new LinkedList<>();
    private String equation;

    public Function(String equation) {
        this.equation = equation;
        infixToPostfix();
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

    private int precedence(String x) {
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

    private void infixToPostfix() {

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
                        || (precedence(operator.peek()) > precedence(element)) //or precedence of top operator is more than current
                        || (precedence(operator.peek()) == precedence(element) && precedence(operator.peek()) != 3)) //Or precedence is equal and not ^
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

//    solves equation for a given x value

    public double evaluate(double value) {
       Stack<Double> answer = new Stack<>();

        for (int i = 0; i < output.size(); i++) {

            if(isOperator(output.get(i))) {
                double y = answer.pop();
                double x = answer.pop();
                answer.push(Function.calculate(x, y, output.get(i)));
            }

            if(isFunction(output.get(i))) {
                double x = answer.pop();
                answer.push(Function.calculate(x, output.get(i)));
            }

            else if(isOperand(output.get(i)))
                if(output.get(i).equals("x")) {
                    answer.push(value);
                }
                else {
                    answer.push(Double.parseDouble(output.get(i)));
                }
        }
        return answer.pop();
    }

//    Used when the token given is an operator

    private static double calculate(Double x, Double y, String token){

        switch (token) {
            case "+":
                return x + y;

            case "-":
                return x - y;

            case "/":
                return x / y;

            case "*":
                return x * y;

            case "^":
                return Math.pow(x, y);
        }

        return 0;
    }

//    Used when the token given is a function

    private static double calculate(Double x, String token){

        switch (token) {
            case "sin":
                return Math.sin(x);

            case "sec":
                return 1 / (Math.cos(x));

            case "tan":
                return Math.tan(x);

            case "cos":
                return Math.cos(x);

            case "csc":
                return 1 / (Math.sin(x));

            case "cot":
                return 1 / (Math.tan(x));
        }
        return 0;
    }

//    Finds numerical derivative of a function at a point

    public double derivative(double value) {
        //Using limit definition of a derivative
        return Math.round((evaluate(value +.00008) - evaluate(value)) / (.00008));
    }

//    Finds numerical integral within a bounded area.

    public double integral(double boundA, double boundB) {
        double width = (boundB - boundA) / (200 * (int)((boundB-boundA)/2)); //Find width of each rectangle in sum
        double sum = 0;

        for (int i = 0; i < 200 * (int)((boundB-boundA)/2); i++) {
            sum += width * evaluate((width*i + width/2));
        }

        return sum;
    }
}

