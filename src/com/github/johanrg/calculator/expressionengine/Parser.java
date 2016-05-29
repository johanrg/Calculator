package com.github.johanrg.calculator.expressionengine;

/**
 * Created by Johan on 2016-05-28.
 */

import java.util.ArrayList;
import java.util.List;

class Parser {
    private final String text;
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

    private char peekAtChar() {
        return text.charAt(pos);
    }

    private void eatTheChar() {
        ++pos;
        ++column;
    }

    private char getAndEatTheChar() {
        char c = peekAtChar();
        eatTheChar();
        return c;
    }

    private void eatAllWhitespaces() {
        char c = peekAtChar();
        while (isWhitespace(c)) {
            if (isNewLine(c)) {
                column = 0;
                ++line;
            }
            eatTheChar();
            c = peekAtChar();
        }
    }

    private Token getNumber(boolean positiveNumber) throws ParserException {
        StringBuilder number = new StringBuilder();

        char c = peekAtChar();
        while (isNumber(c) || c == '.') {
            number.append(c);
            eatTheChar();
            c = peekAtChar();
        }

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
        for (; ; ) {
            char c = getAndEatTheChar();
            if (isWhitespace(c)) {
                eatAllWhitespaces();

            } else if (c == '+') {
                boolean mustBeOperator = (lastToken == TokenType.NUMBER);
                if (!mustBeOperator && isNumber(peekAtChar())) {
                    addToken(getNumber(true));
                } else {
                    addToken(new Token(TokenType.PLUS));
                }

            } else if (c == '-') {
                boolean mustBeOperator = (lastToken == TokenType.NUMBER);
                if (!mustBeOperator && isNumber(peekAtChar())) {
                    addToken(getNumber(false));
                } else {
                    addToken(new Token(TokenType.MINUS));
                }

            } else if (c == '*') {
                addToken(new Token(TokenType.MULTIPLY));

            } else if (c == '/') {
                addToken(new Token(TokenType.DIVIDE));

            } else if (c == '%') {
                addToken(new Token(TokenType.MODULUS));

            } else if (c == '^') {
                addToken(new Token(TokenType.CARET));

            } else if (c == '(') {
                addToken(new Token(TokenType.OPEN_PARENTHESES));

            } else if (c == ')') {
                addToken(new Token(TokenType.CLOSE_PARENTHESES));

            } else if (isNumber(c)) {
                addToken(getNumber(true));

            } else if (c == '\0') {
                break;

            } else {
                throw new ParserException(String.format("Syntax error: '%c' on line %d column %d", c, line, column));
            }
        }
    }

    Parser(String text) throws ParserException {
        // The added 0 to the end of the string makes us not having to test the if the string has reached it's end
        // everywhere. A valid character will always have at least a 0 that follows after it and this can be checked in
        // one place and only when no other valid characters are left.
        this.text = text + "\0";

        parse();

        for (Token token : tokens) {
            System.out.println(token.getType());
        }

    }
}
