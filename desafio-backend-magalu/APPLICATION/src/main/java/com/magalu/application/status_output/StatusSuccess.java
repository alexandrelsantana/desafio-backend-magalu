package com.magalu.application.status_output;

public class StatusSuccess extends StatusOutput{
    final static String SUCCESS = "SUCCESS";

    private StatusSuccess(String status) {
        super(status);
    }

    public static StatusSuccess createStatusSuccess(){
        return new StatusSuccess(SUCCESS);
    }
}
