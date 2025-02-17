package com.magalu.application.use_cases.utils.output;

public class StatusFailed extends StatusOutput{
    private final static String FAILED = "FAILED";

    private StatusFailed(String status) {
        super(status);
    }

    public static StatusFailed create(){
        return new StatusFailed(FAILED);
    }

}
