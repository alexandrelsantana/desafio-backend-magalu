package com.magalu.domain.validation;

public abstract class Validator<T> {

    protected final Notification notification;

    protected Validator(Notification notification) {
        this.notification = notification;
    }

    public abstract void validate(T entity);
}
