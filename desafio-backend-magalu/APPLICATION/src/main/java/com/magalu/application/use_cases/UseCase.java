package com.magalu.application.use_cases;

public abstract class UseCase <IN>{
    public abstract Output execute(IN input);
}
