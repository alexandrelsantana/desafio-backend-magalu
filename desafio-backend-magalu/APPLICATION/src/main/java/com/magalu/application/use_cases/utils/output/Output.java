package com.magalu.application.use_cases.utils.output;

import com.magalu.domain.validation.NotificationInterface;
import lombok.Getter;

@Getter
public abstract class Output <OUT>{
    OUT output;
    StatusOutput statusOutput;
    NotificationInterface notification;

    public Output(OUT output, StatusOutput statusOutput, NotificationInterface notification) {
        this.output = output;
        this.statusOutput = statusOutput;
        this.notification = notification;
    }

    public boolean hasError(){
        return notification.hasError();
    }
}
