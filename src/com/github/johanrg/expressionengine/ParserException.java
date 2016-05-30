package com.github.johanrg.expressionengine;

import java.text.ParseException;

/**
 * Created by Johan on 2016-05-28.
 */
public class ParserException extends Exception {
    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
