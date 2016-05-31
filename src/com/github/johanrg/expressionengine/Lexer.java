package com.github.johanrg.expressionengine;

import java.util.Stack;

/**
 * Created by Johan on 5/30/2016.
 */
class Lexer {
    private final Parser parser;
    private double value;

    public double getValue() {
        return value;
    }

    Lexer(Parser parser) throws ExpressionException {
        this.parser = parser;
        lex(parser);
    }

    void lex(Parser parser) throws ExpressionException {
        AstExpressionNode result;
        result = parseExpression();
        result = solveExpression(result);
        value = result.getToken().getDouble();
    }

    private AstExpressionNode parseExpression() throws ExpressionException {
        Stack<Token> operatorStack = new Stack<>();
        Stack<AstExpressionNode> expressionStack = new Stack<>();

        for (Token token : parser) {
            if (token.getType() == TokenType.OPEN_PARENTHESES) {
                operatorStack.push(token);

            } else if (token.getType().isDigit()) {
                expressionStack.push(new AstExpressionNode(token));

            } else if (token.getType() == TokenType.CLOSE_PARENTHESES) {
                while (operatorStack.peek().getType() != TokenType.OPEN_PARENTHESES) {
                    Token operator = operatorStack.pop();

                    AstExpressionNode expRight = expressionStack.pop();
                    AstExpressionNode expLeft = expressionStack.pop();

                    expressionStack.push(new AstExpressionNode(operator, expLeft, expRight));
                }

                operatorStack.pop();

            } else if (token.getType().isOperator()) {
                while (operatorStack.size() > 0 &&
                        operatorStack.peek().getType().getPrecedence() >= token.getType().getPrecedence()) {
                    Token operator = operatorStack.pop();

                    // Careful, observe order
                    AstExpressionNode expRight = expressionStack.pop();
                    AstExpressionNode expLeft = expressionStack.pop();

                    expressionStack.push(new AstExpressionNode(operator, expLeft, expRight));
                }

                operatorStack.push(token);

            } else {
                throw new ExpressionException("Something went wrong.");
            }
        }

        while (operatorStack.size() > 0) {
            Token operator = operatorStack.pop();

            // Careful, observe order
            AstExpressionNode expRight = expressionStack.pop();
            AstExpressionNode expLeft = expressionStack.pop();

            expressionStack.push(new AstExpressionNode(operator, expLeft, expRight));
        }

        return expressionStack.peek();
    }

    private AstExpressionNode solveExpression(AstExpressionNode node) {
        if (node.getToken().getType().isDigit()) {
            return node;
        } else if (node.getToken().getType().isOperator()) {
            AstExpressionNode leftExpression = solveExpression(node.getLeftExpression());
            AstExpressionNode rightExpression = solveExpression(node.getRightExpression());

            double result = 0;
            switch (node.getToken().getType()) {
                case MULTIPLY:
                    result = leftExpression.getNumber() * rightExpression.getNumber();
                    break;
                case DIVIDE:
                    result = leftExpression.getNumber() / rightExpression.getNumber();
                    break;
                case MODULUS:
                    result = leftExpression.getNumber() % rightExpression.getNumber();
                    break;
                case CARET:
                    result = Math.pow(leftExpression.getNumber(), rightExpression.getNumber());
                    break;

                case PLUS:
                    result = leftExpression.getNumber() + rightExpression.getNumber();
                    break;
                case MINUS:
                    result = leftExpression.getNumber() - rightExpression.getNumber();
                    break;

                default:
                    assert false;
            }
            return new AstExpressionNode(new Token(TokenType.NUMBER, result, 0, 0));
        }

        return null;
    }
}