package com.github.johanrg.expressionengine;

/**
 * Created by Johan 5/30/2016.
 */

class AstExpressionNode {
    private final Token token;
    private final AstExpressionNode leftExpression;
    private final AstExpressionNode rightExpression;

    Token getToken() {
        return token;
    }

    AstExpressionNode getLeftExpression() {
        return leftExpression;
    }

    AstExpressionNode getRightExpression() {
        return rightExpression;
    }

    AstExpressionNode(Token token) {
        this.token = token;
        this.leftExpression = null;
        this.rightExpression = null;
    }

    AstExpressionNode(Token token, AstExpressionNode leftExpression, AstExpressionNode rightExpression) {
        this.token = token;
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    @Override
    public String toString() {
        return token.getType().toString() + " " + Double.toString(token.getDouble());
    }
}
