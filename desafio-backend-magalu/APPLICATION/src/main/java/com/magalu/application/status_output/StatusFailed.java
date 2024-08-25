package com.magalu.application.status_output;

public class StatusFailed extends StatusOutput{
    private final String FAILED = "FAILED";

    protected StatusFailed(String status) {
        super(status);
    }

    private StatusFailed createStatusSuccess(){
        return new StatusFailed(this.FAILED);
    }
}
