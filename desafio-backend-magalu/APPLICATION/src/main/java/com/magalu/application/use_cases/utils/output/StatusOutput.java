package com.magalu.application.use_cases.utils.output;

import lombok.Getter;

@Getter
public abstract class StatusOutput {
    final String status;

    protected StatusOutput(String status) {
        this.status = status;
    }
}
