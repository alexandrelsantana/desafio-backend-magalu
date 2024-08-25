package com.magalu.application.use_cases;

import com.magalu.domain.validation.NotificationInterface;

public abstract class Output <OUT>{
    OUT output;
    NotificationInterface notification;

    public Output(OUT output, NotificationInterface notification) {
        this.output = output;
        this.notification = notification;
    }

    public boolean hasError(){
        return notification.hasError();
    }
}
