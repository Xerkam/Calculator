package com.company;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.TreeVisitor;

import java.util.Arrays;


public class Main {

    public static void main(String[] args) {
        Function g = new Function("sin(3*x)*2+5");
        g.infixToPostfix();
        System.out.println(Arrays.toString(g.operator.toArray()));
        System.out.println(Arrays.toString(g.output.toArray()));

    }


}
