package com.github.johanrg.calculator.expressionengine;

/**
 * Created by Johan on 2016-05-28.
 */

import java.util.ArrayList;
import java.util.List;

class Parser {
    private final String text;
    private int charsRemaining;
    private int pos = 0;
    private int line = 1;
    private int column = 1;
    private TokenType lastToken = TokenType.NONE;
    private final List<Token> tokens = new ArrayList<>();

    private boolean isNumber(char c) {
        return (c >= '0' && c <= '9');
    }

    private boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private boolean isNewLine(char c) {
        return (c == '\n');
    }

    private boolean isWhitespace(char c) {
        return (c == ' ' || c == '\t' || c == '\r' || isNewLine(c));
    }

    private boolean isAValidStopForNumber(char c) {
        return isWhitespace(c) || c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == ')';
    }

    private char peekAtChar() {
        return text.charAt(pos);
    }

    private void eatTheChar() {
        ++pos;
        --charsRemaining;
        ++column;
    }

    private boolean hasNext() {
        return charsRemaining > 0;
    }

    private void eatAllWhitespaces() {
        while (hasNext() && isWhitespace(peekAtChar())) {
            if (isNewLine(peekAtChar())) {
                column = 0;
                ++line;
            }
            eatTheChar();
        }
    }

    private Token getNumber(boolean positiveNumber) throws ParserException {
        StringBuilder number = new StringBuilder();

        do {
            char c = peekAtChar();

            if (isNumber(c) || c == '.') {

                number.append(c);
            } else if (isAValidStopForNumber(c) && number.length() > 0) {
                break;
            } else {
                throw new ParserException(String.format("Expected a number on line %d column %d got '%c'", line, column, c));
            }
            eatTheChar();
        } while (hasNext());

        if (positiveNumber) {
            return new Token(TokenType.NUMBER, Double.parseDouble(number.toString()));
        } else {
            return new Token(TokenType.NUMBER, -Double.parseDouble(number.toString()));
        }
    }

    private void addToken(Token token) {
        tokens.add(token);
        lastToken = token.getType();
    }

    private void parse() throws ParserException {
        while (hasNext()) {
            char c = peekAtChar();
            if (isWhitespace(c)) {
                eatAllWhitespaces();

            } else if (c == '+') {
                eatTheChar();
                boolean mustBeOperator = (lastToken == TokenType.NUMBER);
                if (!mustBeOperator && hasNext() && isNumber(peekAtChar())) {
                    addToken(getNumber(true));
                } else {
                    addToken(new Token(TokenType.PLUS));
                }

            } else if (c == '-') {
                eatTheChar();
                boolean mustBeOperator = (lastToken == TokenType.NUMBER);
                if (!mustBeOperator && hasNext() && isNumber(peekAtChar())) {
                    addToken(getNumber(false));
                } else {
                    addToken(new Token(TokenType.MINUS));
                }

            } else if (c == '*') {
                eatTheChar();
                addToken(new Token(TokenType.MULTIPLY));

            } else if (c == '/') {
                eatTheChar();
                addToken(new Token(TokenType.DIVIDE));

            } else if (c == '%') {
                eatTheChar();
                addToken(new Token(TokenType.MODULUS));

            } else if (c == '^') {
                eatTheChar();
                addToken(new Token(TokenType.CARET));

            } else if (c == '(') {
                eatTheChar();
                addToken(new Token(TokenType.OPEN_PARENTHESES));

            } else if (c == ')') {
                eatTheChar();
                addToken(new Token(TokenType.CLOSE_PARENTHESES));

            } else if (isNumber(c)) {
                addToken(getNumber(true));
            } else {
                throw new ParserException(String.format("Syntax error: '%c' on line %d column %d", c, line, column));
            }
        }
    }

    Parser(String text) throws ParserException {
        this.text = text;
        this.charsRemaining = text.length();

        parse();

        for (Token token : tokens) {
            System.out.println(token.getType());
        }

    }
}
