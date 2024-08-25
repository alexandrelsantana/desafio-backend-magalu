package com.magalu.domain.validation;

public interface NotificationInterface {
    void append(final Error error);
    void append(final String description, String message);
    boolean hasError();
    void logErrors(String id);
}
