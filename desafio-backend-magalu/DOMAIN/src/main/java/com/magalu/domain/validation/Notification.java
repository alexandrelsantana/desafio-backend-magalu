package com.magalu.domain.validation;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Getter
public class Notification implements NotificationInterface {
    private final List<Error> errors;

    private Notification(List<Error> errors) {
        this.errors = errors;
    }

    public static Notification createNotification(){
        return new Notification(new ArrayList<>());
    }

    @Override
    public void append(Error error) {
        this.errors.add(error);
    }

    @Override
    public void append(String description, String message) {
        this.append(new Error(description, message));
    }

    public void validation(){

    }

    @Override
    public boolean hasError(){
        return !errors.isEmpty();
    }

    public void logErrors(String id) {
        StringBuilder l = new StringBuilder();
        for(Error error : errors){
            l.append("***** Description: ")
                    .append(error.getDescription())
                    .append("\n***** Message:")
                    .append(error.getMessage())
                    .append("\n");
        }
        log.warn("\n******* Notification *******\n***** Id: {}\n{}\n**************\n", id, l.toString());
    }

}
