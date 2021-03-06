package com.github.johanrg.expressionengine;

/**
 * Created by Johan on 2016-05-28.
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Parser implements Iterable<Token> {
    private final String text;
    private int pos = 0;
    private int line = 1;
    private int column = 1;
    private TokenType lastToken = TokenType.NONE;
    private final List<Token> tokens = new ArrayList<>();

    private boolean isNumber(char c) {
        return (c >= '0' && c <= '9');
    }

    private boolean isAlpha(char c) {
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

    /**
     * This method is only used from the Constructor.
     *
     * @param c  Valid char from the current string.
     */
    private void eatAllWhitespaces(char c) {
        while (isWhitespace(c)) {
            if (isNewLine(c)) {
                column = 0;
                ++line;
            }
            eatTheChar();
            c = peekAtChar();
        }
    }

    /**
     * This method is only used from the Constructor.
     * Generates a number token.
     *
     * @param c Valid char from the current string.
     * @return Token
     */
    private Token getNumber(char c, boolean positiveNumber) throws ExpressionException {
        StringBuilder number = new StringBuilder();
        int storeColumn = column;

        while (isNumber(c) || c == '.') {
            eatTheChar();
            number.append(c);
            c = peekAtChar();
        }

        if (positiveNumber) {
            return new Token(TokenType.TYPE_DOUBLE, Double.parseDouble(number.toString()), line, storeColumn);
        } else {
            return new Token(TokenType.TYPE_DOUBLE, -Double.parseDouble(number.toString()), line, storeColumn);
        }
    }

    /**
     * This method is only used from the Constructor.
     * Generates an identifier token.
     *
     * @param c a valid alpha char
     * @return Token
     */
    private Token getIdentifier(char c) {
        StringBuilder identifier = new StringBuilder();
        int storeColumn = column;

        while (isAlpha(c)) {
            eatTheChar();
            identifier.append(c);
            c = peekAtChar();
        }

        return new Token(TokenType.IDENTIFIER, identifier.toString(), line, storeColumn);
    }

    /**
     * Just a helper method to keep track of previous token and add the current to the list of tokens.
     *
     * @param token  Token to add to the tokens list.
     */
    private void addToken(Token token) {
        tokens.add(token);
        lastToken = token.getType();
    }

    Parser(String text) throws ExpressionException {
        // The added 0 to the end of the string makes us not having to test the if the string has reached it's end
        // everywhere. A valid character will always have at least a 0 that follows after it and this can be checked in
        // one place and only when no other valid characters are left.
        this.text = text + "\0";

        for (; ; ) {
            char c = peekAtChar();
            if (isWhitespace(c)) {
                eatAllWhitespaces(c);

            } else if (c == '+') {
                eatTheChar();
                boolean mustBeOperator = (lastToken == TokenType.TYPE_DOUBLE);
                if (!mustBeOperator && isNumber(peekAtChar())) {
                    addToken(getNumber(c, true));
                } else {
                    addToken(new Token(TokenType.PLUS, line, column));
                }

            } else if (c == '-') {
                boolean mustBeOperator = (lastToken == TokenType.TYPE_DOUBLE);
                if (!mustBeOperator && isNumber(peekAtChar())) {
                    addToken(getNumber(c, false));
                } else {
                    addToken(new Token(TokenType.MINUS, line, column));
                }
                eatTheChar();

            } else if (c == '*') {
                addToken(new Token(TokenType.MULTIPLY, line, column));
                eatTheChar();

            } else if (c == '/') {
                addToken(new Token(TokenType.DIVIDE, line, column));
                eatTheChar();

            } else if (c == '%') {
                addToken(new Token(TokenType.MODULUS, line, column));
                eatTheChar();

            } else if (c == '^') {
                addToken(new Token(TokenType.CARET, line, column));
                eatTheChar();

            } else if (c == '(') {
                addToken(new Token(TokenType.OPEN_PARENTHESES, line, column));
                eatTheChar();

            } else if (c == ')') {
                addToken(new Token(TokenType.CLOSE_PARENTHESES, line, column));
                eatTheChar();

            } else if (c == '=') {
                addToken(new Token(TokenType.ASSIGNMENT, line, column));
                eatTheChar();

            } else if (isNumber(c)) {
                addToken(getNumber(c, true));

            } else if (isAlpha(c)) {
                addToken(getIdentifier(c));

            } else if (c == '\0') {
                break;

            } else {
                throw new ExpressionException(String.format("Syntax error: '%c' on line %d column %d", c, line, column));
            }
        }
    }

    @Override
    public Iterator<Token> iterator() {
        return tokens.iterator();
    }
}
