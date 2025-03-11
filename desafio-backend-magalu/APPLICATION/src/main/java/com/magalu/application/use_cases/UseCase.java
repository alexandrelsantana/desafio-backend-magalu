package com.magalu.application.use_cases;

public abstract class UseCase <IN, OUT>{
    public abstract OUT execute(IN input);
}
