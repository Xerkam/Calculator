package com.company;

import java.math.BigDecimal;
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

    private static boolean isOperator(String token) {
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

    private static boolean isFunction(String token) {
        switch (token) {
            case "sin":
            case "sec":
            case "tan":
            case "cos":
            case "csc":
            case "cot":
            case "log":
            case "ln":
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

            else if ((i < equation.length() - 5 && isFunction(equation.substring(i, i + 3)))) {

                operator.push(equation.substring(i, i + 3));
                i += 2; // Skip next two characters of trig function
            }

            //Seperate case for ln as it is a two digit function

            else if(i < equation.length() - 4 && isFunction(equation.substring(i, i + 2))){
                operator.push(equation.substring(i, i + 2));
                i += 1;
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
                if(output.get(i).equals("log")) {//Log has two arguments unlike other functions, so requires diff calculate()
                    double y = answer.pop();
                    double x = answer.pop();
                    answer.push(Function.calculate(x, y, output.get(i)));
                }
                else {
                    double x = answer.pop();
                    answer.push(Function.calculate(x, output.get(i)));
                }
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

            case "log":
                return (Math.log10(y)) / (Math.log10(x));
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

            case "ln":
                return Math.log(x);
        }
        return 0;
    }

//    Finds numerical derivative of a function at a point

    public double derivative(double value) {
        double h = .001;
        double val = (evaluate(value + h) - evaluate(value - h)) / (2*h);

        return Math.round(val*100000000d) / 100000000d; //rounds to seven decimal places
    }

    private double unRoundedDerivative(double value) {
        double h = .001;
        return (evaluate(value + h) - evaluate(value - h)) / (2*h);
    }

    public double secondDerivative(double value) {
        double h = .001;

        double val = (unRoundedDerivative(value + h) - unRoundedDerivative(value - h)) / (2*h);
        return Math.round(val*100000000d) / 100000000d;
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

    public boolean isRemovableDiscontinuity(double value) {
        return Double.isNaN(evaluate(value));
    }

//    identifies x value of root within given bounds if it exists

//    public Double newtonsMethod(double xZero) {
//        int maxIterations = 20;
//        double tolerance = 0;
//        double xOne = 0;
//        boolean foundSolution = false;
//        double y;
//        double yPrime;
//
//        for (int i = 0; i < maxIterations; i++) {
//            y = evaluate(xZero);
//            yPrime = derivative(xZero);
//            if(Math.abs(yPrime) < Math.pow(10,-14))
//                break;
//
//            xOne = xZero - y/yPrime;
//
//            if(Math.abs(xOne - xZero) == 0)
//                foundSolution = true;
//
//            xZero = xOne;
//        }
//
//        if(foundSolution)
//            return xOne;
//
//        return null;
//    }

    public Double bisectionMethod(double a, double b) {
        double tolerance = 0.01;
        if (evaluate(a) * evaluate(b) >= 0) {
            return null;
        }

        double c = a;
        while((b - a) >= tolerance) {
            c = (a + b) / 2;

            if(evaluate(c) == 0.0)
                break;

            else if (evaluate(c) * evaluate(a) < 0) {
                b = c;
            }
            else
                a = c;
        }
        return c;
    }

    public Double bisectionMethodDerivative(double a, double b) {
        double tolerance = 0.01;
        if (derivative(a) * derivative(b) >= 0) {
            return null;
        }

        double c = a;
        while((b - a) >= tolerance) {
            c = (a + b) / 2;

            if(derivative(c) == 0.0)
                break;

            else if (derivative(c) * derivative(a) < 0) {
                b = c;
            }
            else
                a = c;
        }
        return c;
    }

    public Double bisectionMethodSecondDerivative(double a, double b) {
        double tolerance = 0.01;
        if (secondDerivative(a) * secondDerivative(b) >= 0) {
            return null;
        }

        double c = a;
        while((b - a) >= tolerance) {
            c = (a + b) / 2;

            if(secondDerivative(c) == 0.0)
                break;

            else if (secondDerivative(c) * secondDerivative(a) < 0) {
                b = c;
            }
            else
                a = c;
        }
        return c;
    }

    public String toString(){
        return equation;
    }
}

