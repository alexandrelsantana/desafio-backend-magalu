package com.magalu.domain.validation;

import java.util.List;

public interface NotificationInterface {
    void append(final Error error);
    void append(final String description, String message);
    boolean hasError();
    List<Error> getErrors();
}
