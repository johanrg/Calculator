package com.github.johanrg.calculator.expressionengine;

/**
 * Created by Johan on 2016-05-28.
 */

public class Expression {
    public Expression(String text) throws ParserException {
        Parser parse = new Parser(text);
    }
}
