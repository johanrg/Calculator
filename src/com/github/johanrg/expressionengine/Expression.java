package com.github.johanrg.expressionengine;

/**
 * Created by Johan on 2016-05-28.
 */

public class Expression {
    private Lexer lex;

    public Expression(String text) throws ExpressionException {
        Parser parse = new Parser(text);
        lex = new Lexer(parse);
    }

    public double getValue() {
        return lex.getValue();
    }
}
