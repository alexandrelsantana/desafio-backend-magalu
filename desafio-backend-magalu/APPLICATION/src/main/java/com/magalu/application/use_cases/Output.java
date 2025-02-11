package com.magalu.application.use_cases;

import com.magalu.domain.validation.NotificationInterface;
import lombok.Getter;

@Getter
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
