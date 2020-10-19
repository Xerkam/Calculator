package expressionEvaluation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Class responsible for symbolically differentiating expressions inputted into Function.java and the GUI
 *  FIXME
 *  - base a logarithmic functions have issues properly differentiating currently
 *
 */


public class DerivationEngine {
    private ArrayList<String> input;
    private ArrayList<String> output = new ArrayList<>();
    private int upper;

    public DerivationEngine(Function func) {
        input = func.rpn();
        upper = input.size() - 1;
        derive();
        Collections.reverse(output); // derive() provides expression in Polish Notation rather than RPN so reverse it

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

        if(isConstant(value)) { // Constant Rule Case
            output.add("0");
            upper--;
        }

        else if(value.equals("x")) { // Power Rule Base Case
            output.add("1");
            upper--;
        }

        else if(value.equals("+")) { // Addition Case
            output.add("+");
            upper--;
            derive();
            derive();

        }

        else if(value.equals("-")) { // Subtraction Case
            output.add("-");
            upper--;
            derive();
            derive();

        }

        else if(Function.isTrig(value)) {
            switch(value) {
                // derivative of sin(f(x)) = f'(x) * cos(f(x))
                // sin(f(x)) in RPN = [f(x), sin]
                // Output in RPN = [ f'(x), f(x), cos, *]
                case "sin": {
                    output.add("*");
                    output.add("cos");
                    upper--; // Move from sin to f(x)
                    int fxLocation = upper; // Save location of f(x)
                    copy(); // Copy f(x)
                    upper = fxLocation; // Revert the focus back to f(x)
                    derive(); // get f'x
                    break;
                }

                // derivative of cos(f(x)) = f'(x) * - sin(f(x)))
                // reference sin
                case "cos": {
                    output.add("*");
                    output.add("-1");
                    output.add("*");
                    output.add("sin");
                    upper--;
                    int fxLocation = upper;
                    copy();
                    upper = fxLocation;
                    derive();
                    break;
                }

                // derivative of tan(f(x)) = f'(x) * sec^2(f(x))
                // tan(f(x)) in RPN = [f(x), tan]
                // Output in RPN = [ f'(x), f(x), sec, 2, ^, *]
                case "tan": {
                    output.add("*");
                    output.add("^");
                    output.add("2");
                    output.add("sec");
                    upper--;
                    int fxLocation = upper;
                    copy();
                    upper = fxLocation;
                    derive();
                    break;
                }

                // derivative of cot(f(x)) = f'(x) * - csc^2(f(x)))
                // reference cot
                case "cot": {
                    output.add("*");
                    output.add("-1");
                    output.add("*");
                    output.add("^");
                    output.add("2");
                    output.add("csc");
                    upper--;
                    int fxLocation = upper;
                    copy();
                    upper = fxLocation;
                    derive();
                    break;
                }

                // derivative of sec(f(x)) = f'(x) * sec(f(x)) * tan(f(x)
                // sec(f(x)) in RPN = [f(x), sec]
                // Output in RPN = [ f'(x), f(x), tan, *, f(x), sec, *]
                case "sec": {
                    output.add("*");
                    int fxPosition = upper - 1;
                    copy();
                    output.add("*");
                    output.add("tan");
                    upper = fxPosition;
                    copy();
                    upper = fxPosition;
                    derive();
                    break;
                }

                // derivative of csc(f(x)) = f'(x) * - csc(f(x))) * cot(f(x))
                // reference sec
                case "csc": {
                    output.add("*");
                    output.add("-1");
                    output.add("*");
                    int fxPosition = upper - 1;
                    copy();
                    output.add("*");
                    output.add("cot");
                    upper = fxPosition;
                    copy();
                    upper = fxPosition;
                    derive();
                    break;
                }
            }

        }

        else if(value.equals("ln")) {
//          derivative of ln(x) = f'x / f(x)
//          ln(f(x)) in RPN = [f(x), ln]
//          Output in RPN = [ f'(x), f(x), /]
            output.add("/");
            upper--;
            int fxPosition = upper;
            copy();
            upper = fxPosition;
            derive();
        }

        else if(value.equals("log")) {
//          derivative of loga(x) = f'x / (f(x) * ln(a))
//          loga(f(x)) in RPN = [ a, f(x), log]
//          Output in RPN = [ f'(x), a, ln, f(x), *, /]
            output.add("/");
            upper--;
            output.add("*");
            int fxPosition = upper;
            copy();
            output.add("ln");
            copy(); // After f(x) was copied, the rest of the expression is whatever a is composed of
            upper = fxPosition;
            derive();

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

    // Required for chain rule operations; Copies the function
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