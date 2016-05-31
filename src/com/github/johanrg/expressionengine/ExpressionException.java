package com.github.johanrg.expressionengine;

import java.text.ParseException;

/**
 * Created by Johan on 2016-05-28.
 */
public class ExpressionException extends Exception {
    public ExpressionException(String message) {
        super(message);
    }

    public ExpressionException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
