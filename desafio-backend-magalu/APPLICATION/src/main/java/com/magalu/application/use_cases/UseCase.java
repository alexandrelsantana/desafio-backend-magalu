package com.magalu.application.use_cases;

import com.magalu.application.use_cases.utils.output.Output;

public abstract class UseCase <IN>{
    public abstract Output execute(IN input);
}
