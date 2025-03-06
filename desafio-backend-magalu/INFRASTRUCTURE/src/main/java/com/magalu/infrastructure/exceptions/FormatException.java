package com.magalu.infrastructure.exceptions;

import com.magalu.domain.validation.Error;

import java.util.List;

public class FormatException extends RuntimeException{
    protected final List<Error> errors;

    public FormatException(final String message, final List<Error> errors) {
        super(message);
        this.errors = errors;
    }

    public static FormatException create(final String message, final List<Error> errors) {
        return new FormatException(message, errors);
    }
}
