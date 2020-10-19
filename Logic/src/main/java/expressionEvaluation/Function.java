package expressionEvaluation;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Stack;

public class Function {
    private Stack<String> operator = new Stack();
    private ArrayList<String> output = new ArrayList();
    private String equation;
    private DerivationEngine derive;
    private DerivationEngine secondDerive;
    public Function derivative;
    public Function secondDerivative;

    public Function(String equation) {
        this.equation = equation;
        this.infixToPostfix();
        this.derive = new DerivationEngine(this);
        this.derivative = new Function(this.derive.rpn());
        this.secondDerive = new DerivationEngine(this.derivative);
        this.secondDerivative = new Function(this.secondDerive.rpn());
    }

    public Function(ArrayList equation) {
        this.output = equation;
    }

    public static void main(String[] args) {
    }

    public static boolean isOperand(String token) {
        if (token.equals("x")) {
            return true;
        } else {
            try {
                double var1 = Double.parseDouble(token);
                return true;
            } catch (NullPointerException | NumberFormatException var3) {
                return false;
            }
        }
    }

    private int precedence(String x) {
        byte var3 = -1;
        switch(x.hashCode()) {
            case 42:
                if (x.equals("*")) {
                    var3 = 1;
                }
                break;
            case 43:
                if (x.equals("+")) {
                    var3 = 3;
                }
                break;
            case 45:
                if (x.equals("-")) {
                    var3 = 4;
                }
                break;
            case 47:
                if (x.equals("/")) {
                    var3 = 2;
                }
                break;
            case 94:
                if (x.equals("^")) {
                    var3 = 0;
                }
        }

        switch(var3) {
            case 0:
                return 3;
            case 1:
            case 2:
                return 2;
            case 3:
            case 4:
                return 1;
            default:
                return -1;
        }
    }

