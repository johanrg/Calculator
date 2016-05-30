package com.github.johanrg.expressionengine;

/**
 * Created by Johan 5/30/2016.
 */
public class AstExpressionNode {

    private final TokenType type;
    private final double number;
    private final AstExpressionNode leftExpression;
    private final AstExpressionNode rightExpression;

    AstExpressionNode(TokenType type, double number) {
        this.type = type;
        this.number = number;
        this.leftExpression = null;
        this.rightExpression = null;
    }

    AstExpressionNode(TokenType type, AstExpressionNode leftExpression, AstExpressionNode rightExpression) {
        this.type = type;
        this.number = 0;
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }
}
