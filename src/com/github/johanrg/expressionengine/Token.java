package com.github.johanrg.expressionengine;

/**
 * Created by Johan on 2016-05-28.
 */

class Token {
    private TokenType type;
    private int intNumber;
    private double doubleNumber;
    private String identifier;
    private int lineNumber;
    private int columnNumber;

    TokenType getType() { return type; }
    int getInteger() { return intNumber; }
    double getDouble() { return doubleNumber; }
    String getIdentifier() { return identifier; }
    int getLineNumber() { return lineNumber; }
    int getColumnNumber() { return columnNumber; }

    Token(TokenType type, int line, int column) {
        this.type = type;
        this.lineNumber = line;
        this.columnNumber = column;
    }

    Token(TokenType type, int number, int line, int column)
    {
        this(type, line, column);
        this.intNumber = number;
    }

    Token(TokenType type, double number, int line, int column) {
        this(type, line, column);
        this.doubleNumber = number;
    }

    Token(TokenType type, String identifier, int line, int column) {
        this(type, line, column);
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder();
        info.append(type.toString());
        if (type == TokenType.NUMBER) {
            info.append(" " + Double.toString(doubleNumber));
        } else if (type == TokenType.IDENTIFIER) {
            info.append(" " + identifier);
        }
        info.append(" @" + Integer.toString(lineNumber) + "," + Integer.toString(columnNumber));

        return info.toString();
    }
}