    public static boolean isOperator(String token) {
        byte var2 = -1;
        switch(token.hashCode()) {
            case 42:
                if (token.equals("*")) {
                    var2 = 1;
                }
                break;
            case 43:
                if (token.equals("+")) {
                    var2 = 3;
                }
                break;
            case 45:
                if (token.equals("-")) {
                    var2 = 4;
                }
                break;
            case 47:
                if (token.equals("/")) {
                    var2 = 2;
                }
                break;
            case 94:
                if (token.equals("^")) {
                    var2 = 0;
                }
        }

        switch(var2) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                return true;
            default:
                return false;
        }
    }

    public static boolean isTrig(String token) {
        byte var2 = -1;
        switch(token.hashCode()) {
            case 98695:
                if (token.equals("cos")) {
                    var2 = 3;
                }
                break;
            case 98696:
                if (token.equals("cot")) {
                    var2 = 5;
                }
                break;
            case 98803:
                if (token.equals("csc")) {
                    var2 = 4;
                }
                break;
            case 113745:
                if (token.equals("sec")) {
                    var2 = 1;
                }
                break;
            case 113880:
                if (token.equals("sin")) {
                    var2 = 0;
                }
                break;
            case 114593:
                if (token.equals("tan")) {
                    var2 = 2;
                }
        }

        switch(var2) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return true;
            default:
                return false;
        }
    }

    public static boolean isFunction(String token) {
        byte var2 = -1;
        switch(token.hashCode()) {
            case 3458:
                if (token.equals("ln")) {
                    var2 = 1;
                }
                break;
            case 107332:
                if (token.equals("log")) {
                    var2 = 0;
                }
        }

        switch(var2) {
            case 0:
            case 1:
                return true;
            default:
                return isTrig(token);
        }
    }

    private static boolean isUnaryOperator(String token) {
        return token.equals("log") ? false : isFunction(token);
    }

    private static boolean isBinaryOperator(String token) {
        return token.equals("log") ? true : isOperator(token);
    }

    private void infixToPostfix() {
        for(int i = 0; i < this.equation.length(); ++i) {
            String element = this.equation.substring(i, i + 1);
            if (isOperand(element)) {
                int e;
                for(e = i + 1; e < this.equation.length() && (isOperand(this.equation.substring(e, e + 1)) || this.equation.substring(e, e + 1).equals(".")); ++e) {
                }

                this.output.add(this.equation.substring(i, e));
                i = e - 1;
            } else if (i < this.equation.length() - 5 && isFunction(this.equation.substring(i, i + 3))) {
                this.operator.push(this.equation.substring(i, i + 3));
                i += 2;
            } else if (i < this.equation.length() - 4 && isFunction(this.equation.substring(i, i + 2))) {
                this.operator.push(this.equation.substring(i, i + 2));
                ++i;
            } else if (isOperator(element)) {
                while(!this.operator.isEmpty() && (isFunction((String)this.operator.peek()) || this.precedence((String)this.operator.peek()) > this.precedence(element) || this.precedence((String)this.operator.peek()) == this.precedence(element) && this.precedence((String)this.operator.peek()) != 3) && !((String)this.operator.peek()).equals("(")) {
                    this.output.add((String)this.operator.pop());
                }

                this.operator.push(element);
            } else if (element.equals("(")) {
                this.operator.push(element);
            } else if (element.equals(")")) {
                try {
                    while(!((String)this.operator.peek()).equals("(")) {
                        this.output.add((String)this.operator.pop());
                    }

                    if (((String)this.operator.peek()).equals("(")) {
                        this.operator.pop();
                    }
                } catch (EmptyStackException var5) {
                    System.out.println("Error: Mismatched Parentheses");
                }
            }
        }

        while(!this.operator.isEmpty()) {
            this.output.add((String)this.operator.pop());
        }

    }

    public double evaluate(double value) {
        Stack<Double> answer = new Stack();

        for(int i = 0; i < this.output.size(); ++i) {
            double y;
            double x;
            if (isOperator((String)this.output.get(i))) {
                y = (Double)answer.pop();
                x = (Double)answer.pop();
                answer.push(calculate(x, y, (String)this.output.get(i)));
            }

            if (isFunction((String)this.output.get(i))) {
                if (((String)this.output.get(i)).equals("log")) {
                    y = (Double)answer.pop();
                    x = (Double)answer.pop();
                    answer.push(calculate(x, y, (String)this.output.get(i)));
                } else {
                    y = (Double)answer.pop();
                    answer.push(calculate(y, (String)this.output.get(i)));
                }
            } else if (isOperand((String)this.output.get(i))) {
                if (((String)this.output.get(i)).equals("x")) {
                    answer.push(value);
                } else {
                    answer.push(Double.parseDouble((String)this.output.get(i)));
                }
            }
        }

        return (Double)answer.pop();
    }

    public static double calculate(Double x, Double y, String token) {
        byte var4 = -1;
        switch(token.hashCode()) {
            case 42:
                if (token.equals("*")) {
                    var4 = 3;
                }
                break;
            case 43:
                if (token.equals("+")) {
                    var4 = 0;
                }
                break;
            case 45:
                if (token.equals("-")) {
                    var4 = 1;
                }
                break;
            case 47:
                if (token.equals("/")) {
                    var4 = 2;
                }
                break;
            case 94:
                if (token.equals("^")) {
                    var4 = 4;
                }
                break;
            case 107332:
                if (token.equals("log")) {
                    var4 = 5;
                }
        }

        switch(var4) {
            case 0:
                return x + y;
            case 1:
                return x - y;
            case 2:
                return x / y;
            case 3:
                return x * y;
            case 4:
                return Math.pow(x, y);
            case 5:
                return Math.log10(y) / Math.log10(x);
            default:
                return 0.0D;
        }
    }

    public static double calculate(Double x, String token) {
        byte var3 = -1;
        switch(token.hashCode()) {
            case 3458:
                if (token.equals("ln")) {
                    var3 = 6;
                }
                break;
            case 98695:
                if (token.equals("cos")) {
                    var3 = 3;
                }
                break;
            case 98696:
                if (token.equals("cot")) {
                    var3 = 5;
                }
                break;
            case 98803:
                if (token.equals("csc")) {
                    var3 = 4;
                }
                break;
            case 113745:
                if (token.equals("sec")) {
                    var3 = 1;
                }
                break;
            case 113880:
                if (token.equals("sin")) {
                    var3 = 0;
                }
                break;
            case 114593:
                if (token.equals("tan")) {
                    var3 = 2;
                }
        }

        switch(var3) {
            case 0:
                return Math.sin(x);
            case 1:
                return 1.0D / Math.cos(x);
            case 2:
                return Math.tan(x);
            case 3:
                return Math.cos(x);
            case 4:
                return 1.0D / Math.sin(x);
            case 5:
                return 1.0D / Math.tan(x);
            case 6:
                return Math.log(x);
            default:
                return 0.0D;
        }
    }

    public double derivative(double value) {
        double h = 0.001D;
        double val = (this.evaluate(value + h) - this.evaluate(value - h)) / (2.0D * h);
        return Double.isNaN(val) ? val : (double)Math.round(val * 1.0E8D) / 1.0E8D;
    }

    private double unRoundedDerivative(double value) {
        double h = 0.001D;
        return (this.evaluate(value + h) - this.evaluate(value - h)) / (2.0D * h);
    }

    public double secondDerivative(double value) {
        double h = 0.001D;
        double val = (this.unRoundedDerivative(value + h) - this.unRoundedDerivative(value - h)) / (2.0D * h);
        return Double.isNaN(val) ? val : (double)Math.round(val * 1000000.0D) / 1000000.0D;
    }

    private double unRoundedSecondDerivative(double value) {
        double h = 0.001D;
        return (this.unRoundedDerivative(value + h) - this.unRoundedDerivative(value - h)) / (2.0D * h);
    }

    public double thirdDerivative(double value) {
        double h = 0.001D;
        double val = (this.unRoundedSecondDerivative(value + h) - this.unRoundedSecondDerivative(value - h)) / (2.0D * h);
        return Double.isNaN(val) ? val : (double)Math.round(val * 1000000.0D) / 1000000.0D;
    }

    public double integral(double boundA, double boundB) {
        double width = (boundB - boundA) / 20000.0D;
        double sum = 0.0D;

        for(double i = boundA; i < boundB; i += width) {
            sum += width * this.evaluate(i + width / 2.0D);
        }

        return sum;
    }

    public boolean isRemovableDiscontinuity(double value) {
        return Double.isNaN(this.evaluate(value));
    }

    public Double newtonsMethod(double xZero) {
        int maxIterations = 20;
        double tolerance = 0.01D;
        double xOne = 0.0D;
        boolean foundSolution = false;

        for(int i = 0; i < maxIterations; ++i) {
            double y = this.evaluate(xZero);
            double yPrime = this.derivative.evaluate(xZero);
            if (Math.abs(yPrime) < tolerance) {
                break;
            }

            xOne = xZero - y / yPrime;
            if (Math.abs(xOne - xZero) < Math.pow(10.0D, -7.0D)) {
                foundSolution = true;
            }

            xZero = xOne;
        }

        return foundSolution ? xOne : null;
    }

    public Double bisectionMethodDerivative(double a, double b) {
        double tolerance = Math.pow(10.0D, -7.0D);
        if (this.unRoundedDerivative(a) * this.unRoundedDerivative(b) >= 0.0D) {
            return null;
        } else {
            double c = a;

            while(b - a >= tolerance) {
                c = (a + b) / 2.0D;
                if (this.unRoundedDerivative(c) == 0.0D) {
                    break;
                }

                if (this.unRoundedDerivative(c) * this.unRoundedDerivative(a) < 0.0D) {
                    b = c;
                } else {
                    a = c;
                }
            }

            return c;
        }
    }

    public Double bisectionMethodSecondDerivative(double a, double b) {
        double tolerance = Math.pow(10.0D, -7.0D);
        if (this.unRoundedSecondDerivative(a) * this.unRoundedSecondDerivative(b) >= 0.0D) {
            return null;
        } else {
            double c = a;

            while(b - a >= tolerance) {
                c = (a + b) / 2.0D;
                if (this.unRoundedSecondDerivative(c) == 0.0D) {
                    break;
                }

                if (this.unRoundedSecondDerivative(c) * this.unRoundedSecondDerivative(a) < 0.0D) {
                    b = c;
                } else {
                    a = c;
                }
            }

            return c;
        }
    }

    public String toString() {
        return this.equation;
    }

    public void fundamentalTheoremOfCalc(double boundA, double boundB) {
        PrintStream var10000 = System.out;
        double var10001 = this.evaluate(boundB) - this.evaluate(boundA);
        var10000.println(var10001 + " approximately equals " + this.FTCintegral(boundA, boundB));
    }

    public double FTCintegral(double boundA, double boundB) {
        double width = (boundB - boundA) / 20000.0D;
        double sum = 0.0D;

        for(double i = boundA; i < boundB; i += width) {
            sum += width * this.derivative.evaluate(i + width / 2.0D);
        }

        return sum;
    }

    public ArrayList<String> rpn() {
        return this.output;
    }

    public String rpnToString() {
        return Arrays.toString(this.output.toArray());
    }
}
