//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DerivationEngine {
    private ArrayList<String> input;
    private ArrayList<String> output = new ArrayList();
    private int upper;

    DerivationEngine(Function func) {
        this.input = func.rpn();
        this.upper = this.input.size() - 1;
        this.derive();
        Collections.reverse(this.output);
    }

    private boolean isConstant(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException var3) {
            return false;
        }
    }

    private void derive() {
        String value = (String)this.input.get(this.upper);
        if (this.isConstant(value)) {
            this.output.add("0");
            --this.upper;
        } else if (value.equals("x")) {
            this.output.add("1");
            --this.upper;
        } else if (value.equals("+")) {
            this.output.add("+");
            --this.upper;
            this.derive();
            this.derive();
        } else if (value.equals("-")) {
            this.output.add("-");
            --this.upper;
            this.derive();
            this.derive();
        } else if (Function.isTrig(value)) {
            byte var3 = -1;
            switch(value.hashCode()) {
                case 98695:
                    if (value.equals("cos")) {
                        var3 = 1;
                    }
                    break;
                case 98696:
                    if (value.equals("cot")) {
                        var3 = 3;
                    }
                    break;
                case 98803:
                    if (value.equals("csc")) {
                        var3 = 5;
                    }
                    break;
                case 113745:
                    if (value.equals("sec")) {
                        var3 = 4;
                    }
                    break;
                case 113880:
                    if (value.equals("sin")) {
                        var3 = 0;
                    }
                    break;
                case 114593:
                    if (value.equals("tan")) {
                        var3 = 2;
                    }
            }

            int fxPosition;
            switch(var3) {
                case 0:
                    this.output.add("*");
                    this.output.add("cos");
                    --this.upper;
                    fxPosition = this.upper;
                    this.copy();
                    this.upper = fxPosition;
                    this.derive();
                    break;
                case 1:
                    this.output.add("*");
                    this.output.add("-1");
                    this.output.add("*");
                    this.output.add("sin");
                    --this.upper;
                    fxPosition = this.upper;
                    this.copy();
                    this.upper = fxPosition;
                    this.derive();
                    break;
                case 2:
                    this.output.add("*");
                    this.output.add("^");
                    this.output.add("2");
                    this.output.add("sec");
                    --this.upper;
                    fxPosition = this.upper;
                    this.copy();
                    this.upper = fxPosition;
                    this.derive();
                    break;
                case 3:
                    this.output.add("*");
                    this.output.add("-1");
                    this.output.add("*");
                    this.output.add("^");
                    this.output.add("2");
                    this.output.add("csc");
                    --this.upper;
                    fxPosition = this.upper;
                    this.copy();
                    this.upper = fxPosition;
                    this.derive();
                    break;
                case 4:
                    this.output.add("*");
                    fxPosition = this.upper - 1;
                    this.copy();
                    this.output.add("*");
                    this.output.add("tan");
                    this.upper = fxPosition;
                    this.copy();
                    this.upper = fxPosition;
                    this.derive();
                    break;
                case 5:
                    this.output.add("*");
                    this.output.add("-1");
                    this.output.add("*");
                    fxPosition = this.upper - 1;
                    this.copy();
                    this.output.add("*");
                    this.output.add("cot");
                    this.upper = fxPosition;
                    this.copy();
                    this.upper = fxPosition;
                    this.derive();
            }
        } else {
            int gxPosition;
            if (value.equals("ln")) {
                this.output.add("/");
                --this.upper;
                gxPosition = this.upper;
                this.copy();
                this.upper = gxPosition;
                this.derive();
            } else if (value.equals("log")) {
                this.output.add("/");
                --this.upper;
                this.output.add("*");
                this.output.add("ln");
                gxPosition = this.upper;
                this.upper -= gxPosition;
                this.copy();
                this.upper = gxPosition;
                this.copy();
                this.upper = gxPosition;
                this.derive();
            } else if (value.equals("*")) {
                this.output.add("+");
                this.output.add("*");
                --this.upper;
                gxPosition = this.upper;
                this.derive();
                this.copy();
                this.output.add("*");
                this.upper = gxPosition;
                this.copy();
                this.derive();
            } else if (value.equals("^")) {
                this.output.add("*");
                --this.upper;
                gxPosition = this.upper;
                this.copy();
                this.output.add("*");
                this.output.add("^");
                this.output.add("-");
                this.output.add("1");
                this.upper = gxPosition;
                this.copy();
                int fxPosition = this.upper;
                this.copy();
                this.upper = fxPosition;
                this.derive();
            } else if (value.equals("/")) {
                this.output.add("/");
                --this.upper;
                this.output.add("^");
                this.output.add("2");
                gxPosition = this.upper;
                this.copy();
                this.output.add("-");
                this.output.add("*");
                this.copy();
                this.upper = gxPosition;
                this.derive();
                this.output.add("*");
                this.derive();
                this.upper = gxPosition;
                this.copy();
            }
        }

    }

    private void copy() {
        String value = (String)this.input.get(this.upper);
        if (this.isConstant(value)) {
            this.output.add(value);
            --this.upper;
        } else if (value.equals("x")) {
            this.output.add("x");
            --this.upper;
        } else if (value.equals("+")) {
            this.output.add("+");
            --this.upper;
            this.copy();
            this.copy();
        } else if (value.equals("-")) {
            this.output.add("-");
            --this.upper;
            this.copy();
            this.copy();
        } else if (value.equals("*")) {
            this.output.add("*");
            --this.upper;
            this.copy();
            this.copy();
        } else if (value.equals("/")) {
            this.output.add("/");
            --this.upper;
            this.copy();
            this.copy();
        } else if (value.equals("^")) {
            this.output.add("^");
            --this.upper;
            this.copy();
            this.copy();
        } else if (value.equals("ln")) {
            this.output.add("ln");
            --this.upper;
            this.copy();
        } else if (value.equals("log")) {
            this.output.add("log");
            --this.upper;
            this.copy();
            this.copy();
        } else if (Function.isTrig(value)) {
            this.output.add(value);
            --this.upper;
            this.copy();
        } else {
            System.out.println("fail");
        }

    }

    public String toString() {
        return Arrays.toString(this.output.toArray());
    }

    public ArrayList rpn() {
        return this.output;
    }
}
