package com.magalu.application.use_cases;

import com.magalu.application.use_cases.utils.output.Output;

public abstract class UseCase <IN, OUT>{
    public abstract OUT execute(IN input);
}
