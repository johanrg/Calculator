package com.github.johanrg.expressionengine;

/**
 * Created by Johan on 2016-05-28.
 */
enum TokenType {
    NONE(false, 0),
    TYPE_INT(false, 0),
    TYPE_DOUBLE(false, 0),
    IDENTIFIER(false, 0),

    OPEN_PARENTHESES(true, 1),
    CLOSE_PARENTHESES(true, 1),

    MULTIPLY(true, 4),
    DIVIDE(true, 4),
    MODULUS(true, 4),
    CARET(true, 4),

    PLUS(true, 5),
    MINUS(true, 5),

    ASSIGNMENT(true, 15);

    private final boolean operator;
    private final int precedence;

    TokenType(boolean isOperator, int precedence) {
        this.operator = isOperator;
        this.precedence = precedence;
    }

    boolean isOperator() {
        return operator;
    }

    boolean isDigit() {
        return this == TYPE_INT || this == TYPE_DOUBLE;
    }

    int getPrecedence() {
        return precedence;
    }
}
