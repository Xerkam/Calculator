package com.company;

import javafx.scene.Parent;

import java.util.*;

public class DerivationEngine {
    private LinkedList<String> input;
    private ArrayList<String> output = new ArrayList<>();
    int upper;
    String focusedOperator;

    DerivationEngine(Function func) {
        input = func.rpn();
        upper = input.size() - 1;
        derive();
        Collections.reverse(output);

    }

    private boolean isConstant(String value){
        try
        {
            Double.parseDouble(value);
        }
        catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void derive() {
        String value = input.get(upper);

        if(isConstant(value)) {
            output.add("0");
            upper--;
        }

        else if(value.equals("x")) {
            output.add("1");
            upper--;
        }

        else if(value.equals("+")) {
            output.add("+");
            upper--;
            derive();
            derive();

        }

        else if(value.equals("-")) {
            output.add("-");
            upper--;
            derive();
            derive();

        }

        else if(value.equals("sin")){
            
        }

        else if(value.equals("*")) {
            int temp;
            //f'(x)*g(x) + g'(x)*f(x)
            output.add("+");
            output.add("*");//Add * operator for f'(x)*g(x)
            upper--;
            temp = upper;
            derive();
            copy();
            output.add("*");
            upper = temp;
            copy();
            derive();

        }

        else if(value.equals("^")) {
//          derivative of f(x) =  (K)*(f(x))^(K - 1)*(f'x)
            output.add("*");//Add multiplication sign for (K) * f(x)^(K - 1)
            upper--;
            int exponentPosition = upper;//The current position is the exponent K, save it so you can come back to it
            copy();//grabs the exponent and brings it down for K
            output.add("*");//Adds multiplication sign for f'x*f(x)
            output.add("^");
            output.add("-");
            output.add("1");//add the 1 value for ^(K - 1)
            upper = exponentPosition;//go back to K's position
            copy();//Grab value of K for ^(K - 1)
            int fxPosition = upper;//Saves position of f(x) so we can later use derive(); for f'(x)
            copy();//Adds the f(x) for the base of f(x)^(K - 1)
            upper = fxPosition;
            System.out.println(fxPosition);
            derive();//Adds the f'(x)

        }

        else if(value.equals("/")) {
//          derivative of f(x)/g(x) = (f'(x)*g(x) - g'(x)*f(x))/(g(x))^2
            output.add("/");//first add division sign
            upper--;//move onto g(x) as In RPN equation is structured FX GX /
            output.add("^");
            output.add("2");//Add ^2 to g(x)
            int gxPosition = upper;//Save g(x) position for later
            copy();//copies g(x) into denominator
            output.add("-");//adds - sign in numerator
            output.add("*");//add multiplication sign between g'x and fx
            copy();//Adds a copy of f(x) into output
            upper = gxPosition;//goes back to position of g(x)
            derive();//adds g'(x)
            output.add("*");//add second multiplication sign for gx/f'x
            derive();//gives f'x
            upper = gxPosition;//back to g(x) position
            copy();

        }

    }

    private void copy() {
        String value = input.get(upper);

        if(isConstant(value)) {
            output.add(value);
            upper--;
        }

        else if(value.equals("x")) {
            output.add("x");
            upper--;
        }

        else if(value.equals("+")) {
            output.add("+");
            upper--;
            copy();
            copy();

        }

        else if(value.equals("-")) {
            output.add("-");
            upper--;
            copy();
            copy();

        }

        else if(value.equals("*")) {
            output.add("*");
            upper--;
            copy();
            copy();

        }

        else if(value.equals("/")) {
            output.add("/");
            upper--;
            copy();
            copy();

        }

        else if(value.equals("^")) {
            output.add("^");
            upper--;
            copy();
            copy();

        }

        else
            System.out.println("fail");
    }

    @SuppressWarnings("Duplicates")
    public double evaluate(double value) {
        Stack<Double> answer = new Stack<>();

        for (int i = 0; i < output.size(); i++) {

            if (Function.isOperator(output.get(i))) {
                double y = answer.pop();
                double x = answer.pop();
                answer.push(Function.calculate(x, y, output.get(i)));
            }

            if (Function.isFunction(output.get(i))) {
                if (output.get(i).equals("log")) {//Log has two arguments unlike other functions, so requires diff calculate()
                    double y = answer.pop();
                    double x = answer.pop();
                    answer.push(Function.calculate(x, y, output.get(i)));
                } else {
                    double x = answer.pop();
                    answer.push(Function.calculate(x, output.get(i)));
                }
            } else if (Function.isOperand(output.get(i)))
                if (output.get(i).equals("x")) {
                    answer.push(value);
                } else {
                    answer.push(Double.parseDouble(output.get(i)));
                }
        }
        return answer.pop();
    }



    @Override
    public String toString() {
        return Arrays.toString(output.toArray());
    }
}

