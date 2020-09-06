/**
 * This Derivation Engine is a component of the Function Class that implements
 * recursive based symbolic differentiation for the functions.

 **/

// ToDo
//   - While functional, the deriviatives of the Function Objects are not currently simplified, so while the derivative
//   of a function can be printed out, it would be in fairly messy form(ex: 'x 1 +' would become '1 0 +' rather than
//   '1')



package com.company;

import javafx.scene.Parent;

import java.util.*;

public class DerivationEngine {
    private ArrayList<String> input;
    private ArrayList<String> output = new ArrayList<>();
    private int upper;

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


    @SuppressWarnings("Duplicates")
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

        else if(Function.isTrig(value)) {
//          derivative of sin(f(x)) = f'(x) * cos(f(x))
//          derivative of cos(f(x)) = f'(x) * - sin(f(x)))

//          derivative of tan(f(x)) = f'(x) * sec^2(f(x))
//          derivative of cot(f(x)) = f'(x) * - csc^2(f(x)))

//          derivative of sec(f(x)) = f'(x) * sec(f(x)) * tan(f(x))
//          derivative of csc(f(x)) = f'(x) * - csc(f(x))) * cot(f(x))

            switch(value) {
                case "sin": {
                    output.add("*");//Multiplying by f'x
                    output.add("cos");
                    upper--;//go to fx
                    int fxLocation = upper;
                    copy();
                    upper = fxLocation;
                    derive();//get f'x
                    break;
                }

                case "cos": {
                    output.add("*");
                    output.add("-1");//* and -1 make equation negative
                    output.add("*");//Multiplying by f'x
                    output.add("sin");
                    upper--;//go to fx
                    int fxLocation = upper;
                    copy();//add fx
                    upper = fxLocation;
                    derive();//get f'x
                    break;
                }

                case "tan": {
                    output.add("*");//Multiplying by f'x
                    output.add("^");
                    output.add("2");//Add ^2 for sec(x)^2
                    output.add("sec");
                    upper--;//go to fx
                    int fxLocation = upper;//saving fx location for derivative;
                    copy();//add fx
                    upper = fxLocation;
                    derive();//add f'x
                    break;
                }

                case "cot": {
                    output.add("*");
                    output.add("-1");//* and -1 make equation negative
                    output.add("*");//Multiplying by f'x
                    output.add("^");
                    output.add("2");//Add ^2 for sec(x)^2
                    output.add("csc");
                    upper--;//go to fx
                    int fxLocation = upper;//saving fx location for derivative;
                    copy();//add fx
                    upper = fxLocation;
                    derive();//add f'x
                    break;
                }

                case "sec": {
                    output.add("*");//multiplying by f'x
                    int fxPosition = upper - 1;//store position of fx so you can place it in the tan and derive
                    copy();//adds copy of whole equation to derivative (aka sec(x))
                    output.add("*");//multiplying by tan
                    output.add("tan");
                    upper = fxPosition;
                    copy();//add fx for tan
                    upper = fxPosition;
                    derive();//add f'x
                    break;
                }

                case "csc": {
                    output.add("*");
                    output.add("-1");////* and -1 make equation negative
                    output.add("*");//multiplying by f'x
                    int fxPosition = upper - 1;//store position of fx so you can place it in the cot and derive
                    copy();//adds copy of whole equation to derivative (aka sec(x))
                    output.add("*");//multiplying by cot
                    output.add("cot");
                    upper = fxPosition;
                    copy();//add fx for tan
                    upper = fxPosition;
                    derive();//add f'x
                    break;
                }
            }

        }

        else if(value.equals("ln")) {
//          derivative of ln(x) = f'x / f(x)
            output.add("/");//Add division sign
            upper--;//move to fx
            int fxPosition = upper;
            copy();//First add the denominator of the derivative so fx
            upper = fxPosition;
            derive();//add the derivative;
        }

        else if(value.equals("log")) {
//          derivative of loga(x) = f'x / (f(x) * ln(a))
            output.add("/");//Add division sign
            upper--;//move to fx
            output.add("*");//add multiplication sign for denominator
            output.add("ln");//add ln of ln(a)
            int fxPosition = upper;//store fx position
            upper = upper - fxPosition;//moves to position that a is present within expression as logax is [a, x, log] in RPN
            copy();//copies A
            upper = fxPosition;
            copy();//add denominator of derivative
            upper = fxPosition;
            derive();//add the numerator

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

//    Required for chain rule operations; Copies the function
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

        else if(value.equals("ln")) {
            output.add("ln");
            upper--;
            copy();
        }

        else if(value.equals("log")) {
            output.add("log");
            upper--;
            copy();
            copy();
        }

        else if(Function.isTrig(value)) {
            output.add(value);
            upper--;
            copy();
        }

        else
            System.out.println("fail");
    }

    @Override
    public String toString() {
        return Arrays.toString(output.toArray());
    }

    public ArrayList rpn() {
        return output;
    }
}

