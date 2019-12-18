package com.company;

import java.util.*;

public class DerivationEngine {
    private LinkedList<String> input;
    private ArrayList<String> output = new ArrayList<>();
    int upper;

    public DerivationEngine(Function func) {
        input = func.rpn();
        upper = input.size() - 1;
        derive();
        Collections.reverse(output);

    }

    private boolean isConstant(String value){
        System.out.println(value);
        try
        {
            Double.parseDouble(value);
        }
        catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    public void derive() {
        String value = input.get(upper);

        if(isConstant(value)) {
            output.add("0");
            upper--;
        }

        if(value.equals("x")) {
            output.add("1");
            upper--;
        }

        if(value.equals("+")) {
            output.add("+");
            upper--;
            derive();
            derive();

        }

        if(value.equals("-")) {
            output.add("-");
            upper--;
            derive();
            derive();

        }

        if(value.equals("*")) {
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

        if(value.equals("^")) {
//            (K - 1)*(f'x)*(f(x))^K
            int temp;
            output.add("*");
            upper--;
            temp = upper;
            copy();
            output.add("*");
            output.add("^");
            output.add("-");
            output.add("1");
            upper = temp;
            copy();
            temp = upper;
            copy();
            upper = temp;
            derive();
        }

        if(value.equals("/")) {
            int temp;
            output.add("/");
            upper--;
            output.add("^");
            output.add("2");
            temp = upper;
            copy();

            output.add("-");
            output.add("*");
            copy();
            upper = temp;
            derive();
            output.add("*");
            upper = temp;
            copy();
            derive();

        }

    }

    private void copy() {
        String value = input.get(upper);

        if(isConstant(value)) {
            output.add(value);
            upper--;
        }

        if(value.equals("x")) {
            output.add("x");
            upper--;
        }

        if(value.equals("+")) {
            output.add("+");
            upper--;
            copy();
            copy();

        }

        if(value.equals("-")) {
            output.add("-");
            upper--;
            copy();
            copy();

        }
    }



    @Override
    public String toString() {
        return Arrays.toString(output.toArray());
    }
}

