package com.github.johanrg.calculator.expressionengine;

/**
 * Created by Johan on 2016-05-28.
 */

class Token {
    private TokenType type;
    private int intNumber;
    private double doubleNumber;
    private String identifier;

    TokenType getType() { return type; }
    int getInteger() { return intNumber; }
    double getDouble() { return doubleNumber; }
    String getIdentifier() { return identifier; }

    Token(TokenType type) {
        this.type = type;
    }

    Token(TokenType type, int number)
    {
        this(type);
        this.intNumber = number;
    }

    Token(TokenType type, double number) {
        this(type);
        this.doubleNumber = number;
    }

    Token(TokenType type, String identifier) {
        this(type);
        this.identifier = identifier;
    }
}
