package com.magalu.application.use_cases.utils.status_output;

public class StatusSuccess extends StatusOutput{
    private final static String SUCCESS = "SUCCESS";

    private StatusSuccess(String status) {
        super(status);
    }

    public static StatusSuccess create(){
        return new StatusSuccess(SUCCESS);
    }
}
